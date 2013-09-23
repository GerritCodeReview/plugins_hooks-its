//Copyright (C) 2013 The Android Open Source Project
//
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at
//
//http://www.apache.org/licenses/LICENSE-2.0
//
//Unless required by applicable law or agreed to in writing, software
//distributed under the License is distributed on an "AS IS" BASIS,
//WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//See the License for the specific language governing permissions and
//limitations under the License.

package com.googlesource.gerrit.plugins.hooks.workflow;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.googlesource.gerrit.plugins.hooks.its.ItsFacade;

/**
 * Executes an {@link ActionRequest}
 */
public class ActionExecutor {
  private static final Logger log = LoggerFactory.getLogger(
      ActionExecutor.class);

  private final ItsFacade its;

  @Inject
  public ActionExecutor(ItsFacade its) {
    this.its = its;
  }

  public void execute(String issue, ActionRequest actionRequest) {
    try {
      its.performAction(issue, actionRequest.getUnparsed());
    } catch (IOException e) {
      log.error("Error while executing action " + actionRequest, e);
    }
  }

  public void execute(String issue, Iterable<ActionRequest> actions) {
    for (ActionRequest actionRequest : actions) {
        execute(issue, actionRequest);
    }
  }
}
