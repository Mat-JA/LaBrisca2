/* DEO GLORIA
 * CHRISTUS REX
 * AVE MARIA
 * Qui Ut Deus
 * Sancte Ioseph, dirige me
 */

//Ausqui Mateo Javier 190236

package Controlador;

//Este controlador se encarga del menú principal en modo gráfico

import Serializacion.TablaGanadores;
import Vista.*;
import ar.edu.unlu.rmimvc.cliente.IControladorRemoto;
import ar.edu.unlu.rmimvc.observer.IObservableRemoto;
import modelo.IPartida;

import javax.swing.*;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ControladorCentralG extends Controlador implements ControladorTroncal, Serializable {

    private int cantJugadores;
    private int numeroRondas;
    private VentanaCJG vcj;
    private VentanaNRG vnr;
    private VentanaATP atp;
    private VentanaMTG mtg;
    private VentanaAnuncio vAnuncio;
    private ControladorP cp;

    //CONSTRUCTOR

    public ControladorCentralG(ControladorP cp) {

        cantJugadores = 0;
        numeroRondas = 0;

        this.cp = cp;

    }

    //METODOS PRIVADOS

    //Inicia ventana de carga de cantidad de jugadores
    private void iniciarCJ () {

        ControladorCentralG c = this;

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                vcj = new VentanaCJG(c);

            }
        });

    }

    //Inicia ventana de carga de numero de rondas
    private void iniciarNR () {

        ControladorCentralG c = this;

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                vnr = new VentanaNRG(c);

            }
        });

    }

    //Inicia ventana de mostrar ganador de partida
    private void iniciarTP (String nGanador) {

        ControladorCentralG c = this;

        System.out.println("Llegue a iniciar TP");

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                atp = new VentanaATP(c, nGanador);

            }
        });

    }

    //METODOS DE Controlador

    //Recibe opcion seleccionada de menu
    public void ventanaTermino(String cadena) {

        v.inhabilitarAcciones();
        if (cadena.compareTo("1") == 0) {

            iniciarCJ();

        } else if (cadena.compareTo("2") == 0) {

            TablaGanadores tg = new TablaGanadores();
            ArrayList<String> tgs = tg.obtenerSTabla();

            ControladorCentralG c = this;

            SwingUtilities.invokeLater(new Runnable() {
                public void run() {

                    mtg = new VentanaMTG(c, tgs);

                }
            });

        } else if (cadena.compareTo("3") == 0) {

            cp.cargarPartida(this);

        }

    }

    //Inicia el procedimiento con menu en estilo grafico
    public void iniciarFlujoGrafico () {

        Controlador c = this;

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                v = new VGMenuPrincipal(c);

            }
        });

    }

    //Recibe el resultado de la ventana de cantidad de jugadores
    public void recibirCJ (int cj) {

        if ((cj >= 2) && (cj <= 4)) {

            cantJugadores = cj;
            iniciarNR();

        } else { //Termina el programa

            System.out.println("Ha ocurrido un error");
            System.out.println("Número incompatible de jugadores");
            v.cerrarVentanaYTerminarPrograma();

        }

    }

    //Recibe el resultado de la ventana de numero de rondas
    public void recibirNR (int nr) {

        if ((nr == 1) || (nr == 3) || (nr == 6)) {

            numeroRondas = nr;
            iniciarPartida();

        } else {

            System.out.println("Ha ocurrido un error");
            System.out.println("Número incompatible de rondas");
            v.cerrarVentanaYTerminarPrograma();

        }

    }

    //Inicia una partida con cantJugadores y numeroRondas
    private void iniciarPartida () {

        cp.iniciarPartida(this, cantJugadores, numeroRondas);

    }

    //Vuelve a habilitar la interacción de usuario con la pantalla de menú
    public void rehabilitarMenuPrincipal () {

        v.habilitarAcciones();

    }


    //METODOS DE Controlador Central

    @Override
    public void derivadoTermino(String cadena) {

        if (!cadena.equals("PartidaGuardada")) {

            iniciarTP(cadena);

        } else {

            rehabilitarMenuPrincipal();

            SwingUtilities.invokeLater(new Runnable() {
                public void run() {

                    vAnuncio = new VentanaAnuncio();

                }
            });

        }

    }

    public void recibirMsjDerivado (String cadena) {}


}
