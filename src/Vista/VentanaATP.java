/* DEO GLORIA
 * CHRISTUS REX
 * AVE MARIA
 * Qui Ut Deus
 * Sancte Ioseph, dirige me
 */

//Ausqui Mateo Javier 190236

//Esta ventana muestra al ganador de una partida que acaba de jugarse

package Vista;

import Controlador.ControladorCentralG;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.Serializable;

public class VentanaATP implements Serializable {

    JFrame frame;
    ControladorCentralG cc;

    public VentanaATP (ControladorCentralG c, String ganador) {

        cc = c;
        iniciarVentana(ganador);

    }

    private void finalizarVentana () {

        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));

    }

    private void iniciarVentana(String nGanador) {

        frame = new JFrame("La Brisca");
        frame.setSize(550, 400);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panelPrincipal = (JPanel) frame.getContentPane();

        panelPrincipal.setLayout(new GridLayout(3, 1, 0, 25));

        panelPrincipal.add(new JLabel("La partida terminó"));
        panelPrincipal.add(new JLabel("El ganador es: " + nGanador));

        JButton bContinuar = new JButton("Continuar");
        bContinuar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cc.rehabilitarMenuPrincipal();
                finalizarVentana();
            }
        });

        panelPrincipal.add(bContinuar);

        frame.setVisible(true);

    }

}
