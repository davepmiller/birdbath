package com.davepmiller.tweetwrangle;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class ApplicationTests {
  @Autowired
  Properties props;

  @Test
  void whenContextLoads_propertiesExist() {
    assertThat(props.getKey()).doesNotStartWith("${");
    assertThat(props.getSecret()).doesNotStartWith("${");
    assertThat(props.getToken()).doesNotStartWith("${");
    assertThat(props.getTokenSecret()).doesNotStartWith("${");
  }
}
