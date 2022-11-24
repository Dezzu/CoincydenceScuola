package com.dezuani.fabio.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * A DTO for the {@link com.dezuani.fabio.domain.Classe} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ClasseDTO implements Serializable {

    private Long id;

    @Size(min = 1, max = 1)
    @NotNull
    private String sezione;

    @Min(1)
    @Max(5)
    @NotNull
    private Integer anno;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSezione() {
        return sezione;
    }

    public void setSezione(String sezione) {
        this.sezione = sezione;
    }

    public Integer getAnno() {
        return anno;
    }

    public void setAnno(Integer anno) {
        this.anno = anno;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClasseDTO)) {
            return false;
        }

        ClasseDTO classeDTO = (ClasseDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, classeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClasseDTO{" +
            "id=" + getId() +
            ", sezione='" + getSezione() + "'" +
            ", anno=" + getAnno() +
            "}";
    }
}
