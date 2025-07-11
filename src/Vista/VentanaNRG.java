/* DEO GLORIA
 * CHRISTUS REX
 * AVE MARIA
 * Qui Ut Deus
 * Sancte Ioseph, dirige me
 */

//Ausqui Mateo Javier 190236

package Vista;

//Ventana particular para elegir numero de rondas

import Controlador.ControladorCentralG;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.Serializable;

public class VentanaNRG implements Serializable {

    private JFrame frame;
    private ControladorCentralG cc;

    public VentanaNRG (ControladorCentralG c) {

        cc = c;
        iniciarVentana();

    }

    private void finalizarVentana () {

        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));

    }

    private void iniciarVentana() {

        frame = new JFrame("La Brisca");
        frame.setSize(550, 400);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panelPrincipal = (JPanel) frame.getContentPane();

        panelPrincipal.setLayout(new BorderLayout());

        JLabel avisoL = new JLabel("Seleccione número de rondas a jugar");

        panelPrincipal.add(avisoL, BorderLayout.NORTH);

        JPanel opciones = new JPanel();

        opciones.setLayout(new GridLayout(3, 1, 0, 30));

        JButton bUno = new JButton("1");
        bUno.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                cc.recibirNR(1);
                finalizarVentana();

            }
        });

        JButton bTres = new JButton("3");
        bTres.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cc.recibirNR(3);
                finalizarVentana();
            }
        });


        JButton bSeis = new JButton("6");
        bSeis.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cc.recibirNR(6);
                finalizarVentana();
            }
        });

        opciones.add(bUno);
        opciones.add(bTres);
        opciones.add(bSeis);

        panelPrincipal.add(opciones, BorderLayout.CENTER);

        frame.setVisible(true);

    }

}
