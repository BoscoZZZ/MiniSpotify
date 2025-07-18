# MiniSpotify

A full-stack music streaming application that mimics Spotify's core functionality. Built with modern Android development practices and a robust backend API.

## Project Structure

```
MiniSpotify/
├── spotify_backend/          # Backend API Server
│   ├── src/main/kotlin/      # Kotlin source files
│   ├── src/main/resources/   # Static resources and data
│   └── build.gradle.kts      # Backend build configuration
└── spotify_fe/               # Android Frontend App
    ├── app/src/main/java/    # Android source files
    ├── app/src/main/res/     # Android resources
    └── app/build.gradle      # Frontend build configuration
```

## Features

### Backend (Ktor Server)
- RESTful API for music data
- Static file serving for audio files
- JSON-based data storage for albums and playlists
- Support for multiple music formats
- CORS enabled for cross-origin requests

### Frontend (Android App)
- Modern Material Design UI with Jetpack Compose
- Bottom navigation with multiple screens:
  - Home: Browse music sections and albums
  - Favorites: Manage favorite albums
  - Playlists: View and play album tracks
- Music player with play/pause controls
- Local database for favorites using Room
- Dependency injection with Hilt
- Network requests with Retrofit
- Image loading with Coil

## Technology Stack

### Backend
- **Kotlin**: Primary programming language
- **Ktor**: Web framework for building APIs
- **Gradle**: Build automation
- **JSON**: Data storage format

### Frontend
- **Kotlin**: Primary programming language
- **Jetpack Compose**: Modern UI toolkit
- **Hilt**: Dependency injection
- **Room**: Local database
- **Retrofit**: HTTP client
- **Navigation Component**: Screen navigation
- **ExoPlayer**: Media playback
- **Coil**: Image loading

## Getting Started

### Prerequisites
- Android Studio Arctic Fox or later
- JDK 11 or higher
- Android SDK (API level 23+)

### Backend Setup
1. Navigate to the backend directory:
   ```bash
   cd spotify_backend
   ```

2. Run the server:
   ```bash
   ./gradlew run
   ```

3. The server will start on `http://localhost:8080`

### Frontend Setup
1. Open the `spotify_fe` directory in Android Studio

2. Update the server URL in network configuration if needed

3. Build and run the Android app on an emulator or device

## API Endpoints

### Music Data
- `GET /feed` - Get all music sections and albums
- `GET /playlists/{id}` - Get playlist details by album ID

### Static Files
- `GET /songs/{filename}` - Stream audio files

## Database Schema

### Albums Table
- `id`: Primary key
- `album`: Album name
- `year`: Release year
- `cover`: Album cover image URL
- `artists`: Artist names
- `description`: Album description

## App Architecture

The Android app follows modern architectural patterns:

- **MVVM**: Model-View-ViewModel pattern
- **Repository Pattern**: Data layer abstraction
- **Single Activity**: Navigation with fragments
- **Dependency Injection**: Hilt for managing dependencies
- **Reactive Programming**: StateFlow for state management

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Submit a pull request

## License

This project is for educational purposes and demonstrates modern Android development practices.

## Music Credits

The application includes sample music tracks from various artists for demonstration purposes. All music rights belong to their respective owners.
