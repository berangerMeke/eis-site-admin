package com.eis.site.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.eis.site.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PageEquipeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PageEquipe.class);
        PageEquipe pageEquipe1 = new PageEquipe();
        pageEquipe1.setId(1L);
        PageEquipe pageEquipe2 = new PageEquipe();
        pageEquipe2.setId(pageEquipe1.getId());
        assertThat(pageEquipe1).isEqualTo(pageEquipe2);
        pageEquipe2.setId(2L);
        assertThat(pageEquipe1).isNotEqualTo(pageEquipe2);
        pageEquipe1.setId(null);
        assertThat(pageEquipe1).isNotEqualTo(pageEquipe2);
    }
}
