/* DEO GLORIA
 * CHRISTUS REX
 * AVE MARIA
 * Qui Ut Deus
 * Sancte Ioseph, dirige me
 */

//Ausqui Mateo Javier 190236

//Mètodos, y atributos comunes a las ventanas

package Vista;

import Controlador.Controlador;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.io.Serializable;
import java.rmi.RemoteException;

public abstract class Ventana implements Serializable{

    protected Controlador c;
    protected JFrame frame;

    //Cierra la ventana en caso de que ocurra un error
    public void cerrarVentanaYTerminarPrograma() {

        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));

    }

    public void cerrarVentana() {

        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));

    }

    public JFrame getFrame () {

        return frame;
    }

    public void inhabilitarAcciones () {

        frame.setEnabled(false);

    }

    public void habilitarAcciones () {

        frame.setEnabled(true);

    }

}
