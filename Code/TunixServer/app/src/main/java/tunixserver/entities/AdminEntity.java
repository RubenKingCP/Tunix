package tunixserver.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "admin")
public class AdminEntity {

    @Id
    private Long adminId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "admin_id")
    private AccountEntity account;

    private String name;

    public AdminEntity() {
    }

    public AdminEntity(AccountEntity account, String name) {
        this.account = account;
        this.name = name;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public AccountEntity getAccount() {
        return account;
    }

    public void setAccount(AccountEntity account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
