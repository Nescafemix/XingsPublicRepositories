# Xing's Public Repositories

Xing's Public Repositories is an awesome app which list Xing's public
repositories on Github.

![](https://media1.giphy.com/media/i4OfVOFke0jxm/giphy.gif)

## Considerations

This Test was done applying Clean code architecture.
Due to the short time for testing, and the requirements,
the generated structure is very simple. I have tried to simplify to
not fall into the overengineering based on what it was really needed.
If there is any doubt I will be happy to defend my proposal.

The feedback is welcome :)

## Configuration

The application should work without configure a personal access token,
but if your API request limit exceeds, the app will give error loading data.
You can configure an access token on the ABSRetrofitApi.java file.
**WARNING: You ONLY need to configure your token in the
PERSONAL_ACCESS_TOKEN constant**

## Special notes

There was a security issue with the app. My personal access token
generated to access the Github API was uploaded to the repository.
This security issue had a low level risk because the repository was
private and the access token was invalidated before the repository
was configured as public.

## License

Copyright Joan Fuentes 2018

This file is part of some open source application.

Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.