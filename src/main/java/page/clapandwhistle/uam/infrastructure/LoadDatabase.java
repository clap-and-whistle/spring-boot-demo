package page.clapandwhistle.uam.infrastructure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import page.clapandwhistle.uam.config.ExternalSettings;
import page.clapandwhistle.uam.infrastructure.TableModel.UserAccountBase;
import page.clapandwhistle.uam.infrastructure.TableModel.UserAccountBaseRepository;
import page.clapandwhistle.uam.infrastructure.TableModel.UserAccountProfile;

@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Autowired
    private ExternalSettings extSettings;

    @Bean
    CommandLineRunner initDatabase(UserAccountBaseRepository userAccountBaseRepos) {
        String profile = this.extSettings.getMvnProfile();
        return args -> {
            log.info("CommandLineRunner::initDatabase: " + profile);
            if (profile.equals("default") || profile.equals("testing")) {
                /* Unitテスト用のデータを登録する */
                UserAccountBase userAccountBase = new UserAccountBase(
                        "hoge01@example.local",
                        "$2a$10$rKNNt5Np1tRUx3vArlZwJ.jTgNspEfD/hLyZjLPeMAX5N0nhbvb7G" // "hoge01TEST"
                );
                userAccountBase.setUserAccountProfile(new UserAccountProfile("hoge01", "20000410"));
                log.info("  Preloading " + userAccountBaseRepos.save(userAccountBase));
            } else {
                log.info("  No Preloading: ");
            }
        };
    }
}
