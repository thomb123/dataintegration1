package com.totient.dataintegration.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.totient.dataintegration.web.rest.TestUtil;

public class ProcessGroupTemplateTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProcessGroupTemplate.class);
        ProcessGroupTemplate processGroupTemplate1 = new ProcessGroupTemplate();
        processGroupTemplate1.setId(1L);
        ProcessGroupTemplate processGroupTemplate2 = new ProcessGroupTemplate();
        processGroupTemplate2.setId(processGroupTemplate1.getId());
        assertThat(processGroupTemplate1).isEqualTo(processGroupTemplate2);
        processGroupTemplate2.setId(2L);
        assertThat(processGroupTemplate1).isNotEqualTo(processGroupTemplate2);
        processGroupTemplate1.setId(null);
        assertThat(processGroupTemplate1).isNotEqualTo(processGroupTemplate2);
    }
}
