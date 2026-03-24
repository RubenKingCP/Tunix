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
[το login ειπε δεν το θελει αρα δεν το βαζω]

# Βασικη Ροη "Select Playlist"
1. Ο χρήστης επιλέγει από το navigation panel την επιλογή "Playlist"
2. Το σύστημα ανακτά από το cloud/database όλες τις playlist που έχει δημιουργήσει ή σώσει ο χρήστης
3. Ο χρήστης βρίσκει την Playlist που θέλει και την επιλέγει
4. Το σύστημα ανακτά από το cloud/database όλα τα τραγούδια που ανήκουν σε αυτό το playlist και τα εμφανίζει

## Εναλλακτική Ροή 1
2.α.1 Το σύστημα διαπιστώνει ότι ο χρήστης δεν έχει δημιουργήση ή σώσει καμία playlist
2.α.2 Το σύστημα παροτρύνει τον χρήστη να δημιουργήσει μία νέα Playlist
(εδω κανουμε extend Create Playlist??)

# Βασική Ροή "Shuffle/Play in sequence" (εδω χρειαζεται extend?????)
1. Εκτέλεση Βασικής Ροής "Select Playlist"
2. Ο χρήστης επιλέγει ένα από τα δύο κουμπιά: "Shuffle" ή "Play in sequence"
3. Το σύστημα τροποποιει την ακολουθια αναπαραγωγης αντιστοιχα (θελει εναλλακτικες ροες???)

# Βασική Ροη "Add Songs to Playlist"
1. Εκτέλεση Βασικής Ροής "Search Song Title"
2. Ο χρήστης επιλέγει τις 3 τελείες δίπλα στον τίτλο του τραγουδιού
3. Το σύστημα εμφανίζει ένα μενού επιλογών
4. Ο χρήστης επιλέγει "Add to Playlist"
5. Εκτέλεση Βασικής Ροής "Select Playlist" (από το βήμα 2, προφανώς)
6. Το σύστημα προσθέτει στην Playlist το επιλεγμένο τραγούδι
7. Το σύστημα κάνει update το νέο περιεχόμενο της playlist στο could/database

## Εναλλακτικη Ροη 1 (Ή βασικη ροη "Favorite Songs"????????)
3.α.1 Ο χρήστης επιλέγει "Add to favorites"
3.α.2 Το σύστημα ανακτά από το cloud/database όλα τα τραγούδια που ανήκουν στο playlist "Favorites"
3.α.3 Επιστροφή στη βασική ροή στο βήμα 6

# Βασική Ροη "Remove Songs from Playlist"
1. Εκτέλεση Βασικής Ροής "Select Playlist"
2. Ο χρήστης επιλέγει το τραγούδι που θέλει να αφαιρέσει και πατάει τις τρεις τελείες δίπλα στον τίτλο του τραγουδιού
3. Το σύστημα εμφανίζει ένα μενού επιλογών
4. Ο χρήστης επιλέγει "Remove from Playlist"
5. Το σύστημα αφαιρεί το επιλεγμένο τραγούδι από την Playlist
6. Το σύστημα κάνει update το νέο περιεχόμενο της playlist στο cloud/database

# Βασική Ροή "Apply For Artist"
1. Ο χρήστης επιλέγει την εικόνα προφίλ του
2. Το σύστημα εμφανίζει το μενού ρυθμίσεων για το προφίλ του χρήστη
3. Ο χρήστης επιλέγει "Apply for Artist"
4. [ εδω παιδια δεν ξερω αν θελετε ο χρηστης να βαζει και εξτρα πληροφοριες για τον εαυτο του ή κατι τελος παντων περα απο την ιδια την αιτηση ]
5. Το σύστημα στέλνει το αίτημα στους Curators

# Βασική Ροή "Approve/Reject UserToArtist"
1. Εκτέλεση Βασικής Ροής "Apply For Artist"
2. Ο curator ελέγχει αν το προφίλ του χρήστη πληροί τις προϋποθέσεις (ποιες ειναι αυτες???)
3. Ο curator επιλέγει το πράσινο κουμπί approve ή το κόκκινο κουμπί reject (ή οτι χρωμα θελετε)
4. Το σύστημα λαμβάνει την έγκριση/απόρριψη και αντίστοιχα αναβαθμίζει τον Basic User σε Artist
5. Εκτελεση Βασικης Ροης "Receive Notifications"
(θέλει κι εδω εναλλακτικες ροες????)

# Βασικη Ροη "Remove Songs from System Recommendations"
(θεωρουμε οτι τα recommendations ειναι στην αρχικη οθονη)
1. Ο χρήστης επιλέγει ένα τραγούδι και πατάει τις τρεις τελείες δίπλα στον τίτλο του τραγουδιού
2. Το σύστημα εμφανίζει ένα μενού επιλογών
3. Ο χρήστης επιλέγει "Remove from Recommendations"
4. Το σύστημα παύει να εμφανίζει αυτό το τραγούδι στα Recommendations
(Εδω προεραιτικα προσθετω)
5. Το σύστημα εμφανίζει ένα μενου επιλογών στον χρήστη με τίτλο "Why do you not want to see this song?"
6. Ο χρήστης πατάει μία επιλογή και επιλέγει "send feedback"

# Βασική Ροή "Create Playlist"
1. Ο χρήστης επιλέγει από το navigation panel την επιλογή "Playlist"
2. Ο χρήστης επιλέγει το κουμπί "+" (ή Create New Playlist, δεν ξερω πως θα το βαλουμε)
3. Ο χρήστης εισάγει το όνομα της Playlist, επιλέγει αν θα είναι δημόσια ή ιδιωτική και προεραιτικά προσθέτει μία σύντομη περιγραφή
4. Το σύστημα αποθηκεύει την νέα playlist στο cloud/database

# Βασική Ροή "Search Album"
1. Ο χρήστης επιλέγει από το navigation panel την επιλογή "Αναζήτηση"
2. Το σύστημα εμφανίζει ένα δεύτερο navigation panel με τις επιλογές για αναζήτηση
3. Ο χρήστης επιλέγει "Album" και εισάγει στην μπάρα αναζήτησης το όνομα του album
4. Το σύστημα αναζητεί στο cloud/database όλα τα album με το ίδιο όνομα και τα εμφανίζει στην οθόνη

# Βασική Ροή "Search Song Title"
1. Ο χρήστης επιλέγει από το navigation panel την επιλογή "Αναζήτηση"
2. Το σύστημα εμφανίζει ένα δεύτερο navigation panel με τις επιλογές για αναζήτηση
3. Ο χρήστης επιλέγει "Song" και εισάγει στην μπάρα αναζήτησης το όνομα του τραγουδιου
4. Το σύστημα αναζητεί στο cloud/database όλα τα τραγουδια με το ίδιο όνομα και τα εμφανίζει στην οθόνη

# Βασικη Ροη "Search Artist Name"
1. Ο χρήστης επιλέγει από το navigation panel την επιλογή "Αναζήτηση"
2. Το σύστημα εμφανίζει ένα δεύτερο navigation panel με τις επιλογές για αναζήτηση
3. Ο χρήστης επιλέγει "Artist" και εισάγει στην μπάρα αναζήτησης το όνομα του artist
4. Το σύστημα αναζητεί στο cloud/database όλους τους artist με το ίδιο όνομα και τα εμφανίζει στην οθόνη

# Βασική Ροη "Follow Artist"
1. Εκτελεση Βασικης Ροης "Search Artist Name"
2. Ο χρήστης επιλέγει τον artist που θέλει να ακολουθησει και πατάει την εικονα προφιλ του
3. Το σύστημα εμφανίζε πληροφοριες σχετικα με τον καλλιτεχνη
4. Ο χρήστης επιλέγει το κουμπι "Follow"
5. Το σύστημα κάνει update το προφιλ του χρήστη στο cloud/database για αν περιλαμβανει το νεο artist που ακολουθει ο χρηστης

# Βασικη Ροη "Download Songs"
1. Εκτελεση Βασικης Ροης "Search Song Title"
2. Ο χρηστης επιλεγει το τραγουδι που θελει να κατεβασει και παταει τις τρεις τελειες διπλα στον τιτλο του
3. Το συστημα εμφανιζε ενα μενου επιλογων
4. Ο χρηστης επιλεγει "Download"
5. Το συστημα αντλει απο το cloud/database το αρχειο του τραγουδιου, καθως και το path στο οποιο θα κατεβει το αρχειο
6. Το συστημα κατεβαζει το αρχειο στο δωσμενο path

# Βασικη Ροη "Check Statistics"
1. Ο καλλιτεχνης παταει στην εικονα προφιλ του
2. Το συστημα εμφανιζει επιλογες ρυθμισεων για το προφιλ του artist
3. Ο καλλιτεχνης παταει την επιλογη "Statistics"
4. Το συστημα αντλει δεδομενα απο το cloud/database για τον καλλιτεχνη, κανει υπολογισμους και εμφανιζει γραφηματα στον artist

# Βασικη Ροη "Upload Music"
1. Ο καλλιτεχνης παταει το κουμπι upload (δεν ξερω που θελετε να το βαλουμε ???)
2. Το συστημα ανοιγει το προγραμμα περιηγησης αρχειων του χρηστη
3. Ο καλλιτεχνης βρισκει και επιλεγει το αρχειο μουσικης που θελει να ανεβασει
4. Ο καλλιτεχνης εισαγει το ονομα του τραγουδιου, το αλμπουμ στο οποιο ανηκει, καθως και τον αριθμο μεσα στο αλμπουμ, το ετος κυκλοφορησης του τραγουδιου και αλλες επιπροσθετες πληροφοριες
5. Το συστημα αποθηκευει το νεο τραγουδι στο cloud/database
6. Εκτελεση Βασικης Ροης "Receive Notifications"

# Βασικη Ροη "Make Artist Announcements"
1. Ο καλλιτεχνης παταει το κουμπι "New Announcement" (δεν ξερω που θελετε να το βαλουμε??)
2. Ο καλλιτεχνης εισαγει τον τιτλο και την περιγραφη της ανακοινωσης
3. Το συστημα αποθηκευει την ανακοινωση στο cloud/database
4. Εκτελεση Βασικης Ροης "Receive Notifications"

# Βασικη Ροη "Receive Notifications"
1. Ο χρηστης λαμβανει απο το συστημα ειδοποιησεις
(.... τι να γραψω???)

# Βασικη Ροη "Make System Announcements"
Δεν ειναι το ιδιο με το Make Artist Announcements? Πανω κατω τα ιδια κανουν, εχουν τιτλο και περιγραφη... μηπως να τα ενωσουμε σε ενα Make Announcements??

# Βασικη Ροη "Curate Media"
1. Εκτέλεση Βασικης Ροης "Search Content"
2. Ο curator επιλεγει τις τρεις τελειες διπλα στο ονομα του καλλιτεχνη/τραγουδιου/αλμπουμ
3. Το συστημα εμφανιζει ενα μενου με επιλογες
4. Ο curator επιλεγει "Curate"
5. (τι θελουμε να γινεται εδω???)
6. Το συστημα κανει update το cloud/database

# Βασικη Ροη "Curate Posts"
1. Ο curator επιλεγει απο το navigation panel την επιλογη posts
2. Το συστημα εμφανιζει ολα τα posts (ή κατι τετοιο???)
3. Ο curator επιλεγει ενα post και παταει τις τρεις τελειες διπλα στον τιτλο του
4. Το συστημα εμφανιζει ενα μενου επιλογων
5. Ο curator επιλεγει "Curate"
6. (τι θελουμε να γινεται εδω???)
7. Το συστημα κανει update το cloud/database

# Βασικη Ροη "Moderate Artists"
1. Εκτελεση Βασικης Ροης "Search Artist Name" (πρεπει να κανουμε μαλλον include???)
2. Ο curator επιλεγει τον καλλιτεχνη που θελει να κανει moderate και παταει τις τρεις τελειες διπλα στο ονομα του
3. Το συστημα εμφανιζει ενα μενου με επιλογες
4. Ο curator επιλεγει "Moderate"
5. Εκτελεση Βασικης Ροης "Check Strike Amount"
6. Το συστημα εμφανιζει στον curator ενα μενου επιλογων

# Βασικη Ροη "Check Strike Amount"
1. Το συστημα ανακτα (για δεδομενο καλλιτεχνη) τον αριθμο των strikes του απο τα δεδομενα του στο cloud/database και τα εμφανιζε στον curator
(... τι αλλο να γραψω??)

# Βασικη Ροη "Issue Strike"
1. Εκτελεση Βασικης Ροης "Moderate Artists"
2. Ο curator επιλεγει "Issue Strike"
3. Το συστημα καταγραφει την αλλαγη και την αποθηκευει στο cloud/database

# Βασικη Ροη "Issue Ban"
1. Εκτελεση Βασικης Ροης "Moderate Artists"
2. O curator επιλεγει "Issue Ban"
3. Το συστημα καταγραφει την αλλαγη και την αποθηκευει στο cloud/database
