package page.clapandwhistle.uam.infrastructure.TableModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_account_profile")
public class UserAccountProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String full_name;

    @Column(name = "birth_date_str", nullable = false)
    private String birth_date_str;

    public UserAccountProfile() {}

    public UserAccountProfile(String full_name, String birth_date_str) {
        this.full_name = full_name;
        this.birth_date_str = birth_date_str;
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return full_name;
    }

    public String getBirthDateStr() {
        return birth_date_str;
    }

    public void setFullName(String full_name) {
        this.full_name = full_name;
    }

    public void setBirthDateStr(String birth_date_str) {
        this.birth_date_str = birth_date_str;
    }

}
