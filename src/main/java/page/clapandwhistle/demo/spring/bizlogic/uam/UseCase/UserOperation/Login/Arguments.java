package page.clapandwhistle.demo.spring.bizlogic.uam.UseCase.UserOperation.Login;

final public class Arguments {
    final private String email;
    final private String password;

    public Arguments(String email, String password) {
        super();
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}
