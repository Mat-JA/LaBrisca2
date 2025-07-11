/* DEO GLORIA
 * CHRISTUS REX
 * AVE MARIA
 * Qui Ut Deus
 * Sancte Ioseph, dirige me
 */

//Ausqui Mateo Javier 190236

package modelo;

import Serializacion.GuardadoModelo.GuardadoPartida;
import Serializacion.TablaGanadores;
import ar.edu.unlu.rmimvc.observer.IObservadorRemoto;
import ar.edu.unlu.rmimvc.observer.ObservableRemoto;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;

//Esta es una partida, básicamente
//Organiza el inicio, la continuación (descarga de info de ronda, nueva ronda si necesario)
//Y la finalización de una partida

//Esta clase no se implementa, sino que lo hacen sus clases hijas (partidas de dos, tres y cuatro
// jugadores).

public class Partida extends ObservableRemoto implements IPartida, Serializable {

    protected ArrayList<Jugador> jugadores;
    protected int rondasAGanar;
    protected Ronda rActual;

    private String estado;

    private int numJugadores;

    private static final long serialVersionUID = 2L;

    public Partida () {

        jugadores = new ArrayList<Jugador>();

    }

    private ArrayList<String> msjManoVacia () {

        ArrayList<String> mensaje = new ArrayList<String>();

        mensaje.add("ManoVacia");

        return mensaje;
    }


    private boolean manosVacias () {

        int totalVacias = 0;

        for (Jugador jugador: jugadores) {

            if (jugador.manoVacia()) {

                totalVacias++;

            }

        }

        return (totalVacias == jugadores.size());
    }

    //Define quien es mano en la siguiente ronda
    private int calcularMano () {

        int nPM = -1;

        int pMActual = rActual.getPosMano();

        if (pMActual == jugadores.size() - 1) {

            nPM = 0;

        } else {

            nPM = pMActual + 1;

        }

        return nPM;
    }

    //Recibe un nuevo Jugador y lo añade a la lista de jugadores
    @Override
    public void aniadirJugador(String nombre) throws RemoteException {

        Jugador jugador = new Jugador(nombre);
        jugadores.add(jugador);

        if (jugadores.size() == numJugadores) {

            //Inicia la primer ronda de la Partida
            crearRonda(0);

        }

    }

    //posMano es la posición de el jugador que es mano en la ronda a crear
    private void crearRonda(int posMano) {

        rActual = new Ronda(jugadores, posMano);

        try {
            estado = "2-"+numJugadores;
            notificarObservadores();

        } catch (RemoteException e) {

            e.printStackTrace();

        }

    }

    private void actualizacionMesaJBCFinalizada () {

        try {

            estado = "3-"+numJugadores;
            notificarObservadores();

        } catch (RemoteException e) {

            e.printStackTrace();

        }

    }

    public ArrayList<ArrayList<String>> darInfoMesa() throws RemoteException {

        ArrayList<ArrayList<String>> cartasMesa = new ArrayList<ArrayList<String>>();

        if (rActual == null) {

            cartasMesa = null;

        } else {

            cartasMesa.add(rActual.verCartasBazaYT());

            for (int i = 0; i < jugadores.size(); i++) {

                if (!jugadores.get(i).manoVacia()) {

                    cartasMesa.add(jugadores.get(i).verCartasMano());

                } else {

                    cartasMesa.add(msjManoVacia());

                }

            }

            ArrayList<String> nomJs = new ArrayList<>();

            for (int j = 0; j < jugadores.size(); j++) {

                nomJs.add(jugadores.get(j).getNick());

            }

            cartasMesa.add(nomJs);

        }

        return cartasMesa;

    }

    private int obtenerPosJ (String nomJ) {

        boolean hallado = false;
        int posJ = 0;

        while (!hallado) {

            try {

                if (jugadores.get(posJ).getNick().equals(nomJ)) {

                    hallado = true;

                }

            } catch (RemoteException e) {

                e.printStackTrace();

            }

            posJ++;
        }

        return posJ-1;
    }

    public void cambiarInfoMesaJBC(String actualizacion, String nomJ) throws RemoteException {

        int posJ = obtenerPosJ(nomJ);

        rActual.actualizarJBC(actualizacion, posJ);
        if (!rActual.bazaLLena()) {

            actualizacionMesaJBCFinalizada();

        } else { //Baza llena con igual cantidad de cartas que jugadores

            if (!rActual.mazoVacio()) {

                rActual.siguienteBaza();
                actualizacionMesaJBCFinalizada();

            } else {

                if (!manosVacias()) { //Mazo vacío, pero jugadores con cartas en mano

                    rActual.siguienteBazaSinReparticion();
                    actualizacionMesaJBCFinalizada();

                } else { //termino ronda

                    actualizacionMesaJBCFinalizada();
                    terminarRondaActual();

                }

            }

        }

    }

    public void cambiarInfoMesaJCT(String nomJ) throws RemoteException {

        int posJ = obtenerPosJ(nomJ);

        rActual.actualizarJCT(posJ);

        actualizacionMesaJCTFinalizada();

    }

    private void actualizacionMesaJCTFinalizada () {

        try {

            estado = "3-"+numJugadores;
            notificarObservadores();

        } catch (RemoteException e) {

            e.printStackTrace();

        }

    }

    private void terminarRondaActual () {

        int posG = posicionJGanador();

        try {

            System.out.println(jugadores.get(posG).getNick());

        } catch (RemoteException e) {

            e.printStackTrace();

        }

        jugadores.get(posG).ganarRonda();

        try {

            if (jugadores.get(posG).getRondasGanadas() == rondasAGanar) {

                terminarPartida(jugadores.get(posG).getNick());

            } else {

                crearRonda(calcularMano());

            }

        } catch (RemoteException e) {

            e.printStackTrace();

        }
    }

    private void terminarPartida(String nickGanador) {

        //Actualizar Tabla Ganadores
        TablaGanadores tg = new TablaGanadores();
        tg.actualizarTabla(nickGanador);

        try {

            estado = "4-"+nickGanador;
            notificarObservadores();

        } catch (RemoteException e) {

            e.printStackTrace();

        }

    }

    private int posicionJGanador () {

        int posG = 0;

        try {

            for (int i = 0; i < jugadores.size(); i++) {

                if (jugadores.get(i).getPuntosRonda() > jugadores.get(posG).getPuntosRonda()) {

                    posG = i;

                } else if (jugadores.get(i).getPuntosRonda() == jugadores.get(posG).getPuntosRonda()) {

                    if (jugadores.get(i).getCantCartasGanadasRonda() >
                            jugadores.get(posG).getCantCartasGanadasRonda()) {

                        posG = i;

                    }

                }

            }

        } catch (RemoteException e) {

            e.printStackTrace();

        }

        return posG;

    }

    public ArrayList<IJugador> darIjugadores () throws RemoteException {

        ArrayList<IJugador> iJugadores = new ArrayList<>();

        for (Jugador jugador: jugadores) {

            iJugadores.add((IJugador) jugador);

        }

        return iJugadores;
    }

    public void actualizarDatosEInicializar (int rondasAGanar, IObservadorRemoto obs, int nj) throws  RemoteException {

        this.rondasAGanar = rondasAGanar;

        numJugadores = nj;

        removerObservador(obs);

        try {

            agregarObservador(obs);

        } catch (RemoteException e) {

            e.printStackTrace();

        }

        estado = "";

        //cargarJugadores();

    }

    @Override
    public String getEstado() throws RemoteException {
        return estado;
    }

    public void guardarPartida () throws RemoteException {

        System.out.println("Llegue a guardar partida");

        GuardadoPartida gp = new GuardadoPartida();
        gp.guardarPartida(this);

        estado = "6-";
        try {

            notificarObservadores();

        } catch (RemoteException e) {

            e.printStackTrace();

        }

    }

    public void cargarPartida () throws RemoteException {

        GuardadoPartida gp = new GuardadoPartida();
        Partida partidaGuardada = (Partida) gp.cargarPartida();

        //Igualar datos de esta partida a los de partidaGuardada;
        igualar(partidaGuardada);

        estado = "5-"+numJugadores;

        try {

            notificarObservadores();

        } catch (RemoteException e) {

            e.printStackTrace();

        }


    }

    private void igualar (Partida pACopiar) {

        numJugadores = pACopiar.getNumJugadores();
        rondasAGanar = pACopiar.getRondasAGanar();
        rActual = pACopiar.getRActual();
        jugadores = pACopiar.getJugadores();

    }

    public int getNumJugadores() {
        return numJugadores;
    }

    public int getRondasAGanar() {
        return rondasAGanar;
    }

    public Ronda getRActual() {
        return rActual;
    }

    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    public ArrayList<String> getNomJ () throws RemoteException {

        ArrayList<String> nomsJ = new ArrayList<>();

        for (int i = 0; i < numJugadores; i++) {

            nomsJ.add(jugadores.get(i).getNick());

        }

        return nomsJ;
    }


}
