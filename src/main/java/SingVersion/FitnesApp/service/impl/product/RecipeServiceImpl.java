package SingVersion.FitnesApp.service.impl.product;

import SingVersion.FitnesApp.core.dto.DetailsDto;
import SingVersion.FitnesApp.core.dto.PageDto;
import SingVersion.FitnesApp.core.dto.audit.CreateEntryDto;
import SingVersion.FitnesApp.core.dto.product.*;
import SingVersion.FitnesApp.core.dto.user.SaveUserDto;
import SingVersion.FitnesApp.core.exception.SingleErrorResponse;
import SingVersion.FitnesApp.entity.Ingredient;
import SingVersion.FitnesApp.entity.Product;
import SingVersion.FitnesApp.entity.Recipe;
import SingVersion.FitnesApp.repository.ProductRepository;
import SingVersion.FitnesApp.repository.RecipeRepository;
import SingVersion.FitnesApp.service.api.audit.AuditService;
import SingVersion.FitnesApp.service.api.product.RecipeService;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final ProductRepository productRepository;

    private final ConversionService conversionService;
    private final AuditService auditService;

    public RecipeServiceImpl(RecipeRepository recipeRepository, ProductRepository productRepository,
                             ConversionService conversionService, AuditService auditService) {
        this.recipeRepository = recipeRepository;
        this.productRepository = productRepository;
        this.conversionService = conversionService;
        this.auditService = auditService;
    }

    @Override
    @Transactional
    public SaveRecipeDto createRecipe(RecipeDto recipeDTO) {
        if (!recipeRepository.existsByTitle(recipeDTO.title())) {
            Recipe recipe = recipeRepository.save(
                    Objects.requireNonNull(conversionService.convert(recipeDTO, Recipe.class))
            );
            toAudit("Создание нового рецепта uuid:" + recipe.getUuid());
            return conversionService.convert(recipe, SaveRecipeDto.class);
        } else {
            throw new SingleErrorResponse("Рецепт с таким названием уже существует");
        }
    }


    @Override
    @ReadOnlyProperty
    public PageDto<SaveRecipeDto> getRecipePage(Pageable pageable) {
        Page<Recipe> allPage = recipeRepository.findAllPage(pageable);
        List<SaveRecipeDto> content = new ArrayList<>();
        for (Recipe recipeEntity : allPage) {
            List<SaveIngredientDto> ingredientDtos = new ArrayList<>();
            List<IngredientCPFCDto> ingredientCPFCDtos = countIngredientCPFC(recipeEntity.getIngredients());
            for (IngredientCPFCDto ingredientCPFCDto : ingredientCPFCDtos) {
                ingredientDtos.add(conversionService.convert(ingredientCPFCDto, SaveIngredientDto.class));
            }
            content.add(conversionService.convert(new RecipeConvertDto(ingredientDtos, recipeEntity,
                    countRecipeCPFC(ingredientCPFCDtos)), SaveRecipeDto.class));
        }
        toAudit("Просмотр всех рецептов");
        return conversionService.convert(new ConvertPageDto(allPage, content), PageDto.class);
    }

    @Override
    @Transactional
    public SaveRecipeDto updateRecipe(UUID uuid, LocalDateTime dtUpdate, RecipeDto recipeDTO) {
        Recipe recipeById = recipeRepository.findById(uuid).orElseThrow(()
                -> new SingleErrorResponse("Продукта с таким ид не существует"));
        if (Objects.equals(dtUpdate, recipeById.getDtUpdate())) {
            List<Ingredient> ingredients = new ArrayList<>();
            for (IngredientDto ingredientDto : recipeDTO.composition()) {
                ingredients.add(conversionService.convert(ingredientDto, Ingredient.class));
            }
            recipeById.setTitle(recipeDTO.title());
            recipeById.setIngredients(ingredients);
            toAudit("Изменение рецепта uuid:" + recipeById.getUuid());
            return conversionService.convert(recipeRepository.save(recipeById), SaveRecipeDto.class);
        } else {
            throw new SingleErrorResponse("Не верная версия");
        }
    }

    private List<IngredientCPFCDto> countIngredientCPFC(List<Ingredient> ingredientList) {
        List<IngredientCPFCDto> result = new ArrayList<>();
        for (Ingredient ingredient : ingredientList) {
            Product product = productRepository.findById(ingredient.getProduct()).orElseThrow(()
                    -> new SingleErrorResponse(("Продукта с данным ид нет")));
            int weight = ingredient.getWeight();
            double coif = (double) weight / product.getWeight();
            int calories = (int) (product.getCalories() * coif);
            double proteins = product.getProteins() * coif;
            double fats = product.getFats() * coif;
            double carbohydrates = product.getCarbohydrates() * coif;
            result.add(new IngredientCPFCDto(product.getUuid(), weight, calories, proteins, fats, carbohydrates));
        }
        return result;
    }

    private RecipeCPFCDto countRecipeCPFC(List<IngredientCPFCDto> ingredientCPFC) {
        int weight = 0;
        int calories = 0;
        double proteins = 0;
        double fats = 0;
        double carbohydrates = 0;
        for (IngredientCPFCDto dto : ingredientCPFC) {
            weight += dto.weight();
            calories += dto.calories();
            proteins += dto.proteins();
            fats += dto.fats();
            carbohydrates += dto.carbohydrates();
        }
        return new RecipeCPFCDto(weight, calories, proteins, fats, carbohydrates);
    }

    private void toAudit(String text) {
        DetailsDto principal = (DetailsDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        auditService.createEntry(new CreateEntryDto(principal.getUuid(), principal.getUsername(), principal.getFio(),
                principal.getRole(), text, 2));
    }
}