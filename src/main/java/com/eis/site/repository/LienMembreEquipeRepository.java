package com.eis.site.repository;

import com.eis.site.domain.LienMembreEquipe;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LienMembreEquipe entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LienMembreEquipeRepository extends JpaRepository<LienMembreEquipe, Long> {}
