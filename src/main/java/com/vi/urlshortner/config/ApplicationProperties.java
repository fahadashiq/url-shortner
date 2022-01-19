package com.vi.urlshortner.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Class to access configs of the application.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = true)
@Data
public class ApplicationProperties
{
  private ServerConfig serverConfig;

  @Data
  public static class ServerConfig {
    private String address;
    private Boolean usePort;
    private String scheme;
  }
}
