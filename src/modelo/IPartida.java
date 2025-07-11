package modelo;

import ar.edu.unlu.rmimvc.observer.IObservableRemoto;
import ar.edu.unlu.rmimvc.observer.IObservadorRemoto;

import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IPartida extends IObservableRemoto {
    //Recibe un nuevo Jugador y lo añade a la lista de jugadores
    void aniadirJugador(String nombre) throws RemoteException;

    ArrayList<ArrayList<String>> darInfoMesa() throws RemoteException;

    void cambiarInfoMesaJBC(String actualizacion, String nomJ) throws RemoteException;

    ArrayList<IJugador> darIjugadores () throws RemoteException;

    public void actualizarDatosEInicializar (int rondasAGanar, IObservadorRemoto obs, int nj) throws  RemoteException;

    public String getEstado() throws RemoteException;

    public void cambiarInfoMesaJCT (String nomJ) throws RemoteException;

    public void guardarPartida () throws RemoteException;

    public void cargarPartida () throws RemoteException;

    public ArrayList<String> getNomJ () throws RemoteException;

}
