/* DEO GLORIA
 * CHRISTUS REX
 * AVE MARIA
 * Qui Ut Deus
 * Sancte Ioseph, dirige me
 */

//Ausqui Mateo Javier 190236

//Controlador genérico de jugador

package Controlador;

import Vista.VentanaJugador;
import ar.edu.unlu.rmimvc.cliente.IControladorRemoto;
import ar.edu.unlu.rmimvc.observer.IObservableRemoto;
import modelo.IJugador;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;

public abstract class ControladorJ extends Controlador implements IControladorRemoto {

    protected ControladorTroncal cp;

    protected String nomJ;
    protected int nroCj;
    protected VentanaJugador vj;

    protected IJugador jugador;

    public ControladorJ () {


    }

    public void setNroCj(int nroCj) {

        this.nroCj = nroCj;
    }

    public String getNomJugador () {

        return nomJ;
    }

    //Crea la ventana con la cuál el Jugador jugará la partida
    public void crearVentanaJugador (ArrayList<ArrayList<String>> listasCartas) {}

    public void actualizarVentanaJugador (ArrayList<ArrayList<String>> listasCartas) {}

    public void cerrarVentanaJ () {

        vj.cerrarVentanaYTerminarPrograma();

    }

    @Override
    public <T extends IObservableRemoto> void setModeloRemoto(T t) throws RemoteException {
        this.jugador = (IJugador) t;
    }

    @Override
    public void actualizar(IObservableRemoto iObservableRemoto, Object o) throws RemoteException {



    }


}
