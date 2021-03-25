package page.clapandwhistle.uam.logics.Aggregate.User;

public enum AccountStatus {
    APPLYING(0),
    IN_OPERATION(1),
    STOPPING(2),
    DELETED(3);

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
