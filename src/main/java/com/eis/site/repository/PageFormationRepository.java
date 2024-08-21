package com.eis.site.repository;

import com.eis.site.domain.PageFormation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PageFormation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PageFormationRepository extends JpaRepository<PageFormation, Long> {}
