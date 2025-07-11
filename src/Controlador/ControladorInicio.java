/* DEO GLORIA
 * CHRISTUS REX
 * AVE MARIA
 * Qui Ut Deus
 * Sancte Ioseph, dirige me
 */

//Ausqui Mateo Javier 190236

package Controlador;

//Recibe tipo de terminal para menu y crea al ControladorCentral

import ar.edu.unlu.rmimvc.cliente.IControladorRemoto;
import ar.edu.unlu.rmimvc.observer.IObservableRemoto;
import modelo.IPartida;

import java.rmi.RemoteException;

public class ControladorInicio extends Controlador implements IControladorRemoto {

    //Indicadores utilizados en conjunto a VInivioPrograma
    //Para determinar tipo de ventana para menu
    private static final String iGraf = "G";
    private static final String iTerm = "T";

    private ControladorP cp;

    public ControladorInicio () {

    }

    @Override
    public void ventanaTermino(String cadena) {

        v.cerrarVentana();


        if (cadena.equals(iGraf)) {

            ControladorCentralG ccg = new ControladorCentralG(cp);
            ccg.iniciarFlujoGrafico();

        } else if (cadena.equals(iTerm)) {

            ControladorCentralT cct = new ControladorCentralT(cp);
            cct.iniciarFlujoTerminal();

        } else {

            System.out.println("Entrada inesperada, Error");

        }

    }

    @Override
    public <T extends IObservableRemoto> void setModeloRemoto(T t) throws RemoteException {

    }

    @Override
    public void actualizar(IObservableRemoto iObservableRemoto, Object o) throws RemoteException {

    }

    public void setCp(ControladorP cp) {
        this.cp = cp;
    }
}
