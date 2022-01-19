package com.vi.urlshortner.service;

import com.vi.urlshortner.entity.Url;
import com.vi.urlshortner.repository.UrlRepository;
import com.vi.urlshortner.service.dto.UrlDto;
import com.vi.urlshortner.service.dto.mapper.UrlMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Test class for {@link UrlService}
 */
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UrlServiceTest
{
  @InjectMocks
  private UrlService urlService;

  @Mock
  private IShortenService shortenService;

  @Mock
  private UrlRepository urlRepository;
  @Mock
  private UrlMapper urlMapper;

  private UrlDto request;
  private  UrlDto response;

  @Test
  public void givenValidUrl_isShortened() {
    givenUrlRequest();
    whenShortenUrlIsCalled();
    thenShortenedUrlISReturned();
  }

  private void givenUrlRequest() {
    request = new UrlDto();
    response = new UrlDto();
    request.setValue("http://google.com");
    response.setShortenedValue("shortvalue");
    when(shortenService.shortenUrl(any())).thenReturn(new Url());
    when(urlRepository.getByValue(any())).thenReturn(null);
    when(urlRepository.save(any())).thenReturn(new Url());
    when(urlMapper.toDto(any(Url.class))).thenReturn(response);
  }

  private void whenShortenUrlIsCalled() {
    response = urlService.shortenUrl(request);
  }

  private void thenShortenedUrlISReturned() {
    assertEquals("shortvalue", response.getShortenedValue());
  }


}