package com.dezuani.fabio.service.dto;

import com.dezuani.fabio.domain.enumeration.Materia;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.NotNull;

/**
 * A DTO for the {@link com.dezuani.fabio.domain.Compito} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CompitoDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant data;

    @NotNull
    private Materia materia;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getData() {
        return data;
    }

    public void setData(Instant data) {
        this.data = data;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompitoDTO)) {
            return false;
        }

        CompitoDTO compitoDTO = (CompitoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, compitoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompitoDTO{" +
            "id=" + getId() +
            ", data='" + getData() + "'" +
            ", materia='" + getMateria() + "'" +
            "}";
    }
}
