package com.dezuani.fabio.repository;

import com.dezuani.fabio.domain.Alunno;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Alunno entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AlunnoRepository extends JpaRepository<Alunno, Long> {
    List<Alunno> findAllByClasseAnnoAndClasseSezione(Integer anno, String sezione);

    Integer countByClasseAnnoAndClasseSezione(Integer anno, String sezione);
}
