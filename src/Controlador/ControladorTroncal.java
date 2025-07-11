/* DEO GLORIA
 * CHRISTUS REX
 * AVE MARIA
 * Qui Ut Deus
 * Sancte Ioseph, dirige me
 */

//Ausqui Mateo Javier 190236

package Controlador;

//Métodos de controladores que tienen otros controladores como dependientes

import java.io.Serializable;

public interface ControladorTroncal {

    public void derivadoTermino(String cadena);
    public void recibirMsjDerivado (String cadena);

}
