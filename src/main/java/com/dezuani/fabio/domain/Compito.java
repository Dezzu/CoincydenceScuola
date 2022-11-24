package com.dezuani.fabio.domain;

import com.dezuani.fabio.domain.enumeration.Materia;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Compito.
 */
@Entity
@Table(name = "compito")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Compito implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "data")
    private Instant data;

    @Enumerated(EnumType.STRING)
    @Column(name = "materia")
    private Materia materia;

    @OneToMany(mappedBy = "compito")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "alunno", "compito" }, allowSetters = true)
    private Set<CompitoSvolto> alunni = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Compito id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getData() {
        return this.data;
    }

    public Compito data(Instant data) {
        this.setData(data);
        return this;
    }

    public void setData(Instant data) {
        this.data = data;
    }

    public Materia getMateria() {
        return this.materia;
    }

    public Compito materia(Materia materia) {
        this.setMateria(materia);
        return this;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public Set<CompitoSvolto> getAlunni() {
        return this.alunni;
    }

    public void setAlunni(Set<CompitoSvolto> compitoSvoltos) {
        if (this.alunni != null) {
            this.alunni.forEach(i -> i.setCompito(null));
        }
        if (compitoSvoltos != null) {
            compitoSvoltos.forEach(i -> i.setCompito(this));
        }
        this.alunni = compitoSvoltos;
    }

    public Compito alunnos(Set<CompitoSvolto> compitoSvoltos) {
        this.setAlunni(compitoSvoltos);
        return this;
    }

    public Compito addAlunno(CompitoSvolto compitoSvolto) {
        this.alunni.add(compitoSvolto);
        compitoSvolto.setCompito(this);
        return this;
    }

    public Compito removeAlunno(CompitoSvolto compitoSvolto) {
        this.alunni.remove(compitoSvolto);
        compitoSvolto.setCompito(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Compito)) {
            return false;
        }
        return id != null && id.equals(((Compito) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Compito{" +
            "id=" + getId() +
            ", data='" + getData() + "'" +
            ", materia='" + getMateria() + "'" +
            "}";
    }
}
