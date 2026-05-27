package tunixserver.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "library")
@Getter
@Setter
public class LibraryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "account_id", nullable = false, unique = true)
    private AccountEntity account;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}