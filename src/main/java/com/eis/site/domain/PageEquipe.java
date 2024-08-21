package com.eis.site.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A PageEquipe.
 */
@Entity
@Table(name = "page_equipe")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PageEquipe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "sec_1_img")
    private String sec1Img;

    @Column(name = "sec_1_titre")
    private String sec1Titre;

    @Column(name = "sec_1_desc")
    private String sec1Desc;

    @OneToMany(mappedBy = "pageEquipe1")
    @JsonIgnoreProperties(value = { "pageAPropos", "pageEquipe1" }, allowSetters = true)
    private Set<MembreEquipe> membreEquipes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PageEquipe id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSec1Img() {
        return this.sec1Img;
    }

    public PageEquipe sec1Img(String sec1Img) {
        this.setSec1Img(sec1Img);
        return this;
    }

    public void setSec1Img(String sec1Img) {
        this.sec1Img = sec1Img;
    }

    public String getSec1Titre() {
        return this.sec1Titre;
    }

    public PageEquipe sec1Titre(String sec1Titre) {
        this.setSec1Titre(sec1Titre);
        return this;
    }

    public void setSec1Titre(String sec1Titre) {
        this.sec1Titre = sec1Titre;
    }

    public String getSec1Desc() {
        return this.sec1Desc;
    }

    public PageEquipe sec1Desc(String sec1Desc) {
        this.setSec1Desc(sec1Desc);
        return this;
    }

    public void setSec1Desc(String sec1Desc) {
        this.sec1Desc = sec1Desc;
    }

    public Set<MembreEquipe> getMembreEquipes() {
        return this.membreEquipes;
    }

    public void setMembreEquipes(Set<MembreEquipe> membreEquipes) {
        if (this.membreEquipes != null) {
            this.membreEquipes.forEach(i -> i.setPageEquipe1(null));
        }
        if (membreEquipes != null) {
            membreEquipes.forEach(i -> i.setPageEquipe1(this));
        }
        this.membreEquipes = membreEquipes;
    }

    public PageEquipe membreEquipes(Set<MembreEquipe> membreEquipes) {
        this.setMembreEquipes(membreEquipes);
        return this;
    }

    public PageEquipe addMembreEquipe(MembreEquipe membreEquipe) {
        this.membreEquipes.add(membreEquipe);
        membreEquipe.setPageEquipe1(this);
        return this;
    }

    public PageEquipe removeMembreEquipe(MembreEquipe membreEquipe) {
        this.membreEquipes.remove(membreEquipe);
        membreEquipe.setPageEquipe1(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PageEquipe)) {
            return false;
        }
        return id != null && id.equals(((PageEquipe) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PageEquipe{" +
            "id=" + getId() +
            ", sec1Img='" + getSec1Img() + "'" +
            ", sec1Titre='" + getSec1Titre() + "'" +
            ", sec1Desc='" + getSec1Desc() + "'" +
            "}";
    }
}
