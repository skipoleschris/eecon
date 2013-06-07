package com.equalexperts.conference.imperative

import org.specs2.Specification
import org.specs2.matcher.ThrownExpectations
import org.specs2.mock.Mockito
import org.specs2.specification.Scope

class OrderServiceSpec extends Specification with ThrownExpectations with Mockito { def is = 

  "Specification for the Order Service"                               ^
                                                                      endp^
  "Setting an address on an order should"                             ^
    "update an persist the order"                                     ! setAddressOnOrder^
    "error if the address is invalid"                                 ! setInvalidAddressOnOrder^
    "error if the order cannot be found"                              ! setAddressOnUnknownOrder^
    "error if the order is not in a modifiable state"                 ! setAddressOnUnmodifiableOrder^
                                                                      end

  def setAddressOnOrder = new context {
    repository.obtainOrder("1234") returns initialOrder
    validator.validate(address) returns address

    val expectedOrder = new Order("1234")
    expectedOrder.setAddress(address)
    expectedOrder.setStatus(Status.requirementsSatisfied)

    val order = service.addAddressToOrder("1234", address)

    order must_== expectedOrder
    there was one(repository).obtainOrder("1234")
    there was one(repository).storeOrder(expectedOrder)
    there was one(validator).validate(address)
  }          

  def setInvalidAddressOnOrder = new context {
    validator.validate(address) returns null

    service.addAddressToOrder("1234", address) must throwAn[IllegalArgumentException]
    there was no(repository).obtainOrder("1234")
    there was no(repository).storeOrder(any[Order])
    there was one(validator).validate(address)
  }                                                                                                                                   

  def setAddressOnUnknownOrder = new context {
    repository.obtainOrder("1234") returns null
    validator.validate(address) returns address

    service.addAddressToOrder("1234", address) must throwAn[IllegalArgumentException]
    there was one(repository).obtainOrder("1234")
    there was no(repository).storeOrder(any[Order])
    there was one(validator).validate(address)
  }                                                                                                                                   

  def setAddressOnUnmodifiableOrder = new context {
    initialOrder.setStatus(Status.submitted)
    repository.obtainOrder("1234") returns initialOrder
    validator.validate(address) returns address

    service.addAddressToOrder("1234", address) must throwAn[IllegalStateException]
    there was one(repository).obtainOrder("1234")
    there was no(repository).storeOrder(any[Order])
    there was one(validator).validate(address)
  }                                                                                                                                   

  class context extends Scope {
    val repository = mock[OrderRepository]
    val validator = mock[AddressValidator]
    val service = new OrderService(repository, validator)

    val initialOrder = new Order("1234")
    val address = new Address("1", "Foo Bar Lane", null, "Some Town", "ST1 1AA", "Testshire")
  }
}
