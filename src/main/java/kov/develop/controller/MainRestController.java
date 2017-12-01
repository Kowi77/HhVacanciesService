package kov.develop.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainRestController {
    @RequestMapping(value = "/vacancies", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String getUsers(){

        return "ZLATAN RULIT !!";
    }

}
