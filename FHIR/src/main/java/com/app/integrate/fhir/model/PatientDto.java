package com.app.integrate.fhir.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
@Data
public class PatientDto implements Serializable {
    private String id;
    private String familyName;
    private String givenName;
    private Date birthDate;
    private String gender;
    private String managingOrganization;
    private List<String> telecoms;


}

