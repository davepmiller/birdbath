#!/usr/local/bin/python3
import tweepy
import yaml
import json
import sqlite3
from sqlite3 import Error
import pathlib
import os

CREATE_TWEET_TABLE_QUERY = """
    CREATE TABLE IF NOT EXISTS "tweet" (
        "id"	INTEGER NOT NULL UNIQUE,
        "author_id"	INTEGER NOT NULL,
        "screen_name"	TEXT NOT NULL,
        "created_at"	TEXT NOT NULL,
        "text"	TEXT NOT NULL,
        "json"	TEXT NOT NULL,
        PRIMARY KEY("id")
    );
"""

INSERT_TWEET_QUERY = """
    INSERT INTO tweet(
        id,
        author_id,
        screen_name,
        created_at,
        text,
        json)
    VALUES(?,?,?,?,?,?)
"""

def parse_config(config_path):
    with open(config_path, 'r') as ymlfile:
        try:
            return yaml.safe_load(ymlfile)
        except yaml.YAMLError as e:
            print(e)

def db_exists(db_path):
    path = pathlib.Path(db_path)
    return path.exists() and path.is_file()

def create_directory(db_path):
    dir_name = os.path.dirname(os.path.abspath(db_path))
    print(f'Creating directory: {dir_name}')
    print(f'Open {db_path} in sqlite browser to browse data')
    os.mkdir(dir_name)

def get_tweet_entry(tweet):
    return (
        tweet.id,
        tweet.author.id,
        tweet.author.screen_name,
        tweet.created_at,
        tweet.text,
        json.dumps(tweet._json))

def get_db_connection(dp_path):
    conn = None
    try:
        conn = sqlite3.connect(dp_path)
    except Error as e:
        print(e)
    return conn

def add_tweet_to_db(connection, tweet):
    tweet_entry = get_tweet_entry(tweet)
    cursor = connection.cursor()
    cursor.execute(INSERT_TWEET_QUERY, tweet_entry)
    return cursor.lastrowid

def populate_db(db_path, tweets):
    if not db_exists(db_path):
        create_directory(db_path)

    conn = get_db_connection(db_path)
    with conn:
        conn.cursor().execute(CREATE_TWEET_TABLE_QUERY)
        for tweet in tweets:
            add_tweet_to_db(conn, tweet)

def get_twitter_api(auth_config):
    auth = tweepy.OAuthHandler(
        auth_config.get('consumer_key'),
        auth_config.get('consumer_secret'))
    auth.set_access_token(
        auth_config.get('access_token'),
        auth_config.get('access_token_secret'))
    api = tweepy.API(auth)
    return api

def get_tweets(screen_name):
    all_tweets = []
    new_tweets = api.user_timeline(screen_name = screen_name, count = 500)
    all_tweets.extend(new_tweets)
    oldest = all_tweets[-1].id - 1
    while len(new_tweets) > 0:
        print(f'gettings tweets before {oldest}')
        new_tweets = api.user_timeline(screen_name = screen_name, count = 500, max_id = oldest)
        all_tweets.extend(new_tweets)
        oldest = all_tweets[-1].id - 1
        print(f'{len(all_tweets)} downloaded so far')
    return all_tweets

if __name__ == '__main__':
    home_dir = os.environ['HOME']
    config = parse_config(f'{home_dir}/.get-tweets.yml')
    api = get_twitter_api(config['auth'])
    tweets = get_tweets(config['screen_name'])
    db_path = home_dir + config['db_path'];
    populate_db(db_path, tweets)
