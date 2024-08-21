package com.eis.site.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.eis.site.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MembreEquipeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MembreEquipe.class);
        MembreEquipe membreEquipe1 = new MembreEquipe();
        membreEquipe1.setId(1L);
        MembreEquipe membreEquipe2 = new MembreEquipe();
        membreEquipe2.setId(membreEquipe1.getId());
        assertThat(membreEquipe1).isEqualTo(membreEquipe2);
        membreEquipe2.setId(2L);
        assertThat(membreEquipe1).isNotEqualTo(membreEquipe2);
        membreEquipe1.setId(null);
        assertThat(membreEquipe1).isNotEqualTo(membreEquipe2);
    }
}
