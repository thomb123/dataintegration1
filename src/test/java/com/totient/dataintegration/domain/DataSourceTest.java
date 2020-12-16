package com.totient.dataintegration.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.totient.dataintegration.web.rest.TestUtil;

public class DataSourceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DataSource.class);
        DataSource dataSource1 = new DataSource();
        dataSource1.setId(1L);
        DataSource dataSource2 = new DataSource();
        dataSource2.setId(dataSource1.getId());
        assertThat(dataSource1).isEqualTo(dataSource2);
        dataSource2.setId(2L);
        assertThat(dataSource1).isNotEqualTo(dataSource2);
        dataSource1.setId(null);
        assertThat(dataSource1).isNotEqualTo(dataSource2);
    }
}
