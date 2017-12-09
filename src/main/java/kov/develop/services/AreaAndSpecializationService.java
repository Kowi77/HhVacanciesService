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

import static kov.develop.utils.JsonUtil.getJSON;

@Service
public class AreaAndSpecializationService {

    private static String HH_URL = "https://api.hh.ru";                       // базовый URL поиска

    private static final Logger log = LogManager.getLogger(AreaAndSpecializationService.class);

    public static List<Area> getRegions() {
       List<Area> regions = new ArrayList<>();
        try {
            regions = LoadAndSaveRegionsFromHh(getJSON(HH_URL + "/areas/113", 1000));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return regions;
    }

    public static List<Specialization> getSpecializations() {
        List<Specialization> specList = new ArrayList<>();
        try {
            specList = LoadAndSaveSpecializationsFromHh(getJSON(HH_URL + "/specializations", 1000));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return specList;
    }

    private static List<Area> LoadAndSaveRegionsFromHh(String json) throws IOException {
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

    private static List<Specialization> LoadAndSaveSpecializationsFromHh(String json) throws IOException {
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
