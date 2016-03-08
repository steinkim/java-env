package com.zoyi.util.env;

/**
 * Created by huy on 15. 10. 14..
 */

import java.io.IOException;
import java.util.Properties;

public class Environment extends Properties {
  private static Environment environment;
  private static final Object lock = new Object();

  public static Environment getSingleton() {
    if (environment == null) {
      synchronized (lock) {
        if (environment == null) {
          environment = new Environment();
        }
      }
    }
    return environment;
  }

  private Environment() {
    try {
      load(getClass().getResourceAsStream("/" + JavaEnv.get() + ".properties"));
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(0);
    }
  }

  public static String get(String key) {
    return getSingleton().getProperty(key);
  }

  public static String get(String key, String defaultValue) {
    return getSingleton().getProperty(key, defaultValue);
  }
}

