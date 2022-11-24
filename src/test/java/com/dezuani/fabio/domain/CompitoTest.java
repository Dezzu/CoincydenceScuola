package com.dezuani.fabio.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.dezuani.fabio.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CompitoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Compito.class);
        Compito compito1 = new Compito();
        compito1.setId(1L);
        Compito compito2 = new Compito();
        compito2.setId(compito1.getId());
        assertThat(compito1).isEqualTo(compito2);
        compito2.setId(2L);
        assertThat(compito1).isNotEqualTo(compito2);
        compito1.setId(null);
        assertThat(compito1).isNotEqualTo(compito2);
    }
}
