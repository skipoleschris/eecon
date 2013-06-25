package com.equalexperts.conference.intermediate

trait OrderServiceModule extends RepositoryModule with ValidationModule {

  object OrderService {

    def addAddressToOrder(orderId: OrderId, address: Address): Either[ErrorState, Order] = 
      for {
        validatedAddress <- validate(address).right                                               // Address
        currentOrder <- obtainOrder(orderId).right                                                // Order
        updatedOrder <- addAddressToOrder(currentOrder, validatedAddress).right                   // Order
        storedOrder <- storeOrder(updatedOrder).right                                             // Order
      } yield storedOrder

    private def addAddressToOrder(order: Order, address: Address): Either[ErrorState, Order] =
      if ( order.status == InProgress ) Right(order.copy(address = Some(address)))
      else Left("Order cannot have an address set if it is not in the InProgress state")
  }
}
