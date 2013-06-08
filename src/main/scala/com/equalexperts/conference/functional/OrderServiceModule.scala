package com.equalexperts.conference.functional

import scalaz._
import Scalaz._

trait OrderServiceModule extends RepositoryModule with ValidationModule {

  object OrderService {
    def addAddressToOrder(orderId: OrderId, address: Address[_]): Validation[ErrorState, Order[InProgress.type, Met.type]] = for {
      validatedAddress <- validate(address)
      currentOrder <- obtainOrder[InProgress.type, Requirement](orderId)
      updatedOrder <- Order.addAddressToOrder(currentOrder, validatedAddress).success
      storedOrder <- storeOrder(updatedOrder)
    } yield storedOrder
  }
}
