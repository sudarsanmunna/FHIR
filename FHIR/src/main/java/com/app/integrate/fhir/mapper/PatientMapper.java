package com.app.integrate.fhir.mapper;

import com.app.integrate.fhir.model.PatientDto;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.ContactPoint;
import org.hl7.fhir.r4.model.Patient;

import java.util.ArrayList;
import java.util.List;

public class PatientMapper {

    public List<PatientDto> mapToFhirPatientDtoList(Bundle bundle) {
        List<PatientDto> patientDtoList = new ArrayList<>();
        if (bundle != null && bundle.hasEntry()) {
            for (Bundle.BundleEntryComponent entry : bundle.getEntry()) {
                if (entry.hasResource() && entry.getResource() instanceof Patient) {
                    Patient patientResource = (Patient) entry.getResource();
                    PatientDto patientDto = mapPatientResourceToDto(patientResource);
                    patientDtoList.add(patientDto);
                }
            }
        }
        return patientDtoList;
    }

    public PatientDto mapPatientResourceToDto(Patient patientResource) {
        PatientDto patientDto = new PatientDto();
        patientDto.setId(patientResource.getIdElement().getIdPart());
        patientDto.setFamilyName(patientResource.getNameFirstRep().getFamily());
        patientDto.setGivenName(patientResource.getNameFirstRep().getGivenAsSingleString());
        patientDto.setBirthDate(patientResource.getBirthDate());
        patientDto.setGender(patientResource.getGender().getDisplay());
        patientDto.setManagingOrganization(patientResource.getManagingOrganization().getDisplay());

        // Map telecoms (phone numbers) from the patient resource
        List<String> telecoms = new ArrayList<>();
        for (ContactPoint telecom: patientResource.getTelecom()) {
            if (telecom.hasValue()) {
                telecoms.add(telecom.getValue());
            }
        }
        patientDto.setTelecoms(telecoms);
        return patientDto;
    }
}
