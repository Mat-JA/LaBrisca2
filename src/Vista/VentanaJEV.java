/* DEO GLORIA
 * CHRISTUS REX
 * AVE MARIA
 * Qui Ut Deus
 * Sancte Ioseph, dirige me
 */

//Con esta ventana, un jugador selecciona si usará modo terminal o gráfico

package Vista;

import Controlador.Controlador;
import ar.edu.unlu.rmimvc.observer.IObservadorRemoto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

public class VentanaJEV extends Ventana {

    public VentanaJEV (Controlador c) {

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
                c.procesarEntrada("VT");
            }
        });

        JButton BGrafica = new JButton("Grafica");
        BGrafica.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                c.procesarEntrada("VG");
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
