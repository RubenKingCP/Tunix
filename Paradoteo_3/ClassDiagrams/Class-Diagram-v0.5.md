@startuml

title Spotify-like Desktop App (Clean Architecture - Fixed)

skinparam classAttributeIconSize 0
skinparam linetype ortho
skinparam packageStyle rectangle
skinparam shadowing false

left to right direction

' =====================================================
' FRONTEND LAYER (WRAPPER)
' =====================================================

package "Frontend" {

    ' =====================================================
    ' MODEL LAYER
    ' =====================================================

    package "Model Layer" {
' -------- ACCOUNT HIERARCHY --------
    abstract class Account {
        # id : String
        # name: String
        # email: String
        # status : Boolean
    }
    class User {
        # displayName: String
    }

    class Artist {
        - bio : String
        - followersCount : int
    }

    class Admin {
        
    }
    class ArtistRequest{
        - userId : String
        - status : String
        --
        + getUserId() : String
        + getStatus() : String
    }

    class LibraryAsset
    User "1"-->"0...1" ArtistRequest : files
    Admin --> ArtistRequest : inspects

    Account <|-- User
    Account <|-- Artist
    Account <|-- Admin

    interface ILibraryAsset {
        + getTitle() : String
        + getId() : int
    }

    Song ..|> ILibraryAsset
    Album ..|> ILibraryAsset
    Playlist ..|> ILibraryAsset

    ' -------- MUSIC CORE --------
    class Song {
        - artist : List<Artist> 
        - genre : List<String>
        --
        + Song(title : String, artist : List<Artist>, genres : List<String>)
        + getTitle() : String
        + getArtist() : Artist
        + getGenres() : List<String>
    }

    class Album

    Artist "1" --> "0..*" Song : creates
    Artist "1" --> "0..*" Album : creates

    Album "1" *-- "1..*" Song

    ' -------- PLAYLIST SYSTEM --------
    class Playlist {
        - songs : List<Song>
        - creator : User
        - coauthors : List<User>

        + getSongs() : List<Song>
        + getCreator() : User
        + getCoauthors() : List<User>
    }

    User "1" --> "0..*" Playlist : owns
    Playlist "0..*" o-- "0..*" Song
    'Album --|> Playlist

    ' -------- LIBRARY --------
    class Library {
        - assets : List<ILibraryAsset>
    }

    User "1" *-- "1" Library

    Library -- ILibraryAsset

    ' -------- LISTENING HISTORY --------
    class ListeningHistory { 
        - songs : Song[]
        - Playlists : Playlist[]
        - Albums : Album[]
        --
    }

    User "1" --> "0..*" ListeningHistory
    ListeningHistory o-- Song
    ListeningHistory o-- Playlist
    ListeningHistory o-- Album

    class Favorites {
        - songs : List<Song>
    }

    User "1" *-- "1" Favorites
    Favorites o-- Song
}

    ' =====================================================
    ' VIEW LAYER
    ' =====================================================

    package "View Layer" {

        interface View
        interface LibraryAssetView

        ' Persistent UI
        class TopBarView{
        + onHomeClicked() : void
        + onSearchClicked() : void
        + onProfileClicked() : void
    }
       class MusicPlayerView{
        + onPlayClicked() : void
        + onPauseClicked() : void
        + onNextClicked() : void
        + onPreviousClicked() : void
        + onVolumeChanged() : void
        + onShuffleClicked() : void
        + onRepeatClicked() : void

        'WIP'
        + onQueueClicked() : void
    }
        class LibraryView{
        + onAssetClicked(asset : ILibraryAsset) : void
        + onAssetRightClicked(asset : ILibraryAsset) : void
    }

        ' Main screens
        class HomeView
        
        class SearchView {
            + onSearchButtonClicked() : void
        }

        class SongView {
            + onRightClickSong()
            + showExtraMenu()
        }
        class AlbumView
        class PlaylistView{
        + onSongClicked(song : Song) : void
        + onRemoveSongClicked(song : Song) : void
        + addSong(song : Song) : void
    }
        class ArtistView
        class LoginView
        class UserProfileView{
        + OnSettingsClicked() : void
        + OnLogoutClicked() : void
    }
        class AdminView
        class RightClickMenuView
        ' Library sub views
        class LibrarySongView
        class LibraryAlbumView
        class LibraryPlaylistView
        class LibraryArtistView

        View <|.. HomeView
        View <|.. SearchView
        View <|.. SongView
        View <|.. AlbumView
        View <|.. PlaylistView
        View <|.. ArtistView
        View <|.. LoginView
        View <|.. UserProfileView
        View <|.. AdminView
        View <|.. LibraryView
        View <|.. RightClickMenuView

        LibraryAssetView <|.. LibrarySongView
        LibraryAssetView <|.. LibraryAlbumView
        LibraryAssetView <|.. LibraryPlaylistView
        LibraryAssetView <|.. LibraryArtistView

        LibraryView <|.. LibraryAssetView
    }

    ' =====================================================
    ' CONTROLLER LAYER
    ' =====================================================

    package "Controller Layer" {

         interface IController {
            + draw() : void
        }

        class TunixApp {
            - {static} current : IController

            + {static} main(args : String[]) : void
            + {static} initialize() : void

            - {static} drawPersistentElements() : void
            - {static} drawCenter() : void

            + onPlaylistClicked() : void
            + onAlbumClicked() : void
            + onSongClicked() : void
            + onHomeClicked() : void
            + onSearchClicked() : void
            + onProfileClicked() : void
        }

        class MusicPlayerController {
            - main : TunixApp
            - player : MusicPlayer
            + MusicPlayerController(main : TunixApp, player : MusicPlayer)
            + draw() : void
            + onPlayClicked() : void
            + onPauseClicked() : void
            + onNextClicked() : void
            + onPreviousClicked() : void
            + onVolumeChanged() : void
            + onShuffleClicked() : void
            + onRepeatClicked() : void

            'WIP'
            + onQueueClicked() : void
        }

        class HomeController {
            - main : TunixApp
            + HomeController(main : TunixApp)
            + draw() : void
            
        }

        class LibraryController {
            - main : TunixApp
            - library : Library
            + LibraryController(main : TunixApp, library : Library)
            + draw() : void
            - fetchLibraryAssets() : List<ILibraryAsset>
            + getLibraryAssets() : List<ILibraryAsset>
            + onAssetClicked(asset : ILibraryAsset) : void
            + onAssetRightClicked(asset : ILibraryAsset) : void
        }

        class TopBarController {
            - main : TunixApp
            + TopBarController(main : TunixApp)
            + draw() : void
            + onHomeClicked() : void
            + onSearchClicked() : void
            + onProfileClicked() : void
        }

        class PlaylistController {
            - main : TunixApp
            - playlist : Playlist
            + PlaylistController(main : TunixApp, playlist : Playlist)
            + draw() : void
            + onSongClicked(song : Song) : void
            + onRemoveSongClicked(song : Song) : void
            + addSong(song : Song) : void
        }

        class ProfileController {
            - main : TunixApp
            - user : User
            + ProfileController(main : TunixApp, user : User)
            + OnSettingsClicked() : void
            + OnLogoutClicked() : void
            + draw() : void
        }

        class LoginController
        class AdminController
        class UserProfileController
        class ArtistController
        class RightClickMenuController
        class SearchController
        
        class SongController {
            + addSongToPlaylist(playlistId : int, songId : int) : void
            + showSongAlreadyInPlaylist() : void
        }

        class AlbumController

        MusicPlayerController ..|> IController
        HomeController ..|> IController
        LibraryController ..|> IController
        TopBarController ..|> IController
        PlaylistController ..|> IController
        ProfileController ..|> IController
        RightClickMenuController ..|> IController
    }
    
    ' =====================================================
    ' SERVICE LAYER
    ' =====================================================

    package "Service Layer" {

        class ListeningHistoryService {
            + addSong(song : Song) : void
            + addPlaylist(pl : Playlist) : void
            + addAlbum(al : Album) : void
            + getHistory(userId) : List<ILibraryAsset>
        }

        class LibraryService {

            + getLibrary(userId : int) : Library

            + getAssets(userId : int) : List<ILibraryAsset>

            + addAsset(userId : int, assetId : int) : void

            + removeAsset(userId : int, assetId : int) : void
        }

        class PlaybackService {
            - currentSong : Song
            - queue : List<Song>
            - volume : int
            - playing : boolean

            + playSong(song : Song) : void
            + pauseSong() : void
            + resumeSong() : void
            + stopSong() : void

            + nextSong() : void
            + previousSong() : void

            + addToQueue(song : Song) : void
            + removeFromQueue(song : Song) : void
            + clearQueue() : void

            + setVolume(volume : int) : void

            + getCurrentSong() : Song
            + getQueue() : List<Song>

            + isPlaying() : boolean
        }
        class SearchService

        class LoginService {

            - currentUser : User
            - authToken : String

            + login(email : String, password : String) : User
            + register(name : String, email : String, password : String) : User
            + logout() : void

            + isLoggedIn() : boolean
            + getCurrentUser() : User
        }

        class UserService {

            + getUserById(userId : int) : User

            + getUserProfile(userId : int) : User

            + updateUserProfile(user : User) : void

            + deactivateUser(userId : int) : void
        }


        class ArtistRequestService {

            + submitRequest(userId : int) : void

            + getRequests() : List<ArtistRequest>

            + approveRequest(requestId : int) : void

            + rejectRequest(requestId : int) : void
        }
        
        class FollowService {

            + followArtist(userId : int, artistId : int) : void

            + unfollowArtist(userId : int, artistId : int) : void

            + getFollowedArtists(userId : int) : List<Artist>

            + isFollowing(userId : int, artistId : int) : boolean
        }

        class PlaylistService {

            + getPlaylist(playlistId : int) : Playlist

            + findByUserId(userId : int) : List<Playlist>

            + getSongsByPlaylistId(playlistId : int) : List<Song>

            + createPlaylist(name : String, creatorId : int) : Playlist

            + deletePlaylist(playlistId : int) : Boolean

            + addSongToPlaylist(playlistId : int, songId : int) : Boolean

            + removeSong(playlistId : int, songId : int) : Boolean

            + addCoauthor(playlistId : int, userId : int) : Boolean

            + removeCoauthor(playlistId : int, userId : int) : Boolean

            + renamePlaylist(playlistId : int, name : String) : Boolean

            + checkIfSongInPlaylist(playlistId: int, songId : int) : boolean
        }

        class SongService {

            + getSongById(songId : int) : Song

            + getSongsByPlaylist(plId : int) : List<Song>

            + createSong(song : Song) : Song

            + deleteSong(songId : int) : Boolean

            + checkDuplicateSong() : void
        }

        class AdminService {
            + postIssueWarning() : Boolean
            + postBan() : Boolean
            + postRejectResponse(applicationId : int) : String
            + postApproveResponse(applicationId : int) : String
            + getSongWarnings(songId : int) : List<String>
        }

        class ArtistService {
            + getArtist(searchName : String) : List<Artist>
        }
    }

    ' =====================================================
    ' API LAYER
    ' =====================================================

    package "API Layer" {

        class ApiClient

        class SongAPI
        class PlaylistAPI {
          + postAddSongToPlaylist() : boolean
          + updatePlaylist() : void
        }
        class AlbumAPI
        class LibraryAPI
        class UserAPI
        class AdminAPI
        class LoginAPI
        class ArtistAPI
        class ArtistRequestAPI
    }

    ' =====================================================
    ' EVENT SYSTEM
    ' =====================================================

    package "Event System" {

        interface Event
        class EventBus

        class ShowHomeEvent
        class ShowSearchEvent
        class ShowPlaylistEvent
        class ShowSongEvent
        class ShowAlbumEvent
        class PlaySongEvent
        class LoginEvent

        Event <|-- ShowHomeEvent
        Event <|-- ShowSearchEvent
        Event <|-- ShowPlaylistEvent
        Event <|-- ShowSongEvent
        Event <|-- ShowAlbumEvent
        Event <|-- PlaySongEvent
        Event <|-- LoginEvent
    }

    ' =====================================================
    ' APP BOOTSTRAP
    ' =====================================================

    MusicApp -- TopBarController
    MusicApp -- LibraryController
    MusicApp -- MusicPlayerController
    MusicApp -- HomeView

    ' =====================================================
    ' VIEW → CONTROLLER
    ' =====================================================

    HomeView -- HomeController
    SearchView -- SearchController
    SongView -- SongController
    AlbumView -- AlbumController
    PlaylistView -- PlaylistController
    ArtistView -- ArtistController
    LoginView -- LoginController
    LibraryView -- LibraryController
    TopBarView -- TopBarController
    MusicPlayerView -- MusicPlayerController
    UserProfileView -- UserProfileController
    AdminView -- AdminController


    ' =====================================================
    ' CONTROLLER → SERVICE
    ' =====================================================

    HomeController -- EventBus
    SearchController -- SearchService
    LoginController -- LoginService
    LibraryController -- LibraryService
    MusicPlayerController -- PlaybackService
    UserProfileController -- UserService
    AdminController -- AdminService
    UserProfileController -- ArtistRequestService

    ArtistController -- FollowService
    ArtistController -- ArtistService

    PlaylistController -- PlaylistService
    LibraryController -- PlaylistService

    SongController -- SongService

    ' =====================================================
    ' SERVICE → API
    ' =====================================================

    LibraryService -- LibraryAPI
    LibraryService -- UserAPI

    SearchService -- SongAPI
    SearchService -- AlbumAPI
    SearchService -- PlaylistAPI

    PlaybackService -- SongAPI
    PlaybackService -- PlaylistAPI

    LoginService -- UserAPI
    UserService -- UserAPI

    AdminService -- AdminAPI

    PlaylistAPI -- PlaylistService

    FollowService -- UserAPI

    LoginService -- LoginAPI

    ArtistService -- ArtistAPI

    ArtistRequestService -- ArtistRequestAPI

    SongService -- SongAPI

    ' =====================================================
    ' API → CLIENT
    ' =====================================================

    SongAPI -- ApiClient
    PlaylistAPI -- ApiClient
    AlbumAPI -- ApiClient
    LibraryAPI -- ApiClient
    UserAPI -- ApiClient
    ArtistAPI -- ApiClient
    LoginAPI -- ApiClient
    ArtistRequestAPI -- ApiClient
    AdminAPI -- ApiClient


    ' =====================================================
    ' EVENT SYSTEM USAGE
    ' =====================================================

    HomeController ..> EventBus : publish
    SearchController ..> EventBus : publish
    PlaylistController ..> EventBus : publish
    SongController ..> EventBus : publish
    AlbumController ..> EventBus : publish
    LibraryController ..> EventBus : publish
    LoginController ..> EventBus : publish

    MusicPlayerController ..> EventBus : subscribe

    ' =====================================================
    ' MODEL USAGE (MINIMAL)
    ' =====================================================

    LibraryService -- Library
    LibraryService -- LibraryAsset
    PlaybackService -- Song
    LoginService -- Account
    UserService -- Account

}
 
left to right direction
package "Spring Backend" <<External System>> {

' =====================================================
' DOMAIN LAYER (ENTITIES)
' =====================================================

package "Domain (Entities)" as E {

    class Account
    class User
    class Artist
    class Admin
    class Song
    class Album
    class Playlist
    class Library

    Account <|-- User
    Account <|-- Artist
    Account <|-- Admin

    Playlist -- Song
    Album -- Song
    Library -- Song
    Library -- Playlist
    Library -- Album
    User -- Library
}

' =====================================================
' DTO LAYER
' =====================================================

package "DTO Layer" {

    class SongDTO
    class AlbumDTO
    class PlaylistDTO
    class UserDTO
    class ArtistDTO
    class LibraryDTO
}

' =====================================================
' CONTROLLER LAYER (REST API)
' =====================================================

package "Controller Layer (REST)" as C{

    interface API

    class UserBackendController
    class ArtistBackendController
    class AdminBackendController
    class SongBackendController
    class AlbumBackendController
    class PlaylistBackendController {
        + addSong(playlistId : int, songId : int) : boolean
    }
    class LibraryBackendController
    class ListeningHistoryBackendController
    class ArtistRequestBackendController

    API <|.. UserBackendController
    API <|.. ArtistBackendController
    API <|.. AdminBackendController
    API <|.. SongBackendController
    API <|.. AlbumBackendController
    API <|.. PlaylistBackendController
    API <|.. LibraryBackendController
    API <|.. ListeningHistoryBackendController
    API <|.. ArtistRequestBackendController
}



' =====================================================
' SERVICE LAYER
' =====================================================

package "BackendService Layer" as S{

    interface BackendService

    class UserBackendService
    class ArtistBackendService
    class AdminBackendService
    class SongBackendService
    class AlbumBackendService
    class PlaylistBackendService {
        addSong(playlistId : int, songId : int) : boolean
    }
    class LibraryBackendService
    class ListeningHistoryBackendService
    class ArtistRequestBackendService

    BackendService <|.. UserBackendService
    BackendService <|.. ArtistBackendService
    BackendService <|.. AdminBackendService
    BackendService <|.. SongBackendService
    BackendService <|.. AlbumBackendService
    BackendService <|.. PlaylistBackendService
    BackendService <|.. LibraryBackendService
    BackendService <|.. ListeningHistoryBackendService
    BackendService <|.. ArtistRequestBackendService
}



' =====================================================
' BackendRepository LAYER
' =====================================================

package "BackendRepository Layer (JPA)" {

    interface JpaBackendRepository

    class UserBackendRepository
    class ArtistBackendRepository
    class AdminBackendRepository
    class SongBackendRepository
    class AlbumBackendRepository
    class PlaylistBackendRepository {
       + addSongQuery() : boolean
    }
    class LibraryBackendRepository
    class ListeningHistoryBackendRepository
    class ArtistRequestBackendRepository

    JpaBackendRepository <|.. UserBackendRepository
    JpaBackendRepository <|.. ArtistBackendRepository
    JpaBackendRepository <|.. AdminBackendRepository
    JpaBackendRepository <|.. SongBackendRepository
    JpaBackendRepository <|.. AlbumBackendRepository
    JpaBackendRepository <|.. PlaylistBackendRepository
    JpaBackendRepository <|.. LibraryBackendRepository
    JpaBackendRepository <|.. ListeningHistoryBackendRepository
    JpaBackendRepository <|.. ArtistRequestBackendRepository

}



' =====================================================
' FLOW: CONTROLLER → BackendService
' =====================================================

SongBackendController -- S.SongBackendService
PlaylistBackendController -- S.PlaylistBackendService
AlbumBackendController -- S.AlbumBackendService
UserBackendController -- S.UserBackendService
ArtistBackendController -- S.ArtistBackendService
LibraryBackendController -- S.LibraryBackendService

' =====================================================
' FLOW: BackendService → BackendRepository
' =====================================================

S.SongBackendService -- SongBackendRepository
S.PlaylistBackendService -- PlaylistBackendRepository
S.AlbumBackendService -- AlbumBackendRepository
S.UserBackendService -- UserBackendRepository
S.ArtistBackendService -- ArtistBackendRepository
S.LibraryBackendService -- LibraryRepository

' =====================================================
' BackendService → DOMAIN
' =====================================================

S.SongBackendService .. E.Song
S.PlaylistBackendService .. E.Playlist
S.AlbumBackendService .. E.Album
S.UserBackendService .. E.User
S.LibraryBackendService .. E.Library
S.ArtistBackendService .. E.Artist

' =====================================================
' CONTROLLER → DTO
' =====================================================

SongBackendController -- SongDTO
PlaylistBackendController -- PlaylistDTO
AlbumBackendController -- AlbumDTO
UserBackendController -- UserDTO
ArtistBackendController -- ArtistDTO
LibraryBackendController -- LibraryDTO

' =====================================================
' CONTROLLER → API layer
' =====================================================
AlbumAPI -- AlbumBackendController
SongAPI -- SongBackendController
PlaylistAPI -- PlaylistBackendController
LibraryAPI -- LibraryBackendController
UserAPI -- UserBackendController
ArtistRequestAPI -- ArtistRequestBackendController
ArtistAPI -- ArtistBackendController
AdminAPI -- AdminBackendController
}
@enduml