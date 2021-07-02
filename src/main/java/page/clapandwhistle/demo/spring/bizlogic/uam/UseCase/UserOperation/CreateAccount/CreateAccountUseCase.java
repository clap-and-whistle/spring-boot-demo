package page.clapandwhistle.demo.spring.bizlogic.uam.UseCase.UserOperation.CreateAccount;

import org.springframework.stereotype.Component;

import page.clapandwhistle.demo.spring.bizlogic.uam.Aggregate.UserAggregateRepositoryInterface;
import page.clapandwhistle.demo.spring.bizlogic.uam.Aggregate.User.User;
import page.clapandwhistle.demo.spring.bizlogic.uam.Aggregate.User.Exception.BirthDateStrInvalidException;
import page.clapandwhistle.demo.spring.bizlogic.uam.Aggregate.User.Exception.FullNameSizeTooLongException;
import page.clapandwhistle.demo.spring.bizlogic.uam.Aggregate.User.Exception.PasswordSizeTooShortException;
import page.clapandwhistle.demo.spring.bizlogic.uam.Aggregate.User.Exception.PasswordTypeCompositionInvalidException;
import page.clapandwhistle.demo.spring.bizlogic.uam.UseCase.UserOperation.CreateAccount.Exception.ApplyingException;
import page.clapandwhistle.demo.spring.bizlogic.uam.UseCase.UserOperation.CreateAccount.Exception.EmailAlreadyUsedException;

@Component
final public class CreateAccountUseCase {
    private UserAggregateRepositoryInterface userRepos;

    final public static String E_MSG_EMAIL_APPLYING = "申請中のメールアドレスです。";
    final public static String E_MSG_EMAIL_ALREADY_USED = "既に使用されているメールアドレスです。";
    final public static String E_MSG_PASSWORD_BASE = "パスワードが要件を満たしていません。";
    final public static String E_MSG_PASSWORD_SIZE_TOO_SHORT = "[" + User.PASSWORD_MIN_LENGTH + "文字以上必要]";
    final public static String E_MSG_PASSWORD_TYPE_INVALID = "[半角英数字で構成する]";
    final public static String E_MSG_FULL_NAME_SIZE_TOO_LONG = "氏名の文字列が長すぎます。";
    final public static String E_MSG_BIRTH_DATE_STR_INVALID = "生年月日の文字列が不正です。";

    public CreateAccountUseCase(UserAggregateRepositoryInterface userRepos) {
        this.userRepos = userRepos;
    }

    public Result execute(Arguments args) throws Exception {
        Result result = new Result();
        long userId;

        try {
            userId = userRepos.getUserIdByEmail(args.getEmail());
            if (userId > 0) {
                throw userRepos.isApplying(userId)
                    ? new ApplyingException()
                    : new EmailAlreadyUsedException();
            }

            User user = User.buildForCreate(
                    args.getEmail()
                    , args.getPassword()
                    , args.getFullName()
                    , args.getBirthDateStr()
                );
            this.userRepos.save(user);
        } catch (PasswordSizeTooShortException e) {
            return result.setFailure(e, E_MSG_PASSWORD_BASE + E_MSG_PASSWORD_SIZE_TOO_SHORT);
        } catch (PasswordTypeCompositionInvalidException e) {
            return result.setFailure(e, E_MSG_PASSWORD_BASE + E_MSG_PASSWORD_TYPE_INVALID);
        } catch (FullNameSizeTooLongException e) {
            return result.setFailure(e, E_MSG_FULL_NAME_SIZE_TOO_LONG);
        } catch (BirthDateStrInvalidException e) {
            return result.setFailure(e, E_MSG_BIRTH_DATE_STR_INVALID);
        } catch (ApplyingException e) {
            return result.setFailure(e, E_MSG_EMAIL_APPLYING);
        } catch (EmailAlreadyUsedException e) {
            return result.setFailure(e, E_MSG_EMAIL_ALREADY_USED);
        }

        return result;
    }
}
