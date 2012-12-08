/*
 * Copyright (C) 2011 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.caliper.config;

import com.google.caliper.util.Util;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.common.io.InputSupplier;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * Loads caliper configuration files and, if necessary, creates new versions from the defaults.
 */
public final class CaliperConfigLoader {
  public static CaliperConfig loadOrCreate(File rcFile) {
    ImmutableMap<String, String> defaults;
    try {
      defaults = Util.loadProperties(Util.resourceSupplier(CaliperRc.class, "global.caliperrc"));
    } catch (IOException impossible) {
      throw new AssertionError(impossible);
    }

    // TODO(kevinb): deal with migration issue from old-style .caliperrc

    if (rcFile.exists()) {
      try {
        ImmutableMap<String, String> overrides =
            Util.loadProperties(Files.newInputStreamSupplier(rcFile));
        return new CaliperConfig(mergeProperties(overrides, defaults));
      } catch (IOException keepGoing) {
      }
    }

    InputSupplier<InputStream> supplier =
        Util.resourceSupplier(CaliperRc.class, "default.caliperrc");
    tryCopyIfNeeded(supplier, rcFile);

    ImmutableMap<String, String> overrides;
    try {
      overrides = Util.loadProperties(supplier);
    } catch (IOException e) {
      throw new AssertionError(e); // class path must be messed up
    }
    return new CaliperConfig(mergeProperties(overrides, defaults));
  }

  private static ImmutableMap<String, String> mergeProperties(Map<String, String> overrides,
      Map<String, String> defaults) {
    Map<String, String> map = Maps.newHashMap(defaults);
    map.putAll(overrides); // overwrite and augment
    Iterables.removeIf(map.values(), Predicates.equalTo(""));
    return ImmutableMap.copyOf(map);
  }

  private static void tryCopyIfNeeded(InputSupplier<? extends InputStream> supplier, File rcFile) {
    if (!rcFile.exists()) {
      try {
        Files.copy(supplier, rcFile);
      } catch (IOException e) {
        rcFile.delete();
      }
    }
  }
}
