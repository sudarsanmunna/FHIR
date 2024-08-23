package com.app.integrate.fhir.mapper;

import com.app.integrate.fhir.model.OrganizationDto;
import org.hl7.fhir.r4.model.Address;
import org.hl7.fhir.r4.model.Organization;

public class OrganizationDtoMapper {

    public OrganizationDto mapOrganizationToDto(Organization organization) {
        OrganizationDto organizationDto = new OrganizationDto();
        if (organization != null) {
            if (organization.hasId()) {
                organizationDto.setId(organization.getId());
            }
            if (organization.hasName()) {
                organizationDto.setName(organization.getName());
            }
            if (organization.hasAddress()) {
                Address address = organization.getAddressFirstRep();
                if (address.hasLine()) {
                    organizationDto.setAddressLine(address.getLine().get(0).getValue());
                }
                if (address.hasCity()) {
                    organizationDto.setCity(address.getCity());
                }
                if (address.hasState()) {
                    organizationDto.setState(address.getState());
                }
                if (address.hasPostalCode()) {
                    organizationDto.setPostalCode(address.getPostalCode());
                }
            }
        }
        return organizationDto;
    }
}
