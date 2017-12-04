package kov.develop.controller;

import kov.develop.repository.VacancyRepository;
import kov.develop.repository.VacancyRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainRestController {


    @Autowired
    VacancyRepository repository;

    @RequestMapping(value = "/vacancies", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String getUsers(){

        //repository = (VacancyRepositoryImpl) ApplicationContextProvider.getApplicationContext().getBean("vacancyRepositoryImpl");
        return repository.toString() + " ZLATAN RULES!";//ApplicationContextProvider.getApplicationContext().getApplicationName().toString();//"ZLATAN RULIT !!";
    }

}
