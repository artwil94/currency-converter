# Currency Converter

## Overview

Currency Converter is an Android application that allows users to convert currencies using real-time foreign exchange (FX) rates from the TransferGo API. The application supports multiple currencies, allows swapping between source and target currencies, and ensures accurate and up-to-date conversions.

## Features

- **Fetch FX Rates**: Retrieves real-time foreign exchange rates from the TransferGo API.
- **Select Currencies**: Allows users to select source (FROM) and target (TO) currencies from a predefined list.
- **Amount Conversion**: Converts the entered amount and updates the converted value.
- **Swap Currencies**: Allows users to swap the source and target currencies and updates the conversion rates accordingly.
- **Amount Conversion**: Converts the entered amount and updates the converted value.

## Installation

To set up the project locally, follow these steps:

1. **Clone the repository**:
    ```bash
    git clone https://github.com/yourusername/currency-converter.git
    cd rail-distance
    ```

2. **Open the project**: Open the project in Android Studio.

3. **Sync the project**: Ensure that all Gradle dependencies are downloaded and the project is synced successfully.

4. **Run the app**: Connect an Android device or start an emulator and run the app from Android Studio. Application requires network connection.

## Usage

1. **Select Currencies**: Open the app and select the source (FROM) and target (TO) currencies from lists.
2. **Enter Amount**: Input the amount to convert (defaults to 300.00 PLN).
3. **View Conversion**: The app will display the converted amount and the conversion rate.

## Technology Stack

- **Kotlin**: Programming language used for the application, including Kotlin Coroutines and Kotlin Flow for asynchronous programming
- **Android Architecture Components**: MVVM pattern
- **Jetpack Compose**: Used for build UI
- **Coroutines and Flow**: For asynchronous operations.
- **Retrofit**: For making API calls
- **Hilt Dagger**: Used for dependency injection
- **JUnit and Mockito**: For unit testing.
- **Timber**: For logging
