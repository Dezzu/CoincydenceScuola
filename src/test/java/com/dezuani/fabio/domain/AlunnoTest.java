package com.dezuani.fabio.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.dezuani.fabio.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AlunnoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Alunno.class);
        Alunno alunno1 = new Alunno();
        alunno1.setId(1L);
        Alunno alunno2 = new Alunno();
        alunno2.setId(alunno1.getId());
        assertThat(alunno1).isEqualTo(alunno2);
        alunno2.setId(2L);
        assertThat(alunno1).isNotEqualTo(alunno2);
        alunno1.setId(null);
        assertThat(alunno1).isNotEqualTo(alunno2);
    }
}
