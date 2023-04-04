package SingVersion.FitnesApp.repository;

import SingVersion.FitnesApp.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends CrudRepository<Product, UUID> {

    boolean existsByTitle(String title);

    @Query("select c from Product c")
    Page<Product> findAllPage(Pageable pageable);
}
