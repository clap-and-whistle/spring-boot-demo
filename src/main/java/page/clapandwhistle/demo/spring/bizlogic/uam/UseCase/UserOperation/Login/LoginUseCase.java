package page.clapandwhistle.demo.spring.bizlogic.uam.UseCase.UserOperation.Login;

import page.clapandwhistle.demo.spring.bizlogic.uam.Aggregate.UserAggregateRepositoryInterface;
import page.clapandwhistle.demo.spring.bizlogic.uam.Aggregate.Exception.NotExistException;
import page.clapandwhistle.demo.spring.bizlogic.uam.Aggregate.Exception.PasswordIsNotMatchException;

final public class LoginUseCase {
    final private UserAggregateRepositoryInterface userRepos;

    final public static String E_MSG_NOT_EXISTS = "入力されたメールアドレスを使用するユーザはいません";
    final public static String E_MSG_PASSWORD_IS_NOT_MATCH = "パスワードが一致しません";

    public LoginUseCase(UserAggregateRepositoryInterface userRepos) {
        this.userRepos = userRepos;
    }

    public Result execute(Arguments args) throws Exception {
        Result result = new Result();

        try {
            result.setUserId(this.userRepos.getUserIdAllowedToLogIn(args.getEmail(), args.getPassword()));
        } catch (NotExistException e) {
            return result.setFailure(e, E_MSG_NOT_EXISTS);
        } catch (PasswordIsNotMatchException e) {
            return result.setFailure(e, E_MSG_PASSWORD_IS_NOT_MATCH);
        }

        return result;
    }
}
