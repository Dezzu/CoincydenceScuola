package com.dezuani.fabio.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Classe.
 */
@Entity
@Table(name = "classe")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Classe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "sezione")
    private String sezione;

    @Column(name = "anno")
    private Integer anno;

    @OneToMany(mappedBy = "classe")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "classe", "compitos" }, allowSetters = true)
    private Set<Alunno> alunnos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Classe id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSezione() {
        return this.sezione;
    }

    public Classe sezione(String sezione) {
        this.setSezione(sezione);
        return this;
    }

    public void setSezione(String sezione) {
        this.sezione = sezione;
    }

    public Integer getAnno() {
        return this.anno;
    }

    public Classe anno(Integer anno) {
        this.setAnno(anno);
        return this;
    }

    public void setAnno(Integer anno) {
        this.anno = anno;
    }

    public Set<Alunno> getAlunnos() {
        return this.alunnos;
    }

    public void setAlunnos(Set<Alunno> alunnos) {
        if (this.alunnos != null) {
            this.alunnos.forEach(i -> i.setClasse(null));
        }
        if (alunnos != null) {
            alunnos.forEach(i -> i.setClasse(this));
        }
        this.alunnos = alunnos;
    }

    public Classe alunnos(Set<Alunno> alunnos) {
        this.setAlunnos(alunnos);
        return this;
    }

    public Classe addAlunno(Alunno alunno) {
        this.alunnos.add(alunno);
        alunno.setClasse(this);
        return this;
    }

    public Classe removeAlunno(Alunno alunno) {
        this.alunnos.remove(alunno);
        alunno.setClasse(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Classe)) {
            return false;
        }
        return id != null && id.equals(((Classe) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Classe{" +
            "id=" + getId() +
            ", sezione='" + getSezione() + "'" +
            ", anno=" + getAnno() +
            "}";
    }
}
