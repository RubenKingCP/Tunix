@startuml
title Music Streaming Application - Spring JPA Architecture (with Admin)

skinparam classAttributeIconSize 0
skinparam linetype ortho
skinparam packageStyle rectangle

' =====================
' DOMAIN LAYER (ENTITIES)
' =====================

package "Model (Entities)" {

    ' -------- ACCOUNT HIERARCHY --------
    abstract class Account
    class User
    class Artist
    class Admin

    Account <|-- User
    Account <|-- Artist
    Account <|-- Admin

    ' -------- MUSIC CORE --------
    class Song
    class Album
    class Genre

    Artist "1" --> "0..*" Song : creates
    Artist "1" --> "0..*" Album : creates

    Album "1" *-- "1..*" Song

    Song "0..*" -- "0..*" Genre

    ' -------- PLAYLIST SYSTEM --------
    class Playlist

    User "1" --> "0..*" Playlist : owns
    Playlist "0..*" -- "0..*" Song

    ' -------- LIBRARY --------
    class Library

    User "1" *-- "1" Library

    Library "0..*" --> Song
    Library "0..*" --> Album
    Library "0..*" --> Playlist

    ' -------- LISTENING HISTORY --------
    class ListeningHistory

    User "1" --> "0..*" ListeningHistory
    ListeningHistory --> Song

    ' -------- PLAYER --------
    class MusicPlayer
    MusicPlayer --> Song
}

' =====================
' PRESENTATION LAYER
' =====================

package "Controllers (UI)" {

    class MainController
    class MusicPlayerController
    class HomeController
    class LibraryController
    class TopBarController
    class PlaylistController
}

package "Views" {
    class MusicPlayerView
    class HomeView
    class LibraryView
    class TopBarView
    class PlaylistView
}

' UI links
MusicPlayerController -- MusicPlayer
MusicPlayerView -- MusicPlayerController
MainController -- MusicPlayerController

HomeView -- HomeController
MainController -- HomeController

LibraryView -- LibraryController
MainController -- LibraryController

PlaylistView -- PlaylistController
PlaylistController -- MainController

TopBarView -- TopBarController
MainController -- Account

' =====================
' SPRING BACKEND
' =====================

package "Spring Backend" {

    ' Controllers (REST)
    class AccountController
    class UserController
    class ArtistController
    class AdminController
    class SongController
    class AlbumController
    class PlaylistControllerAPI
    class LibraryControllerAPI
    class GenreController
    class ListeningHistoryController

    ' Services
    class AccountService
    class UserService
    class ArtistService
    class AdminService
    class SongService
    class AlbumService
    class PlaylistService
    class LibraryService
    class GenreService
    class ListeningHistoryService

    ' Repositories (JPA)
    class AccountRepository
    class UserRepository
    class ArtistRepository
    class AdminRepository
    class SongRepository
    class AlbumRepository
    class PlaylistRepository
    class LibraryRepository
    class GenreRepository
    class ListeningHistoryRepository
}

' Controller → Service
AccountController --> AccountService
UserController --> UserService
ArtistController --> ArtistService
AdminController --> AdminService
SongController --> SongService
AlbumController --> AlbumService
PlaylistControllerAPI --> PlaylistService
LibraryControllerAPI --> LibraryService
GenreController --> GenreService
ListeningHistoryController --> ListeningHistoryService

' Service → Repository
AccountService --> AccountRepository
UserService --> UserRepository
ArtistService --> ArtistRepository
AdminService --> AdminRepository
SongService --> SongRepository
AlbumService --> AlbumRepository
PlaylistService --> PlaylistRepository
LibraryService --> LibraryRepository
GenreService --> GenreRepository
ListeningHistoryService --> ListeningHistoryRepository

' Repository → Entities
AccountRepository --> Account
UserRepository --> User
ArtistRepository --> Artist
AdminRepository --> Admin
SongRepository --> Song
AlbumRepository --> Album
PlaylistRepository --> Playlist
LibraryRepository --> Library
GenreRepository --> Genre
ListeningHistoryRepository --> ListeningHistory

PlaylistController -- Playlist
LibraryController -- Library
Library -- LibraryControllerAPI
Playlist -- PlaylistControllerAPI

@enduml