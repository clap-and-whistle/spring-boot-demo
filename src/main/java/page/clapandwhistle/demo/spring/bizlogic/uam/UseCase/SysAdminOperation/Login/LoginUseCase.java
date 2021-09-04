package page.clapandwhistle.demo.spring.bizlogic.uam.UseCase.SysAdminOperation.Login;

import page.clapandwhistle.demo.spring.bizlogic.uam.Aggregate.AdminUserAggregateRepositoryInterface;

public final class LoginUseCase {
    private AdminUserAggregateRepositoryInterface adminUserRepos;

    public LoginUseCase(AdminUserAggregateRepositoryInterface adminUserRepos) {
        this.adminUserRepos = adminUserRepos;
    }

    public Result execute(String email, String passwrod) {
        // TODO: implementation
        return new Result();
    }
}
