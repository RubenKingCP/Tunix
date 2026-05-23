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
abstract class Account {
- status: bool
+ artist(String name): Artist art
+ flipStatus(): void
+ newStatus(): bool status
}
class User {
- String username
- String password
- String userId
+ displayUserPlaylists(): void
+ selectPlaylist(): Playlist pl
+ addSong(): void
+ createPlaylist(): void
+ showProfile(): void
+ showSettings(): void
+ applyForArtist(): void
+ finishApplication(): void
+ fetchUserProfile(String UserId): Profile pf
+ postArtistRequest(Hashmap<String, String> formInput): void
+ create(): void
+ showSearch(): void
+ search(): void
+ downloadSong(): void
+ followArtist(Artist art): void
+ buyPackage(): void
+ fetchUserStatus(String userId): bool isPremium
}
class Artist {
+ fetchArtists(String searchName): List<Artist> arts
+ fetchSongWarnings(String songId): List<String> warnings
+ postIssueWarning(String songId): String status
+ postBan(): String status
+ uploadSong(): void
+ checkDuplicateSong(String name): bool hasNotDuplicate
}
class Admin {
+ fetchAritstApplications(): List<Application> appls
+ postRejectResponse(String reason): String status
+ postApproveResponse(): String status
}

Account <|-- User
Account <|-- Artist
Account <|-- Admin

' -------- MUSIC CORE --------
class Song {
- name: String
- albId: String
- albCount: int
- userId: String
- path: String
+ fetchSongByPlId(String plId): List<Song> sgs
+ fetchSong(String searchName): List<Song> sgs
+ fetchSongForDownload(): String path
+ saveSongLocally(String pathInDB, String pathInLocalFS): void
+ postRemoveSong(String songId): String status
+ postSong(): String status
}
class Album {
- name: String
- albId: String
- artId: String
- albYear: int
- albCoverPath: String
+ fetchAlbum(String searchName): List<Album> albs
}
class Genre

Artist "1" --> "0..*" Song : creates
Artist "1" --> "0..*" Album : creates

Album "1" *-- "1..*" Song
Song "0..*" -- "0..*" Genre

' -------- PLAYLIST SYSTEM --------
class Playlist {
- plId: String
- name: String
- sgs: List<Song>
- userId: String
- descr: String
- isPublic: bool
+ fetchUserPlaylistsById(String userId): JsonObject userPlaylists
+ fetchSongsByPlaylistId(String plId): JsonObject playlistSongs
+ addSongToFavorites(Song sg): void
+ addSong(Song sg): void
+ sendHTTPRequest(String name): bool hasNotDuplicate
+ postRemoveSongById(String songId, String playlistId): void
+ fetchPlaylist(String searchName): List<Playlist> pls
}

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
interface Controller
class MainController {
+ artist(String name): Artist art
+ flipStatus(String userId): void
}
class MusicPlayerController
class HomeController {
+ display(): void
+ displayUserPlaylists(String userId): void
+ flipStatus(String userId): void
+ getProfile(): void
+ showPlaylists(): void
+ getArtistApplications(): List<Application> appls
+ saveResponse(bool accepted, String reason): String status
+ checkWarnings(String songId): List<String> warnings
+ displayCuratorPopup(): void
+ issueWarning(String songId): String status
+ issueBan(String artId): String status
+ removeSong(String sgId): String status
+ checkForDuplicate(String name): hasNotDuplicate
+ approve(): void
+ uploadSong(Song sg): String status
+ showUploadStatus(): void
+ showDuplicateFoundError(): void
}
class LibraryController
class TopBarController {
+ display(): void
+ searchSongs(String name): List<Song> sgs
+ searchAlbums(String name): List<Album> als
+ searchArtist(String name): List<Artist> art
+ searchPlaylists(String name): List<Playlist> pls
+ checkPremium(String userId): bool isPremium
+ downloadSong(Song sg): String status
}
class PlaylistController {
+ fetchUserPlaylistsById(String userId): List<Playlist> pls
+ getPlaylistSongs(Playlist pl): List<Song> sgs
+ showSelectedPlaylist(Playlist pl): void
+ showSelectedEmptyPlaylist(): void
+ showPlaylistsScreen(): void
+ addSong(String songId, String plId): void
+ display(): void
+ checkDuplicate(String name): bool hasNotDuplicate
+ createPlaylist(String name, String descr, bool isPublic, String userId): bool hasNotDuplicate
+ remove(String songId, String playlistId): void
}
class ProfileController {
+ artist(String name): Artist art
+ getUserProfile(String userId): Profile pf
+ applyForArtist(Hashmap<String, String> formInput): void
}
}

package "Views" {
interface View
class MusicPlayerView
class HomeView {
+ getPlaylist(): void
+ managePremiumScreen(): void
+ confirm(): bool yes
+ flipStatus(String userId): void
+ showProfile(): void
+ displayUserPlaylists(String userId): void
+ getArtistApplications(): void
+ showSelectedRequest(): void
+ saveResponse(): void
+ showRemovalReasonPopup(): void
+ checkWarnings(String songId): void
+ curateContent(): void
+ showUploadSongScreen(): void
+ checkForDuplicate(String name): void
+ uploadSong(): void
}
class LibraryView
class TopBarView {
+ showSearch(): void
+ search(String name): void
+ downloadSong(Song sg): void
}
class PlaylistView {
+ displayPlaylistSongs(String plId): List<Song> sgs
+ showPlaylistEditor(): void
+ checkForDuplicateName(String name): bool hasDuplicate
+ showDuplicateFoundError(): void
+ createPlaylist(String name, String descr, bool isPublic, String userId): bool hasNotDuplicate
+ openSongOptions(): void
+ removeFromPlaylist(int index): void
}
class ProfileView {
+ openSettings(): void
+ showForm(): void
+ applyForArtist(Hashmap<String, String> formInput): void
}
}

' UI links
MainController ..|> Controller
MusicPlayerController ..|> Controller
HomeController ..|> Controller
LibraryController ..|> Controller
TopBarController ..|> Controller
PlaylistController ..|> Controller
ProfileController ..|> Controller

View <|.. MusicPlayerView
View <|.. HomeView
View <|.. LibraryView
View <|.. TopBarView
View <|.. PlaylistView
View <|.. ProfileView

MusicPlayerController -- MusicPlayer
MusicPlayerView -- MusicPlayerController

MainController -- MusicPlayerController
MainController -- ProfileController
ProfileView -- ProfileController
ProfileController -- User

HomeView -- HomeController
MainController -- HomeController

LibraryView -- LibraryController
MainController -- LibraryController

PlaylistView -- PlaylistController
PlaylistController -- MainController

TopBarView -- TopBarController
TopBarController -- MainController
MainController -- Account

' =====================
' SPRING BACKEND
' =====================

package "Spring Backend" {

' Controllers (REST)
interface API
class AccountControllerAPI {
+ artist(String name): Artist art
+ newStatus(String userId, bool status): bool status
+ findById(String userId): Profile pf
}
class UserControllerAPI {
+ checkUserStatus(String userId): bool isPremium
}
class ArtistControllerAPI {
  + findArtistByName(String searchName): List<Artist> arts
  + findWarningBySongId(String songId): List<String> warnings
  + insertWarningBySongId(String songId): String status
  + postBan(String artId)
  + checkForDuplicateSong(String name): bool hasNotDuplicate
}
class AdminControllerAPI
class SongControllerAPI {
+ findByPlaylistId(String plId): List<Song> sgs
+ findSongByName(String searchName): List<Song> sgs
+ getSong(Song sg): String path
+ removeSongById(String songId): String status
+ insertSongToDatabase(Song sg): String status
}
class AlbumControllerAPI {
+ findAlbumByName(String searchName): List<Album> albs
}
class PlaylistControllerAPI {
+ getUserPlaylistsById(String userId): ResultSet playlistRows
+ getSongsWithPlaylistId(String plId): ResultSet songRows

+ createQuery(String userId): List<Playlist> pls
+ addSong(String songId, String plId): void
+ processRequest(String name): bool hasNotDuplicate
+ removeSongById(String songId, String playlistId): void
+ findPlaylistByName(String searchName): List<Playlist> pls
}
class LibraryControllerAPI
class GenreControllerAPI
class ListeningHistoryControllerAPI
class ArtistRequestControllerAPI {
+ createArtistRequest(Hashmap<String, String> formInput): void
+ findAllApplication(): List<Application> appls
+ rejectResponse(String reason): String status
+ approveResponse(): String status
}

' Entities
interface Service
class AccountService {
+ artist(String name): Artist art
+ newStatus(String userId, bool status): bool status
}
class UserService
class ArtistService
class AdminService
class SongService
class AlbumService
class PlaylistService
class PlaylistService {
+ addSong(String sgId, String plId): void
+ runQuery(String name): bool hasNotDuplicate
}
class LibraryService
class GenreService
class ListeningHistoryService
class ArtistRequestService

' Repositories (JPA)
interface JpaRepository
class AccountRepository {
+ exists(String userId): bool e
+ addFollowingArtist(String artId, String userId): void
}
class UserRepository
class ArtistRepository
class AdminRepository
class SongRepository
class AlbumRepository
class PlaylistRepository
class LibraryRepository
class GenreRepository
class ListeningHistoryRepository
class ArtistRequestRepository
}

' Implementations

API <|.. AccountControllerAPI
API <|.. UserControllerAPI
API <|.. ArtistControllerAPI
API <|.. AdminControllerAPI
API <|.. SongControllerAPI
API <|.. AlbumControllerAPI
API <|.. PlaylistControllerAPI
API <|.. LibraryControllerAPI
API <|.. GenreControllerAPI
API <|.. ListeningHistoryControllerAPI
API <|.. ArtistRequestControllerAPI

Service <|.. AccountService
Service <|.. UserService
Service <|.. ArtistService
Service <|.. AdminService
Service <|.. SongService
Service <|.. AlbumService
Service <|.. PlaylistService
Service <|.. LibraryService
Service <|.. GenreService
Service <|.. ListeningHistoryService
Service <|.. ArtistRequestService

JpaRepository <|.. AccountRepository
JpaRepository <|.. UserRepository
JpaRepository <|.. ArtistRepository
JpaRepository <|.. AdminRepository
JpaRepository <|.. SongRepository
JpaRepository <|.. AlbumRepository
JpaRepository <|.. PlaylistRepository
JpaRepository <|.. LibraryRepository
JpaRepository <|.. GenreRepository
JpaRepository <|.. ListeningHistoryRepository
JpaRepository <|.. ArtistRequestRepository

' Controller → Service
AccountControllerAPI -- Account
UserControllerAPI -- User
AdminControllerAPI -- Admin
AccountControllerAPI -- AccountService
ArtistControllerAPI -- Artist
AlbumControllerAPI -- Album
GenreControllerAPI -- Genre
SongControllerAPI -- Song
ListeningHistoryControllerAPI -- ListeningHistory

UserControllerAPI -- UserService
ArtistControllerAPI -- ArtistService
AdminControllerAPI -- AdminService
SongControllerAPI -- SongService
AlbumControllerAPI -- AlbumService
PlaylistControllerAPI -- PlaylistService
LibraryControllerAPI -- LibraryService
GenreControllerAPI -- GenreService
ListeningHistoryControllerAPI -- ListeningHistoryService
ArtistRequestControllerAPI -- ArtistRequestService

' Service → Repository
AccountService -- AccountRepository
UserService -- UserRepository
ArtistService -- ArtistRepository
AdminService -- AdminRepository
SongService -- SongRepository
AlbumService -- AlbumRepository
PlaylistService -- PlaylistRepository
LibraryService -- LibraryRepository
GenreService -- GenreRepository
ListeningHistoryService -- ListeningHistoryRepository
ArtistRequestService -- ArtistRequestRepository

PlaylistController -- Playlist
LibraryController -- Library
Library -- LibraryControllerAPI
Playlist -- PlaylistControllerAPI
User -- ArtistRequestControllerAPI
@enduml
