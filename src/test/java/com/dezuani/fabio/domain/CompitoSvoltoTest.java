package com.dezuani.fabio.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.dezuani.fabio.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CompitoSvoltoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompitoSvolto.class);
        CompitoSvolto compitoSvolto1 = new CompitoSvolto();
        compitoSvolto1.setId(1L);
        CompitoSvolto compitoSvolto2 = new CompitoSvolto();
        compitoSvolto2.setId(compitoSvolto1.getId());
        assertThat(compitoSvolto1).isEqualTo(compitoSvolto2);
        compitoSvolto2.setId(2L);
        assertThat(compitoSvolto1).isNotEqualTo(compitoSvolto2);
        compitoSvolto1.setId(null);
        assertThat(compitoSvolto1).isNotEqualTo(compitoSvolto2);
    }
}
