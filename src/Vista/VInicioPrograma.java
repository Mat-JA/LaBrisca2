/* DEO GLORIA
 * CHRISTUS REX
 * AVE MARIA
 * Qui Ut Deus
 * Sancte Ioseph, dirige me
 */

//Ausqui Mateo Javier 190236

//Ventana de elecciòn presentada al principio del programa
//y a cada jugador al inicio de partida.
//Permite elegir si se quiere vista gràfica o de consola.


package Vista;

import Controlador.Controlador;
import ar.edu.unlu.rmimvc.cliente.IControladorRemoto;
import ar.edu.unlu.rmimvc.observer.IObservadorRemoto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

public class VInicioPrograma extends Ventana {

    public VInicioPrograma(Controlador c) {

        this.c = c;
        c.setVentana(this);
        iniciarVentana();

    }

    private void iniciarVentana () {

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
                c.ventanaTermino("T");
            }
        });

        JButton BGrafica = new JButton("Grafica");
        BGrafica.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                c.ventanaTermino("G");
            }
        });

        JLabel leyenda = new JLabel("Bienvenido a La Brisca. Elija la interfaz del menú:");

        opciones.add(Bterminal);
        opciones.add(BGrafica);

        panelPrincipal.add(opciones, BorderLayout.CENTER);
        panelPrincipal.add(leyenda, BorderLayout.NORTH);

        frame.setVisible(true);

    }

}
