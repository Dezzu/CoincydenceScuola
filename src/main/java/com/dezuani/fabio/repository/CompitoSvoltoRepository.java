package com.dezuani.fabio.repository;

import com.dezuani.fabio.domain.CompitoSvolto;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CompitoSvolto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompitoSvoltoRepository extends JpaRepository<CompitoSvolto, Long> {}
