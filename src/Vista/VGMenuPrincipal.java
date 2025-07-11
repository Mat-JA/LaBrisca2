/* DEO GLORIA
 * CHRISTUS REX
 * AVE MARIA
 * Qui Ut Deus
 * Sancte Ioseph, dirige me
 */

//Ausqui Mateo Javier 190236

//Ventana de menú principal estilo gráfico.

package Vista;

import Controlador.Controlador;
import ar.edu.unlu.rmimvc.observer.IObservadorRemoto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;

public class VGMenuPrincipal extends Ventana {


    //Crear ventana de cero
    public VGMenuPrincipal(Controlador c) {

        this.c = c;
        c.setVentana(this);
        iniciarVentana();

    }

    private void iniciarVentana () {

        frame = new JFrame("La Brisca");
        frame.setSize(550, 400);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panelPrincipal = (JPanel) frame.getContentPane();

        panelPrincipal.setLayout(new BorderLayout());

        JLabel menuL = new JLabel("Menu Principal");

        panelPrincipal.add(menuL, BorderLayout.NORTH);

        JPanel opciones = new JPanel();

        opciones.setLayout(new GridLayout(4, 1, 0, 30));

        JButton bJugar = new JButton("Nueva Partida");
        JButton bTablaGanadores = new JButton("Tabla Ganadores");
        JButton bCargarPartida = new JButton("Cargar Partida");
        JButton bSalir = new JButton("Salir");

        bJugar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                c.ventanaTermino("1");

            }
        });

        bTablaGanadores.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                c.ventanaTermino("2");

            }
        });

        bCargarPartida.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                c.ventanaTermino("3");

            }
        });

        bSalir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });

        opciones.add(bJugar);
        opciones.add(bCargarPartida);
        opciones.add(bTablaGanadores);
        opciones.add(bSalir);

        panelPrincipal.add(opciones, BorderLayout.CENTER);

        frame.setVisible(true);

    }

}
