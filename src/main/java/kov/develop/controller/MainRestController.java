package kov.develop.controller;

import kov.develop.model.Area;
import kov.develop.model.Specialization;
import kov.develop.model.Vacancy;
import kov.develop.repository.VacancyRepository;
import kov.develop.services.HhVacancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class MainRestController {


    @Autowired
    VacancyRepository repository;

    @RequestMapping(value = "/vacancies", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Vacancy> getVacacies(){
        HhVacancyService.refreshDbFromHh();
        return repository.findAll();
    }

    @RequestMapping(value = "/vacancies/{areaId}/{specId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Vacancy> getVacaciesByAreaAndSpec(@PathVariable("areaId") String areaid, @PathVariable("specId") String specId){
        HhVacancyService.refreshDbFromHhByIds(Integer.valueOf(areaid), Integer.valueOf(specId));
        return repository.findAll();
    }

    @RequestMapping(value = "/areas", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Area> getAreas(){
        return HhVacancyService.getRegions();
    }

    @RequestMapping(value = "/specializations", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Specialization> getSpecializations(){
        return HhVacancyService.getSpecializations();
    }
}
