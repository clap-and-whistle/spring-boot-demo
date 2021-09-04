package page.clapandwhistle.demo.spring.bizlogic.uam.Aggregate;

import page.clapandwhistle.demo.spring.bizlogic.uam.Aggregate.AdminUser.AdminUser;

public interface AdminUserAggregateRepositoryInterface {
    public long getAdminIdAllowedToLogIn(String email, String password);

    public AdminUser findById(long id);
}
