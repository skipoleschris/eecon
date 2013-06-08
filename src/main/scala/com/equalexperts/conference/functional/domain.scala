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

case class Order[S <: Status, AddrReq <: Requirement]
      (id: OrderId, address: Option[Address[Validated#Yes]] = None)

object Order {
  def addAddressToOrder(order: Order[Status#InProgress, _], 
                        address: Address[Validated#Yes]): 
        Order[Status#InProgress, Requirement#Met] =
    Order(order.id, Some(address))
}
  