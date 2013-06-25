package com.equalexperts.conference.functional

import org.specs2.Specification
import org.specs2.matcher.ThrownExpectations
import org.specs2.specification.Scope

class OrderServiceSpec extends Specification with ThrownExpectations { def is = 

  "Specification for the Order Service"                               ^
                                                                      endp^
  "Setting an address on an order should"                             ^
    "update and persist the order"                                    ! setAddressOnOrder^
                                                                      end

  def setAddressOnOrder = new context {
    val expectedOrder = Order[Status#InProgress, Requirement#Met, Persisted#Yes]("1234", Some(validatedAddress))
    service.addAddressToOrder("1234", address) must_== Right(expectedOrder)
  }

  class context extends Scope {

    trait StubOrderRepository extends RepositoryModule {
      def obtainOrder[S <: Status : Manifest, AddrReq <: Requirement]
            (id: OrderId): Either[ErrorState, Order[S, AddrReq, Persisted#Yes]] = {
        if ( implicitly[Manifest[S]].runtimeClass != classOf[Status#InProgress]) Left("Order is not InProgress")
        else Right(Order[S, AddrReq, Persisted#Yes](id))
      }

      def storeOrder[S <: Status : Manifest, AddrReq <: Requirement]
            (order: Order[S, AddrReq, _]): Either[ErrorState, Order[S, AddrReq, Persisted#Yes]] = 
        Right(order.copy[S, AddrReq, Persisted#Yes]())
    }

    trait StubAddressValidator extends ValidationModule {
      def validate(address: Address[_]): Either[ErrorState, Address[Validated#Yes]] = 
        Right(address.copy[Validated#Yes]())
    }

    val orderServiceModule = new OrderServiceModule with StubOrderRepository with StubAddressValidator
    val service = orderServiceModule.OrderService

    val address = Address[Validated#No]("1", "Foo Bar Lane", None, "Some Town", "ST1 1AA", "Testshire")
    val validatedAddress = address.copy[Validated#Yes]()
  }
}

