package com.eis.site.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.eis.site.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PageFormationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PageFormation.class);
        PageFormation pageFormation1 = new PageFormation();
        pageFormation1.setId(1L);
        PageFormation pageFormation2 = new PageFormation();
        pageFormation2.setId(pageFormation1.getId());
        assertThat(pageFormation1).isEqualTo(pageFormation2);
        pageFormation2.setId(2L);
        assertThat(pageFormation1).isNotEqualTo(pageFormation2);
        pageFormation1.setId(null);
        assertThat(pageFormation1).isNotEqualTo(pageFormation2);
    }
}
