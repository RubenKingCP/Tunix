# Use Case Model

[UML]
@startuml
skinparam packageStyle rectangle

actor General_User
actor Basic_User
actor Artist
actor Curator

rectangle "Music Platform System" {

  ' General User Use Cases
  General_User -- (Log In)
  General_User -- (Search Content) : abstract
  General_User -- (Shuffle/Play in sequence)
  General_User -- (Select Playlist)
  General_User -- (Favorite Songs)
  General_User -- (Create Playlist)
  General_User -- (Download Songs)
  General_User -- (Remove Songs from System Recommendations)
  General_User -- (Follow Artist)
  General_User -- (Receive Notifications)
  General_User -- (Add Songs to Playlist)
  General_User -- (Remove Songs from Playlist)

  ' Basic User Use Cases
  Basic_User -- (Apply For Artist)

  ' Artist Use Cases
  Artist -- (Check Statistics)
  Artist -- (Upload Music)
  Artist -- (Presale Music)
  Artist -- (Make Artist Announcements) 
' Curator Use Cases
  Curator -- (Curate Content)
 'Curator -- (Issue Strike)
 'Curator -- (Check Strike Amount)
 'Curator -- (Issue Ban)
  Curator -- (Moderate Artists)
  Curator -- (Make System Announcements)
  Curator -- (Approve/Reject UserToArtist)

  ' Relationships

  (Follow Artist) ..> (Search Artist Name) : <<include>>
  (Download Songs) ..> (Search Song Title) : <<include>>
  (Shuffle/Play in sequence) ..> (Select Playlist) : <<include>>
(Search Content) <-- (Search Artist Name)
  (Search Content) <-- (Search Album )
  (Search Content) <-- (Search Song Title)

  (Favorite Songs) <.. (Create Playlist) : <<extend>>

  (Select Playlist) <.. (Add Songs to Playlist) : <<include>>
  (Search Song Title) <.. (Add Songs to Playlist) : <<include>>
  (Select Playlist) <.. (Remove Songs from Playlist) : <include>>


  (Curate Content) <-- (Curate Posts)
  (Curate Content) <-- (Curate Media)
  (Curate Media) ..> (Search Content) : <<include>>

  (Make System Announcements) ..> (Receive Notifications) : <<include>>
  (Make Artist Announcements) ..> (Receive Notifications) : <<include>>
  (Upload Music) ..> (Receive Notifications) : <<include>>

  (Moderate Artists) <-- (Issue Strike)
  (Moderate Artists) ..> (Check Strike Amount) : <<include>>
  (Moderate Artists) <-- (Issue Ban)

    (Artist) --> (General_User) 
  (Curator) --> (General_User)
  (Basic_User) --> (General_User)

  (Approve/Reject UserToArtist) ..> (Apply For Artist)
}

@enduml

[Εικόνα]
![alt text](Use-case-v0.1.png)

# Επεξήγηση των Use Cases
[θα γραψω την εξηγηση οταν καταληξουμε σε ενα τελικο UCD, πρεπει κιολας να ψαξω το πως περιγραφω UC οταν εχουμε γενικευση, επεκταση κι ολα τα σχετικα]