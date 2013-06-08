package com.equalexperts.conference.functional

import scalaz._
import Scalaz._

trait RepositoryModule {
  def obtainOrder[S <: Status : Manifest, AddrReq <: Requirement : Manifest](id: OrderId): Validation[ErrorState, Order[S, AddrReq]]
  def storeOrder[S <: Status : Manifest, AddrReq <: Requirement : Manifest](order: Order[S, AddrReq]): Validation[ErrorState, Order[S, AddrReq]]
}
