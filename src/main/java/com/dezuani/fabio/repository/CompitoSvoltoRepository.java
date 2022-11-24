package com.dezuani.fabio.repository;

import com.dezuani.fabio.domain.Compito;
import com.dezuani.fabio.domain.CompitoSvolto;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CompitoSvolto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompitoSvoltoRepository extends JpaRepository<CompitoSvolto, Long> {
    List<CompitoSvolto> findAllByAlunnoId(Long alunnoId);

    List<CompitoSvolto> findAllByCompitoId(Long compitoId);

    Integer countByCompitoId(Long compitoId);

    Integer countByAlunnoId(Long id);
}
