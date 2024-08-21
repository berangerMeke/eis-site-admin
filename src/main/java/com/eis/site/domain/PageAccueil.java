package com.eis.site.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A PageAccueil.
 */
@Entity
@Table(name = "page_accueil")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PageAccueil implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "sec_1_titre")
    private String sec1Titre;

    @Column(name = "sec_1_texte")
    private String sec1Texte;

    @Lob
    @Column(name = "sec_image")
    private byte[] secImage;

    @Column(name = "sec_image_content_type")
    private String secImageContentType;

    @Column(name = "sec_1_bouton")
    private String sec1Bouton;

    @Column(name = "sec_2_titre")
    private String sec2Titre;

    @Column(name = "sec_2_text")
    private String sec2Text;

    @Column(name = "sec_2_bouton")
    private String sec2Bouton;

    @Column(name = "sec_3_titre")
    private String sec3Titre;

    @Lob
    @Column(name = "sec_3_blog_1_img")
    private byte[] sec3Blog1Img;

    @Column(name = "sec_3_blog_1_img_content_type")
    private String sec3Blog1ImgContentType;

    @Lob
    @Column(name = "sec_3_blog_2_img")
    private byte[] sec3Blog2Img;

    @Column(name = "sec_3_blog_2_img_content_type")
    private String sec3Blog2ImgContentType;

    @Lob
    @Column(name = "sec_3_blog_3_img")
    private byte[] sec3Blog3Img;

    @Column(name = "sec_3_blog_3_img_content_type")
    private String sec3Blog3ImgContentType;

    @Column(name = "sec_3_blog_1_sous_titre")
    private String sec3Blog1SousTitre;

    @Column(name = "sec_3_blog_2_sous_titre")
    private String sec3Blog2SousTitre;

    @Column(name = "sec_3_blog_3_sous_titre")
    private String sec3Blog3SousTitre;

    @Column(name = "sec_3_blog_1_text")
    private String sec3Blog1Text;

    @Column(name = "sec_3_blog_2_text")
    private String sec3Blog2Text;

    @Column(name = "sec_3_blog_3_text")
    private String sec3Blog3Text;

    @Column(name = "sec_4_titre")
    private String sec4Titre;

    @Column(name = "sec_4_text")
    private String sec4Text;

    @Lob
    @Column(name = "sec_4_blog_1_img")
    private byte[] sec4Blog1Img;

    @Column(name = "sec_4_blog_1_img_content_type")
    private String sec4Blog1ImgContentType;

    @Column(name = "sec_4_blog_1_sous_titre")
    private String sec4Blog1SousTitre;

    @Column(name = "sec_4_blog_2_sous_titre")
    private String sec4Blog2SousTitre;

    @Column(name = "sec_4_blog_1_text")
    private String sec4Blog1Text;

    @Column(name = "sec_4_blog_2_text")
    private String sec4Blog2Text;

    @Column(name = "sec_5_titre")
    private String sec5Titre;

    @Lob
    @Column(name = "sec_5_blog_1_img")
    private byte[] sec5Blog1Img;

    @Column(name = "sec_5_blog_1_img_content_type")
    private String sec5Blog1ImgContentType;

    @Lob
    @Column(name = "sec_5_blog_2_img")
    private byte[] sec5Blog2Img;

    @Column(name = "sec_5_blog_2_img_content_type")
    private String sec5Blog2ImgContentType;

    @Lob
    @Column(name = "sec_5_blog_3_img")
    private byte[] sec5Blog3Img;

    @Column(name = "sec_5_blog_3_img_content_type")
    private String sec5Blog3ImgContentType;

    @Lob
    @Column(name = "sec_5_blog_4_img")
    private byte[] sec5Blog4Img;

    @Column(name = "sec_5_blog_4_img_content_type")
    private String sec5Blog4ImgContentType;

    @Lob
    @Column(name = "sec_5_blog_5_img")
    private byte[] sec5Blog5Img;

    @Column(name = "sec_5_blog_5_img_content_type")
    private String sec5Blog5ImgContentType;

    @Lob
    @Column(name = "sec_5_blog_6_img")
    private byte[] sec5Blog6Img;

    @Column(name = "sec_5_blog_6_img_content_type")
    private String sec5Blog6ImgContentType;

    @Lob
    @Column(name = "sec_5_blog_7_img")
    private byte[] sec5Blog7Img;

    @Column(name = "sec_5_blog_7_img_content_type")
    private String sec5Blog7ImgContentType;

    @Lob
    @Column(name = "sec_5_blog_1_sous_titre")
    private byte[] sec5Blog1SousTitre;

    @Column(name = "sec_5_blog_1_sous_titre_content_type")
    private String sec5Blog1SousTitreContentType;

    @Lob
    @Column(name = "sec_5_blog_2_sous_titre")
    private byte[] sec5Blog2SousTitre;

    @Column(name = "sec_5_blog_2_sous_titre_content_type")
    private String sec5Blog2SousTitreContentType;

    @Lob
    @Column(name = "sec_5_blog_3_sous_titre")
    private byte[] sec5Blog3SousTitre;

    @Column(name = "sec_5_blog_3_sous_titre_content_type")
    private String sec5Blog3SousTitreContentType;

    @Lob
    @Column(name = "sec_5_blog_4_sous_titre")
    private byte[] sec5Blog4SousTitre;

    @Column(name = "sec_5_blog_4_sous_titre_content_type")
    private String sec5Blog4SousTitreContentType;

    @Lob
    @Column(name = "sec_5_blog_5_sous_titre")
    private byte[] sec5Blog5SousTitre;

    @Column(name = "sec_5_blog_5_sous_titre_content_type")
    private String sec5Blog5SousTitreContentType;

    @Lob
    @Column(name = "sec_5_blog_6_sous_titre")
    private byte[] sec5Blog6SousTitre;

    @Column(name = "sec_5_blog_6_sous_titre_content_type")
    private String sec5Blog6SousTitreContentType;

    @Lob
    @Column(name = "sec_5_blog_7_sous_titre")
    private byte[] sec5Blog7SousTitre;

    @Column(name = "sec_5_blog_7_sous_titre_content_type")
    private String sec5Blog7SousTitreContentType;

    @Column(name = "sec_5_blog_1_text")
    private String sec5Blog1Text;

    @Column(name = "sec_5_blog_2_text")
    private String sec5Blog2Text;

    @Column(name = "sec_5_blog_3_text")
    private String sec5Blog3Text;

    @Column(name = "sec_5_blog_4_text")
    private String sec5Blog4Text;

    @Column(name = "sec_5_blog_5_text")
    private String sec5Blog5Text;

    @Column(name = "sec_5_blog_6_text")
    private String sec5Blog6Text;

    @Column(name = "sec_5_blog_7_text")
    private String sec5Blog7Text;

    @Column(name = "sec_6_titre")
    private String sec6Titre;

    @Lob
    @Column(name = "sec_6_img")
    private byte[] sec6Img;

    @Column(name = "sec_6_img_content_type")
    private String sec6ImgContentType;

    @Column(name = "sec_6_text")
    private String sec6Text;

    @Column(name = "sec_7_titre")
    private String sec7Titre;

    @Lob
    @Column(name = "sec_7_img")
    private byte[] sec7Img;

    @Column(name = "sec_7_img_content_type")
    private String sec7ImgContentType;

    @Column(name = "sec_7_text")
    private String sec7Text;

    @Column(name = "sec_8_titre")
    private String sec8Titre;

    @Lob
    @Column(name = "sec_8_img")
    private byte[] sec8Img;

    @Column(name = "sec_8_img_content_type")
    private String sec8ImgContentType;

    @Column(name = "sec_8_text")
    private String sec8Text;

    @Column(name = "sec_9_titre")
    private String sec9Titre;

    @Lob
    @Column(name = "sec_9_img")
    private byte[] sec9Img;

    @Column(name = "sec_9_img_content_type")
    private String sec9ImgContentType;

    @Column(name = "sec_9_text")
    private String sec9Text;

    @ManyToOne
    @JsonIgnoreProperties(value = { "pageAccueils" }, allowSetters = true)
    private Partenaires partenaires1;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PageAccueil id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSec1Titre() {
        return this.sec1Titre;
    }

    public PageAccueil sec1Titre(String sec1Titre) {
        this.setSec1Titre(sec1Titre);
        return this;
    }

    public void setSec1Titre(String sec1Titre) {
        this.sec1Titre = sec1Titre;
    }

    public String getSec1Texte() {
        return this.sec1Texte;
    }

    public PageAccueil sec1Texte(String sec1Texte) {
        this.setSec1Texte(sec1Texte);
        return this;
    }

    public void setSec1Texte(String sec1Texte) {
        this.sec1Texte = sec1Texte;
    }

    public byte[] getSecImage() {
        return this.secImage;
    }

    public PageAccueil secImage(byte[] secImage) {
        this.setSecImage(secImage);
        return this;
    }

    public void setSecImage(byte[] secImage) {
        this.secImage = secImage;
    }

    public String getSecImageContentType() {
        return this.secImageContentType;
    }

    public PageAccueil secImageContentType(String secImageContentType) {
        this.secImageContentType = secImageContentType;
        return this;
    }

    public void setSecImageContentType(String secImageContentType) {
        this.secImageContentType = secImageContentType;
    }

    public String getSec1Bouton() {
        return this.sec1Bouton;
    }

    public PageAccueil sec1Bouton(String sec1Bouton) {
        this.setSec1Bouton(sec1Bouton);
        return this;
    }

    public void setSec1Bouton(String sec1Bouton) {
        this.sec1Bouton = sec1Bouton;
    }

    public String getSec2Titre() {
        return this.sec2Titre;
    }

    public PageAccueil sec2Titre(String sec2Titre) {
        this.setSec2Titre(sec2Titre);
        return this;
    }

    public void setSec2Titre(String sec2Titre) {
        this.sec2Titre = sec2Titre;
    }

    public String getSec2Text() {
        return this.sec2Text;
    }

    public PageAccueil sec2Text(String sec2Text) {
        this.setSec2Text(sec2Text);
        return this;
    }

    public void setSec2Text(String sec2Text) {
        this.sec2Text = sec2Text;
    }

    public String getSec2Bouton() {
        return this.sec2Bouton;
    }

    public PageAccueil sec2Bouton(String sec2Bouton) {
        this.setSec2Bouton(sec2Bouton);
        return this;
    }

    public void setSec2Bouton(String sec2Bouton) {
        this.sec2Bouton = sec2Bouton;
    }

    public String getSec3Titre() {
        return this.sec3Titre;
    }

    public PageAccueil sec3Titre(String sec3Titre) {
        this.setSec3Titre(sec3Titre);
        return this;
    }

    public void setSec3Titre(String sec3Titre) {
        this.sec3Titre = sec3Titre;
    }

    public byte[] getSec3Blog1Img() {
        return this.sec3Blog1Img;
    }

    public PageAccueil sec3Blog1Img(byte[] sec3Blog1Img) {
        this.setSec3Blog1Img(sec3Blog1Img);
        return this;
    }

    public void setSec3Blog1Img(byte[] sec3Blog1Img) {
        this.sec3Blog1Img = sec3Blog1Img;
    }

    public String getSec3Blog1ImgContentType() {
        return this.sec3Blog1ImgContentType;
    }

    public PageAccueil sec3Blog1ImgContentType(String sec3Blog1ImgContentType) {
        this.sec3Blog1ImgContentType = sec3Blog1ImgContentType;
        return this;
    }

    public void setSec3Blog1ImgContentType(String sec3Blog1ImgContentType) {
        this.sec3Blog1ImgContentType = sec3Blog1ImgContentType;
    }

    public byte[] getSec3Blog2Img() {
        return this.sec3Blog2Img;
    }

    public PageAccueil sec3Blog2Img(byte[] sec3Blog2Img) {
        this.setSec3Blog2Img(sec3Blog2Img);
        return this;
    }

    public void setSec3Blog2Img(byte[] sec3Blog2Img) {
        this.sec3Blog2Img = sec3Blog2Img;
    }

    public String getSec3Blog2ImgContentType() {
        return this.sec3Blog2ImgContentType;
    }

    public PageAccueil sec3Blog2ImgContentType(String sec3Blog2ImgContentType) {
        this.sec3Blog2ImgContentType = sec3Blog2ImgContentType;
        return this;
    }

    public void setSec3Blog2ImgContentType(String sec3Blog2ImgContentType) {
        this.sec3Blog2ImgContentType = sec3Blog2ImgContentType;
    }

    public byte[] getSec3Blog3Img() {
        return this.sec3Blog3Img;
    }

    public PageAccueil sec3Blog3Img(byte[] sec3Blog3Img) {
        this.setSec3Blog3Img(sec3Blog3Img);
        return this;
    }

    public void setSec3Blog3Img(byte[] sec3Blog3Img) {
        this.sec3Blog3Img = sec3Blog3Img;
    }

    public String getSec3Blog3ImgContentType() {
        return this.sec3Blog3ImgContentType;
    }

    public PageAccueil sec3Blog3ImgContentType(String sec3Blog3ImgContentType) {
        this.sec3Blog3ImgContentType = sec3Blog3ImgContentType;
        return this;
    }

    public void setSec3Blog3ImgContentType(String sec3Blog3ImgContentType) {
        this.sec3Blog3ImgContentType = sec3Blog3ImgContentType;
    }

    public String getSec3Blog1SousTitre() {
        return this.sec3Blog1SousTitre;
    }

    public PageAccueil sec3Blog1SousTitre(String sec3Blog1SousTitre) {
        this.setSec3Blog1SousTitre(sec3Blog1SousTitre);
        return this;
    }

    public void setSec3Blog1SousTitre(String sec3Blog1SousTitre) {
        this.sec3Blog1SousTitre = sec3Blog1SousTitre;
    }

    public String getSec3Blog2SousTitre() {
        return this.sec3Blog2SousTitre;
    }

    public PageAccueil sec3Blog2SousTitre(String sec3Blog2SousTitre) {
        this.setSec3Blog2SousTitre(sec3Blog2SousTitre);
        return this;
    }

    public void setSec3Blog2SousTitre(String sec3Blog2SousTitre) {
        this.sec3Blog2SousTitre = sec3Blog2SousTitre;
    }

    public String getSec3Blog3SousTitre() {
        return this.sec3Blog3SousTitre;
    }

    public PageAccueil sec3Blog3SousTitre(String sec3Blog3SousTitre) {
        this.setSec3Blog3SousTitre(sec3Blog3SousTitre);
        return this;
    }

    public void setSec3Blog3SousTitre(String sec3Blog3SousTitre) {
        this.sec3Blog3SousTitre = sec3Blog3SousTitre;
    }

    public String getSec3Blog1Text() {
        return this.sec3Blog1Text;
    }

    public PageAccueil sec3Blog1Text(String sec3Blog1Text) {
        this.setSec3Blog1Text(sec3Blog1Text);
        return this;
    }

    public void setSec3Blog1Text(String sec3Blog1Text) {
        this.sec3Blog1Text = sec3Blog1Text;
    }

    public String getSec3Blog2Text() {
        return this.sec3Blog2Text;
    }

    public PageAccueil sec3Blog2Text(String sec3Blog2Text) {
        this.setSec3Blog2Text(sec3Blog2Text);
        return this;
    }

    public void setSec3Blog2Text(String sec3Blog2Text) {
        this.sec3Blog2Text = sec3Blog2Text;
    }

    public String getSec3Blog3Text() {
        return this.sec3Blog3Text;
    }

    public PageAccueil sec3Blog3Text(String sec3Blog3Text) {
        this.setSec3Blog3Text(sec3Blog3Text);
        return this;
    }

    public void setSec3Blog3Text(String sec3Blog3Text) {
        this.sec3Blog3Text = sec3Blog3Text;
    }

    public String getSec4Titre() {
        return this.sec4Titre;
    }

    public PageAccueil sec4Titre(String sec4Titre) {
        this.setSec4Titre(sec4Titre);
        return this;
    }

    public void setSec4Titre(String sec4Titre) {
        this.sec4Titre = sec4Titre;
    }

    public String getSec4Text() {
        return this.sec4Text;
    }

    public PageAccueil sec4Text(String sec4Text) {
        this.setSec4Text(sec4Text);
        return this;
    }

    public void setSec4Text(String sec4Text) {
        this.sec4Text = sec4Text;
    }

    public byte[] getSec4Blog1Img() {
        return this.sec4Blog1Img;
    }

    public PageAccueil sec4Blog1Img(byte[] sec4Blog1Img) {
        this.setSec4Blog1Img(sec4Blog1Img);
        return this;
    }

    public void setSec4Blog1Img(byte[] sec4Blog1Img) {
        this.sec4Blog1Img = sec4Blog1Img;
    }

    public String getSec4Blog1ImgContentType() {
        return this.sec4Blog1ImgContentType;
    }

    public PageAccueil sec4Blog1ImgContentType(String sec4Blog1ImgContentType) {
        this.sec4Blog1ImgContentType = sec4Blog1ImgContentType;
        return this;
    }

    public void setSec4Blog1ImgContentType(String sec4Blog1ImgContentType) {
        this.sec4Blog1ImgContentType = sec4Blog1ImgContentType;
    }

    public String getSec4Blog1SousTitre() {
        return this.sec4Blog1SousTitre;
    }

    public PageAccueil sec4Blog1SousTitre(String sec4Blog1SousTitre) {
        this.setSec4Blog1SousTitre(sec4Blog1SousTitre);
        return this;
    }

    public void setSec4Blog1SousTitre(String sec4Blog1SousTitre) {
        this.sec4Blog1SousTitre = sec4Blog1SousTitre;
    }

    public String getSec4Blog2SousTitre() {
        return this.sec4Blog2SousTitre;
    }

    public PageAccueil sec4Blog2SousTitre(String sec4Blog2SousTitre) {
        this.setSec4Blog2SousTitre(sec4Blog2SousTitre);
        return this;
    }

    public void setSec4Blog2SousTitre(String sec4Blog2SousTitre) {
        this.sec4Blog2SousTitre = sec4Blog2SousTitre;
    }

    public String getSec4Blog1Text() {
        return this.sec4Blog1Text;
    }

    public PageAccueil sec4Blog1Text(String sec4Blog1Text) {
        this.setSec4Blog1Text(sec4Blog1Text);
        return this;
    }

    public void setSec4Blog1Text(String sec4Blog1Text) {
        this.sec4Blog1Text = sec4Blog1Text;
    }

    public String getSec4Blog2Text() {
        return this.sec4Blog2Text;
    }

    public PageAccueil sec4Blog2Text(String sec4Blog2Text) {
        this.setSec4Blog2Text(sec4Blog2Text);
        return this;
    }

    public void setSec4Blog2Text(String sec4Blog2Text) {
        this.sec4Blog2Text = sec4Blog2Text;
    }

    public String getSec5Titre() {
        return this.sec5Titre;
    }

    public PageAccueil sec5Titre(String sec5Titre) {
        this.setSec5Titre(sec5Titre);
        return this;
    }

    public void setSec5Titre(String sec5Titre) {
        this.sec5Titre = sec5Titre;
    }

    public byte[] getSec5Blog1Img() {
        return this.sec5Blog1Img;
    }

    public PageAccueil sec5Blog1Img(byte[] sec5Blog1Img) {
        this.setSec5Blog1Img(sec5Blog1Img);
        return this;
    }

    public void setSec5Blog1Img(byte[] sec5Blog1Img) {
        this.sec5Blog1Img = sec5Blog1Img;
    }

    public String getSec5Blog1ImgContentType() {
        return this.sec5Blog1ImgContentType;
    }

    public PageAccueil sec5Blog1ImgContentType(String sec5Blog1ImgContentType) {
        this.sec5Blog1ImgContentType = sec5Blog1ImgContentType;
        return this;
    }

    public void setSec5Blog1ImgContentType(String sec5Blog1ImgContentType) {
        this.sec5Blog1ImgContentType = sec5Blog1ImgContentType;
    }

    public byte[] getSec5Blog2Img() {
        return this.sec5Blog2Img;
    }

    public PageAccueil sec5Blog2Img(byte[] sec5Blog2Img) {
        this.setSec5Blog2Img(sec5Blog2Img);
        return this;
    }

    public void setSec5Blog2Img(byte[] sec5Blog2Img) {
        this.sec5Blog2Img = sec5Blog2Img;
    }

    public String getSec5Blog2ImgContentType() {
        return this.sec5Blog2ImgContentType;
    }

    public PageAccueil sec5Blog2ImgContentType(String sec5Blog2ImgContentType) {
        this.sec5Blog2ImgContentType = sec5Blog2ImgContentType;
        return this;
    }

    public void setSec5Blog2ImgContentType(String sec5Blog2ImgContentType) {
        this.sec5Blog2ImgContentType = sec5Blog2ImgContentType;
    }

    public byte[] getSec5Blog3Img() {
        return this.sec5Blog3Img;
    }

    public PageAccueil sec5Blog3Img(byte[] sec5Blog3Img) {
        this.setSec5Blog3Img(sec5Blog3Img);
        return this;
    }

    public void setSec5Blog3Img(byte[] sec5Blog3Img) {
        this.sec5Blog3Img = sec5Blog3Img;
    }

    public String getSec5Blog3ImgContentType() {
        return this.sec5Blog3ImgContentType;
    }

    public PageAccueil sec5Blog3ImgContentType(String sec5Blog3ImgContentType) {
        this.sec5Blog3ImgContentType = sec5Blog3ImgContentType;
        return this;
    }

    public void setSec5Blog3ImgContentType(String sec5Blog3ImgContentType) {
        this.sec5Blog3ImgContentType = sec5Blog3ImgContentType;
    }

    public byte[] getSec5Blog4Img() {
        return this.sec5Blog4Img;
    }

    public PageAccueil sec5Blog4Img(byte[] sec5Blog4Img) {
        this.setSec5Blog4Img(sec5Blog4Img);
        return this;
    }

    public void setSec5Blog4Img(byte[] sec5Blog4Img) {
        this.sec5Blog4Img = sec5Blog4Img;
    }

    public String getSec5Blog4ImgContentType() {
        return this.sec5Blog4ImgContentType;
    }

    public PageAccueil sec5Blog4ImgContentType(String sec5Blog4ImgContentType) {
        this.sec5Blog4ImgContentType = sec5Blog4ImgContentType;
        return this;
    }

    public void setSec5Blog4ImgContentType(String sec5Blog4ImgContentType) {
        this.sec5Blog4ImgContentType = sec5Blog4ImgContentType;
    }

    public byte[] getSec5Blog5Img() {
        return this.sec5Blog5Img;
    }

    public PageAccueil sec5Blog5Img(byte[] sec5Blog5Img) {
        this.setSec5Blog5Img(sec5Blog5Img);
        return this;
    }

    public void setSec5Blog5Img(byte[] sec5Blog5Img) {
        this.sec5Blog5Img = sec5Blog5Img;
    }

    public String getSec5Blog5ImgContentType() {
        return this.sec5Blog5ImgContentType;
    }

    public PageAccueil sec5Blog5ImgContentType(String sec5Blog5ImgContentType) {
        this.sec5Blog5ImgContentType = sec5Blog5ImgContentType;
        return this;
    }

    public void setSec5Blog5ImgContentType(String sec5Blog5ImgContentType) {
        this.sec5Blog5ImgContentType = sec5Blog5ImgContentType;
    }

    public byte[] getSec5Blog6Img() {
        return this.sec5Blog6Img;
    }

    public PageAccueil sec5Blog6Img(byte[] sec5Blog6Img) {
        this.setSec5Blog6Img(sec5Blog6Img);
        return this;
    }

    public void setSec5Blog6Img(byte[] sec5Blog6Img) {
        this.sec5Blog6Img = sec5Blog6Img;
    }

    public String getSec5Blog6ImgContentType() {
        return this.sec5Blog6ImgContentType;
    }

    public PageAccueil sec5Blog6ImgContentType(String sec5Blog6ImgContentType) {
        this.sec5Blog6ImgContentType = sec5Blog6ImgContentType;
        return this;
    }

    public void setSec5Blog6ImgContentType(String sec5Blog6ImgContentType) {
        this.sec5Blog6ImgContentType = sec5Blog6ImgContentType;
    }

    public byte[] getSec5Blog7Img() {
        return this.sec5Blog7Img;
    }

    public PageAccueil sec5Blog7Img(byte[] sec5Blog7Img) {
        this.setSec5Blog7Img(sec5Blog7Img);
        return this;
    }

    public void setSec5Blog7Img(byte[] sec5Blog7Img) {
        this.sec5Blog7Img = sec5Blog7Img;
    }

    public String getSec5Blog7ImgContentType() {
        return this.sec5Blog7ImgContentType;
    }

    public PageAccueil sec5Blog7ImgContentType(String sec5Blog7ImgContentType) {
        this.sec5Blog7ImgContentType = sec5Blog7ImgContentType;
        return this;
    }

    public void setSec5Blog7ImgContentType(String sec5Blog7ImgContentType) {
        this.sec5Blog7ImgContentType = sec5Blog7ImgContentType;
    }

    public byte[] getSec5Blog1SousTitre() {
        return this.sec5Blog1SousTitre;
    }

    public PageAccueil sec5Blog1SousTitre(byte[] sec5Blog1SousTitre) {
        this.setSec5Blog1SousTitre(sec5Blog1SousTitre);
        return this;
    }

    public void setSec5Blog1SousTitre(byte[] sec5Blog1SousTitre) {
        this.sec5Blog1SousTitre = sec5Blog1SousTitre;
    }

    public String getSec5Blog1SousTitreContentType() {
        return this.sec5Blog1SousTitreContentType;
    }

    public PageAccueil sec5Blog1SousTitreContentType(String sec5Blog1SousTitreContentType) {
        this.sec5Blog1SousTitreContentType = sec5Blog1SousTitreContentType;
        return this;
    }

    public void setSec5Blog1SousTitreContentType(String sec5Blog1SousTitreContentType) {
        this.sec5Blog1SousTitreContentType = sec5Blog1SousTitreContentType;
    }

    public byte[] getSec5Blog2SousTitre() {
        return this.sec5Blog2SousTitre;
    }

    public PageAccueil sec5Blog2SousTitre(byte[] sec5Blog2SousTitre) {
        this.setSec5Blog2SousTitre(sec5Blog2SousTitre);
        return this;
    }

    public void setSec5Blog2SousTitre(byte[] sec5Blog2SousTitre) {
        this.sec5Blog2SousTitre = sec5Blog2SousTitre;
    }

    public String getSec5Blog2SousTitreContentType() {
        return this.sec5Blog2SousTitreContentType;
    }

    public PageAccueil sec5Blog2SousTitreContentType(String sec5Blog2SousTitreContentType) {
        this.sec5Blog2SousTitreContentType = sec5Blog2SousTitreContentType;
        return this;
    }

    public void setSec5Blog2SousTitreContentType(String sec5Blog2SousTitreContentType) {
        this.sec5Blog2SousTitreContentType = sec5Blog2SousTitreContentType;
    }

    public byte[] getSec5Blog3SousTitre() {
        return this.sec5Blog3SousTitre;
    }

    public PageAccueil sec5Blog3SousTitre(byte[] sec5Blog3SousTitre) {
        this.setSec5Blog3SousTitre(sec5Blog3SousTitre);
        return this;
    }

    public void setSec5Blog3SousTitre(byte[] sec5Blog3SousTitre) {
        this.sec5Blog3SousTitre = sec5Blog3SousTitre;
    }

    public String getSec5Blog3SousTitreContentType() {
        return this.sec5Blog3SousTitreContentType;
    }

    public PageAccueil sec5Blog3SousTitreContentType(String sec5Blog3SousTitreContentType) {
        this.sec5Blog3SousTitreContentType = sec5Blog3SousTitreContentType;
        return this;
    }

    public void setSec5Blog3SousTitreContentType(String sec5Blog3SousTitreContentType) {
        this.sec5Blog3SousTitreContentType = sec5Blog3SousTitreContentType;
    }

    public byte[] getSec5Blog4SousTitre() {
        return this.sec5Blog4SousTitre;
    }

    public PageAccueil sec5Blog4SousTitre(byte[] sec5Blog4SousTitre) {
        this.setSec5Blog4SousTitre(sec5Blog4SousTitre);
        return this;
    }

    public void setSec5Blog4SousTitre(byte[] sec5Blog4SousTitre) {
        this.sec5Blog4SousTitre = sec5Blog4SousTitre;
    }

    public String getSec5Blog4SousTitreContentType() {
        return this.sec5Blog4SousTitreContentType;
    }

    public PageAccueil sec5Blog4SousTitreContentType(String sec5Blog4SousTitreContentType) {
        this.sec5Blog4SousTitreContentType = sec5Blog4SousTitreContentType;
        return this;
    }

    public void setSec5Blog4SousTitreContentType(String sec5Blog4SousTitreContentType) {
        this.sec5Blog4SousTitreContentType = sec5Blog4SousTitreContentType;
    }

    public byte[] getSec5Blog5SousTitre() {
        return this.sec5Blog5SousTitre;
    }

    public PageAccueil sec5Blog5SousTitre(byte[] sec5Blog5SousTitre) {
        this.setSec5Blog5SousTitre(sec5Blog5SousTitre);
        return this;
    }

    public void setSec5Blog5SousTitre(byte[] sec5Blog5SousTitre) {
        this.sec5Blog5SousTitre = sec5Blog5SousTitre;
    }

    public String getSec5Blog5SousTitreContentType() {
        return this.sec5Blog5SousTitreContentType;
    }

    public PageAccueil sec5Blog5SousTitreContentType(String sec5Blog5SousTitreContentType) {
        this.sec5Blog5SousTitreContentType = sec5Blog5SousTitreContentType;
        return this;
    }

    public void setSec5Blog5SousTitreContentType(String sec5Blog5SousTitreContentType) {
        this.sec5Blog5SousTitreContentType = sec5Blog5SousTitreContentType;
    }

    public byte[] getSec5Blog6SousTitre() {
        return this.sec5Blog6SousTitre;
    }

    public PageAccueil sec5Blog6SousTitre(byte[] sec5Blog6SousTitre) {
        this.setSec5Blog6SousTitre(sec5Blog6SousTitre);
        return this;
    }

    public void setSec5Blog6SousTitre(byte[] sec5Blog6SousTitre) {
        this.sec5Blog6SousTitre = sec5Blog6SousTitre;
    }

    public String getSec5Blog6SousTitreContentType() {
        return this.sec5Blog6SousTitreContentType;
    }

    public PageAccueil sec5Blog6SousTitreContentType(String sec5Blog6SousTitreContentType) {
        this.sec5Blog6SousTitreContentType = sec5Blog6SousTitreContentType;
        return this;
    }

    public void setSec5Blog6SousTitreContentType(String sec5Blog6SousTitreContentType) {
        this.sec5Blog6SousTitreContentType = sec5Blog6SousTitreContentType;
    }

    public byte[] getSec5Blog7SousTitre() {
        return this.sec5Blog7SousTitre;
    }

    public PageAccueil sec5Blog7SousTitre(byte[] sec5Blog7SousTitre) {
        this.setSec5Blog7SousTitre(sec5Blog7SousTitre);
        return this;
    }

    public void setSec5Blog7SousTitre(byte[] sec5Blog7SousTitre) {
        this.sec5Blog7SousTitre = sec5Blog7SousTitre;
    }

    public String getSec5Blog7SousTitreContentType() {
        return this.sec5Blog7SousTitreContentType;
    }

    public PageAccueil sec5Blog7SousTitreContentType(String sec5Blog7SousTitreContentType) {
        this.sec5Blog7SousTitreContentType = sec5Blog7SousTitreContentType;
        return this;
    }

    public void setSec5Blog7SousTitreContentType(String sec5Blog7SousTitreContentType) {
        this.sec5Blog7SousTitreContentType = sec5Blog7SousTitreContentType;
    }

    public String getSec5Blog1Text() {
        return this.sec5Blog1Text;
    }

    public PageAccueil sec5Blog1Text(String sec5Blog1Text) {
        this.setSec5Blog1Text(sec5Blog1Text);
        return this;
    }

    public void setSec5Blog1Text(String sec5Blog1Text) {
        this.sec5Blog1Text = sec5Blog1Text;
    }

    public String getSec5Blog2Text() {
        return this.sec5Blog2Text;
    }

    public PageAccueil sec5Blog2Text(String sec5Blog2Text) {
        this.setSec5Blog2Text(sec5Blog2Text);
        return this;
    }

    public void setSec5Blog2Text(String sec5Blog2Text) {
        this.sec5Blog2Text = sec5Blog2Text;
    }

    public String getSec5Blog3Text() {
        return this.sec5Blog3Text;
    }

    public PageAccueil sec5Blog3Text(String sec5Blog3Text) {
        this.setSec5Blog3Text(sec5Blog3Text);
        return this;
    }

    public void setSec5Blog3Text(String sec5Blog3Text) {
        this.sec5Blog3Text = sec5Blog3Text;
    }

    public String getSec5Blog4Text() {
        return this.sec5Blog4Text;
    }

    public PageAccueil sec5Blog4Text(String sec5Blog4Text) {
        this.setSec5Blog4Text(sec5Blog4Text);
        return this;
    }

    public void setSec5Blog4Text(String sec5Blog4Text) {
        this.sec5Blog4Text = sec5Blog4Text;
    }

    public String getSec5Blog5Text() {
        return this.sec5Blog5Text;
    }

    public PageAccueil sec5Blog5Text(String sec5Blog5Text) {
        this.setSec5Blog5Text(sec5Blog5Text);
        return this;
    }

    public void setSec5Blog5Text(String sec5Blog5Text) {
        this.sec5Blog5Text = sec5Blog5Text;
    }

    public String getSec5Blog6Text() {
        return this.sec5Blog6Text;
    }

    public PageAccueil sec5Blog6Text(String sec5Blog6Text) {
        this.setSec5Blog6Text(sec5Blog6Text);
        return this;
    }

    public void setSec5Blog6Text(String sec5Blog6Text) {
        this.sec5Blog6Text = sec5Blog6Text;
    }

    public String getSec5Blog7Text() {
        return this.sec5Blog7Text;
    }

    public PageAccueil sec5Blog7Text(String sec5Blog7Text) {
        this.setSec5Blog7Text(sec5Blog7Text);
        return this;
    }

    public void setSec5Blog7Text(String sec5Blog7Text) {
        this.sec5Blog7Text = sec5Blog7Text;
    }

    public String getSec6Titre() {
        return this.sec6Titre;
    }

    public PageAccueil sec6Titre(String sec6Titre) {
        this.setSec6Titre(sec6Titre);
        return this;
    }

    public void setSec6Titre(String sec6Titre) {
        this.sec6Titre = sec6Titre;
    }

    public byte[] getSec6Img() {
        return this.sec6Img;
    }

    public PageAccueil sec6Img(byte[] sec6Img) {
        this.setSec6Img(sec6Img);
        return this;
    }

    public void setSec6Img(byte[] sec6Img) {
        this.sec6Img = sec6Img;
    }

    public String getSec6ImgContentType() {
        return this.sec6ImgContentType;
    }

    public PageAccueil sec6ImgContentType(String sec6ImgContentType) {
        this.sec6ImgContentType = sec6ImgContentType;
        return this;
    }

    public void setSec6ImgContentType(String sec6ImgContentType) {
        this.sec6ImgContentType = sec6ImgContentType;
    }

    public String getSec6Text() {
        return this.sec6Text;
    }

    public PageAccueil sec6Text(String sec6Text) {
        this.setSec6Text(sec6Text);
        return this;
    }

    public void setSec6Text(String sec6Text) {
        this.sec6Text = sec6Text;
    }

    public String getSec7Titre() {
        return this.sec7Titre;
    }

    public PageAccueil sec7Titre(String sec7Titre) {
        this.setSec7Titre(sec7Titre);
        return this;
    }

    public void setSec7Titre(String sec7Titre) {
        this.sec7Titre = sec7Titre;
    }

    public byte[] getSec7Img() {
        return this.sec7Img;
    }

    public PageAccueil sec7Img(byte[] sec7Img) {
        this.setSec7Img(sec7Img);
        return this;
    }

    public void setSec7Img(byte[] sec7Img) {
        this.sec7Img = sec7Img;
    }

    public String getSec7ImgContentType() {
        return this.sec7ImgContentType;
    }

    public PageAccueil sec7ImgContentType(String sec7ImgContentType) {
        this.sec7ImgContentType = sec7ImgContentType;
        return this;
    }

    public void setSec7ImgContentType(String sec7ImgContentType) {
        this.sec7ImgContentType = sec7ImgContentType;
    }

    public String getSec7Text() {
        return this.sec7Text;
    }

    public PageAccueil sec7Text(String sec7Text) {
        this.setSec7Text(sec7Text);
        return this;
    }

    public void setSec7Text(String sec7Text) {
        this.sec7Text = sec7Text;
    }

    public String getSec8Titre() {
        return this.sec8Titre;
    }

    public PageAccueil sec8Titre(String sec8Titre) {
        this.setSec8Titre(sec8Titre);
        return this;
    }

    public void setSec8Titre(String sec8Titre) {
        this.sec8Titre = sec8Titre;
    }

    public byte[] getSec8Img() {
        return this.sec8Img;
    }

    public PageAccueil sec8Img(byte[] sec8Img) {
        this.setSec8Img(sec8Img);
        return this;
    }

    public void setSec8Img(byte[] sec8Img) {
        this.sec8Img = sec8Img;
    }

    public String getSec8ImgContentType() {
        return this.sec8ImgContentType;
    }

    public PageAccueil sec8ImgContentType(String sec8ImgContentType) {
        this.sec8ImgContentType = sec8ImgContentType;
        return this;
    }

    public void setSec8ImgContentType(String sec8ImgContentType) {
        this.sec8ImgContentType = sec8ImgContentType;
    }

    public String getSec8Text() {
        return this.sec8Text;
    }

    public PageAccueil sec8Text(String sec8Text) {
        this.setSec8Text(sec8Text);
        return this;
    }

    public void setSec8Text(String sec8Text) {
        this.sec8Text = sec8Text;
    }

    public String getSec9Titre() {
        return this.sec9Titre;
    }

    public PageAccueil sec9Titre(String sec9Titre) {
        this.setSec9Titre(sec9Titre);
        return this;
    }

    public void setSec9Titre(String sec9Titre) {
        this.sec9Titre = sec9Titre;
    }

    public byte[] getSec9Img() {
        return this.sec9Img;
    }

    public PageAccueil sec9Img(byte[] sec9Img) {
        this.setSec9Img(sec9Img);
        return this;
    }

    public void setSec9Img(byte[] sec9Img) {
        this.sec9Img = sec9Img;
    }

    public String getSec9ImgContentType() {
        return this.sec9ImgContentType;
    }

    public PageAccueil sec9ImgContentType(String sec9ImgContentType) {
        this.sec9ImgContentType = sec9ImgContentType;
        return this;
    }

    public void setSec9ImgContentType(String sec9ImgContentType) {
        this.sec9ImgContentType = sec9ImgContentType;
    }

    public String getSec9Text() {
        return this.sec9Text;
    }

    public PageAccueil sec9Text(String sec9Text) {
        this.setSec9Text(sec9Text);
        return this;
    }

    public void setSec9Text(String sec9Text) {
        this.sec9Text = sec9Text;
    }

    public Partenaires getPartenaires1() {
        return this.partenaires1;
    }

    public void setPartenaires1(Partenaires partenaires) {
        this.partenaires1 = partenaires;
    }

    public PageAccueil partenaires1(Partenaires partenaires) {
        this.setPartenaires1(partenaires);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PageAccueil)) {
            return false;
        }
        return id != null && id.equals(((PageAccueil) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PageAccueil{" +
            "id=" + getId() +
            ", sec1Titre='" + getSec1Titre() + "'" +
            ", sec1Texte='" + getSec1Texte() + "'" +
            ", secImage='" + getSecImage() + "'" +
            ", secImageContentType='" + getSecImageContentType() + "'" +
            ", sec1Bouton='" + getSec1Bouton() + "'" +
            ", sec2Titre='" + getSec2Titre() + "'" +
            ", sec2Text='" + getSec2Text() + "'" +
            ", sec2Bouton='" + getSec2Bouton() + "'" +
            ", sec3Titre='" + getSec3Titre() + "'" +
            ", sec3Blog1Img='" + getSec3Blog1Img() + "'" +
            ", sec3Blog1ImgContentType='" + getSec3Blog1ImgContentType() + "'" +
            ", sec3Blog2Img='" + getSec3Blog2Img() + "'" +
            ", sec3Blog2ImgContentType='" + getSec3Blog2ImgContentType() + "'" +
            ", sec3Blog3Img='" + getSec3Blog3Img() + "'" +
            ", sec3Blog3ImgContentType='" + getSec3Blog3ImgContentType() + "'" +
            ", sec3Blog1SousTitre='" + getSec3Blog1SousTitre() + "'" +
            ", sec3Blog2SousTitre='" + getSec3Blog2SousTitre() + "'" +
            ", sec3Blog3SousTitre='" + getSec3Blog3SousTitre() + "'" +
            ", sec3Blog1Text='" + getSec3Blog1Text() + "'" +
            ", sec3Blog2Text='" + getSec3Blog2Text() + "'" +
            ", sec3Blog3Text='" + getSec3Blog3Text() + "'" +
            ", sec4Titre='" + getSec4Titre() + "'" +
            ", sec4Text='" + getSec4Text() + "'" +
            ", sec4Blog1Img='" + getSec4Blog1Img() + "'" +
            ", sec4Blog1ImgContentType='" + getSec4Blog1ImgContentType() + "'" +
            ", sec4Blog1SousTitre='" + getSec4Blog1SousTitre() + "'" +
            ", sec4Blog2SousTitre='" + getSec4Blog2SousTitre() + "'" +
            ", sec4Blog1Text='" + getSec4Blog1Text() + "'" +
            ", sec4Blog2Text='" + getSec4Blog2Text() + "'" +
            ", sec5Titre='" + getSec5Titre() + "'" +
            ", sec5Blog1Img='" + getSec5Blog1Img() + "'" +
            ", sec5Blog1ImgContentType='" + getSec5Blog1ImgContentType() + "'" +
            ", sec5Blog2Img='" + getSec5Blog2Img() + "'" +
            ", sec5Blog2ImgContentType='" + getSec5Blog2ImgContentType() + "'" +
            ", sec5Blog3Img='" + getSec5Blog3Img() + "'" +
            ", sec5Blog3ImgContentType='" + getSec5Blog3ImgContentType() + "'" +
            ", sec5Blog4Img='" + getSec5Blog4Img() + "'" +
            ", sec5Blog4ImgContentType='" + getSec5Blog4ImgContentType() + "'" +
            ", sec5Blog5Img='" + getSec5Blog5Img() + "'" +
            ", sec5Blog5ImgContentType='" + getSec5Blog5ImgContentType() + "'" +
            ", sec5Blog6Img='" + getSec5Blog6Img() + "'" +
            ", sec5Blog6ImgContentType='" + getSec5Blog6ImgContentType() + "'" +
            ", sec5Blog7Img='" + getSec5Blog7Img() + "'" +
            ", sec5Blog7ImgContentType='" + getSec5Blog7ImgContentType() + "'" +
            ", sec5Blog1SousTitre='" + getSec5Blog1SousTitre() + "'" +
            ", sec5Blog1SousTitreContentType='" + getSec5Blog1SousTitreContentType() + "'" +
            ", sec5Blog2SousTitre='" + getSec5Blog2SousTitre() + "'" +
            ", sec5Blog2SousTitreContentType='" + getSec5Blog2SousTitreContentType() + "'" +
            ", sec5Blog3SousTitre='" + getSec5Blog3SousTitre() + "'" +
            ", sec5Blog3SousTitreContentType='" + getSec5Blog3SousTitreContentType() + "'" +
            ", sec5Blog4SousTitre='" + getSec5Blog4SousTitre() + "'" +
            ", sec5Blog4SousTitreContentType='" + getSec5Blog4SousTitreContentType() + "'" +
            ", sec5Blog5SousTitre='" + getSec5Blog5SousTitre() + "'" +
            ", sec5Blog5SousTitreContentType='" + getSec5Blog5SousTitreContentType() + "'" +
            ", sec5Blog6SousTitre='" + getSec5Blog6SousTitre() + "'" +
            ", sec5Blog6SousTitreContentType='" + getSec5Blog6SousTitreContentType() + "'" +
            ", sec5Blog7SousTitre='" + getSec5Blog7SousTitre() + "'" +
            ", sec5Blog7SousTitreContentType='" + getSec5Blog7SousTitreContentType() + "'" +
            ", sec5Blog1Text='" + getSec5Blog1Text() + "'" +
            ", sec5Blog2Text='" + getSec5Blog2Text() + "'" +
            ", sec5Blog3Text='" + getSec5Blog3Text() + "'" +
            ", sec5Blog4Text='" + getSec5Blog4Text() + "'" +
            ", sec5Blog5Text='" + getSec5Blog5Text() + "'" +
            ", sec5Blog6Text='" + getSec5Blog6Text() + "'" +
            ", sec5Blog7Text='" + getSec5Blog7Text() + "'" +
            ", sec6Titre='" + getSec6Titre() + "'" +
            ", sec6Img='" + getSec6Img() + "'" +
            ", sec6ImgContentType='" + getSec6ImgContentType() + "'" +
            ", sec6Text='" + getSec6Text() + "'" +
            ", sec7Titre='" + getSec7Titre() + "'" +
            ", sec7Img='" + getSec7Img() + "'" +
            ", sec7ImgContentType='" + getSec7ImgContentType() + "'" +
            ", sec7Text='" + getSec7Text() + "'" +
            ", sec8Titre='" + getSec8Titre() + "'" +
            ", sec8Img='" + getSec8Img() + "'" +
            ", sec8ImgContentType='" + getSec8ImgContentType() + "'" +
            ", sec8Text='" + getSec8Text() + "'" +
            ", sec9Titre='" + getSec9Titre() + "'" +
            ", sec9Img='" + getSec9Img() + "'" +
            ", sec9ImgContentType='" + getSec9ImgContentType() + "'" +
            ", sec9Text='" + getSec9Text() + "'" +
            "}";
    }
}
