package kov.develop.repository;

import kov.develop.model.Vacancy;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class VacancyRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Vacancy save(Vacancy vacancy) {
        if (vacancy.getId() == null) {
            em.persist(vacancy);
            return vacancy;
        } else {
            return em.merge(vacancy);
        }
    }

    public String aaa(){
        return "ZLATAN !!";
    }
}
