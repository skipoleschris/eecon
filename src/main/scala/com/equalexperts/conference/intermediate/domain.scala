package com.equalexperts.conference.intermediate

case class Address(buildingNameOrNumber: String,
                   street1: String,
                   street2: Option[String],
                   town: String,
                   postcode: Postcode,
                   county: String)

sealed trait Status
case object InProgress extends Status
case object Submitted extends Status

case class Order(id: OrderId, status: Status, address: Option[Address] = None)
  