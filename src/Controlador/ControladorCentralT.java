/* DEO GLORIA
 * CHRISTUS REX
 * AVE MARIA
 * Qui Ut Deus
 * Sancte Ioseph, dirige me
 */

package Controlador;

import Serializacion.TablaGanadores;
import Vista.VTPrincipal;
import Vista.VentanaMTG;
import ar.edu.unlu.rmimvc.observer.IObservableRemoto;
import modelo.IPartida;

import javax.swing.*;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ControladorCentralT extends Controlador implements ControladorTroncal, Serializable {

    private int cantJugadores;
    private int numeroRondas;
    private VTPrincipal vt;
    private ControladorP cp;


    public ControladorCentralT (ControladorP cp) {

        cantJugadores = 0;
        numeroRondas = 0;

        this.cp = cp;

    }

    //Inicia el procedimiento con menù estilo terminal
    public void iniciarFlujoTerminal () {

        ControladorCentralT c = this;

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                vt = new VTPrincipal(c);

            }
        });

    }

    @Override
    public void ventanaTermino(String cadena) {

        ArrayList<String> entradasValidas = new ArrayList<String>();

        entradasValidas.add("1");
        entradasValidas.add("2");
        entradasValidas.add("3");

        if (!validarEntrada(cadena, entradasValidas)) {

            System.out.println("Entrada no válida");
            vt.cerrarVentanaYTerminarPrograma();

        } else {

            if (cadena.compareTo("1") == 0) {

                vt.pedirCantidadJugadores();

            } else if (cadena.compareTo("2") == 0) {

                //Muestra tabla de ganadores
                TablaGanadores tg = new TablaGanadores();
                ArrayList<String> tgs = tg.obtenerSTabla();

                vt.mostrarTablaGanadores(tgs);

            } else if (cadena.compareTo("3") == 0) {

                cp.cargarPartida(this);

            } else { //Solo queda opcion 4

                vt.cerrarVentanaYTerminarPrograma();

            }

        }

    }

    public void recibirCantJugadores (String cant) {

        ArrayList<String> entradasValidas = new ArrayList<String>();

        entradasValidas.add("2");
        entradasValidas.add("3");
        entradasValidas.add("4");

        if (!validarEntrada(cant, entradasValidas)) {

            System.out.println("Entrada no válida");
            vt.cerrarVentanaYTerminarPrograma(); //Si tengo tiempo desarrollo un metodo
                                                 //para reingresar entrada

        } else {

            cantJugadores = Integer.parseInt(cant);
            vt.pedirCantidadRondas();

        }

    }

    public void recibirCantRondas (String cant) {

        ArrayList<String> entradasValidas = new ArrayList<String>();

        entradasValidas.add("1");
        entradasValidas.add("3");
        entradasValidas.add("6");

        if (!validarEntrada(cant, entradasValidas)) {

            System.out.println("Entrada no válida");
            vt.cerrarVentanaYTerminarPrograma(); //Si tengo tiempo desarrollo un metodo
            //para reingresar entrada

        } else {

            numeroRondas = Integer.parseInt(cant);
            vt.inhabilitarAcciones();
            iniciarPartida();

        }

    }

    private void iniciarPartida () {

        cp.iniciarPartida(this, cantJugadores, numeroRondas);

    }

    public void rehabilitarMenuPrincipal () {

        vt.habilitarAcciones();

    }

    public void derivadoTermino(String cadena) {

        rehabilitarMenuPrincipal();

        if (!cadena.equals("PartidaGuardada")) {

            vt.mostrarGanador(cadena);

        } else {

            vt.avisoGuardado();

        }

    }

    public void recibirMsjDerivado (String cadena) {}

    public <T extends IObservableRemoto> void setModeloRemoto(T t) throws RemoteException {

    }


    public void actualizar(IObservableRemoto iObservableRemoto, Object o) throws RemoteException {

    }

}
