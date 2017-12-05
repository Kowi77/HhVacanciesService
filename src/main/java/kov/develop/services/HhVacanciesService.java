package kov.develop.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kov.develop.config.ApplicationContextProvider;
import kov.develop.model.Vacancy;
import kov.develop.repository.VacancyRepository;
import kov.develop.utils.SalaryConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

public class HhVacanciesService {

    private static String AREA_ID = "4";                                        // = Новосибирск
    private static String SPECIALIZATION = "1";                                 // = Информационные технологии, интернет, телеком
    private static String HH_VACANCIES_URL = "https://api.hh.ru/vacancies"; // базовый URL поиска
    private static String PER_PAGE = "50";                                     // количество вакансий в одном запросе
    private static String URL = HH_VACANCIES_URL + "?area=" + AREA_ID + "&specialization=" + SPECIALIZATION + "&per_page=" + PER_PAGE + "&page=";

    private static List<Vacancy> vacancies;

    private static final Logger log = LogManager.getLogger(HhVacanciesService.class);

    private static VacancyRepository repository = (VacancyRepository) ApplicationContextProvider.getApplicationContext().getBean("vacancyRepository");






    public static void main(String[] args) throws Exception {

       // ApplicationContext ctx = ApplicationContextProvider.getApplicationContext();
       // System.out.println(ctx);

      HhVacanciesService vr = new HhVacanciesService();

     // VacancyRepository repository = (VacancyRepository) ctx.getBean("vacancyRepository");

        int pages = vr.LoadAndSaveVacanciesFromHh(vr.getJSON(URL + "0", 1000));
        System.out.println(pages);

        for(int i = 1; i < pages; i++){
            System.out.println("PAGE #### " + i);
            vr.LoadAndSaveVacanciesFromHh(vr.getJSON(URL + i, 1000));
        }
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
