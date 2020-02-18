package com.davepmiller.tweetwrangle;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "twitter")
public class Properties {
  private String key;
  private String secret;
  private String token;
  private String tokenSecret;
}


