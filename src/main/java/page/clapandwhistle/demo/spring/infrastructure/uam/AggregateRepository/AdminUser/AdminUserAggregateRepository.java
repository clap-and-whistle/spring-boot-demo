package page.clapandwhistle.demo.spring.infrastructure.uam.AggregateRepository.AdminUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import page.clapandwhistle.demo.spring.bizlogic.uam.Aggregate.AdminUser.AdminUser;
import page.clapandwhistle.demo.spring.bizlogic.uam.Aggregate.Exception.NotExistException;
import page.clapandwhistle.demo.spring.bizlogic.uam.Aggregate.Exception.PasswordIsNotMatchException;
import page.clapandwhistle.demo.spring.bizlogic.uam.Aggregate.AdminUserAggregateRepositoryInterface;
import page.clapandwhistle.demo.spring.infrastructure.uam.AggregateRepository.Library.UserAccount.PasswordOperation;
import page.clapandwhistle.demo.spring.infrastructure.uam.TableModel.AdminAccountBase;
import page.clapandwhistle.demo.spring.infrastructure.uam.TableModel.AdminAccountBaseRepository;

import java.util.List;
import java.util.Optional;

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
    public long getAdminIdAllowedToLogIn(String email, String password)
            throws NotExistException, PasswordIsNotMatchException {
        List<AdminAccountBase> listAdminAccountBase = this.adminBaseDao.findByEmail(email);
        if (listAdminAccountBase.isEmpty())  throw new NotExistException();
        if (listAdminAccountBase.size() > 1) throw new RuntimeException("複数ヒットしました");

        AdminAccountBase adminAccountBase = listAdminAccountBase.get(0);
        if (this.passwordOperator.isPasswordMatch(password, adminAccountBase.getPassword())) {
            return adminAccountBase.getId();
        }
        throw new PasswordIsNotMatchException();
    }

    @Override
    public AdminUser findById(long id) {
        Optional<AdminAccountBase> optAdminAccountBase = this.adminBaseDao.findById(id);
        AdminAccountBase adminAccountBase = optAdminAccountBase.orElse(null);
        return adminAccountBase == null
                ? null
                : AdminUser.buildForFind(
                        adminAccountBase.getId()
                        , adminAccountBase.getEmail()
                        , adminAccountBase.getAccountStatus()
                    );
    }
}
