package kov.develop.Services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kov.develop.config.ApplicationContextProvider;
import kov.develop.model.Vacancy;
import kov.develop.repository.VacancyRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Iterator;

public class HhVacanciesService {

    private static String AREA_ID = "4";                                        // = Новосибирск
    private static String SPECIALIZATION = "1";                                 // = Информационные технологии, интернет, телеком
    private static String HH_VACANCIES_URL = "https://api.hh.ru/vacancies"; // базовый URL поиска
    private static String PER_PAGE = "50";                                     // количество вакансий в одном запросе
    private static String URL = HH_VACANCIES_URL + "?area=" + AREA_ID + "&specialization=" + SPECIALIZATION + "&per_page=" + PER_PAGE + "&page=";

    private final VacancyRepository repository = (VacancyRepository) ApplicationContextProvider.getApplicationContext().getBean("vacancyRepository");






    public static void main(String[] args) throws Exception {
        System.out.println(ApplicationContextProvider.getApplicationContext());

      HhVacanciesService vr = new HhVacanciesService();
      //  VacancyRepository repository = (VacancyRepository) ctx.getBean("vacancyRepository");

        int pages = vr.LoadAndSaveVacanciesFromHh(vr.getJSON(URL + "0", 1000));
        System.out.println(pages);

      /*  for(int i = 1; i < pages; i++){
            System.out.println("PAGE #### " + i);
            vr.LoadAndSaveVacanciesFromHh(vr.getJSON(URL + i, 1000));
        }*/
    }

    private Integer LoadAndSaveVacanciesFromHh(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode nodes = objectMapper.readTree(json).path("items");
        Iterator<JsonNode> vacancies = nodes.elements();

        while(vacancies.hasNext()) {
            JsonNode vacancy = vacancies.next();

            String salary = vacancy.at("/salary/from") + " - " + vacancy.at("/salary/to") + " " + vacancy.at("/salary/currency");
            salary = salary.trim().equals("-")? "" : salary;
            //TODO format salary
            LocalDateTime date = LocalDateTime.parse(vacancy.at("/published_at").asText().substring(0, 16));
            String name = vacancy.at("/name").asText();
            String employer = vacancy.at("/employer/name").asText();
            Vacancy vacancy1 = new Vacancy(name, date, employer, salary);
            System.out.println(vacancy1);
            repository.aaa();

        }

        return objectMapper.readTree(json).at("/pages").asInt(); //количество страниц для скачивания
    }

    private String getJSON(String url, int timeout) {
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
