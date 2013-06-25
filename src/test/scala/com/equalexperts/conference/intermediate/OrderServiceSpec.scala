package com.equalexperts.conference.intermediate

import org.specs2.Specification
import org.specs2.matcher.ThrownExpectations
import org.specs2.mock.Mockito
import org.specs2.specification.Scope

class OrderServiceSpec extends Specification with ThrownExpectations with Mockito { def is = 

  "Specification for the Order Service"                               ^
                                                                      endp^
  "Setting an address on an order should"                             ^
    "update and persist the order"                                    ! setAddressOnOrder^
    "error if the order is not in a modifiable state"                 ! setAddressOnUnmodifiableOrder^
                                                                      end

  def setAddressOnOrder = new context {
    val expectedOrder = Order("1234", InProgress, Some(address))

    service.addAddressToOrder("1234", address) must_== Right(expectedOrder)
    orderServiceModule.validatedAddress must beSome(address)
    orderServiceModule.storedOrder must beSome(expectedOrder)
  }          

  def setAddressOnUnmodifiableOrder = new context {
    service.addAddressToOrder("9999", address) must_== Left("Order cannot have an address set if it is not in the InProgress state")
    orderServiceModule.validatedAddress must beSome(address)
    orderServiceModule.storedOrder must beNone
  }                                                                                                                                   

  class context extends Scope {

    trait StubOrderRepository extends RepositoryModule {
      var storedOrder: Option[Order] = None

      def obtainOrder(id: OrderId): Either[ErrorState, Order] = 
        if ( id == "1234" ) Right(Order(id, InProgress))
        else Right(Order(id, Submitted))

      def storeOrder(order: Order): Either[ErrorState, Order] = {
        storedOrder = Some(order)
        Right(order)
      }
    }

    trait StubAddressValidator extends ValidationModule {
      var validatedAddress: Option[Address] = None

      def validate(address: Address): Either[ErrorState, Address] = {
        validatedAddress = Some(address)
        Right(address)
      }
    }

    val orderServiceModule = new OrderServiceModule with StubOrderRepository with StubAddressValidator
    val service = orderServiceModule.OrderService

    val address = Address("1", "Foo Bar Lane", None, "Some Town", "ST1 1AA", "Testshire")
  }
}
