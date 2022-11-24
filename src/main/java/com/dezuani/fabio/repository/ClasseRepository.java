package com.dezuani.fabio.repository;

import com.dezuani.fabio.domain.Classe;
import com.dezuani.fabio.service.ClasseService;
import java.util.Optional;
import javax.swing.text.html.Option;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Classe entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClasseRepository extends JpaRepository<Classe, Long> {
    Optional<Classe> findByAnnoAndSezione(Integer anno, String sezione);

    Optional<Classe> findByAnnoAndSezioneAndIdNot(Integer anno, String sezione, Long id);

    Optional<Classe> deleteByAnnoAndSezione(Integer anno, String sezione);
}
