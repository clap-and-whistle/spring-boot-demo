package page.clapandwhistle.uam.logics.UseCase.UserOperation.Login;

import org.springframework.stereotype.Component;

import page.clapandwhistle.uam.infrastructure.AggregateRepository.User.Exception.NotExistException;
import page.clapandwhistle.uam.infrastructure.AggregateRepository.User.Exception.PasswordIsNotMatchException;
import page.clapandwhistle.uam.logics.Aggregate.UserAggregateRepositoryInterface;

@Component
final public class LoginUseCase {
    private UserAggregateRepositoryInterface userRepos;

    final public static String E_MSG_NOT_EXISTS = "入力されたメールアドレスを使用するユーザはいません";
    final public static String E_MSG_PASSWORD_IS_NOT_MATCH = "パスワードが一致しません";

    public LoginUseCase(UserAggregateRepositoryInterface userRepos) {
        this.userRepos = userRepos;
    }

    public Result execute(Arguments args) throws Exception {
        Result result = new Result();
        long userId;

        try {
            userId = this.userRepos.getUserIdAllowedToLogIn(args.getEmail(), args.getPassword());
            result.setUser(this.userRepos.findById(userId));
        } catch (NotExistException e) {
            return result.setFailure(e, E_MSG_NOT_EXISTS);
        } catch (PasswordIsNotMatchException e) {
            return result.setFailure(e, E_MSG_PASSWORD_IS_NOT_MATCH);
        }

        return result;
    }
}
