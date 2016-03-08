/**
 * Copyright (C) 2015 Red Siwon Choi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zoyi.util.env;

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

