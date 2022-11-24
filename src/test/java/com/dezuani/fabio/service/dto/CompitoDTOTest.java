package com.dezuani.fabio.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dezuani.fabio.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CompitoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompitoDTO.class);
        CompitoDTO compitoDTO1 = new CompitoDTO();
        compitoDTO1.setId(1L);
        CompitoDTO compitoDTO2 = new CompitoDTO();
        assertThat(compitoDTO1).isNotEqualTo(compitoDTO2);
        compitoDTO2.setId(compitoDTO1.getId());
        assertThat(compitoDTO1).isEqualTo(compitoDTO2);
        compitoDTO2.setId(2L);
        assertThat(compitoDTO1).isNotEqualTo(compitoDTO2);
        compitoDTO1.setId(null);
        assertThat(compitoDTO1).isNotEqualTo(compitoDTO2);
    }
}
