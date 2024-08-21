package com.eis.site.repository;

import com.eis.site.domain.PageService;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PageService entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PageServiceRepository extends JpaRepository<PageService, Long> {}
