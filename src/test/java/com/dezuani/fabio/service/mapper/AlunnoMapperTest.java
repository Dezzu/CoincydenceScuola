package com.dezuani.fabio.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AlunnoMapperTest {

    private AlunnoMapper alunnoMapper;

    @BeforeEach
    public void setUp() {
        alunnoMapper = new AlunnoMapperImpl();
    }
}
