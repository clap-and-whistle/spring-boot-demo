package page.clapandwhistle.demo.spring.infrastructure.uam.AggregateRepository.AdminUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import page.clapandwhistle.demo.spring.bizlogic.uam.Aggregate.AdminUser.AdminUser;
import page.clapandwhistle.demo.spring.bizlogic.uam.Aggregate.AdminUser.Exception.NotExistException;
import page.clapandwhistle.demo.spring.bizlogic.uam.Aggregate.AdminUser.Exception.PasswordIsNotMatchException;
import page.clapandwhistle.demo.spring.bizlogic.uam.Aggregate.AdminUserAggregateRepositoryInterface;
import page.clapandwhistle.demo.spring.infrastructure.uam.AggregateRepository.Library.UserAccount.PasswordOperation;
import page.clapandwhistle.demo.spring.infrastructure.uam.TableModel.AdminAccountBaseRepository;

@Component
public final class AdminUserAggregateRepository implements AdminUserAggregateRepositoryInterface {
    final private AdminAccountBaseRepository adminBaseDao;
    final private PasswordOperation passwordOperator;

    @Autowired
    public AdminUserAggregateRepository(AdminAccountBaseRepository adminBaseDao) {
        this.adminBaseDao = adminBaseDao;
        this.passwordOperator = new PasswordOperation();
    }

    @Override
    public long getAdminIdAllowedToLogIn(String email, String password) throws NotExistException, PasswordIsNotMatchException {
        // TODO: implementation
        return 0;
    }

    @Override
    public AdminUser findById(long id) {
        // TODO: implementation
        return null;
    }
}
