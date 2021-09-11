package page.clapandwhistle.demo.spring.bizlogic.uam.Aggregate.AdminUser;

public final class AdminUser {
    final private long id;
    final private String email;
    final private String password;
    final private AccountStatus accountStatus;

    private AdminUser(long id, String email, String password, AccountStatus accountStatus) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.accountStatus = accountStatus;
    }

    /**
     * テスト用データの作成を必要としているクラスでなら使って良い
     * @deprecated
     */
    public static AdminUser buildForTestData(long id, String email, String password, int accountStatus) {
        return new AdminUser(id, email, password, AccountStatus.getByRaw(accountStatus));
    }

    public static AdminUser buildForFind(
        long id,
        String email,
        int accountStatus
    ) {
        return new AdminUser(id, email, "", AccountStatus.getByRaw(accountStatus));
    }

    public long id() {
        return id;
    }

    public String email() {
        return email;
    }

    public String password() {
        return password;
    }

    public AccountStatus accountStatus() {
        return accountStatus;
    }
}
