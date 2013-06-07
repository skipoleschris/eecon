package com.equalexperts.conference.imperative;

public class Order {
    private final String id;
    private Status status;
    private Address address;

    public Order(final String id) {
        this.id = id;
        this.status = Status.inProgress;
    }  

    public String getId() {
        return id;
    }

    public void setStatus(final Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public void setAddress(final Address address) {
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }

    public boolean isModifiable() {
        return status.equals(Status.inProgress) || status.equals(Status.requirementsSatisfied);
    }

    public void changeStatusIfRequirementsMet() {
      if ( address != null ) setStatus(Status.requirementsSatisfied);
      else setStatus(Status.inProgress);
    }

    @Override
    public boolean equals(final Object obj) {
      if ( !(obj instanceof Order) ) return false;
      if ( obj == this ) return true;

      Order that = (Order)obj;
      return this.id.equals(that.id);
    }

    @Override
    public int hashCode() {
      return id.hashCode();
    }
}