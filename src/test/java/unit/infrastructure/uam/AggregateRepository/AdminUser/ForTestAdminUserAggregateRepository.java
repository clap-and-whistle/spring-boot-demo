package unit.infrastructure.uam.AggregateRepository.AdminUser;

import page.clapandwhistle.demo.spring.bizlogic.uam.Aggregate.AdminUser.AdminUser;
import page.clapandwhistle.demo.spring.bizlogic.uam.Aggregate.Exception.NotExistException;
import page.clapandwhistle.demo.spring.bizlogic.uam.Aggregate.Exception.PasswordIsNotMatchException;
import page.clapandwhistle.demo.spring.bizlogic.uam.Aggregate.AdminUserAggregateRepositoryInterface;

import java.util.Map;
import java.util.TreeMap;

/**
 * Bill Pugh Singleton
 */
public class ForTestAdminUserAggregateRepository implements AdminUserAggregateRepositoryInterface {
    private static class SelfInstanceHolder {
        private static final ForTestAdminUserAggregateRepository INSTANCE = new ForTestAdminUserAggregateRepository();
    }
    final private Map<Long, AdminUser> adminAccountMaster = new TreeMap<Long, AdminUser>();

    final public static String テスト用Password = "hogeFuga123";
    final public static String テスト用の誤ったPassword = "hogePiyo123";

    final public static String テスト用の有効な管理者メールアドレス = "adm01@example.local";
    final public static String システムログイン時例外用_使用されていないメールアドレス = "not-used@example.local";

    /** シングルトン */
    private ForTestAdminUserAggregateRepository() {
        AdminUser adminUser = AdminUser.buildForTestData(1, テスト用の有効な管理者メールアドレス, テスト用Password, 1);
        this.adminAccountMaster.put(adminUser.id(), adminUser);
    }

    public static ForTestAdminUserAggregateRepository getInstance() {
        return SelfInstanceHolder.INSTANCE;
    }

    @Override
    public long getAdminIdAllowedToLogIn(String email, String password) throws NotExistException, PasswordIsNotMatchException {
        AdminUser adminUser;
        for (Map.Entry<Long, AdminUser> entry : this.adminAccountMaster.entrySet()) {
            adminUser = entry.getValue();
            if (adminUser.email().equals(email)) {
                if (this.isPasswordMatch(password, adminUser.password())) {
                    return adminUser.id();
                } else {
                    throw new PasswordIsNotMatchException();
                }
            }
        }
        throw new NotExistException();
    }

    @Override
    public AdminUser findById(long id) {
        return this.adminAccountMaster.get(id);
    }

    private boolean isPasswordMatch(String password, String orginPassword) {
        return password.equals(orginPassword);
    }
}
