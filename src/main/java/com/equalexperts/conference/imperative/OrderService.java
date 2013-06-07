package com.equalexperts.conference.imperative;

public class OrderService {
    private final OrderRepository repository;    
    private final AddressValidator validator;

    public OrderService(final OrderRepository repository, final AddressValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    public Order addAddressToOrder(final String orderId, final Address address) {
        final Address validatedAddress = validator.validate(address);
        if ( validatedAddress == null ) {
          throw new IllegalArgumentException("Address is not valid");
        }

        final Order order = repository.obtainOrder(orderId);
        if ( order == null ) {
          throw new IllegalArgumentException("Order id " + orderId + " was  not found");
        }

        if ( !order.isModifiable() ) {
          throw new IllegalStateException("Order cannot have an address set if it is not in the InProgress state");
        }

        order.setAddress(validatedAddress);

        order.changeStatusIfRequirementsMet();

        repository.storeOrder(order);

        return order;
    }  
}
