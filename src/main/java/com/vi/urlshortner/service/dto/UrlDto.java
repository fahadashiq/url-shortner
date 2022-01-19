package com.vi.urlshortner.service.dto;

import lombok.Data;

@Data
public class UrlDto
{
  private String value;
  private String shortenedValue;
  private String description;
  private Integer shortCount;
  private Integer accessCount;
}
