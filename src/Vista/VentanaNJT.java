/* DEO GLORIA
 * CHRISTUS REX
 * AVE MARIA
 * Qui Ut Deus
 * Sancte Ioseph, dirige me
 */

package Vista;

import Controlador.ControladorJT;
import ar.edu.unlu.rmimvc.observer.IObservadorRemoto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

public class VentanaNJT extends Ventana {

    ControladorJT cj;

    public VentanaNJT (ControladorJT cj) {

        this.cj = cj;
        this.cj.setVentana(this);
        iniciarVentana();

    }

    private void iniciarVentana () {

        frame = new JFrame("La Brisca");
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel principal = (JPanel) frame.getContentPane();

        principal.setLayout(new BorderLayout());

        JTextArea terminal = new JTextArea(
                "INGRESE NOMBRE DE JUGADOR: \n");

        principal.add(terminal, BorderLayout.CENTER);

        JPanel pInferior = new JPanel();
        pInferior.setLayout(new GridLayout(2,1, 0, 10));

        JTextField tfInput = new JTextField();

        JButton bInput = new JButton("Enviar");
        bInput.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cj.recibirNomJugador(tfInput.getText());
            }
        });

        pInferior.add(tfInput);
        pInferior.add(bInput);

        principal.add(pInferior, BorderLayout.SOUTH);

        frame.setVisible(true);

    }

}
