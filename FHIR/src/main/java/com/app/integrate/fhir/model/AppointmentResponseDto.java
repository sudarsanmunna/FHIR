package com.app.integrate.fhir.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AppointmentResponseDto implements Serializable {
    private String id;
    private String status;
    private String start;
    private String end;

    @JsonProperty("resourceType")
    private String resourceType;

    private Identifier[] identifier;
    private ServiceCategory[] serviceCategory;
    private ServiceType[] serviceType;
    private AppointmentType appointmentType;
    private ReasonCode[] reasonCode;
    private ReasonReference[] reasonReference;
    private String description;
    private int minutesDuration;
    private String created;
    private String comment;
    private String patientInstruction;
    private Participant[] participant;

    @Data public static class Identifier {
        private String system;
        private String value;

        // Add getters and setters
    }

    @Data public static class ServiceCategory {
        private Coding[] coding;
        private String text;

        // Add getters and setters
    }

    @Data public static class ServiceType {
        private Coding[] coding;

        // Add getters and setters
    }

    @Data public static class AppointmentType {
        private Coding[] coding;

        // Add getters and setters
    }

    @Data public static class ReasonCode {
        private Coding[] coding;
        private String text;

        // Add getters and setters
    }

    @Data public static class ReasonReference {
        private String reference;

        // Add getters and setters
    }

    @Data public static class Coding {
        private String system;
        private String code;
        private String display;

        // Add getters and setters
    }

    @Data public static class Participant {
        private Actor actor;
        private String required;
        private String status;
        private Period period;

        // Add getters and setters
    }

    @Data public static class Actor {
        private String reference;
        private String display;

        // Add getters and setters
    }

    @Data public static class Period {
        private String start;
        private String end;

        // Add getters and setters
    }
}
