package kov.develop.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Data
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "vacancies")
public class Vacancy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "name")
    @NotNull
    @Size(max = 100)
    private String name;

    @Column(name = "date")
    @NotNull
    private LocalDateTime published_at;

    @Column(name = "employer")
    @Size(max = 60)
    private String employer;

    @Column(name = "salary")
    @Size(max = 30)
    private String salary;

    public Vacancy(String name, LocalDateTime published_at, String employer, String salary) {
        this.name = name;
        this.published_at = published_at;
        this.employer = employer;
        this.salary = salary;
    }
}