package com.test.traquer2.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.test.traquer2.web.rest.TestUtil;

public class IncidentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Incident.class);
        Incident incident1 = new Incident();
        incident1.setId(1L);
        Incident incident2 = new Incident();
        incident2.setId(incident1.getId());
        assertThat(incident1).isEqualTo(incident2);
        incident2.setId(2L);
        assertThat(incident1).isNotEqualTo(incident2);
        incident1.setId(null);
        assertThat(incident1).isNotEqualTo(incident2);
    }
}
