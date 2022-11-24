package com.dezuani.fabio.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Alunno.
 */
@Entity
@Table(name = "alunno")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Alunno implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "cognome", nullable = false)
    private String cognome;

    @NotNull
    @Column(name = "data_nascita", nullable = false)
    private Instant dataNascita;

    @ManyToOne
    @JsonIgnoreProperties(value = { "alunni" }, allowSetters = true)
    private Classe classe;

    @OneToMany(mappedBy = "alunno")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "alunno", "compito" }, allowSetters = true)
    private Set<CompitoSvolto> compiti = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Alunno id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public Alunno nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return this.cognome;
    }

    public Alunno cognome(String cognome) {
        this.setCognome(cognome);
        return this;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public Instant getDataNascita() {
        return this.dataNascita;
    }

    public Alunno dataNascita(Instant dataNascita) {
        this.setDataNascita(dataNascita);
        return this;
    }

    public void setDataNascita(Instant dataNascita) {
        this.dataNascita = dataNascita;
    }

    public Classe getClasse() {
        return this.classe;
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
    }

    public Alunno classe(Classe classe) {
        this.setClasse(classe);
        return this;
    }

    public Set<CompitoSvolto> getCompiti() {
        return this.compiti;
    }

    public void setCompiti(Set<CompitoSvolto> compitoSvoltos) {
        if (this.compiti != null) {
            this.compiti.forEach(i -> i.setAlunno(null));
        }
        if (compitoSvoltos != null) {
            compitoSvoltos.forEach(i -> i.setAlunno(this));
        }
        this.compiti = compitoSvoltos;
    }

    public Alunno compitos(Set<CompitoSvolto> compitoSvoltos) {
        this.setCompiti(compitoSvoltos);
        return this;
    }

    public Alunno addCompito(CompitoSvolto compitoSvolto) {
        this.compiti.add(compitoSvolto);
        compitoSvolto.setAlunno(this);
        return this;
    }

    public Alunno removeCompito(CompitoSvolto compitoSvolto) {
        this.compiti.remove(compitoSvolto);
        compitoSvolto.setAlunno(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Alunno)) {
            return false;
        }
        return id != null && id.equals(((Alunno) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Alunno{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", cognome='" + getCognome() + "'" +
            ", dataNascita='" + getDataNascita() + "'" +
            "}";
    }
}
