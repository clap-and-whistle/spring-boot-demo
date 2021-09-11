package page.clapandwhistle.demo.spring.bizlogic.uam.Aggregate.AdminUser;

public enum AccountStatus {
    DISABLED(0),
    ENABLED(1);

    AccountStatus(int rawValue) {
        this.rawValue = rawValue;
    }

    private int rawValue;

    public int raw() {
        return this.rawValue;
    }

    public static AccountStatus getByRaw(int raw) {
        for (AccountStatus instance : AccountStatus.values()) {
            if (instance.raw() == raw) {
                return instance;
            }
        }
        throw new IllegalArgumentException();
    }
}
