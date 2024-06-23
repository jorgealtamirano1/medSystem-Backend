package com.losluminosos.medsystem.profiles.interfaces.rest;

import com.losluminosos.medsystem.profiles.domain.services.DoctorCommandService;
import com.losluminosos.medsystem.profiles.interfaces.rest.resources.CreateDoctorResource;
import com.losluminosos.medsystem.profiles.interfaces.rest.resources.DoctorResource;
import com.losluminosos.medsystem.profiles.interfaces.rest.transform.CreateDoctorCommandFromResourceAssembler;
import com.losluminosos.medsystem.profiles.interfaces.rest.transform.DoctorResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/doctors", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Doctors", description = "Doctor Management Endpoints")
public class DoctorsController {
    private final DoctorCommandService doctorCommandService;

    public DoctorsController(DoctorCommandService doctorCommandService) {
        this.doctorCommandService = doctorCommandService;
    }

    @PostMapping
    public ResponseEntity<DoctorResource> createDoctor(@RequestBody CreateDoctorResource resource) {
        var createDoctorCommand = CreateDoctorCommandFromResourceAssembler.toCommandFromResource(resource);
        var doctor = doctorCommandService.handle(createDoctorCommand);
        if (doctor.isEmpty()) return ResponseEntity.badRequest().build();
        var doctorResource = DoctorResourceFromEntityAssembler.toResourceFromEntity(doctor.get());
        return new ResponseEntity<>(doctorResource, HttpStatus.CREATED);

    }
}