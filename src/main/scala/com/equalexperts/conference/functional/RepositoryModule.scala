package com.equalexperts.conference.functional

import scalaz._
import Scalaz._

trait RepositoryModule {

  def obtainOrder[S <: Status : Manifest, AddrReq <: Requirement]
        (id: OrderId): Validation[ErrorState, Order[S, AddrReq]]
        
  def storeOrder[S <: Status : Manifest, AddrReq <: Requirement]
        (order: Order[S, AddrReq]): Validation[ErrorState, Order[S, AddrReq]]
}
