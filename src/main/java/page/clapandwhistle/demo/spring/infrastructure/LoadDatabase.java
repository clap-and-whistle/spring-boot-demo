package page.clapandwhistle.demo.spring.infrastructure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import page.clapandwhistle.demo.spring.config.ExternalSettings;
import page.clapandwhistle.demo.spring.infrastructure.ec.TableModel.ItemMaster;
import page.clapandwhistle.demo.spring.infrastructure.ec.TableModel.ItemMasterRepository;
import page.clapandwhistle.demo.spring.infrastructure.uam.TableModel.*;

@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Autowired
    private ExternalSettings extSettings;

    @Bean
    CommandLineRunner initDatabase(
            ItemMasterRepository itemMasterRepos,
            UserAccountBaseRepository userAccountBaseRepos,
            AdminAccountBaseRepository adminAccountBaseRepos
    ) {
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
                log.info("  Preloading UserAccount" + userAccountBaseRepos.save(userAccountBase));
                AdminAccountBase adminAccountBase = new AdminAccountBase(
                        "adm01@example.local",
                        "$2a$10$rKNNt5Np1tRUx3vArlZwJ.jTgNspEfD/hLyZjLPeMAX5N0nhbvb7G", // "hoge01TEST"
                        1
                );
                log.info("  Preloading AdminUserAccount" + adminAccountBaseRepos.save(adminAccountBase));

                /* ECパッケージ用のダミー商品マスタを登録する */
                log.info("Preloading " + itemMasterRepos.save(new ItemMaster("ほげ検出装置", 10000, "hoge Inc.")));
                log.info("Preloading " + itemMasterRepos.save(new ItemMaster("ほげ採取マシーン", 25000, "hoge Inc.")));
                log.info("Preloading " + itemMasterRepos.save(new ItemMaster("ふが浄化装置", 150000, "fuga Inc.")));
                log.info("Preloading " + itemMasterRepos.save(new ItemMaster("ふが製造機", 550000, "fuga Inc.")));
                log.info("Preloading " + itemMasterRepos.save(new ItemMaster("ぴよぴよ", 1500, "piyo Inc.")));
                log.info("Preloading " + itemMasterRepos.save(new ItemMaster("ぴよぴよ２", 1500, "piyo Inc.")));
                log.info("  Preloading " + userAccountBaseRepos.save(userAccountBase));
            } else {
                log.info("  No Preloading: ");
            }
        };
    }
}
