package com.eis.site.repository;

import com.eis.site.domain.PageAccueil;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PageAccueil entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PageAccueilRepository extends JpaRepository<PageAccueil, Long> {}
