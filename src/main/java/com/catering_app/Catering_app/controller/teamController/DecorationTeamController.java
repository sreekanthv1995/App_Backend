package com.catering_app.Catering_app.controller.teamController;

import com.catering_app.Catering_app.dto.ResponseDto;
import com.catering_app.Catering_app.dto.team.DecorEmpDto;
import com.catering_app.Catering_app.dto.team.teamDto;
import com.catering_app.Catering_app.model.teams.DecorationEmployees;
import com.catering_app.Catering_app.model.teams.DecorationTeam;
import com.catering_app.Catering_app.service.teamServices.decorationEmployees.DecorationEmployeeService;
import com.catering_app.Catering_app.service.teamServices.decorationTeam.DecorationTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DecorationTeamController {


    @Autowired
    private DecorationTeamService decorationTeamService;
    @Autowired
    private DecorationEmployeeService decorationEmployeeService;


    @PostMapping("/add/decor_team")
    public ResponseEntity<DecorationTeam> addTeam(@RequestBody teamDto teamDto){
        return ResponseEntity.ok(decorationTeamService.addDecorationTeam(teamDto));
    }

    @GetMapping("/decor_teams")
    public ResponseEntity<List<DecorationTeam>> getAllServingTeams(){
        return ResponseEntity.ok(decorationTeamService.getAllDecorationTeam());
    }
    @GetMapping("/decor_emp")
    public ResponseEntity<List<DecorationEmployees>> getAllServingEmployees(){
        return ResponseEntity.ok(decorationEmployeeService.getAllDecorationEmployees());
    }


    @PostMapping("/add/decor_emp")
    public ResponseEntity<?>addServingEmployees(@RequestBody DecorEmpDto decorEmpDto){
        decorationEmployeeService.addDecorationEmployees(decorEmpDto);
        return ResponseEntity.ok(new ResponseDto(true,"success"));

    }
}
