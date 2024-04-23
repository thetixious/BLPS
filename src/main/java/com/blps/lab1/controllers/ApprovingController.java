package com.blps.lab1.controllers;

import com.blps.lab1.service.ApprovingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/approval")
public class ApprovingController {


    private final ApprovingService approvingService;

    public ApprovingController(ApprovingService approvingService) {

        this.approvingService = approvingService;
    }

    @GetMapping(value = "/{id}/info")
    public ResponseEntity<?> info(@PathVariable(value = "id") Long id) {

        return approvingService.getInfo(id);

    }

    @PostMapping(value = "/{id}/result")
    public ResponseEntity<?> result(@PathVariable(value = "id") Long id, @RequestBody List<Long> cardsId) {

        return approvingService.getResult(id, cardsId);

    }

}
