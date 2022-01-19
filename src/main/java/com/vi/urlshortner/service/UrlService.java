package com.vi.urlshortner.service;

import com.vi.urlshortner.entity.Url;
import com.vi.urlshortner.repository.UrlRepository;
import com.vi.urlshortner.service.dto.UrlDto;
import com.vi.urlshortner.service.dto.mapper.UrlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Service class for the url related business logic.
 */
@Service
public class UrlService
{
  @Autowired
  private IShortenService shortenService;
  @Autowired
  private UrlRepository urlRepository;
  @Autowired
  private UrlMapper urlMapper;

  /**
   * Shortens and save the url in database.
   * @param url url to shorten
   * @return shortened URL.
   */
  public UrlDto shortenUrl(UrlDto url) {
    Url savedUrl = urlRepository.getByValue(url.getValue());

    if (savedUrl != null) {
      increaseShortenCount(savedUrl);
      return urlMapper.toDto(savedUrl);
    }

    Url processedUrl =  shortenService.shortenUrl(urlMapper.toEntity(url));
    processedUrl.setUsername(getUserInfo());
    return urlMapper.toDto(saveUrl(processedUrl));
  }

  /**
   * Populate user table as per logged in user.
   * @param url url to return in case no user is logged in.
   * @return list of URLs to display.
   */
  public List<UrlDto> populateUrlTable(UrlDto url) {
    if(SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
      return urlMapper.toDto(urlRepository.findAll());
    }
    else if(SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER"))) {
      return urlMapper.toDto(urlRepository.findAllByUsername(getUserInfo()));
    }
    else {
      return url == null? new ArrayList<UrlDto>() : Collections.singletonList(url);
    }
  }

  /**
   * Used to get full url from shortened value.
   * @param shortenedUrl shortened url
   * @return full url.
   */
  public UrlDto getUrlFromShortenedValue(String shortenedUrl) {
    Url url = urlRepository.getByShortenedValue(shortenedUrl);
    increaseAccessCount(url);
    return urlMapper.toDto(url);
  }

  private String getUserInfo() {
    if (SecurityContextHolder.getContext().getAuthentication() != null) {
      Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      if (principal instanceof UserDetails) {
        return ((UserDetails) principal).getUsername();
      }
    }
    return null;
  }

  private Url saveUrl(Url url) {
    return urlRepository.save(url);
  }

  private void increaseAccessCount(Url url) {
    if ( url.getAccessCount() == null) {
      url.setAccessCount(Integer.valueOf(1));
    }
    else {
      url.setAccessCount(url.getAccessCount() + 1);
    }
    urlRepository.save(url);
  }

  private void increaseShortenCount(Url url) {
    if ( url.getShortCount() == null) {
      url.setShortCount(Integer.valueOf(1));
    }
    else {
      url.setShortCount(url.getShortCount() + 1);
    }
    urlRepository.save(url);
  }
}
