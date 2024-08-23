package com.app.integrate.fhir.service;

import ca.uhn.fhir.rest.client.api.IGenericClient;
import com.app.integrate.fhir.mapper.AppointmentResponseMapper;
import com.app.integrate.fhir.model.AppointmentResponseDto;
import org.hl7.fhir.r4.model.Appointment;
import org.hl7.fhir.r4.model.Bundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService {
    @Autowired
    private IGenericClient fhirClient;

    public AppointmentResponseDto getAppointmentByPatientId(String patientId) {
        Bundle appointmentBundle = fhirClient
                .search()
                .forResource(Appointment.class)
                .where(Appointment.PATIENT.hasId(patientId))
                .where(Appointment.SERVICE_CATEGORY.exactly().code("appointment"))
                .returnBundle(Bundle.class)
                .execute();

        if (appointmentBundle.getTotal() > 0 && appointmentBundle.hasEntry()) {
            String appointmentJson = fhirClient.getFhirContext().newJsonParser().encodeResourceToString(appointmentBundle.getEntryFirstRep().getResource());
            AppointmentResponseMapper responseMapper = new AppointmentResponseMapper();
            return responseMapper.mapJsonToAppointmentResponse(appointmentJson);
        } else {
            return null;
        }
    }
}
