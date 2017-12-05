package kov.develop.repository;

import kov.develop.model.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface VacancyRepository extends JpaRepository<Vacancy, Integer> {

 public List<Vacancy> findAll();

 @Override
 @Transactional
 Vacancy save(Vacancy vacancy);
}
