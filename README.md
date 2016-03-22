Utils is a collection of many functions useful in Android projects during development. Android developers are welcome to use the functions into their projects.

* [Installation](#Installation)
* [Functions](#Functions)
* [Contributing](#contributing)

# Installation

Add Gradle dependency:

```gradle
dependencies {
   compile 'com.licon.utils:utilslib:1.0.0'
}
```

# Functions

[ AppUtils ]

* AppUtils.openBrowser(context, url) : It opens the browser with the input-url using intent.
* AppUtils.shareDataOnSocialSite(data, url, title, activity) : It shares any data/url to facebook, twitter, linkedin & google+
* AppUtils.createAppDirIfNotExists(folder_name) : It creates a folder inside the external storage.

# Contributing
If you want to contribute into this project then fork it & start to add some functions that can be utilized in the Android projects.Note that all pull request should go to `develop` branch. Also, if you have some suggestion then please email me to the following address.

Developer
---------

* [Khairul Alam Licon](https://github.com/liconrepo) (<licon.cseru10@gmail.com>)


License
--------

   Copyright 2016 Khairul Alam Licon

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.