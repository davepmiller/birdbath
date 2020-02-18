package com.davepmiller.tweetwrangle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter4j.*;
import twitter4j.auth.AccessToken;

@Service
public class TwitterService {
  Properties props;
  private static final Twitter TWITTER = TwitterFactory.getSingleton();
  private static final String VALID_HANDLE = "^[a-zA-Z0-9_]{1,15}$";

  @Autowired
  TwitterService(Properties props) {
    this.props = props;
  }

  void loginIfNeeded() {
    if (needToLogin()) {
      TWITTER.setOAuthConsumer(props.getKey(), props.getSecret());
      TWITTER.setOAuthAccessToken(new AccessToken(props.getToken(), props.getTokenSecret()));
    }
  }

  QueryResult sendQuery(String handle, int count) throws TwitterException {
    String queryString = String.format("from:%s", handle);
    Query query = new Query(queryString).count(count);
    return TWITTER.search(query);
  }

  static boolean isValidHandle(String handle) {
    return handle.matches(VALID_HANDLE);
  }

  static private boolean needToLogin() {
    return !TWITTER.getAuthorization().isEnabled();
  }
}
