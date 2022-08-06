package model;


import exception.INcorrectEmailException;
import model.serializable.PersonSerial;

import java.io.Serializable;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public  class Person implements Serializable, PersonSerial {

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private UUID id;
    private String vorName;
    private String nachName;
    private String passwort;
    private String email;

    /**
     * Constructor
     *
     * @param vorName
     * @param nachName
     * @param passwort
     */
    public Person(String vorName, String nachName, String passwort, String email) {
        this.id = UUID.randomUUID();
        this.vorName = vorName;
        this.nachName = nachName;
        this.passwort = passwort;
        this.email = email;

    }

    public static boolean checkEmail(String emailStr) throws INcorrectEmailException {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        if (!matcher.find()) {
            throw new INcorrectEmailException();
        }
        return matcher.find();
    }

    /**
     * Getter und Setter
     *
     * @return
     */
    public UUID getId() {
        return id;
    }

    public String getVorName() {
        return vorName;
    }

    public void setVorName(String vorName) {
        this.vorName = vorName;
    }

    public String getNachName() {
        return nachName;
    }

    public void setNachName(String nachName) {
        this.nachName = nachName;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.passwort;
    }

    public String toString() {
        String person = id + ";" + vorName + ";" + nachName + ";" + passwort + ";" + email;
        return person;
    }

}
