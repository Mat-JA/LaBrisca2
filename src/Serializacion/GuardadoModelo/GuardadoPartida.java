/* DEO GLORIA
 * CHRISTUS REX
 * AVE MARIA
 * Qui Ut Deus
 * Sancte Ioseph, dirige me
 */

package Serializacion.GuardadoModelo;

import Serializacion.Serializador;

public class GuardadoPartida {

    private Serializador serializador;

    public GuardadoPartida () {

        serializador = new Serializador("partida.dat");

    }

    public void guardarPartida(Object partida) {

        serializador.writeOneObject(partida);

    }

    public Object cargarPartida () {

        Object[] aux = serializador.readObjects();

        return aux[0];
    }

}
