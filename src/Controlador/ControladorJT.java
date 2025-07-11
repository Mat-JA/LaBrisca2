/* DEO GLORIA
 * CHRISTUS REX
 * AVE MARIA
 * Qui Ut Deus
 * Sancte Ioseph, dirige me
 */

package Controlador;

import Vista.VentanaJugadorG;
import Vista.VentanaJugadorT;
import Vista.VentanaNJT;
import ar.edu.unlu.rmimvc.cliente.IControladorRemoto;
import ar.edu.unlu.rmimvc.observer.IObservableRemoto;
import modelo.IJugador;
import modelo.Jugador;

import javax.swing.*;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ControladorJT extends ControladorJ implements IControladorRemoto {

    private VentanaNJT njt;
    private boolean esTurno;

    public ControladorJT (ControladorTroncal troncal) {

        cp = troncal;
        esTurno = false;
        iniciarNJT();

    }

    public ControladorJT (ControladorTroncal troncal, String nj) {

        cp = troncal;
        esTurno = false;
        nomJ = nj;

    }

    public void iniciarNJT() {

        ControladorJT c = this;

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                njt = new VentanaNJT(c);

            }
        });

    }

    public void recibirNomJugador (String nombre) {

        nomJ = nombre;

        njt.cerrarVentana();

        cp.recibirMsjDerivado("JLR" + nroCj);

    }

    public void crearVentanaJugador(ArrayList<ArrayList<String>> listasCartas) {

        ControladorJT c = this;

        try {

            esTurno = jugador.esSuTurno();

        } catch (RemoteException e) {

            e.printStackTrace();

        }

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                vj = new VentanaJugadorT(c, listasCartas, esTurno, getNomJugador());

            }
        });

    }

    public void bajarCarta(String cartaS) {

        cp.recibirMsjDerivado("JBC"+nomJ+" "+cartaS);

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

    public void cerrarVentanaJ() {

        super.cerrarVentanaJ();

    }

    @Override
    public <T extends IObservableRemoto> void setModeloRemoto(T t) throws RemoteException {
        super.setModeloRemoto(t);
    }

    @Override
    public void actualizar(IObservableRemoto iObservableRemoto, Object o) throws RemoteException {



    }

}
