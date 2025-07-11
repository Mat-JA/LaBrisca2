/* DEO GLORIA
 * CHRISTUS REX
 * AVE MARIA
 * Qui Ut Deus
 * Sancte Ioseph, dirige me
 */

package modelo;

import ar.edu.unlu.rmimvc.observer.ObservableRemoto;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class Jugador extends ObservableRemoto implements IJugador, Serializable {

    private String nick;
    private ArrayList<Carta> manoJugador;
    private int rondasGanadas;
    private int puntosRonda;
    private int cantCartasGanadasRonda;
    private boolean ganoBPrevia; //Utilizada para intercambio de triunfo
    private boolean esTurno;
    private boolean cambiazoDisponible;

    public Jugador (String nombre) {

        nick = nombre;
        manoJugador = new ArrayList<Carta>();
        rondasGanadas = 0;
        puntosRonda = 0;
        cantCartasGanadasRonda = 0;
        esTurno = false;
        ganoBPrevia = false;
        cambiazoDisponible = false;

    }

    private int getPosCartaPorNom (String nombreCarta) {

        int posCarta = -1;

        int i = 0;

        while ((i < manoJugador.size()) && (posCarta == -1)) {

            System.out.println(manoJugador.get(i).toString());
            System.out.println(nombreCarta);

            if (manoJugador.get(i).toString().compareTo(nombreCarta) == 0) {

                posCarta = i;

            }

            i++;
        }

        return posCarta;
    }

    @Override
    public String getNick() throws RemoteException {

        return nick;
    }

    @Override
    public int getRondasGanadas() throws RemoteException {

        return rondasGanadas;
    }

    @Override
    public int getCantCartasGanadasRonda() throws RemoteException {

        return cantCartasGanadasRonda;
    }

    @Override
    public int getPuntosRonda() throws RemoteException {

        return puntosRonda;
    }

    @Override
    public boolean esSuTurno() throws RemoteException {

        return esTurno;
    }

    public void activarTurno () {

        esTurno = true;
    }

    public void desactivarTurno () {

        esTurno = false;
    }

    //Recibe una carta en su mano
    public void darCarta (Carta carta) {

        manoJugador.add(carta);

    }

    //Baja a Baza la carta en posición c en su mano
    public void bajarCarta (Baza baza, String nomCarta) {

        int posCarta = getPosCartaPorNom(nomCarta);
        
        baza.recibirCarta(manoJugador.get(posCarta));
        manoJugador.remove(posCarta);

    }

    //Devuelve palo y numero de cartas en mano
    public ArrayList<String> verCartasMano () {

        ArrayList<String> cartasMano = new ArrayList<String>();

        if (manoJugador.isEmpty()) {

            cartasMano = null;

        } else {

            for (Carta carta: manoJugador) {

                cartasMano.add(carta.toString());

            }

        }

        return cartasMano;

    }

    public void ganarBaza (int[] resBaza) {

        puntosRonda += resBaza[0];
        cantCartasGanadasRonda += resBaza[1];
        ganoBPrevia = true;

    }

    @Override
    public boolean ganoBazaPrevia() {

        return ganoBPrevia;
    }

    //Se le aplica a todos los jugadores que no ganen baza tras terminar una baza
    public void limpiarEBP () {

        ganoBPrevia = false;
    }

    public void ganarRonda() {

        rondasGanadas++;

    }

    //Vuelve a 0
    public void resetearEstado () {

        puntosRonda = 0;
        cantCartasGanadasRonda = 0;

    }

    public boolean manoVacia () {

        return manoJugador.isEmpty();
    }

    public void habilitarCambiazoSiGanadorBPrev() {

        System.out.println("Llegue a habilitar cambiazo sin baza previa");

        if (ganoBPrevia) {

            System.out.println("Llegue a habilitar cambiazo con baza previa");

            cambiazoDisponible = true;

        }

    }

    public boolean cambiazoEsPosible () throws RemoteException {

        return cambiazoDisponible;
    }

    //Devuelve 0 si tiene pinta baja (2 de triunfo), 1 si tiene pinta alta (7 de triunfo)
    public int tieneCartaDePinta (String paloTriunfo) {

        int tienePinta = -1;

        Carta actual;

        for (int i = 0;  i < manoJugador.size(); i++) {

            actual = manoJugador.get(i);

            if (actual.getPaloStr().equals(paloTriunfo)) {

                if (actual.getNumero() == 2) {

                    tienePinta = 0;

                } else if (actual.getNumero() == 7) {

                    tienePinta = 1;

                }

            }

        }

        return tienePinta;
    }

    //Recibe una carta y la intercambia por la carta con numero y palo señalado.
    //Devuelve la carta pedida de número y palo. Se usa para hacer el cambiazo de triunfo
    public Carta intercambiarCarta (Carta recibe, int numeroDar, String paloDar) {

        Carta da = null;

        Carta actual;

        for (int i = 0; i < manoJugador.size(); i++) {

            actual = manoJugador.get(i);

            if (actual.getNumero()== numeroDar && actual.getPaloStr().equals(paloDar) ) {

                manoJugador.remove(i);
                manoJugador.add(recibe);
                da = actual;

            }

        }

        return da;
    }


}
