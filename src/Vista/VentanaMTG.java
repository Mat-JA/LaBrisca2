/* DEO GLORIA
 * CHRISTUS REX
 * AVE MARIA
 * Qui Ut Deus
 * Sancte Ioseph, dirige me
 */

//Ausqui Mateo Javier 190236

//Esta ventana muestra la tabla de ganadores

package Vista;

import Controlador.ControladorCentralG;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.Serializable;
import java.util.ArrayList;

public class VentanaMTG implements Serializable {

    private JFrame frame;
    private ControladorCentralG cc;

    public VentanaMTG (ControladorCentralG c, ArrayList<String> tgs) {

        cc = c;
        iniciarVentana(tgs);

    }

    private void finalizarVentana () {

        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));

    }

    private void iniciarVentana(ArrayList<String> tgs) {

        frame = new JFrame("La Brisca");
        frame.setSize(550, 400);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panelPrincipal = (JPanel) frame.getContentPane();

        panelPrincipal.setLayout(new GridLayout(3, 1, 0, 25));

        panelPrincipal.add(new JLabel("TABLA GANADORES"));

        if (tgs.get(0).compareTo("sinGanadores") == 0) {

            panelPrincipal.add(new JLabel("De momento, no hay ganadores"));

        } else {

            JPanel jpGanadores = new JPanel();

            jpGanadores.setLayout(new GridLayout(tgs.size(), 1, 0, 5));

            for (int i = 0; i < tgs.size(); i++) {

                jpGanadores.add(new JLabel(tgs.get(i)));

            }

            panelPrincipal.add(jpGanadores);

        }

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
