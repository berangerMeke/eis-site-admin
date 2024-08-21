package com.eis.site.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A Formation.
 */
@Entity
@Table(name = "formation")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Formation implements Serializable {

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

    @Column(name = "texte")
    private String texte;

    @Column(name = "cout")
    private Float cout;

    @Column(name = "duree")
    private Integer duree;

    @Column(name = "categorie")
    private String categorie;

    @Column(name = "lien")
    private String lien;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Formation id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public Formation nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public byte[] getPhoto() {
        return this.photo;
    }

    public Formation photo(byte[] photo) {
        this.setPhoto(photo);
        return this;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return this.photoContentType;
    }

    public Formation photoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
        return this;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public String getTexte() {
        return this.texte;
    }

    public Formation texte(String texte) {
        this.setTexte(texte);
        return this;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    public Float getCout() {
        return this.cout;
    }

    public Formation cout(Float cout) {
        this.setCout(cout);
        return this;
    }

    public void setCout(Float cout) {
        this.cout = cout;
    }

    public Integer getDuree() {
        return this.duree;
    }

    public Formation duree(Integer duree) {
        this.setDuree(duree);
        return this;
    }

    public void setDuree(Integer duree) {
        this.duree = duree;
    }

    public String getCategorie() {
        return this.categorie;
    }

    public Formation categorie(String categorie) {
        this.setCategorie(categorie);
        return this;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getLien() {
        return this.lien;
    }

    public Formation lien(String lien) {
        this.setLien(lien);
        return this;
    }

    public void setLien(String lien) {
        this.lien = lien;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Formation)) {
            return false;
        }
        return id != null && id.equals(((Formation) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Formation{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", photoContentType='" + getPhotoContentType() + "'" +
            ", texte='" + getTexte() + "'" +
            ", cout=" + getCout() +
            ", duree=" + getDuree() +
            ", categorie='" + getCategorie() + "'" +
            ", lien='" + getLien() + "'" +
            "}";
    }
}
