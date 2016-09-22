package yd.web.misc;

import freemarker.core.Environment;
import freemarker.template.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * FreeMarker directive: Static asset resolver
 *
 * example:
 *   &lt;script src="[@asset path='js/fetchJSON.js' /]"&gt;
 */
@Component
@ConfigurationProperties(prefix = "web")
public class AssetDirective implements TemplateDirectiveModel {
  private String assetPrefix;

  @Override
  public void execute(
      Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body
  ) throws TemplateException, IOException {
    Object path = params.get("path");
    if (path == null) {
      throw new TemplateModelException("path required for [@asset] directive.");
    }
    env.getOut().write(assetPrefix + path);
  }

  public void setAssetPrefix(String assetPrefix) {
    this.assetPrefix = assetPrefix;
  }
}
