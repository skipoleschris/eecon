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
    val expectedOrder = Order[Status#InProgress, Requirement#Met, Persisted#Yes]("1234", Some(validatedAddress))
    service.addAddressToOrder("1234", address) must_== expectedOrder.success
  }

  class context extends Scope {

    trait StubOrderRepository extends RepositoryModule {
      def obtainOrder[S <: Status : Manifest, AddrReq <: Requirement]
            (id: OrderId): Validation[ErrorState, Order[S, AddrReq, Persisted#Yes]] = {
        if ( implicitly[Manifest[S]].runtimeClass != classOf[Status#InProgress]) "Order is not InProgress".fail
        else Order[S, AddrReq, Persisted#Yes](id).success
      }

      def storeOrder[S <: Status : Manifest, AddrReq <: Requirement]
            (order: Order[S, AddrReq, _]): Validation[ErrorState, Order[S, AddrReq, Persisted#Yes]] = 
        order.copy[S, AddrReq, Persisted#Yes]().success
    }

    trait StubAddressValidator extends ValidationModule {
      def validate(address: Address[_]): Validation[ErrorState, Address[Validated#Yes]] = 
        address.copy[Validated#Yes]().success
    }

    val orderServiceModule = new OrderServiceModule with StubOrderRepository with StubAddressValidator
    val service = orderServiceModule.OrderService

    val address = Address[Validated#No]("1", "Foo Bar Lane", None, "Some Town", "ST1 1AA", "Testshire")
    val validatedAddress = address.copy[Validated#Yes]()
  }


}

