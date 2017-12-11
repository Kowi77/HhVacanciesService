package kov.develop.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kov.develop.model.Vacancy;
import kov.develop.repository.VacancyRepository;
import kov.develop.utils.SalaryConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

import static kov.develop.utils.JsonUtil.getJSON;

@Service
public class VacancyService {

    private static final Logger log = LogManager.getLogger(VacancyService.class);

    private static String HH_VACANCIES_URL = "https://api.hh.ru/vacancies"; // базовый URL поиска вакансий
    private static String PER_PAGE = "50";  // количество вакансий в одном запросе

    @Autowired
    VacancyRepository repository;

    public List<Vacancy> findAll() {
        log.info("Find all vacancies");
        return repository.findAll();
    }

    public Vacancy save(Vacancy vacancy){
        log.info("Sava vacancy id = {}", vacancy.getId());
        return repository.save(vacancy);
    }

    public void deleteAll(){
        log.info("Delete all vacancies");
        repository.deleteAll();
    }

    public void refreshDbFromHhByIds(int areaId, int specId){
        int pages = 0;
        repository.deleteAll();
        try {
            log.info("Load page № 0");
            pages = LoadAndSaveVacanciesFromHh(getJSON(HH_VACANCIES_URL + "?area=" + areaId + "&specialization=" + specId + "&per_page=" + PER_PAGE + "&page=" + "0", 1000));
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        for(int i = 1; i < pages; i++){
            log.info("Load page № {} ", i);
            try {
                LoadAndSaveVacanciesFromHh(getJSON(HH_VACANCIES_URL + "?area=" + areaId + "&specialization=" + specId + "&per_page=" + PER_PAGE + "&page=" + i, 1000));
            } catch (IOException e) {
               log.error(e.getMessage());
            }
        }
    }

    private Integer LoadAndSaveVacanciesFromHh(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode nodes = objectMapper.readTree(json).path("items");
        Iterator<JsonNode> vacancies = nodes.elements();

        while(vacancies.hasNext()) {
            JsonNode vacancy = vacancies.next();
            String salary = SalaryConverter.convertFromHh(vacancy.at("/salary/from").asText(), vacancy.at("/salary/to").asText(), vacancy.at("/salary/currency").asText());
            LocalDateTime date = LocalDateTime.parse(vacancy.at("/published_at").asText().substring(0, 16));
            String name = vacancy.at("/name").asText();
            String employer = vacancy.at("/employer/name").asText();
            Vacancy vacancy1 = new Vacancy(name, date, employer, salary);
            repository.save(vacancy1);
        }
        return objectMapper.readTree(json).at("/pages").asInt(); //количество страниц для скачивания
    }

}
