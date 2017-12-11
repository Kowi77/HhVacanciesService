package kov.develop.services;

import kov.develop.model.Area;
import kov.develop.model.Specialization;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static kov.develop.utils.JsonUtil.getJSON;

@Service
public class AreaAndSpecializationService {

    private static String HH_URL = "https://api.hh.ru";                       // базовый URL поиска
    private static String RUSSIA_ID = "113";

    private static final Logger log = LogManager.getLogger(AreaAndSpecializationService.class);

    public List<Area> getRegions() {
       List<Area> regions = new ArrayList<>();
        try {
            regions = LoadAndSaveRegionsFromHh(getJSON(HH_URL + "/areas/"  + RUSSIA_ID, 1000));
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        log.info("Get region's list");
        regions = regions.stream().sorted((a, b) -> a.getName().compareTo(b.getName())).collect(Collectors.toList());
        return regions;
    }

    public List<Specialization> getSpecializations() {
        List<Specialization> specList = new ArrayList<>();
        try {
            specList = LoadAndSaveSpecializationsFromHh(getJSON(HH_URL + "/specializations", 1000));
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        log.info("Get specialization's list");
        specList = specList.stream().sorted((a, b) -> a.getName().compareTo(b.getName())).collect(Collectors.toList());
        return specList;
    }

    private List<Area> LoadAndSaveRegionsFromHh(String json) throws IOException {
        List<Area> regionsList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode nodes = objectMapper.readTree(json).path("areas");
        Iterator<JsonNode> regions = nodes.elements();

        while(regions.hasNext()) {
            JsonNode region = regions.next();
            Integer id =region.at("/id").asInt();
            String name = region.at("/name").asText();
            regionsList.add(new Area(id, name));
            log.info(id + " " + name);
        }
        return regionsList;
    }

    private List<Specialization> LoadAndSaveSpecializationsFromHh(String json) throws IOException {
        List<Specialization> specList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode nodes = objectMapper.readTree(json);
        Iterator<JsonNode> specs = nodes.elements();

        while(specs.hasNext()) {
            JsonNode spec = specs.next();
            Integer id =spec.at("/id").asInt();
            String name = spec.at("/name").asText();
            specList.add(new Specialization(id, name));
            log.info(id + " " + name);
        }
        return specList;
    }
}
