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

import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

public class Environment {
  private static Environment environment;
  private static final Object lock = new Object();
  private Properties props;

  public static Environment getSingleton() {
    if (environment == null) {
      synchronized (lock) {
        if (environment == null) {
          environment = new Environment();
          environment.initialize();
        }
      }
    }
    return environment;
  }

  private InputStream getResourceStream(String path) {
    return getClass().getResourceAsStream(path);
  }

  private boolean tryLoadProperties() {
    try {
      this.props.load(getResourceStream("/" + JavaEnv.get() + ".properties"));
      return true;
    } catch (IOException e) {
      return false;
    }
  }

  private boolean tryLoadYaml() {
    InputStream in = getResourceStream("/" + JavaEnv.get() + ".yml");
    if (in == null) {
      in = getResourceStream("/" + JavaEnv.get() + ".yaml");
    }
    if (in == null) return false;
    this.props.putAll(new Yaml().load(in));
    return true;
  }

  private Environment() {
    this.props = new Properties();
  }

  private void initialize() {
    if (!tryLoadProperties() && !tryLoadYaml()) {
      System.out.println("Failed to load both properties and yaml file.");
      System.exit(0);
    }
  }


  public static String get(String key) {
    return getSingleton().props.getProperty(key);
  }

  public static String get(String key, String defaultValue) {
    return getSingleton().props.getProperty(key, defaultValue);
  }

  public static Integer getInt(String key)  {
    return Integer.parseInt(get(key));
  }

  public static Integer getInt(String key, int defaultValue)  {
    return Optional.ofNullable(get(key)).map(Integer::parseInt).orElse(defaultValue);
  }
}

