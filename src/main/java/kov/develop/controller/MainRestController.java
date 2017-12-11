package kov.develop.controller;

import kov.develop.model.Area;
import kov.develop.model.Specialization;
import kov.develop.model.Vacancy;
import kov.develop.services.AreaAndSpecializationService;
import kov.develop.services.VacancyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class MainRestController {

    @Autowired
    VacancyService service;

    @Autowired
    AreaAndSpecializationService utilService;

    @RequestMapping(value = "/vacancies/{areaId}/{specId}", method = RequestMethod.GET)
    public List<Vacancy> getVacaciesByAreaAndSpec(@PathVariable("areaId") String areaid, @PathVariable("specId") String specId){
        service.refreshDbFromHhByIds(Integer.valueOf(areaid), Integer.valueOf(specId));
        return service.findAll();
    }

    @RequestMapping(value = "/areas", method = RequestMethod.GET)
    public List<Area> getAreas(){
        return utilService.getRegions();
    }

    @RequestMapping(value = "/specializations", method = RequestMethod.GET)
    public List<Specialization> getSpecializations(){
        return utilService.getSpecializations();
    }
}
