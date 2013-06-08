package com.equalexperts.conference.functional

sealed trait ValidationState
case object NotValidated extends ValidationState
case object Validated extends ValidationState

case class Address[V <: ValidationState](buildingNameOrNumber: String,
                                         street1: String,
                                         street2: Option[String],
                                         town: String,
                                         postcode: Postcode,
                                         county: String)

sealed trait Status
case object InProgress extends Status
case object Submitted extends Status

sealed trait Requirement
case object Unmet extends Requirement
case object Met extends Requirement

case class Order[S <: Status, AddrReq <: Requirement](id: OrderId, address: Option[Address[Validated.type]] = None)

object Order {
  def addAddressToOrder(order: Order[InProgress.type, _], address: Address[Validated.type]): Order[InProgress.type, Met.type] =
    Order(order.id, Some(address))
}
