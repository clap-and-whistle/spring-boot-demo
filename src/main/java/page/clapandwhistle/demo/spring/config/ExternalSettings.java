package page.clapandwhistle.demo.spring.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "external.settings")
public class ExternalSettings {
    /**
     * values: default, development, testing
     * @see /src/main/resources/application.properties
     */
    private String mvnProfile;

    public String getMvnProfile() {
        return mvnProfile;
    }

    public void setMvnProfile(String mvnProfile) {
        this.mvnProfile = mvnProfile;
    }
}
