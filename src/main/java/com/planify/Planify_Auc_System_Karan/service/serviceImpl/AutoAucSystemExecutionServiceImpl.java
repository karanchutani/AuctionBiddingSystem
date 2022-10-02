package com.planify.Planify_Auc_System_Karan.service.serviceImpl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.planify.Planify_Auc_System_Karan.constant.BasicConstants;
import com.planify.Planify_Auc_System_Karan.dto.AutoRequestModel;
import com.planify.Planify_Auc_System_Karan.service.AutoAucSystemExecutionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This is AutoAucSystemExecutionServiceImpl class for automatic execution.
 * @author Karan.
 */
@Service
@Transactional
public class AutoAucSystemExecutionServiceImpl implements AutoAucSystemExecutionService {

    /**
     * port field.
     */
    @Value("${server.port}")
    private int port;

    /**
     * restTemplate field.
     */
    private RestTemplate restTemplate = new RestTemplate();

    /**
     * objectMapper field.
     */
    final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * This is automateSystem() method for implementation.
     * @param jsonFile field.
     * @throws IOException exception.
     */
    @Override
    public void automateSystem(String jsonFile) throws IOException {

        try {
            System.out.println(BasicConstants.EXE_START);
            final File from = new File(jsonFile);
            final JsonNode jsonFromFile = objectMapper.readTree(from);
            final Map<String, AutoRequestModel> requestMap = getAutoRequestMap();
            for (JsonNode jsonNode : jsonFromFile) {
                requestMap.entrySet().stream().forEach(entry -> {
                    checkAndCallService(jsonNode, entry);
                });
            }
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println(BasicConstants.CHECK_JSON_FILE_MSG);
        }
        System.out.println(BasicConstants.EXE_END);
    }

    /**
     * This checkAndCallService() method is used for call the exact APIS based on the requested json.
     * @param jsonFromFile field.
     * @param entry field.
     */
    private void checkAndCallService(JsonNode jsonFromFile, Map.Entry<String, AutoRequestModel> entry) {

        if (jsonFromFile.has(entry.getKey())) {
            for (JsonNode jsonNode : jsonFromFile.get(entry.getKey())) {
                callOurCustomAPIs(jsonNode, entry);
            }
        }
    }

    /**
     * This callOurCustomAPIs() method is used for calling our own apis by localhost.
     * @param request field.
     * @param entry field.
     */
    private void callOurCustomAPIs(JsonNode request, Map.Entry<String, AutoRequestModel> entry) {
        try {
            String url = entry.getValue().getUrl();

            if (entry.getKey().equalsIgnoreCase(BasicConstants.WITHDRAW_BID)) {
                url = url + "/" + request.get(BasicConstants.ID).asText();
            } else if (entry.getKey().equalsIgnoreCase(BasicConstants.CLOSE_AUCTION)) {
                url = url + "?"+BasicConstants.AUCTION_USERNAME+"=" + request.get(BasicConstants.AUCTION_USERNAME).asText();
            } else if (entry.getKey().equalsIgnoreCase(BasicConstants.GET_PROFIT)) {
                url = url + "?"+BasicConstants.USERNAME+"=" + request.get(BasicConstants.USERNAME).asText();
            }

            final HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
                    restTemplate.exchange(BasicConstants.BASE_URL+port + url,
                            entry.getValue().getHttpMethod(),
                            new HttpEntity<String>(objectMapper
                                    .writeValueAsString(request), headers), String.class);

        } catch (Exception e) {
            System.out.println();
            System.out.println(e.getLocalizedMessage());
            System.out.println(BasicConstants.CHECK_JSON_FILE_MSG);
            System.out.println(request);
            System.out.println();
        }
    }

    /**
     * This is getAutoRequestMap() method.
     * @return map.
     */
    private Map<String, AutoRequestModel> getAutoRequestMap() {
        final Map<String, AutoRequestModel> map = new LinkedHashMap<>();
        map.put(BasicConstants.CREATE_USER, new AutoRequestModel(HttpMethod.POST, BasicConstants.CREATE_USER_URL));
        map.put(BasicConstants.CREATE_AUCTION, new AutoRequestModel(HttpMethod.POST, BasicConstants.CREATE_AUCTION_URL));
        map.put(BasicConstants.CREATE_BID, new AutoRequestModel(HttpMethod.POST, BasicConstants.CREATE_BID_URL));
        map.put(BasicConstants.WITHDRAW_BID, new AutoRequestModel(HttpMethod.POST, BasicConstants.WITHDRAW_BID_URL));
        map.put(BasicConstants.UPDATE_BID, new AutoRequestModel(HttpMethod.POST, BasicConstants.UPDATE_BID_URL));
        map.put(BasicConstants.CLOSE_AUCTION, new AutoRequestModel(HttpMethod.POST, BasicConstants.CLOSE_AUCTION_URL));
        map.put(BasicConstants.GET_PROFIT, new AutoRequestModel(HttpMethod.GET, BasicConstants.GET_PROFIT_URL));
        return map;
    }
}
