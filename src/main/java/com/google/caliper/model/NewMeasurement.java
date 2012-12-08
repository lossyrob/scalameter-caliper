/*
 * Copyright (C) 2012 Google Inc.
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

package com.google.caliper.model;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.base.Objects;

import javax.annotation.concurrent.Immutable;

/**
 * A single, weighted measurement.
 *
 * @author gak@google.com (Gregory Kick)
 */
@Immutable
public class NewMeasurement {
  private final NewValue value;
  private final double weight;
  private final String description;

  private NewMeasurement(Builder builder) {
    this.value = builder.value;
    this.description = builder.description;
    this.weight = builder.weight;
  }

  @Override public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    } else if (obj instanceof NewMeasurement) {
      NewMeasurement that = (NewMeasurement) obj;
      return this.value.equals(that.value)
          && this.weight == that.weight
          && this.description.equals(that.description);
    } else {
      return false;
    }
  }

  @Override public int hashCode() {
    return Objects.hashCode(value, weight, description);
  }

  @Override public String toString() {
    return Objects.toStringHelper(this)
        .add("value", value)
        .add("weight", weight)
        .add("description", description)
        .toString();
  }

  public NewValue value() {
    return value;
  }

  public double weight() {
    return weight;
  }

  public String description() {
    return description;
  }

  public static final class Builder {
    private NewValue value;
    private Double weight;
    private String description;

    public Builder value(NewValue value) {
      this.value = checkNotNull(value);
      return this;
    }

    public Builder weight(double weight) {
      this.weight = weight;
      return this;
    }

    public Builder description(String description) {
      this.description = checkNotNull(description);
      return this;
    }

    public NewMeasurement build() {
      checkArgument(value != null);
      checkArgument(weight != null);
      checkArgument(description != null);
      return new NewMeasurement(this);
    }
  }
}
