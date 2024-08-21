package com.eis.site.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.eis.site.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PageServiceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PageService.class);
        PageService pageService1 = new PageService();
        pageService1.setId(1L);
        PageService pageService2 = new PageService();
        pageService2.setId(pageService1.getId());
        assertThat(pageService1).isEqualTo(pageService2);
        pageService2.setId(2L);
        assertThat(pageService1).isNotEqualTo(pageService2);
        pageService1.setId(null);
        assertThat(pageService1).isNotEqualTo(pageService2);
    }
}
