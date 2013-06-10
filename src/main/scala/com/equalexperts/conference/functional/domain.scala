package com.equalexperts.conference.functional

trait Validated {
  type No <: Validated
  type Yes <: Validated
}

case class Address[V <: Validated](buildingNameOrNumber: String,
                                   street1: String,
                                   street2: Option[String],
                                   town: String,
                                   postcode: Postcode,
                                   county: String)

trait Status {
  trait InProgress extends Status
  trait Submitted extends Status
}

trait Requirement {
  type Unmet <: Requirement
  type Met <: Requirement
}

trait Persisted {
  type No <: Persisted
  type Yes <: Persisted
}

case class Order[S <: Status, AddrReq <: Requirement, P <: Persisted]
      (id: OrderId, address: Option[Address[Validated#Yes]] = None)

object Order {
  def addAddressToOrder(order: Order[Status#InProgress, _, _], 
                        address: Address[Validated#Yes]): 
        Order[Status#InProgress, Requirement#Met, Persisted#No] =
    Order(order.id, Some(address))
}
  