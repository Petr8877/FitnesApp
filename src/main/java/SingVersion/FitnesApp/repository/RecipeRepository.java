package SingVersion.FitnesApp.repository;

import SingVersion.FitnesApp.entity.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, UUID> {

    boolean existsByTitle(String title);

    @Query("select c from Recipe c")
    Page<Recipe> findAllPage(Pageable pageable);
}
