package SingVersion.FitnesApp.repository;

import SingVersion.FitnesApp.entity.Audit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface AuditRepository extends CrudRepository<Audit, UUID> {

    @Query("select c from Audit c")
    Page<Audit> findAllPage(Pageable pageable);
}
