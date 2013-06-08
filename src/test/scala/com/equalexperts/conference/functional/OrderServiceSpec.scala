package com.equalexperts.conference.functional

import org.specs2.Specification
import org.specs2.matcher.ThrownExpectations
import org.specs2.specification.Scope

import scalaz._
import Scalaz._

class OrderServiceSpec extends Specification with ThrownExpectations { def is = 

  "Specification for the Order Service"                               ^
                                                                      endp^
  "Setting an address on an order should"                             ^
    "update and persist the order"                                    ! setAddressOnOrder^
                                                                      end

  def setAddressOnOrder = new context {
    val expectedOrder = Order[InProgress.type, Met.type]("1234", Some(validatedAddress))
    service.addAddressToOrder("1234", address) must_== expectedOrder.success
  }

  class context extends Scope {

    trait StubOrderRepository extends RepositoryModule {
      def obtainOrder[S <: Status : Manifest, AddrReq <: Requirement : Manifest](id: OrderId): Validation[ErrorState, Order[S, AddrReq]] = {
        if ( implicitly[Manifest[S]].runtimeClass != InProgress.getClass) "Order is not InProgress".fail
        else Order[S, AddrReq](id).success
      }

      def storeOrder[S <: Status : Manifest, AddrReq <: Requirement : Manifest](order: Order[S, AddrReq]): Validation[ErrorState, Order[S, AddrReq]] = 
        order.success
    }

    trait StubAddressValidator extends ValidationModule {
      def validate(address: Address[_]): Validation[ErrorState, Address[Validated.type]] = 
        address.copy[Validated.type]().success
    }

    val orderServiceModule = new OrderServiceModule with StubOrderRepository with StubAddressValidator
    val service = orderServiceModule.OrderService

    val address = Address[NotValidated.type]("1", "Foo Bar Lane", None, "Some Town", "ST1 1AA", "Testshire")
    val validatedAddress = address.copy[Validated.type]()
  }


}

