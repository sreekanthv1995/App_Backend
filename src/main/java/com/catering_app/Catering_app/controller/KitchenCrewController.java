package com.catering_app.Catering_app.controller;

import com.catering_app.Catering_app.dto.ResponseDto;
import com.catering_app.Catering_app.dto.team.KitchenCrewEmpDto;
import com.catering_app.Catering_app.dto.team.teamDto;
import com.catering_app.Catering_app.model.teams.KitchenCrew;
import com.catering_app.Catering_app.model.teams.KitchenCrewEmployees;
import com.catering_app.Catering_app.service.teamServices.kitchenCrew.KitchenCrewService;
import com.catering_app.Catering_app.service.teamServices.kitchenCrewEmployees.KitchenCrewEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class KitchenCrewController {

    @Autowired
    private KitchenCrewService kitchenCrewService;
    @Autowired
    private KitchenCrewEmployeeService kitchenCrewEmployeeService;

    @PostMapping("/add/kitchenCrew_team")
    public ResponseEntity<KitchenCrew> addTeam(@RequestBody teamDto teamDto) {
        return ResponseEntity.ok(kitchenCrewService.addKitchenCrewTeam(teamDto));
    }

    @PostMapping("/add/kitchenCrew_impl")
    public ResponseEntity<?> addKitchenCrewEmployees(@RequestBody KitchenCrewEmpDto kitchenCrewEmpDto) {
        kitchenCrewEmployeeService.addKitchenCrewEmployees(kitchenCrewEmpDto);
        return ResponseEntity.ok(new ResponseDto(true, "success"));

    }

    @GetMapping("/kitchenCrew_teams")
    public ResponseEntity<List<KitchenCrew>> getAllKitchenCrewTeams() {
        return ResponseEntity.ok(kitchenCrewService.getAllSKitchenCrewTeam());
    }

    @GetMapping("/kitchenCrew_emp")
    public ResponseEntity<List<KitchenCrewEmployees>> getAllKitchenCrewEmployees() {
        return ResponseEntity.ok(kitchenCrewEmployeeService.getAllKitchenCrewEmployees());
    }


}