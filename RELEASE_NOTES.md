# AndroidMaterialValidation - RELEASE NOTES

## Version 2.1.0 (Oct. 1st 2017)

A feature release, which introduces the following changes:

- Updated dependency "AndroidUtil" to version 1.18.0.
- Updated AppCompat v7 support libary to version 26.1.0.
- Updating the dependencies required to increase the `minSdkVersion` to API level 14.

## Version 2.0.6 (Sep. 20th 2017)

A minor release, which introduces the following changes:

- Updated `targetSdkVersion` to API level 26 (Android 8.0).
- Updated dependency "AndroidUtil" to version 1.17.1.
- Updated AppCompat v7 support library to version 25.3.1.

## Version 2.0.5 (Jan. 25th 2017)

A minor release, which introduces the following changes:

- Updated `targetSdkVersion` to API level 25 (Android 7.1).
- Updated dependency "AndroidUtil" to version 1.12.3.
- Updated AppCompat v7 support library to version 25.1.0.

## Version 2.0.4 (Sep. 11th 2016)

A minor release, which introduces the following changes:

- Updated `targetSdkVersion` to API level 24 (Android 7.0).
- Updated dependency "AndroidUtil" to version 1.11.1.
- Updated AppCompat v7 support library to version 24.2.0.

## Version 2.0.3 (Sep. 7th 2016)

A bugfix release, which fixes the following issues:

- https://github.com/michael-rapp/AndroidMaterialValidation/issues/1
- Updated dependency "AndroidUtil" to version 1.11.0.
- Updated AppCompat support library to version 23.4.0.

## Version 2.0.2 (Mar. 17th 2016)

A minor release, which introduces the following changes:

- Updated the dependency "AndroidUtil" to version 1.4.5.
- Fixed some deprecation warnings.

## Version 2.0.1 (Feb. 24th 2016)

A minor release, which introduces the following changes:

- The library is from now on distributed under the Apache License version 2.0. 
- Updated the dependency "AndroidUtil" to version 1.4.3.
- Updated the AppCompat support library to version 23.1.1.
- Minor changes of the example app.

## Version 2.0.0 (Oct. 18th 2015)

A major release, which introduces the following changes:

- The project has been migrated from the legacy Eclipse ADT folder structure to Android Studio. It now uses the Gradle build system and the library as well as the example app are contained by one single project.
- The library can now be added to Android apps using the Gradle dependency `com.github.michael-rapp:android-material-validation:2.0.0`

## Version 1.0.0 (June 7th 2015)

The first stable release, which initially provides the following features:
	
- The library provides a custom `EditText` implementation, which can be validated according to the Material Design guidelines.
- The library provides a custom `EditText` implementation, which can not only be validated according to the Material Design guidelines, but also allows to visualize the password strength, depending on customizable constraints.
- The library provides a custom `Spinner` implementation, which can be validated according to the Material Design guidelines.
- The library includes a large number of pre-defined validators, for example for validating phone numbers, e-mail addresses, domain names etc.