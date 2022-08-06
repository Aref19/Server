package model.serializable;

import java.io.Serializable;
import java.rmi.RemoteException;

public interface AddresseSerial extends Serializable {
    public int getHausNr() throws RemoteException;

    public void setHausNr(int hausNr) throws RemoteException;

    public int getPlz() throws RemoteException;

    public void setPlz(int plz) throws RemoteException;

    public String getStadt() throws RemoteException;

    public void setStadt(String stadt) throws RemoteException;

    public String getHerkunft() throws RemoteException;

    public void setHerkunft(String herkunft) throws RemoteException;
}
