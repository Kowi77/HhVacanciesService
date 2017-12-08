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
public class Area {

    private int id;
    private String name;

    public Area(@JsonProperty("id") int id,
                @JsonProperty("name") String name) {
        this.id = id;
        this.name = name;
    }
}
