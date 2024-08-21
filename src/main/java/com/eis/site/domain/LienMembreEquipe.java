package com.eis.site.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A LienMembreEquipe.
 */
@Entity
@Table(name = "lien_membre_equipe")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LienMembreEquipe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "facebook")
    private String facebook;

    @Column(name = "whatsapp")
    private String whatsapp;

    @Column(name = "linkedin")
    private String linkedin;

    @Column(name = "twitter")
    private String twitter;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LienMembreEquipe id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFacebook() {
        return this.facebook;
    }

    public LienMembreEquipe facebook(String facebook) {
        this.setFacebook(facebook);
        return this;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getWhatsapp() {
        return this.whatsapp;
    }

    public LienMembreEquipe whatsapp(String whatsapp) {
        this.setWhatsapp(whatsapp);
        return this;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public String getLinkedin() {
        return this.linkedin;
    }

    public LienMembreEquipe linkedin(String linkedin) {
        this.setLinkedin(linkedin);
        return this;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public String getTwitter() {
        return this.twitter;
    }

    public LienMembreEquipe twitter(String twitter) {
        this.setTwitter(twitter);
        return this;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LienMembreEquipe)) {
            return false;
        }
        return id != null && id.equals(((LienMembreEquipe) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LienMembreEquipe{" +
            "id=" + getId() +
            ", facebook='" + getFacebook() + "'" +
            ", whatsapp='" + getWhatsapp() + "'" +
            ", linkedin='" + getLinkedin() + "'" +
            ", twitter='" + getTwitter() + "'" +
            "}";
    }
}
