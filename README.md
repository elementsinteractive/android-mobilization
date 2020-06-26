![Platform](https://img.shields.io/badge/platform-Android-blue.svg)
![CI](https://github.com/elementsinteractive/android-mobilization/workflows/CI/badge.svg)

Elements Android Mobilization documentation (Work In Progress)
==============================================================

#### Introduction
_Mobilization_:
```
Mobilization, in military terminology, is the act of assembling and readying troops and supplies for war.
```

This repository contains a number of libraries and collections of utilities to ready and supplement apps at Elements.

#### Artifacts

The following artifacts are available:

| Artifact name | Description  | Dependency |
|-|-|-|
| logging-api  | Interface for logging | `implementation 'nl.elements.mobilization:logging-api:VERSION'` |
| logging  | Actual logging implementation | `implementation 'nl.elements.mobilization:logging:VERSION'` |

#### Development requirements

* Android Studio 4.0
* Android SDK 6.0 (23) and above

### Code style

To guarantee consistent styling we use the [spotless](https://github.com/diffplug/spotless) gradle plugin along with [ktlint](https://github.com/pinterest/ktlint).  
Static code analysis is performed with detekt.

If a pull request does not pass all its checks related to both code style and code smells you can fix those locally first.
By running `./gradlew spotlessApply` the code styles will be fixed automatically.  
By running `./gradlew detektCheck` you can see which code smells are present.

#### Contributions
All contributions are welcome and will be reviewed by the Elements team.  
If you find a bug or have a suggestion feel free to file a [new](https://github.com/elementsinteractive/android-mobilization/issues/new) issue.
