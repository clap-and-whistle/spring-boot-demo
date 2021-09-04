package page.clapandwhistle.demo.spring.bizlogic.uam.Aggregate;

import page.clapandwhistle.demo.spring.bizlogic.uam.Aggregate.AdminUser.AdminUser;
import page.clapandwhistle.demo.spring.bizlogic.uam.Aggregate.AdminUser.Exception.NotExistException;
import page.clapandwhistle.demo.spring.bizlogic.uam.Aggregate.AdminUser.Exception.PasswordIsNotMatchException;

public interface AdminUserAggregateRepositoryInterface {
    public long getAdminIdAllowedToLogIn(String email, String password) throws NotExistException, PasswordIsNotMatchException;

    public AdminUser findById(long id);
}
