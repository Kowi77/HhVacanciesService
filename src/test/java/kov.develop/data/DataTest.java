package kov.develop.data;

import kov.develop.model.Area;
import kov.develop.model.Specialization;
import kov.develop.model.Vacancy;

import java.time.LocalDateTime;

public class DataTest {

    public static final Specialization BANKI = new Specialization(5, "Банки, инвестиции, лизинг");
    public static final Specialization TURISM = new Specialization(22, "Туризм, гостиницы, рестораны");
    public static final Area KURSK = new Area(1880, "Курская область");
    public static final Area OMSK = new Area(1249, "Омская область");
    public static final Vacancy VACANCY_IT_CONSTRUCT= new Vacancy("Веб-разработчик Bitrix / Web-программист PHP", LocalDateTime.parse("2017-12-10T19:30"), "Ай Ти Констракт","30000 - 65000 руб");
    public static final Vacancy VACANCY_DEVSMONKEYS= new Vacancy("WEB Senior Developer", LocalDateTime.parse("2017-11-29T12:37"), "DevsMonkeys","400 USD");
}
