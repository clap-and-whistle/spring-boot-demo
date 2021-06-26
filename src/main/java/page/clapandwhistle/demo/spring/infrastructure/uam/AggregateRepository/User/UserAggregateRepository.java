package page.clapandwhistle.demo.spring.infrastructure.uam.AggregateRepository.User;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import page.clapandwhistle.demo.spring.bizlogic.uam.Aggregate.UserAggregateRepositoryInterface;
import page.clapandwhistle.demo.spring.bizlogic.uam.Aggregate.User.AccountStatus;
import page.clapandwhistle.demo.spring.bizlogic.uam.Aggregate.User.User;
import page.clapandwhistle.demo.spring.infrastructure.uam.AggregateRepository.Exception.RegistrationProcessFailedException;
import page.clapandwhistle.demo.spring.infrastructure.uam.AggregateRepository.User.Exception.NotExistException;
import page.clapandwhistle.demo.spring.infrastructure.uam.AggregateRepository.User.Exception.PasswordIsNotMatchException;
import page.clapandwhistle.demo.spring.infrastructure.uam.TableModel.UserAccountBase;
import page.clapandwhistle.demo.spring.infrastructure.uam.TableModel.UserAccountBaseRepository;
import page.clapandwhistle.demo.spring.infrastructure.uam.TableModel.UserAccountProfile;

@Component
final public class UserAggregateRepository implements UserAggregateRepositoryInterface {
    final private UserAccountBaseRepository tableRepoUserAccountBase;

    @Autowired
    public UserAggregateRepository(UserAccountBaseRepository userAccountBaseRepo) {
        super();
        this.tableRepoUserAccountBase = userAccountBaseRepo;
    }

    @Override
    public User findById(long id) {
        Optional<UserAccountBase> optUserAccountBase = this.tableRepoUserAccountBase.findById(id);
        UserAccountBase userAccountBase = optUserAccountBase.get();
        UserAccountProfile userAccountProfile = userAccountBase.getUserAccountProfile();
        return optUserAccountBase.isEmpty()
            ? null
            : new User(
                    userAccountBase.getId()
                    , userAccountBase.getEmail()
                    , null
                    , AccountStatus.getByRaw(userAccountBase.getAccount_status())
                    , userAccountProfile.getFullName()
                    , userAccountProfile.getBirthDateStr()
                );
    }

    @Override
    public long isExist(String email) {
        List<UserAccountBase> listUserAccountBase = this.tableRepoUserAccountBase.findByEmail(email);
        if (listUserAccountBase.isEmpty())  return 0;
        if (listUserAccountBase.size() > 1) throw new RuntimeException("複数ヒットしました");

        return listUserAccountBase.get(0).getId();
    }

    @Override
    public boolean isApplying(long userId) {
        Optional<UserAccountBase> optUserAccountBase = this.tableRepoUserAccountBase.findById(userId);
        return optUserAccountBase.isEmpty()
            ? false
            : AccountStatus.APPLYING.raw() == optUserAccountBase.get().getAccount_status();
    }

    @Override
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
        return this.tableRepoUserAccountBase.findTopByOrderByIdDesc().get().getId();
    }

    @Override
    public long getUserIdAllowedToLogIn(String email, String password) {
        List<UserAccountBase> listUserAccountBase = this.tableRepoUserAccountBase.findByEmail(email);
        if (listUserAccountBase.isEmpty())  throw new NotExistException();
        if (listUserAccountBase.size() > 1) throw new RuntimeException("複数ヒットしました");

        UserAccountBase userAccountBase = listUserAccountBase.get(0);
        if (this.isPasswordMatch(password, userAccountBase)) {
            return userAccountBase.getId();
        }
        throw new PasswordIsNotMatchException();
    }

    private boolean isPasswordMatch(String input, UserAccountBase userAccountBase) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String savedDigest = userAccountBase.getPassword();
        return passwordEncoder.matches(input, savedDigest);
    }
}
