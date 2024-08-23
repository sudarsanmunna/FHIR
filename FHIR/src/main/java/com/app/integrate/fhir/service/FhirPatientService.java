package com.app.integrate.fhir.service;

import ca.uhn.fhir.rest.client.api.IGenericClient;
import com.app.integrate.fhir.mapper.PatientMapper;
import com.app.integrate.fhir.model.PatientDto;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FhirPatientService {

    @Autowired
    private IGenericClient fhirClient;

    public List<PatientDto> getPatientsByCriteria(String family, String given, String date) {
        // Construct the FHIR search parameters for querying patients based on department and date
        Bundle bundle = fhirClient
                .search()
                .forResource(Patient.class)
                .where(Patient.FAMILY.matches().value(family))
                .where(Patient.GIVEN.matches().value(given))
                .where(Patient.BIRTHDATE.exactly().day(date))
                .returnBundle(Bundle.class)
                .execute();

        PatientMapper patientMapper = new PatientMapper();
        List<PatientDto> patientDtoList = patientMapper.mapToFhirPatientDtoList(bundle);

        return patientDtoList;
    }

    public PatientDto getPatientById(String patientId) {
        Patient patient = fhirClient.read().resource(Patient.class).withId(patientId).execute();
        PatientMapper patientMapper = new PatientMapper();
        PatientDto patientDto = patientMapper.mapPatientResourceToDto(patient);
        return patientDto;
    }
}
