package page.clapandwhistle.demo.spring.bizlogic.uam.Aggregate.User;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import page.clapandwhistle.demo.spring.bizlogic.uam.Aggregate.User.Exception.BirthDateStrInvalidException;
import page.clapandwhistle.demo.spring.bizlogic.uam.Aggregate.User.Exception.FullNameSizeTooLongException;
import page.clapandwhistle.demo.spring.bizlogic.uam.Aggregate.Exception.PasswordSizeTooShortException;
import page.clapandwhistle.demo.spring.bizlogic.uam.Aggregate.Exception.PasswordTypeCompositionInvalidException;

final public class User {
    final public static int PASSWORD_MIN_LENGTH = 8;
    final public static int FULLNAME_BYTE_MAX_LENGTH = 255;

    final public static String ROLE = "ROLE_USER";
    final private long id;
    final private String email;
    final private String password;
    final private AccountStatus accountStatus;
    final private String fullName;
    final private String birthDateStr;

    private User(long id, String email, String password, AccountStatus accountStatus, String fullName, String birthDateStr) {
        this.id = id;
        this.email = makeNonNullString(email);
        this.password = makeNonNullString(password);
        this.accountStatus = accountStatus;
        this.fullName = makeNonNullString(fullName);
        this.birthDateStr = makeNonNullString(birthDateStr);
    }

    /**
     * テスト用データの作成を必要としているクラスでなら使って良い
     * @deprecated
     */
    public static User buildForTestData(long id, String email, String password, int accountStatus, String fullName, String birthDateStr) {
        return new User(id, email, password, AccountStatus.getByRaw(accountStatus), fullName, birthDateStr);
    }

    public static User buildForFind(long id, String email, int accountStatus, String fullName, String birthDateStr) {
        return new User(id, email, "", AccountStatus.getByRaw(accountStatus), fullName, birthDateStr);
    }

    public static User buildForCreate(String email, String password, String fullName, String birthDateStr) {
        User user = new User(0, email, password, AccountStatus.APPLYING, fullName, birthDateStr);

        // TODO: 現状、emailのバリデータについて設計段階で要件に無いので実装してないが本来は必要で、ここへ実装されるべきなはず。

        if (!user.isValidPasswordSize())
            throw new PasswordSizeTooShortException();

        if (!user.isValidPasswordCharacterTypeComposition())
            throw new PasswordTypeCompositionInvalidException();

        if (!user.isValidFullNameSize())
            throw new FullNameSizeTooLongException();

        if (!user.isValidBirthDateStr())
            throw new BirthDateStrInvalidException();

        return user;
    }

    private boolean isValidPasswordSize() throws PasswordSizeTooShortException {
        return this.password.length() >= User.PASSWORD_MIN_LENGTH;
    }

    private boolean isValidPasswordCharacterTypeComposition() {
        String regexNum = "^.*[0-9].*$";
        String regexSmall = "^.*[a-z].*$";
        String regexLarge = "^.*[A-Z].*$";
        String regexOther = "^.*[^0-9a-zA-Z].*$";
        return password.matches(regexNum)       // 半角数値がある
            && password.matches(regexSmall)     // 半角小文字がある
            && password.matches(regexLarge)     // 半角大文字がある
            && !password.matches(regexOther);   // いずれでもない文字は無い
    }

    private boolean isValidFullNameSize() {
        return this.fullName.getBytes().length < User.FULLNAME_BYTE_MAX_LENGTH;
    }

    private boolean isValidBirthDateStr() {
        if (this.birthDateStr.length() == 0)
            return true;

        if (this.birthDateStr.matches("^.*[^0-9].*$"))
            return false;

        boolean ret = true;
        String year, month, day;
        DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        try {
            year    = this.birthDateStr.substring(0, 4);
            month   = this.birthDateStr.substring(4, 6);
            day     = this.birthDateStr.substring(6);
            format.setLenient(false);
            format.parse(year + "-" + month + "-" + day);
        } catch (ParseException e) {
//            System.out.println("User.isValidBirthDateStr(): ParseException: " + e.getMessage());
            ret = false;
        } catch (Exception e) {
            System.out.println("User.isValidBirthDateStr(): " + e.getClass() + ": " + e.getMessage());
            ret = false;
        }
        return ret;
    }

    private String makeNonNullString(String target) {
        return target != null && target.length() > 0 ? target : "";
    }

    public long id() {
        return this.id;
    }

    public String email() {
        return this.email;
    }

    public String password() {
        return this.password;
    }

    public AccountStatus accountStatus() {
        return this.accountStatus;
    }

    public String fullName() {
        return fullName;
    }

    public String birthDateStr() {
        return birthDateStr;
    }
}
