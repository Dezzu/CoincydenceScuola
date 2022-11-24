package com.dezuani.fabio.repository;

import com.dezuani.fabio.domain.Compito;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Compito entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompitoRepository extends JpaRepository<Compito, Long> {}
