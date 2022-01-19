package com.vi.urlshortner.service;

import com.vi.urlshortner.entity.Url;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

/**
 * A custom implementation for the url shortening service.
 */
@Service
public class ShortenServiceInternalImpl implements IShortenService
{


  /**
   * {@inheritDoc}
   */
  @Override
  public Url shortenUrl(Url url) {
    url.setShortenedValue(generateCode());
    url.setShortCount(1);
    url.setAccessCount(0);
    return url;
  }

  /**
   * This isn't the best implementation of this as it can generate duplicates.
   * For now we are using random string generator but this must be generated using the provided link so there are no duplicates.
   */
  private String generateCode() {
    return RandomStringUtils.randomAlphanumeric(5);
  }

}
