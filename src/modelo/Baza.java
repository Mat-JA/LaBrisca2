/* DEO GLORIA
 * CHRISTUS REX
 * AVE MARIA
 * Qui Ut Deus
 * Sancte Ioseph, dirige me
 */

package modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class Baza implements Serializable {

    private ArrayList<Carta> cartas;

    public Baza () {

        cartas = new ArrayList<Carta>();

    }

    public int puntuacion() {

        int puntos = 0;

        for (Carta carta: cartas) {

            puntos += carta.valorCarta();

        }

        return puntos;
    }

    public int[] entregarBaza () {

        int[] datosBaza = new int[2];

        datosBaza[0] = puntuacion();
        datosBaza[1] = cartas.size();

        return datosBaza;

    }

    private int posMayorCarta (ArrayList<Carta> cs) {

        int pos = -1;

        if (cs.size() == 1) {

            pos = 0;

        } else {

            int valorMayor = -1;
            int numMayor = -1;

            for (int j = 0; j < cartas.size(); j++) {

                if (cartas.get(j).valorCarta() > valorMayor) {

                    numMayor = cartas.get(j).getNumero();
                    valorMayor = cartas.get(j).valorCarta();
                    pos = j;

                } else if (cartas.get(j).valorCarta() == valorMayor) {

                    if (cartas.get(j).getNumero() > numMayor) {

                        numMayor = cartas.get(j).getNumero();
                        valorMayor = cartas.get(j).valorCarta();
                        pos = j;

                    }

                }

            }

        }

        return pos;

    }

    public int cartaGanadora(Palo pTriunfo) {

        int posCG = -1;

        ArrayList<Carta> cTriunfo = new ArrayList<>();

        for (Carta c: cartas) {

            if (c.getPaloStr().equals(pTriunfo.name())) {

                cTriunfo.add(c);

            }

        }

        if (cTriunfo.isEmpty()) { //No hay cartas del palo de triunfo

            posCG = posMayorCarta(cartas);

        } else {

            int posMTriunfo = posMayorCarta(cTriunfo);

            Carta cg = cTriunfo.get(posMTriunfo);

            for (int i = 0; i < cartas.size(); i++) {

                if (cg.toString().equals(cartas.get(i).toString())) {
                    posCG = i;

                }

            }

        }

        return posCG;
    }

    public void recibirCarta (Carta carta) {

        cartas.add(carta);

    }

    //Utilizado para notificar cartas y que lleguen a las ventanas mediante Observer
    //Básicamente, para MVC
    public ArrayList<String> verCartas () {

        ArrayList<String> descCartas = new ArrayList<String>();

        if (cartas.isEmpty()) {

            descCartas.add("BazaVacia");

        } else {

            for (Carta carta: cartas) {

                descCartas.add(carta.toString());

            }

        }

        return descCartas;

    }

    public boolean isEmpty () {

        return cartas.isEmpty();
    }

    public int size () {

        return cartas.size();
    }

}
