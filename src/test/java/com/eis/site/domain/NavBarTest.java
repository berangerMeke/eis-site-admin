package com.eis.site.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.eis.site.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NavBarTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NavBar.class);
        NavBar navBar1 = new NavBar();
        navBar1.setId(1L);
        NavBar navBar2 = new NavBar();
        navBar2.setId(navBar1.getId());
        assertThat(navBar1).isEqualTo(navBar2);
        navBar2.setId(2L);
        assertThat(navBar1).isNotEqualTo(navBar2);
        navBar1.setId(null);
        assertThat(navBar1).isNotEqualTo(navBar2);
    }
}
