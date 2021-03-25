package unit.logics.UseCase.UserOperation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import page.clapandwhistle.uam.infrastructure.AggregateRepository.User.ForTestUserAggregateRepository;
import page.clapandwhistle.uam.infrastructure.AggregateRepository.User.Exception.NotExistException;
import page.clapandwhistle.uam.infrastructure.AggregateRepository.User.Exception.PasswordIsNotMatchException;
import page.clapandwhistle.uam.logics.UseCase.UserOperation.Login.Arguments;
import page.clapandwhistle.uam.logics.UseCase.UserOperation.Login.LoginUseCase;
import page.clapandwhistle.uam.logics.UseCase.UserOperation.Login.Result;

final public class LoginUseCaseTest {
    @BeforeEach
    void setUp(TestInfo testInfo) {
//        System.out.println(String.format("test started: %s", testInfo.getDisplayName()));
    }

    @Test
    public void システムへログインする_基本系列() throws Exception {
        Arguments args = new Arguments(
                ForTestUserAggregateRepository.例外用_ユーザID_2_既に使用されているメールアドレス
                , ForTestUserAggregateRepository.テスト用Password
            );
        LoginUseCase useCase = new LoginUseCase(ForTestUserAggregateRepository.getInstance());
        Result result = useCase.execute(args);
        assertTrue(result.isSuccess());
    }

    @Test
    public void システムへログインする_代替系列_メールアドレスの一致する既存ユーザが無い() throws Exception {
        ForTestUserAggregateRepository repo = ForTestUserAggregateRepository.getInstance();
        LoginUseCase useCase = new LoginUseCase(repo);

        assertEquals(NotExistException.class, useCase.execute(
            new Arguments(
                ForTestUserAggregateRepository.システムログイン時例外用_使用されていないメールアドレス
                , ForTestUserAggregateRepository.テスト用Password
            )).exception().getClass());
    }

    @Test
    public void システムへログインする_代替系列_パスワードが一致しない() throws Exception {
        ForTestUserAggregateRepository repo = ForTestUserAggregateRepository.getInstance();
        LoginUseCase useCase = new LoginUseCase(repo);

        assertEquals(PasswordIsNotMatchException.class, useCase.execute(
            new Arguments(
                ForTestUserAggregateRepository.例外用_ユーザID_2_既に使用されているメールアドレス
                , ForTestUserAggregateRepository.テスト用の誤ったPassword
            )).exception().getClass());
    }

}
