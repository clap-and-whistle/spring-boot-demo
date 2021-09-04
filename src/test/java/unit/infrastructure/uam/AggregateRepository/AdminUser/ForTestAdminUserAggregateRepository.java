package unit.infrastructure.uam.AggregateRepository.AdminUser;

import page.clapandwhistle.demo.spring.bizlogic.uam.Aggregate.AdminUser.AdminUser;
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
    public long getAdminIdAllowedToLogIn(String email, String password) {
        return 0;
    }

    @Override
    public AdminUser findById(long id) {
        return null;
    }
}
