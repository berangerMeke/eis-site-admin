package com.eis.site.repository;

import com.eis.site.domain.Partenaires;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Partenaires entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartenairesRepository extends JpaRepository<Partenaires, Long> {}
