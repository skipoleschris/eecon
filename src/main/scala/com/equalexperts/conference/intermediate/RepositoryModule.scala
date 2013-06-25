package com.equalexperts.conference.intermediate

trait RepositoryModule {

  def obtainOrder(id: OrderId): Either[ErrorState, Order]

  def storeOrder(order: Order): Either[ErrorState, Order]
}
