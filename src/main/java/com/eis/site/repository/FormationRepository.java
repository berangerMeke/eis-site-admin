package com.eis.site.repository;

import com.eis.site.domain.Formation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Formation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FormationRepository extends JpaRepository<Formation, Long> {

    //Rechercher formation par categorie

    List<Formation> findByCategorie(String categorie);

    //Repertorier les formations par categorie

    Long countByCategorie(String categorie);

    

}
