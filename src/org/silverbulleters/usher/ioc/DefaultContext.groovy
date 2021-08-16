/*
 * Vanessa-Usher
 * Copyright (C) 2019-2021 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
package org.silverbulleters.usher.ioc

class DefaultContext implements IContext, Serializable {
  private steps

  DefaultContext(steps) {
    this.steps = steps
  }

  @Override
  IStepExecutor getStepExecutor() {
    return new StepExecutor(this.steps)
  }
}
