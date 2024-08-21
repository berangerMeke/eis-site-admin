package com.eis.site.repository;

import com.eis.site.domain.PageAPropos;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PageAPropos entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PageAProposRepository extends JpaRepository<PageAPropos, Long> {}
