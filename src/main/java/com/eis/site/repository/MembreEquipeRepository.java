package com.eis.site.repository;

import com.eis.site.domain.MembreEquipe;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MembreEquipe entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MembreEquipeRepository extends JpaRepository<MembreEquipe, Long> {}
