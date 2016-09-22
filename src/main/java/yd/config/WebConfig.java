package yd.config;

import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ConfigurationProperties(prefix = "web")
public class WebConfig extends WebMvcConfigurerAdapter {
  private String assetPrefix;
  private String assetPath;

  @Bean
  DefaultErrorAttributes errorAttributes() { return new DefaultErrorAttributes(); }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    // For assets served directly from spring MVC
    // irrelevant if using CDN for assets.
    registry
        .addResourceHandler(assetPrefix + "**")
        .addResourceLocations(assetPath)
        .setCachePeriod(31556926);
  }

  public void setAssetPath(String assetPath) { this.assetPath = assetPath; }
  public void setAssetPrefix(String assetPrefix) { this.assetPrefix = assetPrefix; }
}
