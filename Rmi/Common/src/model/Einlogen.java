package model;

import java.io.Serializable;

public class Einlogen implements Serializable {
    public Person person;
    public boolean gefunden;

    public Einlogen(Person person, boolean gefunden) {
        this.person = person;
        this.gefunden = gefunden;
    }

}
