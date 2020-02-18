package com.davepmiller.tweetwrangle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import twitter4j.TwitterException;

@RestController
public class Controller {
  private final ApiService apiService;

  @Autowired
  Controller(ApiService apiService) {
    this.apiService = apiService;
  }

  @GetMapping("/gettweets/{handle}/{count}")
  String getTweets(@PathVariable String handle, @PathVariable int count)
      throws TwitterException {
    return apiService.getStatus(handle, count);
  }
}
