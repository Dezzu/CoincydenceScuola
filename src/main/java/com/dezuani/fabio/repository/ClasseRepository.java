package com.dezuani.fabio.repository;

import com.dezuani.fabio.domain.Classe;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Classe entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClasseRepository extends JpaRepository<Classe, Long> {}
