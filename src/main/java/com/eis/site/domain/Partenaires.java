package com.eis.site.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Partenaires.
 */
@Entity
@Table(name = "partenaires")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Partenaires implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Lob
    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "photo_content_type")
    private String photoContentType;

    @Column(name = "description")
    private String description;

    @Column(name = "lien")
    private String lien;

    @OneToMany(mappedBy = "partenaires1")
    @JsonIgnoreProperties(value = { "partenaires1" }, allowSetters = true)
    private Set<PageAccueil> pageAccueils = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Partenaires id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public Partenaires nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public byte[] getPhoto() {
        return this.photo;
    }

    public Partenaires photo(byte[] photo) {
        this.setPhoto(photo);
        return this;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return this.photoContentType;
    }

    public Partenaires photoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
        return this;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public String getDescription() {
        return this.description;
    }

    public Partenaires description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLien() {
        return this.lien;
    }

    public Partenaires lien(String lien) {
        this.setLien(lien);
        return this;
    }

    public void setLien(String lien) {
        this.lien = lien;
    }

    public Set<PageAccueil> getPageAccueils() {
        return this.pageAccueils;
    }

    public void setPageAccueils(Set<PageAccueil> pageAccueils) {
        if (this.pageAccueils != null) {
            this.pageAccueils.forEach(i -> i.setPartenaires1(null));
        }
        if (pageAccueils != null) {
            pageAccueils.forEach(i -> i.setPartenaires1(this));
        }
        this.pageAccueils = pageAccueils;
    }

    public Partenaires pageAccueils(Set<PageAccueil> pageAccueils) {
        this.setPageAccueils(pageAccueils);
        return this;
    }

    public Partenaires addPageAccueil(PageAccueil pageAccueil) {
        this.pageAccueils.add(pageAccueil);
        pageAccueil.setPartenaires1(this);
        return this;
    }

    public Partenaires removePageAccueil(PageAccueil pageAccueil) {
        this.pageAccueils.remove(pageAccueil);
        pageAccueil.setPartenaires1(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Partenaires)) {
            return false;
        }
        return id != null && id.equals(((Partenaires) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Partenaires{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", photoContentType='" + getPhotoContentType() + "'" +
            ", description='" + getDescription() + "'" +
            ", lien='" + getLien() + "'" +
            "}";
    }
}
