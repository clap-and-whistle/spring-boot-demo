package page.clapandwhistle.demo.spring.bizlogic.uam.UseCase.SysAdminOperation.Login;

import page.clapandwhistle.demo.spring.bizlogic.uam.Aggregate.AdminUserAggregateRepositoryInterface;
import page.clapandwhistle.demo.spring.bizlogic.uam.Aggregate.AdminUser.Exception.NotExistException;
import page.clapandwhistle.demo.spring.bizlogic.uam.Aggregate.AdminUser.Exception.PasswordIsNotMatchException;

public final class LoginUseCase {
    private AdminUserAggregateRepositoryInterface adminUserRepos;

    final public static String E_MSG_NOT_EXISTS = "入力されたメールアドレスを使用するユーザはいません";
    final public static String E_MSG_PASSWORD_IS_NOT_MATCH = "パスワードが一致しません";

    public LoginUseCase(AdminUserAggregateRepositoryInterface adminUserRepos) {
        this.adminUserRepos = adminUserRepos;
    }

    public Result execute(String email, String passwrod) throws Exception {
        Result result = new Result();
        long adminId;

        try {
            adminId = adminUserRepos.getAdminIdAllowedToLogIn(email, passwrod);
            result.setAdminUser(adminUserRepos.findById(adminId));
        } catch (NotExistException e) {
            return result.setFailure(e, E_MSG_NOT_EXISTS);
        } catch (PasswordIsNotMatchException e) {
            return result.setFailure(e, E_MSG_PASSWORD_IS_NOT_MATCH);
        }

        return result;
    }
}
