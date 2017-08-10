Conjugaison Française
=====================

<a href='https://play.google.com/store/apps/details?id=com.xengar.android.conjugaisonfrancaise'><img alt='Get it on Google Play' src='https://play.google.com/intl/en_us/badges/images/generic/en_badge_web_generic.png' height=90px/></a>

![Scheme](/readmeImages/Screenshot_20170810-125823.png)
![Scheme](/readmeImages/Screenshot_20170810-125850.png)


Android application to learn french verb tenses.


Pre-requisites
--------------
- Android SDK 25 or Higher
- [Color Picker Module](http://www.materialdoc.com/color-picker/)


References
----------
- http://bescherelle.com/conjugueur.php?term=trouver
- http://www.conjugaison.com/grammaire/conjugaison.html
- http://monsu.desiderio.free.fr/atelier/freqverb.html
- http://dictionnaire.reverso.net/francais-portugais/ouvrir


# Set up

Color Picker Module
-------------------

1.  Download repository from
  ```
  git clone https://android.googlesource.com/platform/frameworks/opt/colorpicker  (preferred) or
  git clone https://xengar@bitbucket.org/xengar/colorpicker.git
  ```

2. Import a new module in android studio with the New/import module menu,
   choosing the path where the project was cloned.
   Remove the empty "colorpicker" directory if needed.

3. Add dependency to app/build.gradle
   ```
   apply plugin: 'com.android.application'

   android {
       ...
   }

   dependencies {
       compile project(':colorpicker')
       ...
   }
   ```

4. Add compileSdkVersion and buildToolsVersion in colorpicker/build.gradle to avoid
   Error buildToolsVersion is not specified. Try to use latest versions.
   ```
    apply plugin: 'com.android.library'

    android {

        compileSdkVersion 26
        buildToolsVersion "26.0.1"

        sourceSets.main {
            manifest.srcFile 'AndroidManifest.xml'
            java.srcDirs = ['src']
            res.srcDirs = ['res']
        }
    }
   ```

5. Commit the modified changes in the colorpicker module.
   (There is no remote repository to push. Keep it local.)
   ```
   cd englishverbs/colorpicker
   git add -A
   git commit -m "Import colorpicker module into English Verbs project"
   ```

## License

Copyright 2017 Angel Garcia

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.


