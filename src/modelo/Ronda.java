/* DEO GLORIA
 * CHRISTUS REX
 * AVE MARIA
 * Qui Ut Deus
 * Sancte Ioseph, dirige me
 */

package modelo;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class Ronda implements Serializable {

    private ArrayList<Jugador> jugadores;
    private Mazo mazo;
    private Baza bActual;
    private Carta triunfo;
    private int posMano;
    private int turnoPos;
    private int posGanadorBaza;


    public Ronda (ArrayList<Jugador> jugadores, int posMano) {

        this.jugadores = jugadores;
        for (int i = 0; i < jugadores.size(); i++) {

            jugadores.get(i).resetearEstado();

        }
        this.mazo = new Mazo(jugadores.size());
        bActual = new Baza();
        triunfo = null;
        this.posMano = posMano;
        turnoPos = posMano;
        posGanadorBaza = -1;
        primeraRepartida();

    }

    private void primeraRepartida() {

        //Primeras tres repartidas iniciales
        for (int i = 0; i < 3; i++) {

            repartirCartas();

        }

        definirTriunfo();
        jugadores.get(0).activarTurno();

    }

    private void repartirCartas() {

        for (Jugador jugador: jugadores) {

            if (!mazo.esVacio()) {

                jugador.darCarta(mazo.darCarta());

            } else { //Final de última ronda de repartición

                jugador.darCarta(triunfo);

            }

        }

    }

    private void definirTriunfo() {

        triunfo = mazo.darCarta();

    }

    //Establece que jugador va primero en una baza no inicial
    //Básicamente, el que se llevó la baza anterior
    private void definirTurnoInicial () {

        for (int i = 0; i < jugadores.size(); i++) {

            if (jugadores.get(i).ganoBazaPrevia()) {

                jugadores.get(i).activarTurno();
                turnoPos = i;

            } else {

                jugadores.get(i).desactivarTurno();

            }

        }

    }

    private void actualizarCambiazo (Jugador jActual) {

        //Si jugador tiene cartas para cambiazo (que pinte)
        int tienePinta = jActual.tieneCartaDePinta(triunfo.getPaloStr());

        if (tienePinta == 0) { //Pinta baja (tiene 2 de triunfo)

            if (triunfo.getNumero() >= 3 && triunfo.getNumero() <= 7) {

                jActual.habilitarCambiazoSiGanadorBPrev(); //Si el jugadpr gano la baza previa, se habilita el cambiazo en su turno

            }

        } else if (tienePinta == 1) { //Pinta alta (tiene 7 de triunfo)

            if ( (triunfo.getNumero() >= 10 && triunfo.getNumero() <= 12) ||
                    (triunfo.getNumero() == 1) || (triunfo.getNumero() == 3) ) {

                jActual.habilitarCambiazoSiGanadorBPrev();

            }

        }

    }

    //Actualiza turno pos e indica a los jugadores de quién es el turno
    private void actualizarTurno () {

        for (Jugador jugador: jugadores) {

            jugador.desactivarTurno();

        }

        if (turnoPos == jugadores.size() - 1) {

            turnoPos = 0;

        } else {

            turnoPos += 1;

        }

        jugadores.get(turnoPos).activarTurno();

    }

    public boolean bazaLLena () {

        return bActual.size() == jugadores.size();
    }

    public boolean mazoVacio () {

        return mazo.esVacio();
    }

    public int getPosMano () {

        return posMano;
    }

    public void actualizarJBC (String nomCarta, int posJ) {

        if (!jugadores.get(posJ).manoVacia()) {

            jugadores.get(posJ).bajarCarta(bActual, nomCarta);

        }

        if (bazaLLena()) {

            contabilizarBaza();

        }
        actualizarTurno();

    }

    public void actualizarJCT (int posJ) {

        int tienePinta = jugadores.get(posJ).tieneCartaDePinta(triunfo.getPaloStr());

        if (tienePinta == 0) {

            triunfo = jugadores.get(posJ).intercambiarCarta(triunfo, 2, triunfo.getPaloStr());

        } else if (tienePinta == 1) {

            triunfo = jugadores.get(posJ).intercambiarCarta(triunfo, 7, triunfo.getPaloStr());

        }

    }

    private int obtenerPosicionJCartaGanadora (int posCG) {

        int posInicial = -1;
        int posJCG = -1;

        //Se detecta posición inicial de bajada en la Baza actual
        if (posGanadorBaza < 0) { //Primer ronda

            posInicial = posMano;

        } else {

            posInicial = posGanadorBaza;

        }

        try {

            System.out.println(jugadores.get(0).getNick());
            System.out.println(jugadores.get(1).getNick());

        } catch (RemoteException e) {

            e.printStackTrace();

        }

        if (posCG == 0) {

            posJCG = posInicial;

        } else if (posInicial + posCG < jugadores.size()) {

            posJCG = posInicial + posCG;

        } else if (posInicial + posCG == jugadores.size()) {

            posJCG = 0;

        } else {

            posJCG = posCG - posInicial;

        }

        return posJCG;
    }

    //Vacia Baza, dandole los puntos y cartas al jugador ganador de baza
    //El jugador ganador de baza es marcado como ganador de baza
    //El estado de los demas en ese respecto se limpia
    public void contabilizarBaza () {

        for (Jugador jugador: jugadores) {

            jugador.limpiarEBP();

        }

        int pcg = bActual.cartaGanadora(triunfo.getPalo());

        int poscjCG = obtenerPosicionJCartaGanadora(pcg);

        int[] resBaza = bActual.entregarBaza();

        jugadores.get(poscjCG).ganarBaza(resBaza);
        actualizarCambiazo(jugadores.get(poscjCG));
        posGanadorBaza = poscjCG;

    }

    public void siguienteBaza() {

        bActual = new Baza();
        definirTurnoInicial();
        repartirCartas();

    }

    public void siguienteBazaSinReparticion () {

        bActual = new Baza();
        definirTurnoInicial();

    }

    //Retorna un array de string describiendo cartas.
    //El primero es triunfo, el resto es la baza
    public ArrayList<String> verCartasBazaYT() {

        ArrayList<String> cbyt = bActual.verCartas();

        cbyt.add(0, triunfo.toString());

        return cbyt;

    }

}
