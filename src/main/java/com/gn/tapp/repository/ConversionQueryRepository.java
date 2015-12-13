package com.gn.tapp.repository;

import com.gn.tapp.domain.ConversionQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Spring Data JPA repository for the ConversionQuery entity.
 */
public interface ConversionQueryRepository extends JpaRepository<ConversionQuery, Long> {

    @Query("select conversionQuery from ConversionQuery conversionQuery where conversionQuery.owner.login = ?#{principal.username}")
    List<ConversionQuery> findByOwnerIsCurrentUser();

    List<ConversionQuery> findTop10ByOwnerLoginOrderByCreatedOnDesc(String login);
}
