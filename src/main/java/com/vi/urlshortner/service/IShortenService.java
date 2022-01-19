package com.vi.urlshortner.service;

import com.vi.urlshortner.entity.Url;

/**
 * Interface to implement by url shortening service.
 */
public interface IShortenService
{
  /**
   * Shorten the URL
   * @param url Url to shorten
   * @return returns passed url with shortened value.
   */
  Url shortenUrl(Url url);
}
