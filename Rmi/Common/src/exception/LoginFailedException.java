package exception;

public class LoginFailedException extends Exception {

    public LoginFailedException() {
        super("Diese Kombination von Nutzername und Passwort existiert nicht.");
    }
}
