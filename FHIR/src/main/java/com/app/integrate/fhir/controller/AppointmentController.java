package com.app.integrate.fhir.controller;

import com.app.integrate.fhir.model.AppointmentResponseDto;
import com.app.integrate.fhir.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app")
public class AppointmentController {

    @Autowired
    public AppointmentService appointmentService;

    @GetMapping(value = "/appointment/{id}", produces = "application/json")
    public AppointmentResponseDto getAppointmentByPatientId(@PathVariable("id") String patientId) {
        return appointmentService.getAppointmentByPatientId(patientId);
    }
}
