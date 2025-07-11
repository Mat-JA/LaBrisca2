/* DEO GLORIA
 * CHRISTUS REX
 * AVE MARIA
 * Qui Ut Deus
 * Sancte Ioseph, dirige me
 */

//Ausqui Mateo Javier 190236

//Formato utilizado para guardar un ganador historico en memoria secundaria

package Serializacion;

import java.io.Serializable;

public class RenglonTG implements Serializable {

    private static final long serialVersionUID = 1L;
    private String nombre;
    private int ganadas;

    public RenglonTG (String nombre) {

        this.nombre = nombre;
        ganadas = 1;

    }

    public String getNombre() {

        return nombre;
    }

    public int getGanadas() {

        return ganadas;
    }

    public void ganar() {

        ganadas++;
    }

    public String toString () {

        return(nombre + " ------------------ " + ganadas);
    }

    public void igualar(RenglonTG aCopiar) {

        this.ganadas = aCopiar.getGanadas();
        this.nombre = aCopiar.getNombre();

    }

}
