package page.clapandwhistle.demo.spring.bizlogic.uam.Aggregate.AdminUser;

public final class AdminUser {
    private long id;
    private String email;
    private String password;
    private AccountStatus accountStatus;

    private AdminUser(long id, String email, String password, AccountStatus accountStatus) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.accountStatus = accountStatus;
    }

    public static AdminUser buildForFind(
        long id,
        String email,
        int accountStatus
    ) {
        return new AdminUser(id, email, "", AccountStatus.getByRaw(accountStatus));
    }
}
