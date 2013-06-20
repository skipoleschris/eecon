package com.equalexperts.conference.functional

trait OrderServiceModule extends RepositoryModule with ValidationModule {

  object OrderService {

    def addAddressToOrder(orderId: OrderId, address: Address[_]): 
          Either[ErrorState, Order[Status#InProgress, Requirement#Met, Persisted#Yes]] = 
      for {
        validatedAddress <- validate(address).right                                                           // Address[Validated#Yes]
        currentOrder <- obtainOrder[Status#InProgress, Requirement](orderId).right                            // Order[Status#InProgress, Requirement, Persisted#Yes]
        storedOrder <- storeOrder(Order.addAddressToOrder(currentOrder, validatedAddress)).right              // Order[Status#InProgress, Requirement#Met, Persisted#Yes]
      } yield storedOrder
  }
}
