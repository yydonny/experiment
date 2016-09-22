package yd.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;
import yd.web.misc.AssetDirective;

import java.util.HashMap;
import java.util.Map;

/**
 * Configure the FreeMarker template engine and Spring's View Resolution.
 *
 * We can't rely on the Spring Boot's auto-configure feature, as we need to register
 * custom directives.
 */
@Configuration
@ConfigurationProperties(prefix = "web")
public class FreeMarkerConfig {
  private String templatePath;
  private boolean templateCache;

  @Bean
  FreeMarkerViewResolver freeMarkerViewResolver() {
    FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
    resolver.setPrefix("");
    resolver.setSuffix(".html");
    resolver.setCache(templateCache);
    return resolver;
  }

  @Bean
  FreeMarkerConfigurer freeMarkerConfigurer(AssetDirective assetDirective) {
    FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
    Map<String, Object> directives = new HashMap<>();

    // Custom directives: [@asset /]
    directives.put("asset", assetDirective);

    configurer.setFreemarkerVariables(directives);
    configurer.setTemplateLoaderPath(templatePath);
    return configurer;
  }

  public void setTemplateCache(boolean templateCache) { this.templateCache = templateCache; }
  public void setTemplatePath(String templatePath) {
    this.templatePath = templatePath;
  }
}
