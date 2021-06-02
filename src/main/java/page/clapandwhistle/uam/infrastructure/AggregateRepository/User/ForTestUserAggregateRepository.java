package page.clapandwhistle.uam.infrastructure.AggregateRepository.User;

import java.security.SecureRandom;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import page.clapandwhistle.uam.infrastructure.AggregateRepository.User.Exception.NotExistException;
import page.clapandwhistle.uam.infrastructure.AggregateRepository.User.Exception.PasswordIsNotMatchException;
import page.clapandwhistle.uam.logics.Aggregate.UserAggregateRepositoryInterface;
import page.clapandwhistle.uam.logics.Aggregate.User.AccountStatus;
import page.clapandwhistle.uam.logics.Aggregate.User.User;

final public class ForTestUserAggregateRepository implements UserAggregateRepositoryInterface {
    final private Map<Long, User> masterAccountList = new TreeMap<Long, User>();

    final public static String テスト用Password = "hogeFuga123";
    final public static String テスト用の誤ったPassword = "hogePiyo123";

    final public static int 例外用_ユーザID_1_申請中 = 1;
    final public static String 例外用_ユーザID_1_申請中メールアドレス = "appraying@example.local";

    final public static int 例外用_ユーザID_2_既に使用中 = 2;
    final public static String 例外用_ユーザID_2_既に使用されているメールアドレス = "used@example.local";

    final public static String システムログイン時例外用_使用されていないメールアドレス = "not-used@example.local";

    /* シングルトン */
    private ForTestUserAggregateRepository() {
        this.save(new User(
                ForTestUserAggregateRepository.例外用_ユーザID_1_申請中
                , ForTestUserAggregateRepository.例外用_ユーザID_1_申請中メールアドレス
                , ForTestUserAggregateRepository.テスト用Password
                , AccountStatus.APPLYING
                , "ほげ田ほげ夫"
                , null));
        this.save(new User(
                ForTestUserAggregateRepository.例外用_ユーザID_2_既に使用中
                , ForTestUserAggregateRepository.例外用_ユーザID_2_既に使用されているメールアドレス
                , ForTestUserAggregateRepository.テスト用Password
                , AccountStatus.IN_OPERATION
                , null
                , "19771231"));
    }

    public static ForTestUserAggregateRepository getInstance() {
        return SelfInstanceHolder.INSTANCE;
    }

    public static class SelfInstanceHolder {
        final private static ForTestUserAggregateRepository INSTANCE = new ForTestUserAggregateRepository();
    }

    /* ここから一般メソッド */
    @Override
    public User findById(long id) {
        return this.masterAccountList.get(id);
    }

    @Override
    public long isExist(String email) {
        User user;
        for (Map.Entry<Long, User> entry : this.masterAccountList.entrySet()) {
            user = entry.getValue();
            if (user.email().equals(email)) {
                return user.id();
            }
        }
        return 0;
    }

    @Override
    public boolean isApplying(long userId) {
        return this.masterAccountList.get(userId).accountStatus() == AccountStatus.APPLYING;
    }

    @Override
    public long save(User user) {
        long id = user.id();
        if (id == 0) {
            id = (new Random().nextInt(10000)) + 1;
            user = new User(
                    id
                    , user.email()
                    , user.password()
                    , user.accountStatus()
                    , user.fullName()
                    , user.birthDateStr()
                );
        }
        masterAccountList.put(id, user);
        return id;
    }

    public String 使われていないメールアドレスを生成する() {
        String email_domain = "@example.local";
        String email_local;

        do {
            email_local = this.ランダムな20桁の文字列を生成する();
//            System.out.println("email_local: " + email_local);
        } while (this.isExist(email_local + "@" + email_domain) > 0);
        return email_local + "@" + email_domain;
    }

    private String ランダムな20桁の文字列を生成する() {
        SecureRandom random;
        byte bytes[] = new byte[10];
        StringBuffer sb;

        sb = new StringBuffer();
        random = new SecureRandom();
        random.nextBytes(bytes);
        for (int i = 0; i < bytes.length; i++) {
            sb.append(String.format("%02x", bytes[i]));
        }
        return  sb.toString();
    }

    @Override
    public long getUserIdAllowedToLogIn(String email, String password) {
        User user;
        for (Map.Entry<Long, User> entry : this.masterAccountList.entrySet()) {
            user = entry.getValue();
            if (user.email().equals(email)) {
                if (this.isPasswordMatch(password, user.password())) {
                    return user.id();
                } else {
                    throw new PasswordIsNotMatchException();
                }
            }
        }
        throw new NotExistException();
    }

    private boolean isPasswordMatch(String password, String orginPassword) {
        return password.equals(orginPassword);
    }
}
