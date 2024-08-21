package com.eis.site.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.eis.site.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PageAccueilTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PageAccueil.class);
        PageAccueil pageAccueil1 = new PageAccueil();
        pageAccueil1.setId(1L);
        PageAccueil pageAccueil2 = new PageAccueil();
        pageAccueil2.setId(pageAccueil1.getId());
        assertThat(pageAccueil1).isEqualTo(pageAccueil2);
        pageAccueil2.setId(2L);
        assertThat(pageAccueil1).isNotEqualTo(pageAccueil2);
        pageAccueil1.setId(null);
        assertThat(pageAccueil1).isNotEqualTo(pageAccueil2);
    }
}
