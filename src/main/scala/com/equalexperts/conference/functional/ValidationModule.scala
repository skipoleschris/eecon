package com.equalexperts.conference.functional

trait ValidationModule {
  def validate(address: Address[_]): Either[ErrorState, Address[Validated#Yes]]
}
