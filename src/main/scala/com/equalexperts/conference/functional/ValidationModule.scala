package com.equalexperts.conference.functional

import scalaz._
import Scalaz._

trait ValidationModule {
  def validate(address: Address[_]): Validation[ErrorState, Address[Validated.type]]
}
