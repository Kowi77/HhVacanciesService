package kov.develop.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@Data
@ToString
public class Specialization {

    private int id;
    private String name;

    public Specialization(@JsonProperty("id") int id,
                @JsonProperty("name") String name) {
        this.id = id;
        this.name = name;
    }
}
