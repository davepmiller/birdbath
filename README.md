# tweet-wranger
Grab all tweets and store in a local postgres instance with persistance

## Requirements
* [Install Docker](https://docs.docker.com/docker-for-mac/install/)

* A yml config file set up with your Twitter API credentials
  ```yaml
  consumer_key: <your consumer key>
  consumer_secret: <your consumer secret>
  access_token: <your access token>
  access_token_secret: <your access token secret>
  screen_name: <the screen name you'd like to gather tweets from>
  ```
