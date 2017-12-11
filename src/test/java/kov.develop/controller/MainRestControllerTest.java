package kov.develop.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import kov.develop.model.Area;
import kov.develop.model.Specialization;
import kov.develop.model.Vacancy;
import kov.develop.services.AreaAndSpecializationService;
import kov.develop.services.VacancyService;
import kov.develop.data.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.annotation.PostConstruct;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration({"classpath:spring/spring-beans.xml", "classpath:spring/spring-mvc-configuration.xml"})
@WebAppConfiguration("WEB-INF/rest-controller-servlet.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@Sql(scripts = "classpath:db/schema.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MainRestControllerTest {

    private static final CharacterEncodingFilter CHARACTER_ENCODING_FILTER = new CharacterEncodingFilter();
    static final String VACANCIES_URL = "/vacancies/1202/1"; // Новосибирск, информационные технологии
    static final String SPECIALIZATIONS_URL = "/specializations";
    static final String AREAS_URL = "/areas";

    static {
        CHARACTER_ENCODING_FILTER.setEncoding("UTF-8");
        CHARACTER_ENCODING_FILTER.setForceEncoding(true);
    }

    protected MockMvc mockMvc;
    protected final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Autowired
    protected VacancyService service;
    @Autowired
    protected AreaAndSpecializationService utilService;


    @Autowired
    private WebApplicationContext webApplicationContext;

    @PostConstruct
    private void postConstruct() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilter(CHARACTER_ENCODING_FILTER)
                .build();
    }


    // Перед тестом необходимо проверять актуальность константных вакансий и, при необходимости, корректировать!!!
    @Test
    public void testFindAll() throws Exception {
        ResultActions resultActions = mockMvc.perform(get(VACANCIES_URL));
        String resultAsString = resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();
        List<Vacancy> result = mapper.readValue(resultAsString, new TypeReference<List<Vacancy>>(){});
        Assert.assertTrue(result.contains(DataTest.VACANCY_IT_CONSTRUCT));
        Assert.assertTrue(result.contains(DataTest.VACANCY_DEVSMONKEYS));

        result = service.findAll();
        Assert.assertTrue(result.contains(DataTest.VACANCY_IT_CONSTRUCT));
        Assert.assertTrue(result.contains(DataTest.VACANCY_DEVSMONKEYS));
    }

    @Test
    public void testFindAreas() throws Exception {
        ResultActions resultActions = mockMvc.perform(get(AREAS_URL));
        String resultAsString = resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();
        List<Area> result = mapper.readValue(resultAsString, new TypeReference<List<Area>>(){});
        Assert.assertTrue(result.contains(DataTest.OMSK));
        Assert.assertTrue(result.contains(DataTest.KURSK));
        Assert.assertEquals(84, result.size());
    }

    @Test
    public void testFindSpecs() throws Exception {
        ResultActions resultActions = mockMvc.perform(get(SPECIALIZATIONS_URL));
        String resultAsString = resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();
        List<Specialization> result = mapper.readValue(resultAsString, new TypeReference<List<Specialization>>(){});
        Assert.assertTrue(result.contains(DataTest.BANKI));
        Assert.assertTrue(result.contains(DataTest.TURISM));
        Assert.assertEquals(28, result.size());
    }
}
