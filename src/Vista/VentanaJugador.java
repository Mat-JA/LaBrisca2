/* DEO GLORIA
 * CHRISTUS REX
 * AVE MARIA
 * Qui Ut Deus
 * Sancte Ioseph, dirige me
 */

//Ausqui Mateo Javier 190236

//Ventana genérica de Jugador

package Vista;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class VentanaJugador extends Ventana implements Serializable {

    public VentanaJugador () {}

    public void actualizarVentana (ArrayList<ArrayList<String>> cartasMesa, boolean esTurno, boolean cambiazoDisponible) {}

}
