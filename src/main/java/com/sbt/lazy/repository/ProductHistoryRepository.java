package com.sbt.lazy.repository;
import com.sbt.lazy.domain.ProductHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProductHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductHistoryRepository extends JpaRepository<ProductHistory, Long> {

}
