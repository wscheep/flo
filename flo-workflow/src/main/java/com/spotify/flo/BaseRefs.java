/*-
 * -\-\-
 * Flo Workflow Definition
 * --
 * Copyright (C) 2016 - 2017 Spotify AB
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

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * A convenience class for holding some reference. This is only so that we don't have to repeat
 * these declaration in every class above.
 */
abstract class BaseRefs<Z> implements Serializable {

  private static final long serialVersionUID = 1L;

  final Fn<List<Task<?>>> inputs;
  final List<TaskContext<?, ? super Z>> taskContexts;
  final List<ProcessFnArg> args;
  final TaskId taskId;
  protected final Class<Z> type;

  BaseRefs(TaskId taskId, Class<Z> type) {
    this(Collections::emptyList, Collections.emptyList(), Collections.emptyList(), taskId, type);
  }

  BaseRefs(Fn<List<Task<?>>> inputs, List<TaskContext<?, ? super Z>> taskContexts, List<ProcessFnArg> args,
      TaskId taskId, Class<Z> type) {
    this.inputs = inputs;
    this.taskContexts = taskContexts;
    this.args = args;
    this.taskId = taskId;
    this.type = type;
  }
}
