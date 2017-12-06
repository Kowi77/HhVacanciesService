package kov.develop.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kov.develop.config.ApplicationContextProvider;
import kov.develop.model.Vacancy;
import kov.develop.repository.VacancyRepository;
import kov.develop.utils.SalaryConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HhVacancyService {

    private static String AREA_ID = "1202";                                        // Новосибирск (по умолчанию)
    private static String SPECIALIZATION = "1";                                 // Информационные технологии, интернет, телеком (по умолчанию)
    private static String HH_URL = "https://api.hh.ru";                       // базовый поиска
    private static String HH_VACANCIES_URL = "https://api.hh.ru/vacancies"; // базовый URL поиска вакансий
    private static String PER_PAGE = "50";                                     // количество вакансий в одном запросе
    private static String URL = HH_VACANCIES_URL + "?area=" + AREA_ID + "&specialization=" + SPECIALIZATION + "&per_page=" + PER_PAGE + "&page=";

    private static List<Vacancy> vacancies;



    private static final Logger log = LogManager.getLogger(HhVacancyService.class);

    private static VacancyRepository repository = (VacancyRepository) ApplicationContextProvider.getApplicationContext().getBean("vacancyRepository");






    public static void main(String[] args) throws Exception {

       // ApplicationContext ctx = ApplicationContextProvider.getApplicationContext();
       // System.out.println(ctx);

      HhVacancyService vr = new HhVacancyService();
      //regionsMap = new HashMap<>();
     // VacancyRepository repository = (VacancyRepository) ctx.getBean("vacancyRepository");

        vr.LoadAndSaveSpecializationsFromHh(vr.getJSON("https://api.hh.ru/specializations", 1000));

    }

    public static void refreshDbFromHh(){
        int pages = 0;
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

    public static Map<Integer, String> getRegions() {
        Map<Integer, String> regionsMap = new HashMap<>();
        try {
            regionsMap = LoadAndSaveRegionsFromHh(getJSON(HH_URL + "/areas/113", 1000));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return regionsMap;
    }

    public static Map<Integer, String> getSpecializations() {
        Map<Integer, String> specMap = new HashMap<>();
        try {
            specMap = LoadAndSaveSpecializationsFromHh(getJSON(HH_URL + "/specializations", 1000));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return specMap;
    }

    private static Integer LoadAndSaveVacanciesFromHh(String json) throws IOException {
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

    private static Map<Integer, String> LoadAndSaveRegionsFromHh(String json) throws IOException {
        Map<Integer, String> regionsMap = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode nodes = objectMapper.readTree(json).path("areas");
        Iterator<JsonNode> regions = nodes.elements();

        while(regions.hasNext()) {
            JsonNode region = regions.next();
            Integer id =region.at("/id").asInt();
            String name = region.at("/name").asText();
            regionsMap.put(id, name);
            log.info(id + " " + name);
        }
        return regionsMap;
    }

    private static Map<Integer, String> LoadAndSaveSpecializationsFromHh(String json) throws IOException {
        Map<Integer, String> specMap = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode nodes = objectMapper.readTree(json);
        Iterator<JsonNode> specs = nodes.elements();

        while(specs.hasNext()) {
            JsonNode spec = specs.next();
            Integer id =spec.at("/id").asInt();
            String name = spec.at("/name").asText();
            specMap.put(id, name);
            log.info(id + " " + name);
        }
        return specMap;
    }

    private static String getJSON(String url, int timeout) {
        HttpURLConnection c = null;
        try {
            java.net.URL u = new URL(url);
            c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("GET");
            c.setRequestProperty("Content-length", "0");
            c.setUseCaches(false);
            c.setAllowUserInteraction(false);
            c.setConnectTimeout(timeout);
            c.setReadTimeout(timeout);
            c.connect();
            int status = c.getResponseCode();

            switch (status) {
                case 200:
                case 201:
                    BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line+"\n");
                    }
                    br.close();
                    return sb.toString();
            }

        }
        catch (MalformedURLException ex) {
        } catch (IOException ex) {
        } finally {
            if (c != null) {
                try {
                    c.disconnect();
                } catch (Exception ex) {
                }
            }
        }
        return null;
    }
}
