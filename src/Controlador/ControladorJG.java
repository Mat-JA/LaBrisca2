/* DEO GLORIA
 * CHRISTUS REX
 * AVE MARIA
 * Qui Ut Deus
 * Sancte Ioseph, dirige me
 */

package Controlador;

import Vista.Ventana;
import Vista.VentanaJugadorG;
import Vista.VentanaNJG;
import ar.edu.unlu.rmimvc.RMIMVCException;
import ar.edu.unlu.rmimvc.cliente.IControladorRemoto;
import ar.edu.unlu.rmimvc.observer.IObservableRemoto;
import ar.edu.unlu.rmimvc.observer.ObservableRemoto;
import modelo.IJugador;
import modelo.Jugador;

import javax.swing.*;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ControladorJG extends ControladorJ implements IControladorRemoto {

    private VentanaNJG njg;
    private boolean esTurno;

    public ControladorJG (ControladorTroncal troncal) {

        cp = troncal;
        esTurno = false;
        iniciarNj();

    }

    public ControladorJG (ControladorTroncal troncal, String nj) {

        cp = troncal;
        esTurno = false;
        nomJ = nj;

    }

    //SETUP

    public void recibirNomJugador (String nombre) {

        nomJ = nombre;

        njg.cerrarVentana();

        cp.recibirMsjDerivado("JLR" + nroCj);

    }

    public void iniciarNj() {

        ControladorJG c = this;

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                njg = new VentanaNJG(c);

            }
        });

    }

    @Override
    public void crearVentanaJugador(ArrayList<ArrayList<String>> listasCartas) {

        ControladorJG c = this;

        try {

            esTurno = jugador.esSuTurno();

        } catch (RemoteException e) {

            e.printStackTrace();

        }

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                vj = new VentanaJugadorG(c, listasCartas, esTurno, getNomJugador());

            }
        });

    }

    public void bajarCarta(String cartaS) {

        cp.recibirMsjDerivado("JBC"+nomJ+" "+cartaS);//Hacer que jugadores no puedan poner " " en nombre

    }

    public void cambiazo() {

        cp.recibirMsjDerivado("JCT"+nroCj);

    }

    public void guardarPartida() {

        cp.recibirMsjDerivado("JQG");

    }

    public void actualizarVentanaJugador (ArrayList<ArrayList<String>> infoMesaJugador) {

        try {

            ArrayList<String> infoJugador = new ArrayList<String>();

            infoJugador.add("Puntaje: " + jugador.getPuntosRonda());
            infoJugador.add("Cartas ganadas: " + jugador.getCantCartasGanadasRonda());
            infoJugador.add("Rondas ganadas: " + jugador.getRondasGanadas());

            infoMesaJugador.add(infoJugador);

            vj.actualizarVentana(infoMesaJugador, jugador.esSuTurno(), jugador.cambiazoEsPosible());

        } catch (RemoteException e) {

            e.printStackTrace();

        }

    }

    @Override
    public <T extends IObservableRemoto> void setModeloRemoto(T t) throws RemoteException {
        super.setModeloRemoto(t);
    }

    @Override
    public void actualizar(IObservableRemoto iObservableRemoto, Object o) throws RemoteException {



    }


}
