package com.blps.lab1.controllers;

import com.blps.lab1.dto.utils.DataRequest;
import com.blps.lab1.dto.utils.LongWrapper;
import com.blps.lab1.service.ApprovingService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
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
    @Operation(summary = "Вывод информации о пользователе и его заявке на кредитную карту")
    @GetMapping(value = "/info")
    public ResponseEntity<?> info(@Valid @RequestBody LongWrapper longWrapper) {

        return approvingService.getInfo(longWrapper.getId());

    }

    @Operation(summary="Выбор одобренных предложений")
    @PostMapping(value = "/result")
    public ResponseEntity<?> result(@RequestBody DataRequest approvalRequest) {
        LongWrapper longWrapper = approvalRequest.getLongWrapper();
        List<Long> cardsId = approvalRequest.getCardsId();
        return approvingService.getResult(longWrapper.getId(), cardsId);

    }

}
