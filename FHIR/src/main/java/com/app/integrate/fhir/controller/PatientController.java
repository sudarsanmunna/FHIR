package com.app.integrate.fhir.controller;

import com.app.integrate.fhir.model.PatientDto;
import com.app.integrate.fhir.service.FhirPatientService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    private FhirPatientService patientService;

    @GetMapping(value = "/findPatients", produces = "application/json")
    public List<PatientDto> getPatientsByCriteria(
            @RequestParam String family,
            @RequestParam String given,
            @RequestParam String birthdate
    ) {
        return patientService.getPatientsByCriteria(family, given, birthdate);
    }

    @GetMapping(value = "/patient/{patientId}", produces = "application/json")
    public PatientDto getPatientById(@PathVariable String patientId) {
        return patientService.getPatientById(patientId);
    }
}
