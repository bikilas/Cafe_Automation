package com.kifiya.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data

public class IngredientsItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  long id;
    private  String  name;
    @ManyToOne
    private  IngredientsCategory category;
    @JsonIgnore
    @ManyToOne
    private  Cafe cafe;
    private boolean InStoke= true;

    public boolean isInStoke() {
        return InStoke;
    }

    public void setInStoke(boolean inStoke) {
        InStoke = inStoke;
    }
}
