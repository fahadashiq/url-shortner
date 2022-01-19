package com.vi.urlshortner.service.dto.mapper;

import com.vi.urlshortner.service.dto.UrlDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UrlMapper extends DtoMapper<UrlDto, com.vi.urlshortner.entity.Url>
{
}
