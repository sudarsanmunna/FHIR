package com.app.integrate.fhir.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrganizationDto implements Serializable {
    private String id;
    private String name;
    private String addressLine;
    private String city;
    private String state;
    private String postalCode;
}
