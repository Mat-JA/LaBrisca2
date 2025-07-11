package modelo;

import ar.edu.unlu.rmimvc.observer.IObservableRemoto;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IJugador extends IObservableRemoto {
    String getNick() throws RemoteException;

    int getRondasGanadas() throws RemoteException;

    int getCantCartasGanadasRonda() throws RemoteException;

    int getPuntosRonda() throws RemoteException;

    boolean esSuTurno() throws RemoteException;

    boolean ganoBazaPrevia() throws RemoteException;

    boolean cambiazoEsPosible() throws RemoteException;

}
