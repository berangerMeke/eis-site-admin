package com.eis.site.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A PageFormation.
 */
@Entity
@Table(name = "page_formation")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PageFormation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "titre")
    private String titre;

    @Column(name = "sous_titre")
    private String sousTitre;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PageFormation id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return this.titre;
    }

    public PageFormation titre(String titre) {
        this.setTitre(titre);
        return this;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getSousTitre() {
        return this.sousTitre;
    }

    public PageFormation sousTitre(String sousTitre) {
        this.setSousTitre(sousTitre);
        return this;
    }

    public void setSousTitre(String sousTitre) {
        this.sousTitre = sousTitre;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PageFormation)) {
            return false;
        }
        return id != null && id.equals(((PageFormation) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PageFormation{" +
            "id=" + getId() +
            ", titre='" + getTitre() + "'" +
            ", sousTitre='" + getSousTitre() + "'" +
            "}";
    }
}
