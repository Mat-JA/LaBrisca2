/* DEO GLORIA
 * CHRISTUS REX
 * AVE MARIA
 * Qui Ut Deus
 * Sancte Ioseph, dirige me
 */

//Ausqui Mateo Javier 190236

package Vista;

import Controlador.ControladorCentralG;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.Serializable;

//Ventana particular para elegir cantidad de jugadores

public class VentanaCJG implements Serializable {

    private JFrame frame;
    private ControladorCentralG cc;

    public VentanaCJG(ControladorCentralG c) {

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

        JLabel avisoL = new JLabel("Seleccione cantidad de jugadores");

        panelPrincipal.add(avisoL, BorderLayout.NORTH);

        JPanel opciones = new JPanel();

        opciones.setLayout(new GridLayout(3, 1, 0, 30));

        JButton bDos = new JButton("2");
        bDos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                cc.recibirCJ(2);
                finalizarVentana();

            }
        });

        JButton bTres = new JButton("3");
        bTres.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cc.recibirCJ(3);
                finalizarVentana();
            }
        });


        JButton bCuatro = new JButton("4");
        bCuatro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cc.recibirCJ(4);
                finalizarVentana();
            }
        });

        opciones.add(bDos);
        opciones.add(bTres);
        opciones.add(bCuatro);

        panelPrincipal.add(opciones, BorderLayout.CENTER);

        frame.setVisible(true);

    }

}
