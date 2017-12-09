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

    private static String AREA_ID = "1202";                                        // Новосибирск (по умолчанию)
    private static String SPECIALIZATION = "1";                                 // Информационные технологии, интернет, телеком (по умолчанию)
    private static String HH_VACANCIES_URL = "https://api.hh.ru/vacancies"; // базовый URL поиска вакансий
    private static String PER_PAGE = "50";                                     // количество вакансий в одном запросе
    private static String URL = HH_VACANCIES_URL + "?area=" + AREA_ID + "&specialization=" + SPECIALIZATION + "&per_page=" + PER_PAGE + "&page=";

    @Autowired
    VacancyRepository repository;

    public List<Vacancy> findAll() {
        log.info("Find all vacancies");
        return repository.findAll();
    }

    public Vacancy save(Vacancy vacancy){
        log.info("Sava vacancy id = {id}", vacancy.getId());
        return repository.save(vacancy);
    }

    public void deleteAll(){
        log.info("Delete all vacancies");
        repository.deleteAll();
    }

    public void refreshDbFromHh(){
        int pages = 0;
        repository.deleteAll();
        try {
            pages = LoadAndSaveVacanciesFromHh(getJSON(URL + "0", 1000));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(pages);

       /* for(int i = 1; i < pages; i++){
            log.info("PAGE #### " + i);
            try {
                LoadAndSaveVacanciesFromHh(getJSON(URL + i, 1000));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
    }

    public void refreshDbFromHhByIds(int areaId, int specId){
        int pages = 0;
        repository.deleteAll();
        try {
            pages = LoadAndSaveVacanciesFromHh(getJSON(HH_VACANCIES_URL + "?area=" + areaId + "&specialization=" + specId + "&per_page=" + PER_PAGE + "&page=" + "0", 1000));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(pages);

       /* for(int i = 1; i < pages; i++){
            log.info("PAGE #### " + i);
            try {
                LoadAndSaveVacanciesFromHh(getJSON(URL + i, 1000));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
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
            log.info(repository.save(vacancy1));
        }
        return objectMapper.readTree(json).at("/pages").asInt(); //количество страниц для скачивания
    }

}
