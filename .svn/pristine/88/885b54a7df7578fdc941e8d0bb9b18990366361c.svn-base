package org.marmots.simulator.objects;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenericObjectFactoryTest {
  static Logger LOGGER = LoggerFactory.getLogger(GenericObjectFactoryTest.class);

  private GenericObjectFactory factory = new GenericObjectFactory();

  @Test
  public void testGetInstance() {
    try {
      SampleVO sample = factory.getInstance(SampleVO.class);
      LOGGER.info("-------------------------------");
      LOGGER.info("object instantiated: {}", sample);
    } catch (Exception e) {
      fail("Exception generating object: " + e.getMessage());
    }
  }
}
