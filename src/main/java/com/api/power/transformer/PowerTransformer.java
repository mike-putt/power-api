package com.api.power.transformer;

import com.api.power.model.PowerUsageDto;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class PowerTransformer {

    public List<PowerUsageDto> powerUsageEntityToDtoList(final JSONObject powerUsageEntity) {
        final List<PowerUsageDto> powerUsageList = new ArrayList<>();

        if (powerUsageEntity.has("data")
                && powerUsageEntity.getJSONObject("data").has("usage")
        ) {
            final JSONObject usageList = powerUsageEntity.getJSONObject("data").getJSONObject("usage");
            final Iterator<String> usageKeys = usageList.keys();

            while (usageKeys.hasNext()) {
                final String idDate = usageKeys.next();
                final JSONObject usage = usageList.getJSONObject(idDate);
                final LocalDate date = LocalDate.parse(idDate);
                final JSONObject intervals = usage.getJSONObject("intervals");
                final Iterator<String> intervalKeys = intervals.keys();

                while (intervalKeys.hasNext()) {
                    final String id = intervalKeys.next();
                    final JSONObject interval = intervals.getJSONObject(id);
                    final PowerUsageDto powerUsage = new PowerUsageDto();
                    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:m a");
                    final LocalTime time = LocalTime.parse(interval.getString("time"), formatter);
                    final LocalDateTime dateTime = LocalDateTime.of(date, time);
                    powerUsage.setDateTime(dateTime);
                    final Double consumption = Double.valueOf(interval.getString("consumption"));
                    powerUsage.setPowerUsage(consumption);
                    powerUsageList.add(powerUsage);
                }
            }
        } else {
            throw new RuntimeException("No data found in power consumption response.");
        }

        return powerUsageList;
    }

}
