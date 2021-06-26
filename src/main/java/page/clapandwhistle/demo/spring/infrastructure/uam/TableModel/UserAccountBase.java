package page.clapandwhistle.demo.spring.infrastructure.uam.TableModel;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "user_account_base")
public class UserAccountBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email", nullable = false)
    @NotBlank(message = "emailアドレスは必須です。")
    private String email;

    @Column(name = "password", nullable = false)
    @NotBlank(message = "passwordは必須です。")
    private String password;

    @Column(name = "account_status", nullable = false)
    private int account_status;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UserAccountProfile userAccountProfile;

    public UserAccountBase() {}

    public UserAccountBase(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAccount_status() {
        return account_status;
    }

    public void setAccountStatus(int account_status) {
        this.account_status = account_status;
    }

    public UserAccountProfile getUserAccountProfile() {
        return userAccountProfile;
    }

    public void setUserAccountProfile(UserAccountProfile userAccountProfile) {
        this.userAccountProfile = userAccountProfile;
    }

    public String getRole() { return "ROLE_USER"; }
}
