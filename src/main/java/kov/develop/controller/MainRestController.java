package kov.develop.controller;

import kov.develop.model.Vacancy;
import kov.develop.repository.VacancyRepository;
import kov.develop.services.HhVacancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MainRestController {


    @Autowired
    VacancyRepository repository;

    @RequestMapping(value = "/vacancies", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Vacancy> getUsers(){
        HhVacancyService.refreshDbFromHh();
        return repository.findAll();
    }

}
