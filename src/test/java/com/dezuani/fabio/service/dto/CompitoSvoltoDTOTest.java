package com.dezuani.fabio.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dezuani.fabio.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CompitoSvoltoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompitoSvoltoDTO.class);
        CompitoSvoltoDTO compitoSvoltoDTO1 = new CompitoSvoltoDTO();
        compitoSvoltoDTO1.setId(1L);
        CompitoSvoltoDTO compitoSvoltoDTO2 = new CompitoSvoltoDTO();
        assertThat(compitoSvoltoDTO1).isNotEqualTo(compitoSvoltoDTO2);
        compitoSvoltoDTO2.setId(compitoSvoltoDTO1.getId());
        assertThat(compitoSvoltoDTO1).isEqualTo(compitoSvoltoDTO2);
        compitoSvoltoDTO2.setId(2L);
        assertThat(compitoSvoltoDTO1).isNotEqualTo(compitoSvoltoDTO2);
        compitoSvoltoDTO1.setId(null);
        assertThat(compitoSvoltoDTO1).isNotEqualTo(compitoSvoltoDTO2);
    }
}
