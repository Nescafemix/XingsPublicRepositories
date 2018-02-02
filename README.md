# XingsPublicRepositories

Configuration:

The application should work without configure a personal access token,
but if your API request limit exceeds, the app will give error loading data.
You can configure an access token on the ABSRetrofitApi.java file.
**WARNING: You ONLY need to configure your token in the
PERSONAL_ACCESS_TOKEN constant**

Notes:

There was a security issue with the app. My personal access token
generated to access the Github API was uploaded to the repository.
This security issue had a lowlevel risk because the repository was
private and the access token was invalidated before the repository
was configured as public.