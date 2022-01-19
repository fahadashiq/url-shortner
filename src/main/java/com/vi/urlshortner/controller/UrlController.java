package com.vi.urlshortner.controller;

import com.vi.urlshortner.service.UrlService;
import com.vi.urlshortner.service.dto.UrlDto;
import com.vi.urlshortner.service.utils.ServerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Controller class for URL.
 */
@Controller
public class UrlController
{

  @Autowired
  private UrlService urlService;
  @Autowired
  private ServerUtils serverUtils;

  @PostMapping("/")
  public ModelAndView shortenUrl(@ModelAttribute("url") UrlDto url, Model model) {
    UrlDto processedUrl = urlService.shortenUrl(url);
    model.addAttribute("urlList", urlService.populateUrlTable(processedUrl));
    model.addAttribute("serverUrl", serverUtils.getServerAddress());
    return new ModelAndView("index");
  }

  @GetMapping("/")
  public ModelAndView index(Model model)
  {
    model.addAttribute("url", new UrlDto());
    model.addAttribute("urlList", urlService.populateUrlTable(null));
    model.addAttribute("serverUrl", serverUtils.getServerAddress());
    return new ModelAndView("index");

  }

  @GetMapping("/{code}")
  public RedirectView redirectToFullVersion(@PathVariable String code) {
    RedirectView redirectView = new RedirectView();
    redirectView.setUrl(urlService. getUrlFromShortenedValue(code).getValue());
    return redirectView;
  }
}
