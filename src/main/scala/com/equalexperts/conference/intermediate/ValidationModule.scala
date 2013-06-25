package com.equalexperts.conference.intermediate

trait ValidationModule {
  def validate(address: Address): Either[ErrorState, Address]
}
