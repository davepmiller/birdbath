package com.davepmiller.tweetwrangle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter4j.*;

@Slf4j
@Service
public class ApiService {
  private TwitterService twitterService;

  @Autowired
  ApiService(TwitterService twitterService) {
    this.twitterService = twitterService;
  }

  String getStatus(String handle, int count) throws TwitterException {
    if (TwitterService.isValidHandle(handle)) {
      twitterService.loginIfNeeded();
      QueryResult queryResult = twitterService.sendQuery(handle, count);
      return buildResultString(queryResult);
    } else {
      String errMsg = String.format("Invalid username: %s", handle);
      log.error(errMsg);
      return errMsg;
    }
  }

  private static String buildResultString(QueryResult result) {
    StringBuilder sb = new StringBuilder();
    result.getTweets().forEach(status -> sb.append(getTweetLine(status)));
    return sb.toString();
  }

  private static String getTweetLine(Status s) {
    return String.format("@%s:%s<br />", s.getUser().getScreenName(), s.getText());
  }
}
