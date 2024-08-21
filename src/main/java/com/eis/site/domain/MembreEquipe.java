package com.eis.site.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A MembreEquipe.
 */
@Entity
@Table(name = "membre_equipe")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MembreEquipe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "adresse_mail")
    private String adresseMail;

    @Column(name = "certification")
    private String certification;

    @Column(name = "diplome")
    private String diplome;

    @Column(name = "niveau_etude")
    private String niveauEtude;

    @Lob
    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "photo_content_type")
    private String photoContentType;

    @Column(name = "description")
    private String description;

    @Column(name = "message")
    private String message;

    @Column(name = "fonction")
    private String fonction;

    @OneToMany(mappedBy = "membreEquipe1")
    @JsonIgnoreProperties(value = { "membreEquipe1" }, allowSetters = true)
    private Set<PageAPropos> pageAPropos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "membreEquipes" }, allowSetters = true)
    private PageEquipe pageEquipe1;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MembreEquipe id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public MembreEquipe nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public MembreEquipe prenom(String prenom) {
        this.setPrenom(prenom);
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public MembreEquipe telephone(String telephone) {
        this.setTelephone(telephone);
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAdresseMail() {
        return this.adresseMail;
    }

    public MembreEquipe adresseMail(String adresseMail) {
        this.setAdresseMail(adresseMail);
        return this;
    }

    public void setAdresseMail(String adresseMail) {
        this.adresseMail = adresseMail;
    }

    public String getCertification() {
        return this.certification;
    }

    public MembreEquipe certification(String certification) {
        this.setCertification(certification);
        return this;
    }

    public void setCertification(String certification) {
        this.certification = certification;
    }

    public String getDiplome() {
        return this.diplome;
    }

    public MembreEquipe diplome(String diplome) {
        this.setDiplome(diplome);
        return this;
    }

    public void setDiplome(String diplome) {
        this.diplome = diplome;
    }

    public String getNiveauEtude() {
        return this.niveauEtude;
    }

    public MembreEquipe niveauEtude(String niveauEtude) {
        this.setNiveauEtude(niveauEtude);
        return this;
    }

    public void setNiveauEtude(String niveauEtude) {
        this.niveauEtude = niveauEtude;
    }

    public byte[] getPhoto() {
        return this.photo;
    }

    public MembreEquipe photo(byte[] photo) {
        this.setPhoto(photo);
        return this;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return this.photoContentType;
    }

    public MembreEquipe photoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
        return this;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public String getDescription() {
        return this.description;
    }

    public MembreEquipe description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMessage() {
        return this.message;
    }

    public MembreEquipe message(String message) {
        this.setMessage(message);
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFonction() {
        return this.fonction;
    }

    public MembreEquipe fonction(String fonction) {
        this.setFonction(fonction);
        return this;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    public Set<PageAPropos> getPageAPropos() {
        return this.pageAPropos;
    }

    public void setPageAPropos(Set<PageAPropos> pageAPropos) {
        if (this.pageAPropos != null) {
            this.pageAPropos.forEach(i -> i.setMembreEquipe1(null));
        }
        if (pageAPropos != null) {
            pageAPropos.forEach(i -> i.setMembreEquipe1(this));
        }
        this.pageAPropos = pageAPropos;
    }

    public MembreEquipe pageAPropos(Set<PageAPropos> pageAPropos) {
        this.setPageAPropos(pageAPropos);
        return this;
    }

    public MembreEquipe addPageAPropos(PageAPropos pageAPropos) {
        this.pageAPropos.add(pageAPropos);
        pageAPropos.setMembreEquipe1(this);
        return this;
    }

    public MembreEquipe removePageAPropos(PageAPropos pageAPropos) {
        this.pageAPropos.remove(pageAPropos);
        pageAPropos.setMembreEquipe1(null);
        return this;
    }

    public PageEquipe getPageEquipe1() {
        return this.pageEquipe1;
    }

    public void setPageEquipe1(PageEquipe pageEquipe) {
        this.pageEquipe1 = pageEquipe;
    }

    public MembreEquipe pageEquipe1(PageEquipe pageEquipe) {
        this.setPageEquipe1(pageEquipe);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MembreEquipe)) {
            return false;
        }
        return id != null && id.equals(((MembreEquipe) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MembreEquipe{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", adresseMail='" + getAdresseMail() + "'" +
            ", certification='" + getCertification() + "'" +
            ", diplome='" + getDiplome() + "'" +
            ", niveauEtude='" + getNiveauEtude() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", photoContentType='" + getPhotoContentType() + "'" +
            ", description='" + getDescription() + "'" +
            ", message='" + getMessage() + "'" +
            ", fonction='" + getFonction() + "'" +
            "}";
    }
}
