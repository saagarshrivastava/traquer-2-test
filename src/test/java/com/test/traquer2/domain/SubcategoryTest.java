package com.test.traquer2.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.test.traquer2.web.rest.TestUtil;

public class SubcategoryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Subcategory.class);
        Subcategory subcategory1 = new Subcategory();
        subcategory1.setId(1L);
        Subcategory subcategory2 = new Subcategory();
        subcategory2.setId(subcategory1.getId());
        assertThat(subcategory1).isEqualTo(subcategory2);
        subcategory2.setId(2L);
        assertThat(subcategory1).isNotEqualTo(subcategory2);
        subcategory1.setId(null);
        assertThat(subcategory1).isNotEqualTo(subcategory2);
    }
}
