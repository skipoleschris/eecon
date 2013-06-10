package com.equalexperts.conference.functional

import scalaz._
import Scalaz._

trait OrderServiceModule extends RepositoryModule with ValidationModule {

  object OrderService {

    def addAddressToOrder(orderId: OrderId, address: Address[_]): 
          Validation[ErrorState, Order[Status#InProgress, Requirement#Met, Persisted#Yes]] = 
      for {
        validatedAddress <- validate(address)                                                                 // Address[Validated#Yes]
        currentOrder <- obtainOrder[Status#InProgress, Requirement](orderId)                                  // Order[Status#InProgress, Requirement, Persisted#Yes]
        updatedOrder <- Order.addAddressToOrder(currentOrder, validatedAddress).success                       // Order[Status#InProgress, Requirement#Met, Persisted#No]
        storedOrder <- storeOrder(updatedOrder)                                                               // Order[Status#InProgress, Requirement#Met, Persisted#Yes]
      } yield storedOrder
  }
}
