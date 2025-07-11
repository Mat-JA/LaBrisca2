/* DEO GLORIA
 * CHRISTUS REX
 * AVE MARIA
 * Qui Ut Deus
 * Sancte Ioseph, dirige me
 */

package Vista;

import Controlador.Controlador;

import javax.swing.*;
import java.awt.*;

public class VentanaAnuncio extends Ventana {

    public VentanaAnuncio() {

        iniciarVentana();

    }

    private void iniciarVentana () {

        frame = new JFrame("La Brisca");
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        JPanel panelPrincipal = (JPanel) frame.getContentPane();

        panelPrincipal.setLayout(new GridLayout(1,1,0,5));

        panelPrincipal.add(new JLabel("Partida Guardada"));

    }

}
