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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaJEVCarga extends Ventana {

    public VentanaJEVCarga (Controlador c) {

        this.c = c;
        c.setVentana(this);
        iniciarVentana();

    }

    private void iniciarVentana() {

        frame = new JFrame("La Brisca");
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panelPrincipal = (JPanel) frame.getContentPane();

        panelPrincipal.setLayout(new BorderLayout());

        JPanel opciones = new JPanel();

        opciones.setLayout(new GridLayout(2, 1, 0, 25));

        JButton Bterminal = new JButton("Terminal");
        Bterminal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                c.procesarEntrada("VTC");
            }
        });

        JButton BGrafica = new JButton("Grafica");
        BGrafica.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                c.procesarEntrada("VGC");
            }
        });

        JLabel leyenda = new JLabel("Bienvenido Jugador. Elija su interfaz de Juego:");

        opciones.add(Bterminal);
        opciones.add(BGrafica);

        panelPrincipal.add(opciones, BorderLayout.CENTER);
        panelPrincipal.add(leyenda, BorderLayout.NORTH);

        frame.setVisible(true);

    }

}
