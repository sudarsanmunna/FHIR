package com.app.integrate.fhir.service;

import ca.uhn.fhir.rest.client.api.IGenericClient;
import com.app.integrate.fhir.mapper.OrganizationDtoMapper;
import com.app.integrate.fhir.model.OrganizationDto;
import org.hl7.fhir.r4.model.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FhirOrganizationService {
    @Autowired
    private IGenericClient fhirClient;

    public OrganizationDto getOrganizationById(String organizationId) {
        // Retrieve the FHIR Organization resource using the provided organizationId
        Organization org = fhirClient
                .read()
                .resource(Organization.class)
                .withId(organizationId)
                .execute();

        OrganizationDtoMapper organizationDtoMapper = new OrganizationDtoMapper();
        OrganizationDto organizationDto = organizationDtoMapper.mapOrganizationToDto(org);
        return organizationDto;
    }
}
