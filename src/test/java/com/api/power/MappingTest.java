package com.api.power;

import com.api.power.model.PowerUsageDto;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class MappingTest {

    @Test
    public void mappingPowerUsage() {
        final String response = "{\n" +
                "    \"data\": {\n" +
                "        \"group_breakdown\": [\n" +
                "            \"2021-11-01\"\n" +
                "        ],\n" +
                "        \"range\": {\n" +
                "            \"end_date\": \"2021-11-01\",\n" +
                "            \"group_by\": \"day\",\n" +
                "            \"start_date\": \"2021-11-01\"\n" +
                "        },\n" +
                "        \"type\": \"consumption_averages\",\n" +
                "        \"usage\": {\n" +
                "            \"2021-11-01\": {\n" +
                "                \"adjustment_charges_incl_gst\": \"0.44\",\n" +
                "                \"bill_consumption\": \"62.77\",\n" +
                "                \"consumption\": \"64.60\",\n" +
                "                \"consumption_adjustment\": \"1.83\",\n" +
                "                \"fixed_charges_excl_gst\": \"2.21\",\n" +
                "                \"fixed_charges_incl_gst\": \"2.54\",\n" +
                "                \"intervals\": {\n" +
                "                    \"1\": {\n" +
                "                        \"consumption\": \"0.97\",\n" +
                "                        \"hop_best\": 0,\n" +
                "                        \"time\": \"12:00 AM\"\n" +
                "                    },\n" +
                "                    \"10\": {\n" +
                "                        \"consumption\": \"1.16\",\n" +
                "                        \"hop_best\": 0,\n" +
                "                        \"time\": \"4:30 AM\"\n" +
                "                    },\n" +
                "                    \"11\": {\n" +
                "                        \"consumption\": \"1.20\",\n" +
                "                        \"hop_best\": 0,\n" +
                "                        \"time\": \"5:00 AM\"\n" +
                "                    },\n" +
                "                    \"12\": {\n" +
                "                        \"consumption\": \"1.13\",\n" +
                "                        \"hop_best\": 0,\n" +
                "                        \"time\": \"5:30 AM\"\n" +
                "                    },\n" +
                "                    \"13\": {\n" +
                "                        \"consumption\": \"1.04\",\n" +
                "                        \"hop_best\": 0,\n" +
                "                        \"time\": \"6:00 AM\"\n" +
                "                    },\n" +
                "                    \"14\": {\n" +
                "                        \"consumption\": \"1.19\",\n" +
                "                        \"hop_best\": 0,\n" +
                "                        \"time\": \"6:30 AM\"\n" +
                "                    },\n" +
                "                    \"15\": {\n" +
                "                        \"consumption\": \"1.31\",\n" +
                "                        \"hop_best\": 0,\n" +
                "                        \"time\": \"7:00 AM\"\n" +
                "                    },\n" +
                "                    \"16\": {\n" +
                "                        \"consumption\": \"1.98\",\n" +
                "                        \"hop_best\": 0,\n" +
                "                        \"time\": \"7:30 AM\"\n" +
                "                    },\n" +
                "                    \"17\": {\n" +
                "                        \"consumption\": \"2.58\",\n" +
                "                        \"hop_best\": 0,\n" +
                "                        \"time\": \"8:00 AM\"\n" +
                "                    },\n" +
                "                    \"18\": {\n" +
                "                        \"consumption\": \"2.09\",\n" +
                "                        \"hop_best\": 0,\n" +
                "                        \"time\": \"8:30 AM\"\n" +
                "                    },\n" +
                "                    \"19\": {\n" +
                "                        \"consumption\": \"2.26\",\n" +
                "                        \"hop_best\": 1,\n" +
                "                        \"time\": \"9:00 AM\"\n" +
                "                    },\n" +
                "                    \"2\": {\n" +
                "                        \"consumption\": \"0.97\",\n" +
                "                        \"hop_best\": 0,\n" +
                "                        \"time\": \"12:30 AM\"\n" +
                "                    },\n" +
                "                    \"20\": {\n" +
                "                        \"consumption\": \"1.82\",\n" +
                "                        \"hop_best\": 1,\n" +
                "                        \"time\": \"9:30 AM\"\n" +
                "                    },\n" +
                "                    \"21\": {\n" +
                "                        \"consumption\": \"1.75\",\n" +
                "                        \"hop_best\": 0,\n" +
                "                        \"time\": \"10:00 AM\"\n" +
                "                    },\n" +
                "                    \"22\": {\n" +
                "                        \"consumption\": \"1.65\",\n" +
                "                        \"hop_best\": 0,\n" +
                "                        \"time\": \"10:30 AM\"\n" +
                "                    },\n" +
                "                    \"23\": {\n" +
                "                        \"consumption\": \"1.49\",\n" +
                "                        \"hop_best\": 0,\n" +
                "                        \"time\": \"11:00 AM\"\n" +
                "                    },\n" +
                "                    \"24\": {\n" +
                "                        \"consumption\": \"1.19\",\n" +
                "                        \"hop_best\": 0,\n" +
                "                        \"time\": \"11:30 AM\"\n" +
                "                    },\n" +
                "                    \"25\": {\n" +
                "                        \"consumption\": \"1.33\",\n" +
                "                        \"hop_best\": 0,\n" +
                "                        \"time\": \"12:00 PM\"\n" +
                "                    },\n" +
                "                    \"26\": {\n" +
                "                        \"consumption\": \"1.26\",\n" +
                "                        \"hop_best\": 0,\n" +
                "                        \"time\": \"12:30 PM\"\n" +
                "                    },\n" +
                "                    \"27\": {\n" +
                "                        \"consumption\": \"1.80\",\n" +
                "                        \"hop_best\": 0,\n" +
                "                        \"time\": \"1:00 PM\"\n" +
                "                    },\n" +
                "                    \"28\": {\n" +
                "                        \"consumption\": \"1.70\",\n" +
                "                        \"hop_best\": 0,\n" +
                "                        \"time\": \"1:30 PM\"\n" +
                "                    },\n" +
                "                    \"29\": {\n" +
                "                        \"consumption\": \"1.53\",\n" +
                "                        \"hop_best\": 0,\n" +
                "                        \"time\": \"2:00 PM\"\n" +
                "                    },\n" +
                "                    \"3\": {\n" +
                "                        \"consumption\": \"0.96\",\n" +
                "                        \"hop_best\": 0,\n" +
                "                        \"time\": \"1:00 AM\"\n" +
                "                    },\n" +
                "                    \"30\": {\n" +
                "                        \"consumption\": \"1.64\",\n" +
                "                        \"hop_best\": 0,\n" +
                "                        \"time\": \"2:30 PM\"\n" +
                "                    },\n" +
                "                    \"31\": {\n" +
                "                        \"consumption\": \"1.83\",\n" +
                "                        \"hop_best\": 0,\n" +
                "                        \"time\": \"3:00 PM\"\n" +
                "                    },\n" +
                "                    \"32\": {\n" +
                "                        \"consumption\": \"1.52\",\n" +
                "                        \"hop_best\": 0,\n" +
                "                        \"time\": \"3:30 PM\"\n" +
                "                    },\n" +
                "                    \"33\": {\n" +
                "                        \"consumption\": \"0.93\",\n" +
                "                        \"hop_best\": 0,\n" +
                "                        \"time\": \"4:00 PM\"\n" +
                "                    },\n" +
                "                    \"34\": {\n" +
                "                        \"consumption\": \"1.24\",\n" +
                "                        \"hop_best\": 0,\n" +
                "                        \"time\": \"4:30 PM\"\n" +
                "                    },\n" +
                "                    \"35\": {\n" +
                "                        \"consumption\": \"1.51\",\n" +
                "                        \"hop_best\": 0,\n" +
                "                        \"time\": \"5:00 PM\"\n" +
                "                    },\n" +
                "                    \"36\": {\n" +
                "                        \"consumption\": \"1.02\",\n" +
                "                        \"hop_best\": 0,\n" +
                "                        \"time\": \"5:30 PM\"\n" +
                "                    },\n" +
                "                    \"37\": {\n" +
                "                        \"consumption\": \"1.09\",\n" +
                "                        \"hop_best\": 0,\n" +
                "                        \"time\": \"6:00 PM\"\n" +
                "                    },\n" +
                "                    \"38\": {\n" +
                "                        \"consumption\": \"1.67\",\n" +
                "                        \"hop_best\": 0,\n" +
                "                        \"time\": \"6:30 PM\"\n" +
                "                    },\n" +
                "                    \"39\": {\n" +
                "                        \"consumption\": \"1.26\",\n" +
                "                        \"hop_best\": 0,\n" +
                "                        \"time\": \"7:00 PM\"\n" +
                "                    },\n" +
                "                    \"4\": {\n" +
                "                        \"consumption\": \"1.18\",\n" +
                "                        \"hop_best\": 0,\n" +
                "                        \"time\": \"1:30 AM\"\n" +
                "                    },\n" +
                "                    \"40\": {\n" +
                "                        \"consumption\": \"1.26\",\n" +
                "                        \"hop_best\": 0,\n" +
                "                        \"time\": \"7:30 PM\"\n" +
                "                    },\n" +
                "                    \"41\": {\n" +
                "                        \"consumption\": \"1.52\",\n" +
                "                        \"hop_best\": 0,\n" +
                "                        \"time\": \"8:00 PM\"\n" +
                "                    },\n" +
                "                    \"42\": {\n" +
                "                        \"consumption\": \"1.48\",\n" +
                "                        \"hop_best\": 0,\n" +
                "                        \"time\": \"8:30 PM\"\n" +
                "                    },\n" +
                "                    \"43\": {\n" +
                "                        \"consumption\": \"0.92\",\n" +
                "                        \"hop_best\": 0,\n" +
                "                        \"time\": \"9:00 PM\"\n" +
                "                    },\n" +
                "                    \"44\": {\n" +
                "                        \"consumption\": \"0.91\",\n" +
                "                        \"hop_best\": 0,\n" +
                "                        \"time\": \"9:30 PM\"\n" +
                "                    },\n" +
                "                    \"45\": {\n" +
                "                        \"consumption\": \"0.93\",\n" +
                "                        \"hop_best\": 0,\n" +
                "                        \"time\": \"10:00 PM\"\n" +
                "                    },\n" +
                "                    \"46\": {\n" +
                "                        \"consumption\": \"0.93\",\n" +
                "                        \"hop_best\": 0,\n" +
                "                        \"time\": \"10:30 PM\"\n" +
                "                    },\n" +
                "                    \"47\": {\n" +
                "                        \"consumption\": \"0.92\",\n" +
                "                        \"hop_best\": 0,\n" +
                "                        \"time\": \"11:00 PM\"\n" +
                "                    },\n" +
                "                    \"48\": {\n" +
                "                        \"consumption\": \"1.11\",\n" +
                "                        \"hop_best\": 0,\n" +
                "                        \"time\": \"11:30 PM\"\n" +
                "                    },\n" +
                "                    \"5\": {\n" +
                "                        \"consumption\": \"0.97\",\n" +
                "                        \"hop_best\": 0,\n" +
                "                        \"time\": \"2:00 AM\"\n" +
                "                    },\n" +
                "                    \"6\": {\n" +
                "                        \"consumption\": \"1.19\",\n" +
                "                        \"hop_best\": 0,\n" +
                "                        \"time\": \"2:30 AM\"\n" +
                "                    },\n" +
                "                    \"7\": {\n" +
                "                        \"consumption\": \"0.95\",\n" +
                "                        \"hop_best\": 0,\n" +
                "                        \"time\": \"3:00 AM\"\n" +
                "                    },\n" +
                "                    \"8\": {\n" +
                "                        \"consumption\": \"1.18\",\n" +
                "                        \"hop_best\": 0,\n" +
                "                        \"time\": \"3:30 AM\"\n" +
                "                    },\n" +
                "                    \"9\": {\n" +
                "                        \"consumption\": \"1.11\",\n" +
                "                        \"hop_best\": 0,\n" +
                "                        \"time\": \"4:00 AM\"\n" +
                "                    }\n" +
                "                },\n" +
                "                \"percent_consumption_adjustment\": \"2.8\",\n" +
                "                \"range\": {\n" +
                "                    \"end_date\": \"2021-11-01\",\n" +
                "                    \"start_date\": \"2021-11-01\"\n" +
                "                },\n" +
                "                \"status\": \"B\",\n" +
                "                \"total_charges_incl_gst\": \"17.57\",\n" +
                "                \"type\": \"A\",\n" +
                "                \"variable_charges_excl_gst\": \"13.07\",\n" +
                "                \"variable_charges_incl_gst\": \"15.03\"\n" +
                "            }\n" +
                "        }\n" +
                "    },\n" +
                "    \"status\": 1\n" +
                "}";

        final ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);
        final JSONObject powerUsageEntity = mapper.convertValue(response, JSONObject.class);

        final List<PowerUsageDto> powerUsageList = new ArrayList<>();
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
    }

}
