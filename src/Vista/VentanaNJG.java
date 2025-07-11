/* DEO GLORIA
 * CHRISTUS REX
 * AVE MARIA
 * Qui Ut Deus
 * Sancte Ioseph, dirige me
 */

//Ventana gráfica donde un jugador ingresa su nick

package Vista;

import Controlador.ControladorCentralG;
import Controlador.ControladorJ;
import Controlador.ControladorJG;
import ar.edu.unlu.rmimvc.observer.IObservadorRemoto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

public class VentanaNJG extends Ventana {

    private ControladorJG cj;

    public VentanaNJG (ControladorJG cj) {

        this.cj = cj;
        this.cj.setVentana(this);
        iniciarVentana();

    }

    private void iniciarVentana () {

        frame = new JFrame("La Brisca");
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panelPrincipal = (JPanel) frame.getContentPane();

        panelPrincipal.setLayout(new BorderLayout());

        JPanel opciones = new JPanel();

        opciones.setLayout(new GridLayout(3, 1, 0, 25));

        JLabel explicacion = new JLabel("Jugador, ingrese su apodo:");

        JTextField tfNombre = new JTextField("Escriba aquí su nombre...");

        JButton bNom = new JButton("Aceptar");
        bNom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cj.recibirNomJugador(tfNombre.getText());
            }
        });

        opciones.add(explicacion);
        opciones.add(tfNombre);
        opciones.add(bNom);

        panelPrincipal.add(opciones, BorderLayout.CENTER);

        frame.setVisible(true);

    }

}
