package com.dezuani.fabio.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ClasseMapperTest {

    private ClasseMapper classeMapper;

    @BeforeEach
    public void setUp() {
        classeMapper = new ClasseMapperImpl();
    }
}
