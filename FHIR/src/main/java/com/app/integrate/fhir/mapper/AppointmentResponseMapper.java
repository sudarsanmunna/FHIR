package com.app.integrate.fhir.mapper;

import com.app.integrate.fhir.model.AppointmentResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AppointmentResponseMapper {

    public AppointmentResponseDto mapJsonToAppointmentResponse(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json, AppointmentResponseDto.class);
        } catch (Exception e) {
            // Handle the exception
            return null;
        }
    }
}
