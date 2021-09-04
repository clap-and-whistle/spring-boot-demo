package page.clapandwhistle.demo.spring.infrastructure.uam.AggregateRepository.Library.UserAccount;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public final class PasswordOperation {
    /**
     * ログイン処理等で使用されることを想定している。
     *
     * @param input         画面から入力された生データ
     * @param savedDigest   データアクセス層で取得されたエンコード済みのデータ
     */
    public boolean isPasswordMatch(String input, String savedDigest) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(input, savedDigest);
    }
}
