package com.equalexperts.conference.imperative;

public class Address {
    private final String buildingNameOrNumber;
    private final String street1;
    private final String street2;
    private final String town;
    private final String postcode;
    private final String county;

    public Address(final String buildingNameOrNumber,
                   final String street1,
                   final String street2,
                   final String town,
                   final String postcode,
                   final String county) {
        this.buildingNameOrNumber = buildingNameOrNumber;
        this.street1 = street1;
        this.street2 = street2;
        this.town = town;
        this.postcode = postcode;
        this.county = county;
    }

    public String getBuildingNameOrNumber() {
        return buildingNameOrNumber;
    }

    public String getStreet1() {
        return street1;
    }

    public String getStreet2() {
        return street2;
    }

    public String getTown() {
        return town;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getCounty() {
        return county;
    }

    @Override
    public boolean equals(final Object obj) {
        if ( !(obj instanceof Address) ) return false;
        if ( obj == this ) return true;

        Address that = (Address)obj;
        return this.buildingNameOrNumber.equals(that.buildingNameOrNumber) &&
               this.street1.equals(that.street1) &&
               this.street2.equals(that.street2) &&
               this.town.equals(that.town) &&
               this.postcode.equals(that.postcode) &&
               this.county.equals(that.county);
    }

    @Override
    public int hashCode() {
        return (buildingNameOrNumber + postcode).hashCode();
    }
}
