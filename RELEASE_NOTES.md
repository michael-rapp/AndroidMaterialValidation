# AndroidMaterialValidation - RELEASE NOTES

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