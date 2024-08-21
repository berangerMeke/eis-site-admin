package com.eis.site.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A PageService.
 */
@Entity
@Table(name = "page_service")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PageService implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "titre")
    private String titre;

    @Column(name = "soustitre")
    private String soustitre;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PageService id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return this.titre;
    }

    public PageService titre(String titre) {
        this.setTitre(titre);
        return this;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getSoustitre() {
        return this.soustitre;
    }

    public PageService soustitre(String soustitre) {
        this.setSoustitre(soustitre);
        return this;
    }

    public void setSoustitre(String soustitre) {
        this.soustitre = soustitre;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PageService)) {
            return false;
        }
        return id != null && id.equals(((PageService) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PageService{" +
            "id=" + getId() +
            ", titre='" + getTitre() + "'" +
            ", soustitre='" + getSoustitre() + "'" +
            "}";
    }
}
