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

/**
 * Created by red on 11/14/15.
 */
public class JavaEnv {
  public static String get() {
    return get("development");
  }

  public static String get(String def) {
    String env = System.getProperty("env");
    if (env == null || env.equals("")) {
      env = System.getenv("JAVA_ENV");
    }
    return (env == null || env.equals("")) ? def : env;
  }
}
