/*
 * Copyright 2011-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.vertx.java.deploy.impl;

import org.vertx.java.core.Handler;
import org.vertx.java.core.impl.Context;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class CountingCompletionHandler {

  private final Context context;

  public CountingCompletionHandler(Context context) {
    this.context = context;
  }

  public CountingCompletionHandler(Context context, int required) {
    this.context = context;
    this.required = required;
  }

  int count;
  int required;
  Handler<Void> doneHandler;

  public synchronized void complete() {
    count++;
    checkDone();
  }

  public synchronized void incRequired() {
    required++;
  }

  public synchronized void setHandler(Handler<Void> doneHandler) {
    this.doneHandler = doneHandler;
    checkDone();
  }

  void checkDone() {
    if (doneHandler != null && count == required) {
      doneHandler.handle(null);
    }
  }
}