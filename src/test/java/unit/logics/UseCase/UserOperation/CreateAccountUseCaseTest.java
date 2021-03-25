package unit.logics.UseCase.UserOperation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import page.clapandwhistle.uam.infrastructure.AggregateRepository.User.ForTestUserAggregateRepository;
import page.clapandwhistle.uam.logics.Aggregate.User.Exception.BirthDateStrInvalidException;
import page.clapandwhistle.uam.logics.Aggregate.User.Exception.FullNameSizeTooLongException;
import page.clapandwhistle.uam.logics.Aggregate.User.Exception.PasswordSizeTooShortException;
import page.clapandwhistle.uam.logics.Aggregate.User.Exception.PasswordTypeCompositionInvalidException;
import page.clapandwhistle.uam.logics.UseCase.UserOperation.CreateAccount.Arguments;
import page.clapandwhistle.uam.logics.UseCase.UserOperation.CreateAccount.CreateAccountUseCase;
import page.clapandwhistle.uam.logics.UseCase.UserOperation.CreateAccount.Result;
import page.clapandwhistle.uam.logics.UseCase.UserOperation.CreateAccount.Exception.ApplyingException;
import page.clapandwhistle.uam.logics.UseCase.UserOperation.CreateAccount.Exception.EmailAlreadyUsedException;

final public class CreateAccountUseCaseTest {
    @BeforeEach
    void setUp(TestInfo testInfo) {
//        System.out.println(String.format("test started: %s", testInfo.getDisplayName()));
    }

    @Test
    public void アカウント作成の申請をする_基本系列() throws Exception {
        ForTestUserAggregateRepository repo = ForTestUserAggregateRepository.getInstance();
        CreateAccountUseCase useCase = new CreateAccountUseCase(repo);
        Result result;

        result = useCase.execute(new Arguments(
                repo.使われていないメールアドレスを生成する()
                , ForTestUserAggregateRepository.テスト用Password
            ));
        assertTrue(result.isSuccess());

        result = useCase.execute(new Arguments(
                repo.使われていないメールアドレスを生成する()
                , ForTestUserAggregateRepository.テスト用Password
                , null
                , null
            ));
        assertTrue(result.isSuccess());

        result = useCase.execute(new Arguments(
                repo.使われていないメールアドレスを生成する()
                , ForTestUserAggregateRepository.テスト用Password
                , ""
                , ""
            ));
        assertTrue(result.isSuccess());

        result = useCase.execute(new Arguments(
                repo.使われていないメールアドレスを生成する()
                , ForTestUserAggregateRepository.テスト用Password
                , "ほげ田ほげ夫"
                , "19770401"
            ));
        assertTrue(result.isSuccess());
    }

    @Test
    public void アカウント作成の申請をする_代替系列_申請中のメールアドレス() throws Exception {
        Arguments args = new Arguments(
                ForTestUserAggregateRepository.例外用_ユーザID_1_申請中メールアドレス
                , ForTestUserAggregateRepository.テスト用Password
            );
        CreateAccountUseCase useCase = new CreateAccountUseCase(ForTestUserAggregateRepository.getInstance());
        assertEquals(ApplyingException.class, useCase.execute(args).exception().getClass());
    }

    @Test
    public void アカウント作成の申請をする_代替系列_既に使用中のメールアドレス() throws Exception {
        Arguments args = new Arguments(
                ForTestUserAggregateRepository.例外用_ユーザID_2_既に使用されているメールアドレス
                , ForTestUserAggregateRepository.テスト用Password
            );
        CreateAccountUseCase useCase = new CreateAccountUseCase(ForTestUserAggregateRepository.getInstance());
        assertEquals(EmailAlreadyUsedException.class, useCase.execute(args).exception().getClass());
    }

    @Test
    public void アカウント作成の申請をする_代替系列_パスワードが8文字未満() throws Exception {
        ForTestUserAggregateRepository repo = ForTestUserAggregateRepository.getInstance();
        CreateAccountUseCase useCase = new CreateAccountUseCase(repo);

        assertEquals(PasswordSizeTooShortException.class, useCase.execute(
            new Arguments(
                repo.使われていないメールアドレスを生成する()
                , "Hoge1"
            )).exception().getClass());

        assertEquals(PasswordSizeTooShortException.class, useCase.execute(
            new Arguments(
                repo.使われていないメールアドレスを生成する()
                , "" // 空のパスワード
            )).exception().getClass());
    }

    @Test
    public void アカウント作成の申請をする_代替系列_パスワードの文字種に過不足() throws Exception {
        ForTestUserAggregateRepository repo = ForTestUserAggregateRepository.getInstance();
        CreateAccountUseCase useCase = new CreateAccountUseCase(repo);

        assertEquals(PasswordTypeCompositionInvalidException.class, useCase.execute(
            new Arguments(
                repo.使われていないメールアドレスを生成する()
                , "hogehoge" // 半角英小文字のみ
            )).exception().getClass());

        assertEquals(PasswordTypeCompositionInvalidException.class, useCase.execute(
            new Arguments(
                repo.使われていないメールアドレスを生成する()
                , "HOGEHOGE" // 半角英大文字のみ
            )).exception().getClass());

        assertEquals(PasswordTypeCompositionInvalidException.class, useCase.execute(
            new Arguments(
                repo.使われていないメールアドレスを生成する()
                , "12345678" // 半角数字のみ
            )).exception().getClass());

        assertEquals(PasswordTypeCompositionInvalidException.class, useCase.execute(
            new Arguments(
                repo.使われていないメールアドレスを生成する()
                , "FUGAfuga" // 半角数字が含まれてない
            )).exception().getClass());

        assertEquals(PasswordTypeCompositionInvalidException.class, useCase.execute(
            new Arguments(
                repo.使われていないメールアドレスを生成する()
                , "FUGA1234" // 半角小文字が含まれてない
            )).exception().getClass());

        assertEquals(PasswordTypeCompositionInvalidException.class, useCase.execute(
            new Arguments(
                repo.使われていないメールアドレスを生成する()
                , "1234fuga" // 半角大文字が含まれてない
            )).exception().getClass());

        assertEquals(PasswordTypeCompositionInvalidException.class, useCase.execute(
            new Arguments(
                repo.使われていないメールアドレスを生成する()
                , "1234ほげFuga" // 半角数値・半角大文字・半角小文字のいずれでもない文字が含まれている
            )).exception().getClass());

        assertEquals(PasswordTypeCompositionInvalidException.class, useCase.execute(
            new Arguments(
                repo.使われていないメールアドレスを生成する()
                , "1234_Fuga." // 半角数値・半角大文字・半角小文字のいずれでもない文字が含まれている
            )).exception().getClass());
    }

    @Test
    public void アカウント作成の申請をする_代替系列_プロフィールの入力内容が要件を満たしていない() throws Exception {
        ForTestUserAggregateRepository repo = ForTestUserAggregateRepository.getInstance();
        CreateAccountUseCase useCase = new CreateAccountUseCase(repo);

        assertEquals(FullNameSizeTooLongException.class, useCase.execute(
            new Arguments(
                repo.使われていないメールアドレスを生成する()
                , ForTestUserAggregateRepository.テスト用Password
                , "ほげ田ほげ夫ほげ田ほげ夫ほげ田ほげ夫ほげ田ほげ夫ほげ田ほげ夫ほげ田ほげ夫ほげ田ほげ夫ほげ田ほげ夫ほげ田ほげ夫ほげ田ほげ夫ほげ田ほげ夫ほげ田ほげ夫ほげ田ほげ夫ほげ田ほげ夫ほげ田ほげ夫ほげ田ほげ夫ほげ田ほげ夫ほげ田ほげ夫ほげ田ほげ夫ほげ田ほげ夫ほげ田ほげ夫ほげ田ほげ夫"
                , null
            )).exception().getClass());

        assertEquals(FullNameSizeTooLongException.class, useCase.execute(
            new Arguments(
                repo.使われていないメールアドレスを生成する()
                , ForTestUserAggregateRepository.テスト用Password
                , "ahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoahoaho"
                , null
            )).exception().getClass());

        assertEquals(BirthDateStrInvalidException.class, useCase.execute(
            new Arguments(
                repo.使われていないメールアドレスを生成する()
                , ForTestUserAggregateRepository.テスト用Password
                , null
                , "99999999"
            )).exception().getClass());

        assertEquals(BirthDateStrInvalidException.class, useCase.execute(
            new Arguments(
                repo.使われていないメールアドレスを生成する()
                , ForTestUserAggregateRepository.テスト用Password
                , null
                , "ほげほげ"
            )).exception().getClass());

        assertEquals(BirthDateStrInvalidException.class, useCase.execute(
            new Arguments(
                repo.使われていないメールアドレスを生成する()
                , ForTestUserAggregateRepository.テスト用Password
                , null
                , "19800340"
            )).exception().getClass());
    }

}
