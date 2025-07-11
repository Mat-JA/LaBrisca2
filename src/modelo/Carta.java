/* DEO GLORIA
 * CHRISTUS REX
 * AVE MARIA
 * Qui Ut Deus
 * Sancte Ioseph, dirige me
 */

package modelo;

import java.io.Serializable;

public class Carta implements Serializable {

    private Palo palo;
    private int numero;

    public Carta (Palo palo, int numero) {

        this.palo = palo;
        this.numero = numero;

    }

    public Palo getPalo() {

        return palo;
    }

    public String getPaloStr() {

        return palo.name();
    }

    public int getNumero() {

        return numero;
    }

    public int valorCarta() {

        int valor = 0;

        if (numero == 1) {

            valor = 11;

        } else if (numero == 3) {

            valor = 10;

        } else if (numero == 12) {

            valor = 4;

        } else if (numero == 11) {

            valor = 3;

        } else if (numero == 10) {

            valor = 2;

        }

        return valor;

    }

    @Override
    public String toString() {

        String stringifiedStuffHuzzah = "";

        stringifiedStuffHuzzah = stringifiedStuffHuzzah.concat(palo.name() + numero);

        return stringifiedStuffHuzzah;
    }
}
