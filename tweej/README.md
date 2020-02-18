# Java Implementation
Gather some tweets via rest call, using [twitter4j](http://twitter4j.org/en/) and [spring](https://spring.io/)

## Requirements
### Setup [Twitter developer account](https://developer.twitter.com/)
    * Add app to your profile (this provides key and secret)
    * Use key and secret to programmatically log in and request token and token secret

### ENV
Add these variables to your startup script (.bashrc, .zshrc, etc)
```bash
export TWITTER_API_KEY=''
export TWITTER_API_SECRET=''
export TWITTER_OATH_TOKEN=''
export TWITTER_OATH_TOKEN_SECRET=''
```

Make sure you source your environment (or restart your IDE)
