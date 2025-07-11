/* DEO GLORIA
 * CHRISTUS REX
 * AVE MARIA
 * Qui Ut Deus
 * Sancte Ioseph, dirige me
 */

package Controlador;

//Este controlador se encarga de conseguir que el jugador elija vista G o T

import Vista.VentanaJEV;
import Vista.VentanaJEVCarga;

import javax.swing.*;
import java.rmi.RemoteException;

public class ControladorEJ extends Controlador {

    protected ControladorTroncal cp;
    private VentanaJEV jev;
    private VentanaJEVCarga jevc;

    private String nombre;

    //modo indica si es iniciar o carga
    public ControladorEJ (ControladorTroncal troncal) {

        cp = troncal;

        crearJEV();

    }

    public ControladorEJ (ControladorTroncal troncal, String nombreJ) {

        cp = troncal;
        nombre = nombreJ;
        crearJEVCarga();

    }

    private void crearJEV () {

        Controlador c = this;

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                jev = new VentanaJEV(c);

            }
        });

    }

    private void crearJEVCarga () {

        Controlador c = this;

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                jevc = new VentanaJEVCarga(c);

            }
        });

    }

    public void procesarEntrada (String entrada) {

        if (entrada.compareTo("VT") == 0) {

            //Jugador elige procesamiento de terminal
            //Se cierra la ventana de eleccion
            jev.cerrarVentana();
            cp.recibirMsjDerivado("JLCT");


        } else if (entrada.compareTo("VG") == 0) {

            //Jugador elige procesamiento gráfico
            //Se cierra la ventana de eleccion
            jev.cerrarVentana();
            cp.recibirMsjDerivado("JLCG");

        } else if (entrada.compareTo("VTC") == 0) {

            jevc.cerrarVentana();
            cp.recibirMsjDerivado("JECT"+nombre);


        } else if (entrada.compareTo("VGC") == 0) {

            jevc.cerrarVentana();
            cp.recibirMsjDerivado("JECG"+nombre);

        }

    }

}
