package com.dezuani.fabio.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * A DTO for the {@link com.dezuani.fabio.domain.CompitoSvolto} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CompitoSvoltoDTO implements Serializable {

    private Long id;

    @Min(1)
    @Max(10)
    @NotNull
    private Double voto;

    @Size(max = 500)
    private String note;

    private AlunnoDTO alunno;

    private CompitoDTO compito;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getVoto() {
        return voto;
    }

    public void setVoto(Double voto) {
        this.voto = voto;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public AlunnoDTO getAlunno() {
        return alunno;
    }

    public void setAlunno(AlunnoDTO alunno) {
        this.alunno = alunno;
    }

    public CompitoDTO getCompito() {
        return compito;
    }

    public void setCompito(CompitoDTO compito) {
        this.compito = compito;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompitoSvoltoDTO)) {
            return false;
        }

        CompitoSvoltoDTO compitoSvoltoDTO = (CompitoSvoltoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, compitoSvoltoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompitoSvoltoDTO{" +
            "id=" + getId() +
            ", voto=" + getVoto() +
            ", note='" + getNote() + "'" +
            ", alunno=" + getAlunno() +
            ", compito=" + getCompito() +
            "}";
    }
}
