package com.dezuani.fabio.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CompitoSvolto.
 */
@Entity
@Table(name = "compito_svolto")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CompitoSvolto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "voto")
    private Double voto;

    @Column(name = "note")
    private String note;

    @ManyToOne
    @JsonIgnoreProperties(value = { "classe", "compiti" }, allowSetters = true)
    private Alunno alunno;

    @ManyToOne
    @JsonIgnoreProperties(value = { "alunni" }, allowSetters = true)
    private Compito compito;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CompitoSvolto id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getVoto() {
        return this.voto;
    }

    public CompitoSvolto voto(Double voto) {
        this.setVoto(voto);
        return this;
    }

    public void setVoto(Double voto) {
        this.voto = voto;
    }

    public String getNote() {
        return this.note;
    }

    public CompitoSvolto note(String note) {
        this.setNote(note);
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Alunno getAlunno() {
        return this.alunno;
    }

    public void setAlunno(Alunno alunno) {
        this.alunno = alunno;
    }

    public CompitoSvolto alunno(Alunno alunno) {
        this.setAlunno(alunno);
        return this;
    }

    public Compito getCompito() {
        return this.compito;
    }

    public void setCompito(Compito compito) {
        this.compito = compito;
    }

    public CompitoSvolto compito(Compito compito) {
        this.setCompito(compito);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompitoSvolto)) {
            return false;
        }
        return id != null && id.equals(((CompitoSvolto) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompitoSvolto{" +
            "id=" + getId() +
            ", voto=" + getVoto() +
            ", note='" + getNote() + "'" +
            "}";
    }
}
