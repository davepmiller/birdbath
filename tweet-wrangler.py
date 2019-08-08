#!/usr/local/bin/python3
import tweepy
import yaml

def parse_config(config_path):
  with open(config_path, 'r') as ymlfile:
    try:
      return yaml.safe_load(ymlfile)
    except yaml.YAMLError as e:
      print(e)

config = parse_config('./.get-tweets.yml')
auth = tweepy.OAuthHandler(config['consumer_key'], config['consumer_secret'])
auth.set_access_token(config['access_token'], config['access_token_secret'])
api = tweepy.API(auth)
tweets = api.user_timeline(screen_name = config['screen_name'])
for tweet in tweets:
    print(tweet)
