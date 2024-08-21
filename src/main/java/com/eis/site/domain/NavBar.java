package com.eis.site.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A NavBar.
 */
@Entity
@Table(name = "nav_bar")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NavBar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Lob
    @Column(name = "logo")
    private byte[] logo;

    @Column(name = "logo_content_type")
    private String logoContentType;

    @Column(name = "menu_1")
    private String menu1;

    @Column(name = "menu_2")
    private String menu2;

    @Column(name = "menu_3")
    private String menu3;

    @Column(name = "menu_4")
    private String menu4;

    @Column(name = "menu_5")
    private String menu5;

    @Column(name = "menu_6")
    private String menu6;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public NavBar id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getLogo() {
        return this.logo;
    }

    public NavBar logo(byte[] logo) {
        this.setLogo(logo);
        return this;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getLogoContentType() {
        return this.logoContentType;
    }

    public NavBar logoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
        return this;
    }

    public void setLogoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
    }

    public String getMenu1() {
        return this.menu1;
    }

    public NavBar menu1(String menu1) {
        this.setMenu1(menu1);
        return this;
    }

    public void setMenu1(String menu1) {
        this.menu1 = menu1;
    }

    public String getMenu2() {
        return this.menu2;
    }

    public NavBar menu2(String menu2) {
        this.setMenu2(menu2);
        return this;
    }

    public void setMenu2(String menu2) {
        this.menu2 = menu2;
    }

    public String getMenu3() {
        return this.menu3;
    }

    public NavBar menu3(String menu3) {
        this.setMenu3(menu3);
        return this;
    }

    public void setMenu3(String menu3) {
        this.menu3 = menu3;
    }

    public String getMenu4() {
        return this.menu4;
    }

    public NavBar menu4(String menu4) {
        this.setMenu4(menu4);
        return this;
    }

    public void setMenu4(String menu4) {
        this.menu4 = menu4;
    }

    public String getMenu5() {
        return this.menu5;
    }

    public NavBar menu5(String menu5) {
        this.setMenu5(menu5);
        return this;
    }

    public void setMenu5(String menu5) {
        this.menu5 = menu5;
    }

    public String getMenu6() {
        return this.menu6;
    }

    public NavBar menu6(String menu6) {
        this.setMenu6(menu6);
        return this;
    }

    public void setMenu6(String menu6) {
        this.menu6 = menu6;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NavBar)) {
            return false;
        }
        return id != null && id.equals(((NavBar) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NavBar{" +
            "id=" + getId() +
            ", logo='" + getLogo() + "'" +
            ", logoContentType='" + getLogoContentType() + "'" +
            ", menu1='" + getMenu1() + "'" +
            ", menu2='" + getMenu2() + "'" +
            ", menu3='" + getMenu3() + "'" +
            ", menu4='" + getMenu4() + "'" +
            ", menu5='" + getMenu5() + "'" +
            ", menu6='" + getMenu6() + "'" +
            "}";
    }
}
