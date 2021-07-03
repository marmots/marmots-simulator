package org.marmots.simulator.objects;

import static org.junit.Assert.assertTrue;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleObjectFactoryTest {
  static Logger LOGGER = LoggerFactory.getLogger(SimpleObjectFactoryTest.class);

  @Test
  public void testGetRandomColor() {
    for(int i=0;i<20;i++) {
      LOGGER.info(SimpleObjectFactory.getRandomColor());
    }
  }
  
  @Test
  public void testGetRandomHTMLDescription() {
    for(int i=0;i<20;i++) {
      LOGGER.info(SimpleObjectFactory.getRandomHTMLDescription());
    }
  }
  
  @Test
  public void testGetRandomHTML() {
    for(int i=0;i<20;i++) {
      int limit = RandomUtils.nextInt(500, 3000);
      String html = SimpleObjectFactory.getRandomHTML(limit);
      LOGGER.info("limit:{}, html.length:{}, HTML: {} ", limit, html.length(), html);
      assertTrue(String.format("Generated html problem: %s (html.length %d is greater than %d)", html, html.length(), limit), limit >= html.length());
    }
  }

}
