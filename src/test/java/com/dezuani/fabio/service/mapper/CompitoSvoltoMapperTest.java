package com.dezuani.fabio.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CompitoSvoltoMapperTest {

    private CompitoSvoltoMapper compitoSvoltoMapper;

    @BeforeEach
    public void setUp() {
        compitoSvoltoMapper = new CompitoSvoltoMapperImpl();
    }
}
