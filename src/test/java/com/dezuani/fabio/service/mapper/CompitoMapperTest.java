package com.dezuani.fabio.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CompitoMapperTest {

    private CompitoMapper compitoMapper;

    @BeforeEach
    public void setUp() {
        compitoMapper = new CompitoMapperImpl();
    }
}
