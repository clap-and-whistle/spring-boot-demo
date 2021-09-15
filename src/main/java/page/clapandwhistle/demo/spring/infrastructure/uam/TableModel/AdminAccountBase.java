package page.clapandwhistle.demo.spring.infrastructure.uam.TableModel;

import page.clapandwhistle.demo.spring.bizlogic.uam.Aggregate.AdminUser.AdminUser;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "admin_account_base")
public class AdminAccountBase {
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

    public AdminAccountBase() {}

    public AdminAccountBase(String email, String password, int account_status) {
        this.email = email;
        this.password = password;
        this.account_status = account_status;
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

    public int getAccountStatus() {
        return account_status;
    }

    public void setAccountStatus(int account_status) {
        this.account_status = account_status;
    }

    public String getRole() { return AdminUser.ROLE; }
}
