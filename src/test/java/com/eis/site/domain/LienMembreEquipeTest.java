package com.eis.site.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.eis.site.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LienMembreEquipeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LienMembreEquipe.class);
        LienMembreEquipe lienMembreEquipe1 = new LienMembreEquipe();
        lienMembreEquipe1.setId(1L);
        LienMembreEquipe lienMembreEquipe2 = new LienMembreEquipe();
        lienMembreEquipe2.setId(lienMembreEquipe1.getId());
        assertThat(lienMembreEquipe1).isEqualTo(lienMembreEquipe2);
        lienMembreEquipe2.setId(2L);
        assertThat(lienMembreEquipe1).isNotEqualTo(lienMembreEquipe2);
        lienMembreEquipe1.setId(null);
        assertThat(lienMembreEquipe1).isNotEqualTo(lienMembreEquipe2);
    }
}
