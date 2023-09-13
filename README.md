# RickAndMorty
App that shows characters of Rick And Morty.
<br><br>
[![Maintainability](https://api.codeclimate.com/v1/badges/dd754f1953ff39dcfc4f/maintainability)](https://codeclimate.com/github/TheBlackBit/RickAndMorty/maintainability) [![Test Coverage](https://api.codeclimate.com/v1/badges/dd754f1953ff39dcfc4f/test_coverage)](https://codeclimate.com/github/TheBlackBit/RickAndMorty/test_coverage)

<img src="https://github.com/TheBlackBit/resources/blob/main/randm_gif.gif" alt="demo" height="300" />

## Configuration
After cloning the repository you have to create the file `secrets.defaults.properties` in the root of the project with the next properties:

- API_URL="https://rickandmortyapi.com/graphql" (In this case we expose the url because is public)
- ALIAS = Example
- KEY_PASS = Example
- KEY_ALIAS = Example
<br>
Looks like this:
<img src="https://github.com/TheBlackBit/resources/blob/main/r%26m_1.png" alt="example" width="400" />


The ALIAS, KEY_PASS, KEY_ALIAS keys are optional, if you want to run the app in build type release but you have to generate a keystore and store it in app module with the name `keystore.jks` and when you generate your keystore.jks add the keys that you store in `secrets.defaults.properties`.



## Modularization

- `:build-logic`
- `:app`
- `:core:local-storage`
- `:core:network`
- `:core:model`
- `:core:util`
- `:core:testing`
- `:core:resources`
- `:feature:character`

## build-logic
The build-logic module contains all the gradle configurations. [See more](https://developer.squareup.com/blog/herding-elephants/).

### app module
Contains the Application class and the Launcher Activity.


### local-storage module
Contains the local storage with the [Room](https://developer.android.com/training/data-storage/room?hl=es-419) library.

### network module
Contains the networking using GraphQl with [Apollo3](https://www.apollographql.com/docs/kotlin) client library.

### model module
Contains model classes.

### util module
Contains common Util classes.

### testing module
Contains helper classes for unit test and instrumentationTest.

### resources module
Contains all the resources of the app in order to have a single source of truth.

### character module
Contains the Character feature with clean architecture and MVVM architectural pattern (All the features has the same structure).

## Catalogs
[Catalogs](https://developer.android.com/build/migrate-to-catalogs) is implemented in order to handle all the libraries.

## Dependency injection
For dependency injection [Dagger hilt](https://developer.android.com/training/dependency-injection/hilt-android?hl=es-419) is used

## Cache
cache is implemented when the device is offline using Room

## Pagination
Pagination implemented with [Paging3](https://developer.android.com/jetpack/androidx/releases/paging?hl=es-419)

## Fastlane
Fastlane is used in order to handle CI/CD


## Technologies stack
- Kotlin
- Jetpack Compose
- MVVM
- Dagger Hilt
- MVVM
- Flows
- Paging3
- Graphql + Apollo3
- Kotlin coroutines
- Fastlane
