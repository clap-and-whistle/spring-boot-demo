package page.clapandwhistle.demo.spring.bizlogic.uam.UseCase.SysAdminOperation.Login;

import page.clapandwhistle.demo.spring.bizlogic.uam.Aggregate.AdminUserAggregateRepositoryInterface;

public final class LoginUseCase {
    private AdminUserAggregateRepositoryInterface adminUserRepos;

    final public static String E_MSG_NOT_EXISTS = "入力されたメールアドレスを使用するユーザはいません";
    final public static String E_MSG_PASSWORD_IS_NOT_MATCH = "パスワードが一致しません";

    public LoginUseCase(AdminUserAggregateRepositoryInterface adminUserRepos) {
        this.adminUserRepos = adminUserRepos;
    }

    public Result execute(String email, String passwrod) {
        // TODO: implementation
        Result result = new Result();
        return result.setFailure(new RuntimeException(), "未実装");
    }
}
