package com.vi.urlshortner.repository;

import com.vi.urlshortner.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TO query on URL table.
 */
@Repository
public interface UrlRepository extends JpaRepository<Url, Long>
{
  Url getByShortenedValue(String shortenedUrl);

  Url getByValue(String value);

  List<Url> findAllByUsername(String username);
}
