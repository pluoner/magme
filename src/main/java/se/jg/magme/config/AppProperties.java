package se.jg.magme.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private String cardImagesPath;
    private String startupTarget;

    public String getCardImagesPath() {
        return cardImagesPath;
    }

    public void setCardImagesPath(String cardImagesPath) {
        this.cardImagesPath = cardImagesPath;
    }

    public String getStartupTarget() {
        return startupTarget;
    }

    public void setStartupTarget(String startupTarget) {
        this.startupTarget = startupTarget;
    }
}