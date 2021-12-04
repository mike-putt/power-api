package com.api.power.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PowerUsageDto {

    private LocalDateTime dateTime;
    private Double powerUsage;

}
