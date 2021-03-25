package page.clapandwhistle.uam.logics.UseCase.UserOperation.CreateAccount;

final public class Arguments {
    final private String email;
    final private String password;
    final private String fullName;
    final private String birthDateStr;

    public Arguments(String email, String password) {
        this(email, password, null, null);
    }

    public Arguments(String email, String password, String fullName, String birthDateStr) {
        super();
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.birthDateStr = birthDateStr;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public String getBirthDateStr() {
        return birthDateStr;
    }
}
