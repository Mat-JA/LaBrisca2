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
import java.util.ArrayList;

public class VentanaJugadorT extends VentanaJugador {

    private ControladorJT cjt;

    //recibe cartas de baza (en 0) y cartas de su propia mano (en 1).
    public VentanaJugadorT (ControladorJT c, ArrayList<ArrayList<String>> cartasIniciales,
                            boolean esTurno, String nombreJ) {

        cjt = c;
        iniciarVentana(cartasIniciales, nombreJ);
        if (!esTurno) { inhabilitarAcciones(); }

    }

    private void iniciarVentana (ArrayList<ArrayList<String>> cartasMesa, String nomJ) {

        frame = new JFrame(nomJ);
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        JPanel principal = (JPanel) frame.getContentPane();

        principal.setLayout(new BorderLayout());

        JTextArea terminal = new JTextArea("");

        String triunfo = "La carta de triunfo es: " + cartasMesa.get(0).get(0) + "\n\n";

        terminal.setText(terminal.getText() + triunfo);

        String baza = "Sobre la mesa no hay cartas.\n";

        terminal.setText(terminal.getText() + baza);

        String mano = "En la mano tenes: \n\n";

        for (int i = 0; i < cartasMesa.get(1).size(); i++) {

            mano = mano.concat(cartasMesa.get(1).get(i) + "\n");

        }

        mano = mano.concat("\nIngrese la carta que quiere bajar a la mesa");

        terminal.setText(terminal.getText() + mano);

        JPanel pInferior = new JPanel();
        pInferior.setLayout(new GridLayout(2,1, 0, 10));

        JTextField tfInput = new JTextField();

        JButton bInput = new JButton("Enviar");
        bInput.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cjt.bajarCarta(tfInput.getText());
            }
        });

        JButton bGuardar = new JButton("Guardar partida");
        bGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Se presiono guardar");
                cjt.guardarPartida();
            }
        });

        pInferior.add(tfInput);
        pInferior.add(bInput);

        pInferior.add(new JLabel("Presione para Guardar"));
        pInferior.add(bGuardar);

        principal.add(terminal, BorderLayout.CENTER);
        principal.add(pInferior, BorderLayout.SOUTH);

        frame.setVisible(true);

    }


    public void actualizarVentana(ArrayList<ArrayList<String>> infoMesaJugador, boolean esTurno, boolean cambiazoDisponible) {

        JPanel principal = (JPanel) frame.getContentPane();

        principal.removeAll();

        principal.setLayout(new BorderLayout());

        JTextArea terminal = new JTextArea("");

        String infoJugador = "";

        infoJugador = infoJugador.concat(infoMesaJugador.get(2).get(0) + "\n" +
                                             infoMesaJugador.get(2).get(1) + "\n" +
                                             infoMesaJugador.get(2).get(2) + "\n\n");

        terminal.setText(terminal.getText() + infoJugador);

        String triunfo = "La carta de triunfo es: " + infoMesaJugador.get(0).get(0) + "\n\n";

        terminal.setText(terminal.getText() + triunfo);

        String baza = "";

        if (infoMesaJugador.get(0).get(1).compareTo("BazaVacia") == 0) {

            baza = baza.concat("Sobre la mesa no hay cartas.\n");

        } else {

            baza = baza.concat("Cartas en la mesa: \n\n");

            for (int i = 1; i < infoMesaJugador.get(0).size(); i++) {

                baza = baza.concat(infoMesaJugador.get(0).get(i) + "\n");

            }

        }

        terminal.setText(terminal.getText() + baza + "\n");

        String mano = "";

        ArrayList<String> strValidos = new ArrayList<>();

        if (infoMesaJugador.get(1).get(0).compareTo("ManoVacia") == 0) {

            mano = mano.concat("Sin cartas en mano.\n\n");

        } else {

            mano = mano.concat("En la mano tenes: \n\n");

            for (int j = 0; j < infoMesaJugador.get(1).size(); j++) {

                mano = mano.concat(infoMesaJugador.get(1).get(j) + "\n");
                strValidos.add(infoMesaJugador.get(1).get(j));

            }

            mano = mano.concat("\nIngrese la carta que quiere bajar a la mesa\n");

        }

        terminal.setText(terminal.getText() + mano);

        if (cambiazoDisponible) {

            terminal.setText(terminal.getText() + "\nCambiazo disponible, ingrese 'C' para usar.\n");
        }

        JPanel pInferior = new JPanel();
        pInferior.setLayout(new GridLayout(2,2, 0, 10));

        JTextField tfInput = new JTextField();

        JButton bInput = new JButton("Enviar");
        bInput.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (cjt.validarEntrada(tfInput.getText(), strValidos)) {

                    cjt.bajarCarta(tfInput.getText());

                } else if (tfInput.getText().equals("C")) {

                    cjt.cambiazo();

                }

            }
        });

        JButton bGuardar = new JButton("Guardar partida");
        bGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Se presiono guardar");
                cjt.guardarPartida();
            }
        });

        pInferior.add(tfInput);
        pInferior.add(bInput);

        pInferior.add(new JLabel("Presione para Guardar"));
        pInferior.add(bGuardar);

        principal.add(terminal, BorderLayout.CENTER);
        principal.add(pInferior, BorderLayout.SOUTH);

        if (!esTurno) {

            inhabilitarAcciones();

        } else {

            habilitarAcciones();

        }

        frame.setVisible(true);

    }

}
