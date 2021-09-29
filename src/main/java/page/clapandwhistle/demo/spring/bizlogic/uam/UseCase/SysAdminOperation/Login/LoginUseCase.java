package page.clapandwhistle.demo.spring.bizlogic.uam.UseCase.SysAdminOperation.Login;

import page.clapandwhistle.demo.spring.bizlogic.uam.Aggregate.AdminUserAggregateRepositoryInterface;
import page.clapandwhistle.demo.spring.bizlogic.uam.Aggregate.Exception.NotExistException;
import page.clapandwhistle.demo.spring.bizlogic.uam.Aggregate.Exception.PasswordIsNotMatchException;

public final class LoginUseCase {
    final private AdminUserAggregateRepositoryInterface adminUserRepos;

    final public static String E_MSG_NOT_EXISTS = "入力されたメールアドレスを使用するユーザはいません";
    final public static String E_MSG_PASSWORD_IS_NOT_MATCH = "パスワードが一致しません";

    public LoginUseCase(AdminUserAggregateRepositoryInterface adminUserRepos) {
        this.adminUserRepos = adminUserRepos;
    }

    public Result execute(String email, String passwrod) throws Exception {
        Result result = new Result();

        try {
            result.setAdminId(adminUserRepos.getAdminIdAllowedToLogIn(email, passwrod));
        } catch (NotExistException e) {
            return result.setFailure(e, E_MSG_NOT_EXISTS);
        } catch (PasswordIsNotMatchException e) {
            return result.setFailure(e, E_MSG_PASSWORD_IS_NOT_MATCH);
        }

        return result;
    }
}
