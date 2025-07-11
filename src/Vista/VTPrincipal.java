/* DEO GLORIA
 * CHRISTUS REX
 * AVE MARIA
 * Qui Ut Deus
 * Sancte Ioseph, dirige me
 */

package Vista;

import Controlador.ControladorCentralT;
import ar.edu.unlu.rmimvc.observer.IObservadorRemoto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class VTPrincipal extends Ventana {

    private ControladorCentralT ct;
    private JPanel principal;
    private JTextArea terminal;
    private JPanel pInferior;

    public VTPrincipal(ControladorCentralT c) {

        ct = c;
        iniciarVentana();

    }

    private void iniciarVentana () {

        frame = new JFrame("La Brisca");
        frame.setSize(700, 550);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        principal = (JPanel) frame.getContentPane();

        principal.setLayout(new BorderLayout());

        terminal = new JTextArea(
                "---------- MENU PRINCIPAL ----------\n\n" +
                "1. Nueva Partida\n" +
                "2. Tabla Ganadores\n" +
                "3. Cargar Partida\n" +
                "4. Salir\n");

        pInferior = new JPanel();
        pInferior.setLayout(new GridLayout(2,1, 0, 10));

        JTextField tfInput = new JTextField();

        JButton bInput = new JButton("Enviar");
        bInput.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ct.ventanaTermino(tfInput.getText());
            }
        });

        pInferior.add(tfInput);
        pInferior.add(bInput);

        principal.add(terminal, BorderLayout.CENTER);
        principal.add(pInferior, BorderLayout.SOUTH);

        frame.setVisible(true);

    }

    public void pedirCantidadJugadores () {

        principal = (JPanel) frame.getContentPane();

        terminal = new JTextArea("------- NUEVA PARTIDA -------\n\n" +
                "Elija cantidad de jugadores:\n" +
                "2\n" +
                "3\n" +
                "4\n");

        principal.removeAll();

        principal.setLayout(new BorderLayout());

        pInferior = new JPanel();
        pInferior.setLayout(new GridLayout(2,1, 0, 10));

        JTextField tfInput = new JTextField();

        JButton bInput = new JButton("Enviar");
        bInput.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ct.recibirCantJugadores(tfInput.getText());
            }
        });

        pInferior.add(tfInput);
        pInferior.add(bInput);

        principal.add(terminal, BorderLayout.CENTER);
        principal.add(pInferior, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    public void pedirCantidadRondas() {

        principal = (JPanel) frame.getContentPane();

        terminal = new JTextArea(terminal.getText() + "\n\n" +
                "------- NUEVA PARTIDA -------\n\n" +
                "Elija cantidad de rondas a ganar:\n" +
                "1\n" +
                "3\n" +
                "6\n");

        principal.removeAll();

        principal.setLayout(new BorderLayout());

        pInferior = new JPanel();
        pInferior.setLayout(new GridLayout(2,1, 0, 10));

        JTextField tfInput = new JTextField();

        JButton bInput = new JButton("Enviar");
        bInput.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ct.recibirCantRondas(tfInput.getText());
            }
        });

        pInferior.add(tfInput);
        pInferior.add(bInput);

        principal.add(terminal, BorderLayout.CENTER);
        principal.add(pInferior, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    public void mostrarGanador(String nickGanador) {

        principal = (JPanel) frame.getContentPane();

        terminal = new JTextArea(terminal.getText() + "\n\n" +
                nickGanador + " ganó la partida\n\n" +
                "---------- MENU PRINCIPAL ----------\n\n" +
                "1. Nueva Partida\n" +
                "2. Tabla Ganadores\n" +
                "3. Cargar Partida\n" +
                "4. Salir\n");

        principal.removeAll();

        pInferior = new JPanel();
        pInferior.setLayout(new GridLayout(2,1, 0, 10));

        JTextField tfInput = new JTextField();

        JButton bInput = new JButton("Enviar");
        bInput.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ct.ventanaTermino(tfInput.getText());
            }
        });

        pInferior.add(tfInput);
        pInferior.add(bInput);

        principal.add(terminal, BorderLayout.CENTER);
        principal.add(pInferior, BorderLayout.SOUTH);

        frame.setVisible(true);

    }

    public void mostrarTablaGanadores (ArrayList<String> tgs) {

        principal = (JPanel) frame.getContentPane();

        String ganadores = "";

        for (int i = 0; i < tgs.size(); i++) {

            ganadores = ganadores.concat(tgs.get(i) + "\n");
            System.out.println(tgs.get(i));

        }

        ganadores = ganadores.concat("\n");

        terminal = new JTextArea(ganadores +
                "---------- MENU PRINCIPAL ----------\n\n" +
                "1. Nueva Partida\n" +
                "2. Tabla Ganadores\n" +
                "3. Cargar Partida\n" +
                "4. Salir\n");

        principal.removeAll();

        pInferior = new JPanel();
        pInferior.setLayout(new GridLayout(2,1, 0, 10));

        JTextField tfInput = new JTextField();

        JButton bInput = new JButton("Enviar");
        bInput.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ct.ventanaTermino(tfInput.getText());
            }
        });

        pInferior.add(tfInput);
        pInferior.add(bInput);

        principal.add(terminal, BorderLayout.CENTER);
        principal.add(pInferior, BorderLayout.SOUTH);

        frame.setVisible(true);

    }

    public void avisoGuardado () {

        principal = (JPanel) frame.getContentPane();

        terminal = new JTextArea(terminal.getText() + "\n\nPartida Guardada\n\n" +
                "---------- MENU PRINCIPAL ----------\n\n" +
                "1. Nueva Partida\n" +
                "2. Tabla Ganadores\n" +
                "3. Cargar Partida\n" +
                "4. Salir\n");

        principal.removeAll();

        pInferior = new JPanel();
        pInferior.setLayout(new GridLayout(2,1, 0, 10));

        JTextField tfInput = new JTextField();

        JButton bInput = new JButton("Enviar");
        bInput.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ct.ventanaTermino(tfInput.getText());
            }
        });

        pInferior.add(tfInput);
        pInferior.add(bInput);

        principal.add(terminal, BorderLayout.CENTER);
        principal.add(pInferior, BorderLayout.SOUTH);

        frame.setVisible(true);

    }



}
