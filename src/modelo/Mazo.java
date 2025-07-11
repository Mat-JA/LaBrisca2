/* DEO GLORIA
 * CHRISTUS REX
 * AVE MARIA
 * Qui Ut Deus
 * Sancte Ioseph, dirige me
 */

package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Mazo implements Serializable {

    private ArrayList<Carta> cartas;

    //Mezcla el mazo
    private void shuffle (ArrayList<Carta> cartas) {

        Random aleatorio = new Random();

        for (int i = 0; i < 3; i++) {

            for (int j = 0; j < cartas.size(); j++) {

                Collections.swap(cartas, j, aleatorio.nextInt(cartas.size()));

            }

        }

    }

    private void quitarCartaPorNumero (int numero) {


        int j = cartas.size();

        for (int i = 0; i < j; i++) {

            if (cartas.get(i).getNumero() == numero) {

                cartas.remove(i);
                i--;
                j = cartas.size();

            }

        }

    }

    public Mazo (int jugadores) {

        cartas = new ArrayList<Carta>();

        for (int i = 1; i < 13; i++) {

            for (Palo palo: Palo.values()) {

                cartas.add(new Carta(palo, i));

            }

        }

        if (jugadores == 3) {

            this.quitarCartaPorNumero(2);

        }

        this.quitarCartaPorNumero(8);
        this.quitarCartaPorNumero(9);

        this.shuffle(cartas);

    }

    public Carta darCarta () {

        Carta carta = cartas.get(0);
        cartas.remove(0);

        return carta;
    }

    public boolean esVacio () {

        return cartas.isEmpty();
    }

    //Solo para tests
    /*
    public void testShowCartas () {

        for (Carta carta: cartas) {

            System.out.println(carta.toString());

        }

    }
     */



}
