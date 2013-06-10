package com.equalexperts.conference.functional

import scalaz._
import Scalaz._

trait RepositoryModule {

  def obtainOrder[S <: Status : Manifest, AddrReq <: Requirement]
        (id: OrderId): Validation[ErrorState, Order[S, AddrReq, Persisted#Yes]]

  def storeOrder[S <: Status : Manifest, AddrReq <: Requirement]
        (order: Order[S, AddrReq, _]): Validation[ErrorState, Order[S, AddrReq, Persisted#Yes]]
}
