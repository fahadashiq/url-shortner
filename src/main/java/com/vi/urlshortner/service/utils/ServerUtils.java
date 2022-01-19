package com.vi.urlshortner.service.utils;

import com.vi.urlshortner.config.ApplicationProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Utils class to get server info.
 */
@Component
public class ServerUtils
{
  @Autowired
  private Environment environment;
  @Autowired
  private ApplicationProperties applicationProperties;

  private String serverAddress;

  /**
   * Return current server address.
   * @return server address.
   */
  public String getServerAddress() {
    if (StringUtils.isEmpty(serverAddress)) {
      constructServerUrl();
    }
    return serverAddress;
  }

  private void constructServerUrl()
  {
    String port = environment.getProperty("local.server.port");

    serverAddress =  UriComponentsBuilder
            .newInstance()
            .scheme(applicationProperties.getServerConfig().getScheme())
            .host(getAddress())
            .port(applicationProperties.getServerConfig().getUsePort() ? port : null)
            .path("/")
            .build()
            .toUriString();
  }

  private String getAddress() {
    if (StringUtils.isNotEmpty(applicationProperties.getServerConfig().getAddress())) {
      return applicationProperties.getServerConfig().getAddress();
    }
    else {
      try {
        return InetAddress.getLocalHost().getHostAddress();
      }
      catch (UnknownHostException e) {
        return "localhost";
      }
    }
  }

}
