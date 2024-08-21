package com.eis.site.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A PageAPropos.
 */
@Entity
@Table(name = "page_a_propos")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PageAPropos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Lob
    @Column(name = "sec_1_img_1")
    private byte[] sec1Img1;

    @Column(name = "sec_1_img_1_content_type")
    private String sec1Img1ContentType;

    @Column(name = "sec_1_desc_1")
    private String sec1Desc1;

    @Lob
    @Column(name = "sec_1_logo")
    private byte[] sec1Logo;

    @Column(name = "sec_1_logo_content_type")
    private String sec1LogoContentType;

    @Lob
    @Column(name = "sec_1_img_2")
    private byte[] sec1Img2;

    @Column(name = "sec_1_img_2_content_type")
    private String sec1Img2ContentType;

    @Column(name = "sec_1_desc_2")
    private String sec1Desc2;

    @Lob
    @Column(name = "sec_1_img_3")
    private byte[] sec1Img3;

    @Column(name = "sec_1_img_3_content_type")
    private String sec1Img3ContentType;

    @Column(name = "sec_1_desc_3")
    private String sec1Desc3;

    @Column(name = "sec_2_titre")
    private String sec2Titre;

    @Lob
    @Column(name = "sec_2_img")
    private byte[] sec2Img;

    @Column(name = "sec_2_img_content_type")
    private String sec2ImgContentType;

    @Column(name = "sec_2_sous_titre")
    private String sec2SousTitre;

    @Column(name = "sec_2_text")
    private String sec2Text;

    @Column(name = "sec_3_titre")
    private String sec3Titre;

    @Lob
    @Column(name = "sec_3_img")
    private byte[] sec3Img;

    @Column(name = "sec_3_img_content_type")
    private String sec3ImgContentType;

    @Column(name = "sec_3_sous_titre")
    private String sec3SousTitre;

    @Column(name = "sec_3_text")
    private String sec3Text;

    @Lob
    @Column(name = "sec_4_img")
    private byte[] sec4Img;

    @Column(name = "sec_4_img_content_type")
    private String sec4ImgContentType;

    @Column(name = "sec_4_desc_1")
    private String sec4Desc1;

    @Column(name = "sec_4_desc_2")
    private String sec4Desc2;

    @Column(name = "sec_4_desc_3")
    private String sec4Desc3;

    @Column(name = "sec_4_desc_4")
    private String sec4Desc4;

    @Column(name = "sec_5_titre")
    private String sec5Titre;

    @ManyToOne
    @JsonIgnoreProperties(value = { "pageAPropos", "pageEquipe1" }, allowSetters = true)
    private MembreEquipe membreEquipe1;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PageAPropos id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getSec1Img1() {
        return this.sec1Img1;
    }

    public PageAPropos sec1Img1(byte[] sec1Img1) {
        this.setSec1Img1(sec1Img1);
        return this;
    }

    public void setSec1Img1(byte[] sec1Img1) {
        this.sec1Img1 = sec1Img1;
    }

    public String getSec1Img1ContentType() {
        return this.sec1Img1ContentType;
    }

    public PageAPropos sec1Img1ContentType(String sec1Img1ContentType) {
        this.sec1Img1ContentType = sec1Img1ContentType;
        return this;
    }

    public void setSec1Img1ContentType(String sec1Img1ContentType) {
        this.sec1Img1ContentType = sec1Img1ContentType;
    }

    public String getSec1Desc1() {
        return this.sec1Desc1;
    }

    public PageAPropos sec1Desc1(String sec1Desc1) {
        this.setSec1Desc1(sec1Desc1);
        return this;
    }

    public void setSec1Desc1(String sec1Desc1) {
        this.sec1Desc1 = sec1Desc1;
    }

    public byte[] getSec1Logo() {
        return this.sec1Logo;
    }

    public PageAPropos sec1Logo(byte[] sec1Logo) {
        this.setSec1Logo(sec1Logo);
        return this;
    }

    public void setSec1Logo(byte[] sec1Logo) {
        this.sec1Logo = sec1Logo;
    }

    public String getSec1LogoContentType() {
        return this.sec1LogoContentType;
    }

    public PageAPropos sec1LogoContentType(String sec1LogoContentType) {
        this.sec1LogoContentType = sec1LogoContentType;
        return this;
    }

    public void setSec1LogoContentType(String sec1LogoContentType) {
        this.sec1LogoContentType = sec1LogoContentType;
    }

    public byte[] getSec1Img2() {
        return this.sec1Img2;
    }

    public PageAPropos sec1Img2(byte[] sec1Img2) {
        this.setSec1Img2(sec1Img2);
        return this;
    }

    public void setSec1Img2(byte[] sec1Img2) {
        this.sec1Img2 = sec1Img2;
    }

    public String getSec1Img2ContentType() {
        return this.sec1Img2ContentType;
    }

    public PageAPropos sec1Img2ContentType(String sec1Img2ContentType) {
        this.sec1Img2ContentType = sec1Img2ContentType;
        return this;
    }

    public void setSec1Img2ContentType(String sec1Img2ContentType) {
        this.sec1Img2ContentType = sec1Img2ContentType;
    }

    public String getSec1Desc2() {
        return this.sec1Desc2;
    }

    public PageAPropos sec1Desc2(String sec1Desc2) {
        this.setSec1Desc2(sec1Desc2);
        return this;
    }

    public void setSec1Desc2(String sec1Desc2) {
        this.sec1Desc2 = sec1Desc2;
    }

    public byte[] getSec1Img3() {
        return this.sec1Img3;
    }

    public PageAPropos sec1Img3(byte[] sec1Img3) {
        this.setSec1Img3(sec1Img3);
        return this;
    }

    public void setSec1Img3(byte[] sec1Img3) {
        this.sec1Img3 = sec1Img3;
    }

    public String getSec1Img3ContentType() {
        return this.sec1Img3ContentType;
    }

    public PageAPropos sec1Img3ContentType(String sec1Img3ContentType) {
        this.sec1Img3ContentType = sec1Img3ContentType;
        return this;
    }

    public void setSec1Img3ContentType(String sec1Img3ContentType) {
        this.sec1Img3ContentType = sec1Img3ContentType;
    }

    public String getSec1Desc3() {
        return this.sec1Desc3;
    }

    public PageAPropos sec1Desc3(String sec1Desc3) {
        this.setSec1Desc3(sec1Desc3);
        return this;
    }

    public void setSec1Desc3(String sec1Desc3) {
        this.sec1Desc3 = sec1Desc3;
    }

    public String getSec2Titre() {
        return this.sec2Titre;
    }

    public PageAPropos sec2Titre(String sec2Titre) {
        this.setSec2Titre(sec2Titre);
        return this;
    }

    public void setSec2Titre(String sec2Titre) {
        this.sec2Titre = sec2Titre;
    }

    public byte[] getSec2Img() {
        return this.sec2Img;
    }

    public PageAPropos sec2Img(byte[] sec2Img) {
        this.setSec2Img(sec2Img);
        return this;
    }

    public void setSec2Img(byte[] sec2Img) {
        this.sec2Img = sec2Img;
    }

    public String getSec2ImgContentType() {
        return this.sec2ImgContentType;
    }

    public PageAPropos sec2ImgContentType(String sec2ImgContentType) {
        this.sec2ImgContentType = sec2ImgContentType;
        return this;
    }

    public void setSec2ImgContentType(String sec2ImgContentType) {
        this.sec2ImgContentType = sec2ImgContentType;
    }

    public String getSec2SousTitre() {
        return this.sec2SousTitre;
    }

    public PageAPropos sec2SousTitre(String sec2SousTitre) {
        this.setSec2SousTitre(sec2SousTitre);
        return this;
    }

    public void setSec2SousTitre(String sec2SousTitre) {
        this.sec2SousTitre = sec2SousTitre;
    }

    public String getSec2Text() {
        return this.sec2Text;
    }

    public PageAPropos sec2Text(String sec2Text) {
        this.setSec2Text(sec2Text);
        return this;
    }

    public void setSec2Text(String sec2Text) {
        this.sec2Text = sec2Text;
    }

    public String getSec3Titre() {
        return this.sec3Titre;
    }

    public PageAPropos sec3Titre(String sec3Titre) {
        this.setSec3Titre(sec3Titre);
        return this;
    }

    public void setSec3Titre(String sec3Titre) {
        this.sec3Titre = sec3Titre;
    }

    public byte[] getSec3Img() {
        return this.sec3Img;
    }

    public PageAPropos sec3Img(byte[] sec3Img) {
        this.setSec3Img(sec3Img);
        return this;
    }

    public void setSec3Img(byte[] sec3Img) {
        this.sec3Img = sec3Img;
    }

    public String getSec3ImgContentType() {
        return this.sec3ImgContentType;
    }

    public PageAPropos sec3ImgContentType(String sec3ImgContentType) {
        this.sec3ImgContentType = sec3ImgContentType;
        return this;
    }

    public void setSec3ImgContentType(String sec3ImgContentType) {
        this.sec3ImgContentType = sec3ImgContentType;
    }

    public String getSec3SousTitre() {
        return this.sec3SousTitre;
    }

    public PageAPropos sec3SousTitre(String sec3SousTitre) {
        this.setSec3SousTitre(sec3SousTitre);
        return this;
    }

    public void setSec3SousTitre(String sec3SousTitre) {
        this.sec3SousTitre = sec3SousTitre;
    }

    public String getSec3Text() {
        return this.sec3Text;
    }

    public PageAPropos sec3Text(String sec3Text) {
        this.setSec3Text(sec3Text);
        return this;
    }

    public void setSec3Text(String sec3Text) {
        this.sec3Text = sec3Text;
    }

    public byte[] getSec4Img() {
        return this.sec4Img;
    }

    public PageAPropos sec4Img(byte[] sec4Img) {
        this.setSec4Img(sec4Img);
        return this;
    }

    public void setSec4Img(byte[] sec4Img) {
        this.sec4Img = sec4Img;
    }

    public String getSec4ImgContentType() {
        return this.sec4ImgContentType;
    }

    public PageAPropos sec4ImgContentType(String sec4ImgContentType) {
        this.sec4ImgContentType = sec4ImgContentType;
        return this;
    }

    public void setSec4ImgContentType(String sec4ImgContentType) {
        this.sec4ImgContentType = sec4ImgContentType;
    }

    public String getSec4Desc1() {
        return this.sec4Desc1;
    }

    public PageAPropos sec4Desc1(String sec4Desc1) {
        this.setSec4Desc1(sec4Desc1);
        return this;
    }

    public void setSec4Desc1(String sec4Desc1) {
        this.sec4Desc1 = sec4Desc1;
    }

    public String getSec4Desc2() {
        return this.sec4Desc2;
    }

    public PageAPropos sec4Desc2(String sec4Desc2) {
        this.setSec4Desc2(sec4Desc2);
        return this;
    }

    public void setSec4Desc2(String sec4Desc2) {
        this.sec4Desc2 = sec4Desc2;
    }

    public String getSec4Desc3() {
        return this.sec4Desc3;
    }

    public PageAPropos sec4Desc3(String sec4Desc3) {
        this.setSec4Desc3(sec4Desc3);
        return this;
    }

    public void setSec4Desc3(String sec4Desc3) {
        this.sec4Desc3 = sec4Desc3;
    }

    public String getSec4Desc4() {
        return this.sec4Desc4;
    }

    public PageAPropos sec4Desc4(String sec4Desc4) {
        this.setSec4Desc4(sec4Desc4);
        return this;
    }

    public void setSec4Desc4(String sec4Desc4) {
        this.sec4Desc4 = sec4Desc4;
    }

    public String getSec5Titre() {
        return this.sec5Titre;
    }

    public PageAPropos sec5Titre(String sec5Titre) {
        this.setSec5Titre(sec5Titre);
        return this;
    }

    public void setSec5Titre(String sec5Titre) {
        this.sec5Titre = sec5Titre;
    }

    public MembreEquipe getMembreEquipe1() {
        return this.membreEquipe1;
    }

    public void setMembreEquipe1(MembreEquipe membreEquipe) {
        this.membreEquipe1 = membreEquipe;
    }

    public PageAPropos membreEquipe1(MembreEquipe membreEquipe) {
        this.setMembreEquipe1(membreEquipe);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PageAPropos)) {
            return false;
        }
        return id != null && id.equals(((PageAPropos) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PageAPropos{" +
            "id=" + getId() +
            ", sec1Img1='" + getSec1Img1() + "'" +
            ", sec1Img1ContentType='" + getSec1Img1ContentType() + "'" +
            ", sec1Desc1='" + getSec1Desc1() + "'" +
            ", sec1Logo='" + getSec1Logo() + "'" +
            ", sec1LogoContentType='" + getSec1LogoContentType() + "'" +
            ", sec1Img2='" + getSec1Img2() + "'" +
            ", sec1Img2ContentType='" + getSec1Img2ContentType() + "'" +
            ", sec1Desc2='" + getSec1Desc2() + "'" +
            ", sec1Img3='" + getSec1Img3() + "'" +
            ", sec1Img3ContentType='" + getSec1Img3ContentType() + "'" +
            ", sec1Desc3='" + getSec1Desc3() + "'" +
            ", sec2Titre='" + getSec2Titre() + "'" +
            ", sec2Img='" + getSec2Img() + "'" +
            ", sec2ImgContentType='" + getSec2ImgContentType() + "'" +
            ", sec2SousTitre='" + getSec2SousTitre() + "'" +
            ", sec2Text='" + getSec2Text() + "'" +
            ", sec3Titre='" + getSec3Titre() + "'" +
            ", sec3Img='" + getSec3Img() + "'" +
            ", sec3ImgContentType='" + getSec3ImgContentType() + "'" +
            ", sec3SousTitre='" + getSec3SousTitre() + "'" +
            ", sec3Text='" + getSec3Text() + "'" +
            ", sec4Img='" + getSec4Img() + "'" +
            ", sec4ImgContentType='" + getSec4ImgContentType() + "'" +
            ", sec4Desc1='" + getSec4Desc1() + "'" +
            ", sec4Desc2='" + getSec4Desc2() + "'" +
            ", sec4Desc3='" + getSec4Desc3() + "'" +
            ", sec4Desc4='" + getSec4Desc4() + "'" +
            ", sec5Titre='" + getSec5Titre() + "'" +
            "}";
    }
}
