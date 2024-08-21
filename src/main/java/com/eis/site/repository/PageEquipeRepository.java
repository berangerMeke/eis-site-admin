package com.eis.site.repository;

import com.eis.site.domain.PageEquipe;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PageEquipe entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PageEquipeRepository extends JpaRepository<PageEquipe, Long> {}
