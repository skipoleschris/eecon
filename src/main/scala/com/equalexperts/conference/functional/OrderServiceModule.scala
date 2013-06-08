package com.equalexperts.conference.functional

import scalaz._
import Scalaz._

trait OrderServiceModule extends RepositoryModule with ValidationModule {

  object OrderService {
    def addAddressToOrder(orderId: OrderId, address: Address[_]): Validation[ErrorState, Order[Status#InProgress, Requirement#Met]] = for {
      validatedAddress <- validate(address)
      currentOrder <- obtainOrder[Status#InProgress, Requirement](orderId)
      updatedOrder <- Order.addAddressToOrder(currentOrder, validatedAddress).success
      storedOrder <- storeOrder(updatedOrder)
    } yield storedOrder
  }
}
