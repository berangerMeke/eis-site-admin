package com.eis.site.repository;

import com.eis.site.domain.NavBar;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the NavBar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NavBarRepository extends JpaRepository<NavBar, Long> {}
