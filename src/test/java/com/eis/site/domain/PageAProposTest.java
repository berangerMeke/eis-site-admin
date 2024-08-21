package com.eis.site.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.eis.site.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PageAProposTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PageAPropos.class);
        PageAPropos pageAPropos1 = new PageAPropos();
        pageAPropos1.setId(1L);
        PageAPropos pageAPropos2 = new PageAPropos();
        pageAPropos2.setId(pageAPropos1.getId());
        assertThat(pageAPropos1).isEqualTo(pageAPropos2);
        pageAPropos2.setId(2L);
        assertThat(pageAPropos1).isNotEqualTo(pageAPropos2);
        pageAPropos1.setId(null);
        assertThat(pageAPropos1).isNotEqualTo(pageAPropos2);
    }
}
