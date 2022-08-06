package model.serializable;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.UUID;

public interface PersonSerial extends Serializable {

    public UUID getId() throws RemoteException;

    public String getVorName() throws RemoteException;

    public void setVorName(String vorName) throws RemoteException;

    public String getNachName() throws RemoteException;

    public void setNachName(String nachName) throws RemoteException;

    public String getPasswort() throws RemoteException;

    public void setPasswort(String passwort) throws RemoteException;

    public String getEmail() throws RemoteException;

    public void setEmail(String email) throws RemoteException;

    public String getPassword() throws RemoteException;


}
