package com.api.power.controller;

import com.api.power.model.PowerUsageDto;
import com.api.power.service.PowerService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PowerController {

    private final PowerService powerService;

    @GetMapping("/power/usage/{customer}")
    public List<PowerUsageDto> getPowerUsageByCustomer(
            @PathVariable("customer") final String customer,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") final LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") final LocalDate endDate,
            @RequestParam(value = "groupBy", required = false) final String groupBy
    ) {
        return powerService.getPowerUsageForCustomer(customer, startDate, endDate, groupBy);
    }

    @GetMapping("/config/refresh-token")
    public String getRefreshToken() {
        return powerService.getRefreshToken();
    }

    @PutMapping("/config/refresh-token")
    public void updateRefreshToken(
            @PathVariable("refreshToken") final String refreshToken
    ) {
        powerService.updateRefreshToken(refreshToken);
    }

}
