package com.equalexperts.conference.functional

trait RepositoryModule {

  def obtainOrder[S <: Status : Manifest, AddrReq <: Requirement]
        (id: OrderId): Either[ErrorState, Order[S, AddrReq, Persisted#Yes]]

  def storeOrder[S <: Status : Manifest, AddrReq <: Requirement]
        (order: Order[S, AddrReq, _]): Either[ErrorState, Order[S, AddrReq, Persisted#Yes]]
}
