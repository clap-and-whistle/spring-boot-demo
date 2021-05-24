package page.clapandwhistle.uam.infrastructure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import page.clapandwhistle.uam.infrastructure.TableModel.UserAccountBase;
import page.clapandwhistle.uam.infrastructure.TableModel.UserAccountBaseRepository;
import page.clapandwhistle.uam.infrastructure.TableModel.UserAccountProfile;

@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

//    @Bean
//    CommandLineRunner initDatabase(UserAccountBaseRepository userAccountBaseRepos) {
//
//        return args -> {
//            UserAccountBase userAccountBase = new UserAccountBase("hoge01@example.local", "hoge01TEST");
//            userAccountBase.setUserAccountProfile(new UserAccountProfile("hoge01", "20000410"));
//            log.info("Preloading " + userAccountBaseRepos.save(userAccountBase));
//        };
//    }

    @Bean
    CommandLineRunner tmp() {
        return args -> {
            log.info("CommandLineRunner: no execution");
        };
    }
}
