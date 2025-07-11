/* DEO GLORIA
 * CHRISTUS REX
 * AVE MARIA
 * Qui Ut Deus
 * Sancte Ioseph, dirige me
 */

package Vista;

import Controlador.ControladorJG;
import ar.edu.unlu.rmimvc.observer.IObservadorRemoto;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class VentanaJugadorG extends VentanaJugador implements Serializable {

    private ControladorJG cjg;
    private JFrame frame;

    //recibe cartas de baza (en 0) y cartas de su propia mano (en 1).
    public VentanaJugadorG (ControladorJG c, ArrayList<ArrayList<String>> cartasIniciales,
                            boolean esTurno, String nombreJ) {

        cjg = c;

        iniciarVentana(cartasIniciales, nombreJ);
        if (!esTurno) { inhabilitarAcciones(); }

    }

    private void iniciarVentana (ArrayList<ArrayList<String>> cartasMesa, String nomJ) {

        frame = new JFrame(nomJ);
        frame.setSize(500, 700);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        //Seteo panel principal

        JPanel panelPrincipal = (JPanel) frame.getContentPane();

        //!er columna triunfo, 2da columna Baza, 3er columna mano jugador, 4ta informes
        panelPrincipal.setLayout(new GridLayout(5, 1, 0, 20));

        //Seteo label de triunfo

        String triunfo = cartasMesa.get(0).get(0);

        BufferedImage imgCartaTriunfo = null;
        try {
            imgCartaTriunfo = ImageIO.read(new File("src/imgcartas/"+triunfo+".png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JLabel lTriunfo = new JLabel(new ImageIcon(imgCartaTriunfo));
        //Falta añiadir el mouse action listener
        //Como ésta es la creación y no la actualización, no debería ir (ningún jugador ganó ronda)

        //Seteo labels de Baza
        JPanel JPBaza = new JPanel();

        //Modelo de cómo debe ser el panel para la baza
        JPBaza.setLayout(new GridLayout(1, 5, 10, 0));

        //Recién inicia, no hay cartas en baza
        JPBaza.add(new JLabel("Sin cartas en baza")); //Esto va dentro de un if en actualizar


        //Seteo labels de cartas de mano de Jugador
        JPanel JPMano = new JPanel();

        JPMano.setLayout(new GridLayout(1, 3, 10, 0));

        ArrayList<JLabel> cartasMJ = new ArrayList<JLabel>();

        for (int i = 0; i < cartasMesa.get(1).size(); i++) {

            String sAux = cartasMesa.get(1).get(i);

            BufferedImage aux = null;
            try {
                aux = ImageIO.read(new File("src/imgcartas/"+sAux+".png"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            cartasMJ.add(new JLabel(new ImageIcon(aux)));

            Ventana v = this;

            cartasMJ.get(i).addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {}
                @Override
                public void mousePressed(MouseEvent e) {}
                @Override
                public void mouseReleased(MouseEvent e) {

                    cjg.bajarCarta(sAux);
                }
                @Override
                public void mouseEntered(MouseEvent e) {}
                @Override
                public void mouseExited(MouseEvent e) {}
            });

            JPMano.add(cartasMJ.get(i));

        }

        JButton bGuardar = new JButton("Guardar partida");
        bGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Se presiono guardar");
                cjg.guardarPartida();
            }
        });

        panelPrincipal.add(lTriunfo);
        panelPrincipal.add(JPBaza);
        panelPrincipal.add(JPMano);
        panelPrincipal.add(bGuardar);

        frame.setVisible(true);

    }

    @Override
    public void actualizarVentana(ArrayList<ArrayList<String>> infoMesaJugador, boolean esTurno, boolean cambiazoPosible) {

        JPanel panelPrincipal = (JPanel) frame.getContentPane();

        panelPrincipal.removeAll();

        //!er columna triunfo, 2da columna Baza, 3er columna mano jugador, 4ta informes
        panelPrincipal.setLayout(new GridLayout(5, 1, 0, 20));

        String triunfo = infoMesaJugador.get(0).get(0);

        BufferedImage imgCartaTriunfo = null;
        try {
            imgCartaTriunfo = ImageIO.read(new File("src/imgcartas/"+triunfo+".png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JLabel lTriunfo = new JLabel(new ImageIcon(imgCartaTriunfo));

        if (cambiazoPosible) {

            lTriunfo.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {}
                @Override
                public void mousePressed(MouseEvent e) {}
                @Override
                public void mouseReleased(MouseEvent e) {

                    cjg.cambiazo();
                }
                @Override
                public void mouseEntered(MouseEvent e) {}
                @Override
                public void mouseExited(MouseEvent e) {}
            });

        }

        //Seteo labels de Baza
        JPanel JPBaza = new JPanel();

        //Modelo de cómo debe ser el panel para la baza
        JPBaza.setLayout(new GridLayout(1, 5, 10, 0));

        if (infoMesaJugador.get(0).get(1).compareTo("BazaVacia") == 0) {

            JPBaza.add(new JLabel("Sin cartas en baza"));

        } else {

            //Cargar cartas de baza para ver
            ArrayList<JLabel> cartasB = new ArrayList<JLabel>();

            for (int i = 1; i < infoMesaJugador.get(0).size(); i++) {

                String sbAux = infoMesaJugador.get(0).get(i);

                BufferedImage bAux = null;
                try {
                    bAux = ImageIO.read(new File("src/imgcartas/"+sbAux+".png"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                cartasB.add(new JLabel(new ImageIcon(bAux)));
                JPBaza.add(cartasB.get(i-1));

            }

        }

        //Seteo labels de cartas de mano de Jugador
        JPanel JPMano = new JPanel();

        JPMano.setLayout(new GridLayout(1, infoMesaJugador.get(1).size(), 10, 0));

        if (infoMesaJugador.get(1).get(0).compareTo("ManoVacia") == 0) {

            JPBaza.add(new JLabel("Sin cartas en mano"));

        } else {

            ArrayList<JLabel> cartasMJ = new ArrayList<JLabel>();

            for (int j = 0; j < infoMesaJugador.get(1).size(); j++) {

                String sAux = infoMesaJugador.get(1).get(j);

                BufferedImage aux = null;
                try {
                    aux = ImageIO.read(new File("src/imgcartas/"+sAux+".png"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                cartasMJ.add(new JLabel(new ImageIcon(aux)));

                cartasMJ.get(j).addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {}
                    @Override
                    public void mousePressed(MouseEvent e) {}
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        cjg.bajarCarta(sAux);
                    }
                    @Override
                    public void mouseEntered(MouseEvent e) {}
                    @Override
                    public void mouseExited(MouseEvent e) {}
                });

                JPMano.add(cartasMJ.get(j));

            }

        }

        JPanel JPInfoJ = new JPanel();

        JPInfoJ.setLayout(new GridLayout(1, 3, 0, 15));

        JLabel lPuntaje = new JLabel(infoMesaJugador.get(2).get(0));
        JLabel lCantCart = new JLabel(infoMesaJugador.get(2).get(1));
        JLabel lCantRondas = new JLabel(infoMesaJugador.get(2).get(2));

        JPInfoJ.add(lPuntaje);
        JPInfoJ.add(lCantCart);
        JPInfoJ.add(lCantRondas);

        JButton bGuardar = new JButton("Guardar partida");
        bGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cjg.guardarPartida();
            }
        });

        panelPrincipal.add(lTriunfo);
        panelPrincipal.add(JPBaza);
        panelPrincipal.add(JPMano);
        panelPrincipal.add(JPInfoJ);
        panelPrincipal.add(bGuardar);

        if (!esTurno) {

            inhabilitarAcciones();

        } else {

            habilitarAcciones();

        }

        frame.setVisible(true);

    }

    public void inhabilitarAcciones () {

        frame.setEnabled(false);

    }

    public void habilitarAcciones () {

        frame.setEnabled(true);

    }

}
