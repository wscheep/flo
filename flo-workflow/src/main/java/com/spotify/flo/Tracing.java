/*-
 * -\-\-
 * Flo Workflow Definition
 * --
 * Copyright (C) 2016 - 2018 Spotify AB
 * --
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * -/-/-
 */

package com.spotify.flo;

import io.grpc.Context;

public final class Tracing {

  private static final Context.Key<TaskId> TASK_ID = Context.key("task-id");

  private Tracing() {
    throw new UnsupportedOperationException();
  }

  public static TaskId currentTaskId() {
    return TASK_ID.get();
  }

  public static <T> Fn<T> trace(Fn<T> fn) {
    final TaskId taskId = currentTaskId();
    if (taskId == null) {
      return fn;
    }
    return trace(taskId, fn);
  }

  public static <T> Fn<T> trace(TaskId taskId, Fn<T> fn) {
    return () -> {
      try {
        return Context.current()
            .withValue(Tracing.TASK_ID, taskId)
            .call(fn::get);
      } catch (RuntimeException e) {
        throw e;
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    };
  }
}
