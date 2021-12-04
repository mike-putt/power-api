package com.api.power.service;

import com.api.power.config.MqttConfig;
import com.api.power.model.PowerUsageDto;
import com.api.power.repository.ElectricKiwiRepository;
import com.api.power.repository.model.SessionEntity;
import com.api.power.transformer.PowerTransformer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@EnableScheduling
@Slf4j
public class PowerService {

    @Value("${config.customer}")
    public String customer;

    @Value("${config.group-by}")
    public String groupBy;

    @Value("${config.days-back}")
    public int daysBack;

    private final PowerTransformer powerTransformer;
    private final ElectricKiwiRepository electrickiwiRepository;
    private final MqttConfig.MqttGateway mqttGateway;

    private final Map<String, String> icpIdByCustomer;
    private final ObjectMapper objectMapper;

    @Autowired
    public PowerService(
            final PowerTransformer powerTransformer,
            final ElectricKiwiRepository electrickiwiRepository,
            final MqttConfig.MqttGateway mqttGateway
    ) {
        this.powerTransformer = powerTransformer;
        this.electrickiwiRepository = electrickiwiRepository;
        this.mqttGateway = mqttGateway;
        icpIdByCustomer = new HashMap<>();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    public List<PowerUsageDto> getPowerUsageForCustomer(final String customer, final LocalDate startDate, final LocalDate endDate, final String groupBy) {
        final String icpId = getIcpFromSession(customer);
        final JSONObject powerUsage = electrickiwiRepository.getConsumptionAverages(customer, icpId, startDate, endDate, groupBy);

        return powerTransformer.powerUsageEntityToDtoList(powerUsage);
    }

//    @Scheduled(cron = "0 0 6 * * *")
    @Scheduled(fixedDelay = 3600000)
    public void getPowerUsageForCustomer() {
        final String icpId = getIcpFromSession(customer);
        final LocalDate yesterday = LocalDate.now().minusDays(daysBack);
        final JSONObject powerUsageEntity = electrickiwiRepository.getConsumptionAverages(customer, icpId, yesterday, yesterday, groupBy);
        final List<PowerUsageDto> powerUsageDtoList = powerTransformer.powerUsageEntityToDtoList(powerUsageEntity);

        for (final PowerUsageDto powerUsage : powerUsageDtoList) {
            try {
                final String mqttData = objectMapper.writeValueAsString(powerUsage);
                mqttGateway.sendToMqtt(mqttData);
            } catch (final JsonProcessingException ex) {
                log.error(ex.getMessage());
                throw new RuntimeException(ex.getMessage());
            }
        }
    }

    private String getIcpFromSession(final String customer) {
        String icpId;

        if (icpIdByCustomer.containsKey(customer)) {
            icpId = icpIdByCustomer.get(customer);
        } else {
            final SessionEntity session = electrickiwiRepository.getSession();
            final SessionEntity.Data.Customer customerInfo = Arrays.stream(session.getData().getCustomer()).findFirst().get();
            icpId = customerInfo.getConnection().getConnection_id();
            icpIdByCustomer.put(customer, icpId);
        }

        return icpId;
    }

    public String getRefreshToken() {
        return electrickiwiRepository.getOauthRefreshToken();
    }

    public void updateRefreshToken(final String refreshToken) {
        electrickiwiRepository.setOauthRefreshToken(refreshToken);
    }

}
