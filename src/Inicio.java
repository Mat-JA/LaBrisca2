/* DEO GLORIA
 * CHRISTUS REX
 * AVE MARIA
 * Qui Ut Deus
 * Sancte Ioseph, dirige me
 */

//Ausqui Mateo Javier 190236

import Controlador.ControladorInicio;
import Vista.VInicioPrograma;

import javax.swing.*;

public class Inicio {

    public static void main (String[] args) {

        ControladorInicio ci = new ControladorInicio();

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                VInicioPrograma v = new VInicioPrograma(ci);

            }
        });

    }

}
