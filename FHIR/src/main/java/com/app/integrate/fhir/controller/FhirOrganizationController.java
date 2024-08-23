package com.app.integrate.fhir.controller;

import com.app.integrate.fhir.model.OrganizationDto;
import com.app.integrate.fhir.service.FhirOrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/org")
public class FhirOrganizationController {

    @Autowired
    private FhirOrganizationService fhirOrganizationService;

    @GetMapping(value = "/getById/{id}", produces = "application/json")
    public OrganizationDto getOrganizationById(@PathVariable("id") String organizationId) {
        return fhirOrganizationService.getOrganizationById(organizationId);
    }

}
