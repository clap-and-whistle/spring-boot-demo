package page.clapandwhistle.demo.spring.infrastructure.uam.AggregateRepository.User;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import org.springframework.transaction.annotation.Transactional;
import page.clapandwhistle.demo.spring.bizlogic.uam.Aggregate.Exception.RegistrationProcessFailedException;
import page.clapandwhistle.demo.spring.bizlogic.uam.Aggregate.UserAggregateRepositoryInterface;
import page.clapandwhistle.demo.spring.bizlogic.uam.Aggregate.User.AccountStatus;
import page.clapandwhistle.demo.spring.bizlogic.uam.Aggregate.User.User;
import page.clapandwhistle.demo.spring.bizlogic.uam.Aggregate.Exception.NotExistException;
import page.clapandwhistle.demo.spring.bizlogic.uam.Aggregate.Exception.PasswordIsNotMatchException;
import page.clapandwhistle.demo.spring.infrastructure.uam.AggregateRepository.Library.UserAccount.PasswordOperation;
import page.clapandwhistle.demo.spring.infrastructure.uam.TableModel.UserAccountBase;
import page.clapandwhistle.demo.spring.infrastructure.uam.TableModel.UserAccountBaseRepository;
import page.clapandwhistle.demo.spring.infrastructure.uam.TableModel.UserAccountProfile;

@Component
public class UserAggregateRepository implements UserAggregateRepositoryInterface {
    final private UserAccountBaseRepository tableRepoUserAccountBase;
    final private PasswordOperation passwordOperator;

    @Autowired
    public UserAggregateRepository(UserAccountBaseRepository userAccountBaseRepo) {
        super();
        this.tableRepoUserAccountBase = userAccountBaseRepo;
        this.passwordOperator = new PasswordOperation();
    }

    @Override
    public User findById(long id) {
        Optional<UserAccountBase> optUserAccountBase = this.tableRepoUserAccountBase.findById(id);
        UserAccountBase userAccountBase = optUserAccountBase.orElse(null);
        if (userAccountBase == null) {
            return null;
        }
        UserAccountProfile userAccountProfile = userAccountBase.getUserAccountProfile();
        return  User.buildForFind(
                    userAccountBase.getId()
                    , userAccountBase.getEmail()
                    , userAccountBase.getAccountStatus()
                    , userAccountProfile.getFullName()
                    , userAccountProfile.getBirthDateStr()
                );
    }

    @Override
    public long getUserIdByEmail(String email) {
        List<UserAccountBase> listUserAccountBase = this.tableRepoUserAccountBase.findByEmail(email);
        if (listUserAccountBase.isEmpty())  return 0;
        if (listUserAccountBase.size() > 1) throw new RuntimeException("複数ヒットしました");

        return listUserAccountBase.get(0).getId();
    }

    @Override
    public boolean isApplying(long userId) {
        Optional<UserAccountBase> optUserAccountBase = this.tableRepoUserAccountBase.findById(userId);
        return optUserAccountBase.isPresent() && AccountStatus.APPLYING.raw() == optUserAccountBase.get().getAccountStatus();
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public long save(User user) throws RegistrationProcessFailedException {
        // TODO: まだ「アカウント作成」のケースだけのためのロジックなので、「user.id() が空かそうでないか」で分岐必要
        UserAccountBase entityUserAccountBase = new UserAccountBase();
        UserAccountProfile entityUserAccountProfile = new UserAccountProfile();

        entityUserAccountBase.setEmail(user.email());
        // TODO: パスワード保存処理の分離（setPasswordは「アカウント作成」と「パスワード変更/再発行」のUseCaseだけ行うようにする）
        BCryptPasswordEncoder pwEncoder = new BCryptPasswordEncoder();
        entityUserAccountBase.setPassword(pwEncoder.encode(user.password()));
        entityUserAccountBase.setAccountStatus(user.accountStatus().raw());
        entityUserAccountProfile.setFullName(user.fullName());
        entityUserAccountProfile.setBirthDateStr(user.birthDateStr());
        entityUserAccountBase.setUserAccountProfile(entityUserAccountProfile);
        try {
            this.tableRepoUserAccountBase.save(entityUserAccountBase);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RegistrationProcessFailedException();
        }
        // 「アカウント作成」のケースでも、上記の save(entityUserAccountBase) が成功していれば
        //  自動生成された id が entityUserAccountBaseインスタンスへ反映されていることを確認済み
        long id = entityUserAccountBase.getId();

        // 登録されたレコード内容によってUser集約を構築できることを確認するために、ここでfindById()まで実行しておく
        User createdUser = this.findById(id);
        return createdUser.id();
    }

    @Override
    public long getUserIdAllowedToLogIn(String email, String password) {
        List<UserAccountBase> listUserAccountBase = this.tableRepoUserAccountBase.findByEmail(email);
        if (listUserAccountBase.isEmpty())  throw new NotExistException();
        if (listUserAccountBase.size() > 1) throw new RuntimeException("複数ヒットしました");

        UserAccountBase userAccountBase = listUserAccountBase.get(0);
        if (passwordOperator.isPasswordMatch(password, userAccountBase.getPassword())) {
            return userAccountBase.getId();
        }
        throw new PasswordIsNotMatchException();
    }

}
