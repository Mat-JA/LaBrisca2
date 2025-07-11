/* DEO GLORIA
 * CHRISTUS REX
 * AVE MARIA
 * Qui Ut Deus
 * Sancte Ioseph, dirige me
 */

//Ausqui Mateo Javier 190236

//Controlador de partida y el principal implementador de Observador sobre el modelo

package Controlador;

import ar.edu.unlu.rmimvc.cliente.IControladorRemoto;
import ar.edu.unlu.rmimvc.observer.IObservableRemoto;
import modelo.*;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ControladorP extends Controlador implements ControladorTroncal, IControladorRemoto, Serializable {

    private ControladorTroncal central;
    private int cantJugadores;
    private int numeroRondas;
    private ArrayList<ControladorJ> controladoresJ;
    private IPartida partida;
    private String estado;

    //Utilizado para eliminar datos al guardar partida

    public ControladorP () {}

    public void iniciarPartida (ControladorTroncal central, int cantJugadores, int numeroRondas) {

        //Aquí puede ser el punto de quiebre, donde se haga el set modelo,
        //Tengo que ver como hacer la carga de los clientes
        //partida = new Partida(numeroRondas, this, cantJugadores);

        this.central = central;
        this.cantJugadores = cantJugadores;
        this.numeroRondas = numeroRondas;
        controladoresJ = new ArrayList<ControladorJ>();

        try {

            this.partida.actualizarDatosEInicializar(numeroRondas, this, cantJugadores);

            for (int i = 0; i < cantJugadores; i++) {

                ControladorEJ ej = new ControladorEJ(this);

            }


        } catch (RemoteException e) {

            e.printStackTrace();

        }

    }

    public void cargarPartida (ControladorTroncal central) {

        try {

            this.central = central;
            partida.cargarPartida();

        } catch (RemoteException e) {

            e.printStackTrace();

        }

    }

    //Podría hacer el siguiente método más chico con modularización
    //Glosario de estados
    //n-m
    //n indica accion, m información adicional
    //n=1 indica cargar jugadores.
    //n=2 indica obtener información y crear VentanaJugador (cada ControladorJ se encarga)
    //n=3 indica obtener información y actualizar VentanaJugador (cada ControladorJ se encarga)
    //n=4 indica que hay ganador de partida, m es su nombre

    //No lo utiliza pero, eventualmente, puede utilizarse para responder a problemas de ControladorJ
    public void derivadoTermino(String cadena) {}

    private int extraerLargoNombre (String nomYC) {

        boolean hallado = false;
        int largo = 0;
        int contador = 0;

        while (!hallado) {

            if (nomYC.charAt(contador) == ' ') {

                hallado = true;
                largo = contador;

            } else {

                contador++;

            }

        }

        return largo;

    }

    //Glosario
    //"JLCm" Jugador Listo para Crear Controlador tipo m y nombrar.
    //"JLRn" Jugador Listo para Recibir de controlador número n.
    //"JBCnC" Jugador Baja Carta (jugador n) (String que identifica carta)
    public void recibirMsjDerivado (String msj) {

        int posJ = 0;
        String opcionS = "";

        //Añade un jugador al controlador
        if (msj.startsWith("JLC")) {

            opcionS = msj.substring(3);

            ControladorJ aux = null;

            if (opcionS.compareTo("G") == 0) {

                aux = new ControladorJG(this);

            } else if (opcionS.compareTo("T") == 0) {

                aux = new ControladorJT(this);

            }

            if (aux != null) {

                controladoresJ.add(aux);
                aux.setNroCj(controladoresJ.size()-1);

            }

        } else if (msj.startsWith("JEC")) {

            opcionS = msj.substring(3, 4);

            ControladorJ aux = null;

            if (opcionS.compareTo("G") == 0) {

                aux = new ControladorJG(this, msj.substring(4));

            } else if (opcionS.compareTo("T") == 0) {

                aux = new ControladorJT(this, msj.substring(4));

            }

            if (aux != null) {

                controladoresJ.add(aux);
                aux.setNroCj(controladoresJ.size()-1);

            }

            if (controladoresJ.size() == cantJugadores) {

                estado = "2-"+cantJugadores;
                ejecutarActualizacion();

            }

        } else if (msj.startsWith("JLR")) {

            posJ = Integer.parseInt(msj.substring(3));

            try {

                partida.aniadirJugador(controladoresJ.get(posJ).getNomJugador());

            } catch (RemoteException e) {

                e.printStackTrace();

            }

        } else if (msj.startsWith("JBC")) {

            //Obtener largo nombre jugador
            int lNom = extraerLargoNombre(msj.substring(3));
            //Cambiar por obtener nombre de jugador
            String nomJ = msj.substring(3, 3+lNom);

            try {

                partida.cambiarInfoMesaJBC(msj.substring(4+lNom), nomJ);

            } catch (RemoteException e) {

                e.printStackTrace();

            }

        } else if (msj.startsWith("JCT")) {

            posJ = Integer.parseInt(msj.substring(3));

            //Cambiar por obtener nombre de jugador
            String nomJ = controladoresJ.get(posJ).getNomJugador();

            try {

                partida.cambiarInfoMesaJCT(nomJ);

            } catch (RemoteException e) {

                e.printStackTrace();

            }

        } else if (msj.startsWith("JQG")) {

            System.out.println("Llegue a JQG");
            try {

                partida.guardarPartida();

            } catch (RemoteException e) {

                e.printStackTrace();

            }



        }

    }

    private ControladorJ determinarControladorPorNombreJ (String nomJ) {

        ControladorJ aux = null;

        boolean hallado = false;
        int i = 0;

        while (!hallado && i < controladoresJ.size()) {

            if (controladoresJ.get(i).getNomJugador().compareTo(nomJ) == 0) {

                aux = controladoresJ.get(i);
                hallado = true;

            }

            i++;
        }

        return aux;

    }

    @Override
    public <T extends IObservableRemoto> void setModeloRemoto(T t) throws RemoteException {
        this.partida = (IPartida) t;
    }

    @Override
    public void actualizar(IObservableRemoto iObservableRemoto, Object o) throws RemoteException {

        estado = partida.getEstado();
        this.ejecutarActualizacion();

    }

    private void refrescarInfoJugadores () {

        try {

            ArrayList<IJugador> ijugadores = partida.darIjugadores();

            int jAsignados = 0;

            int contadorC = 0;
            boolean noAsignado = true;

            while (jAsignados < ijugadores.size()) {

                while (contadorC < controladoresJ.size() && noAsignado) {

                    if (controladoresJ.get(contadorC).getNomJugador().compareTo(ijugadores.get(jAsignados).getNick()) == 0) {

                        controladoresJ.get(contadorC).setModeloRemoto(ijugadores.get(jAsignados));
                        noAsignado = false;

                    }

                    contadorC++;

                }

                noAsignado = true;
                contadorC = 0;
                jAsignados++;

            }

        } catch (RemoteException e) {

            e.printStackTrace();

        }

    }

    private void ejecutarActualizacion () {

        try {

            if (estado.substring(0,2).compareTo("2-") == 0) {

                int cantJ = Integer.parseInt(estado.substring(2));

                refrescarInfoJugadores();

                ArrayList<ArrayList<String>> pasajePartida = partida.darInfoMesa();

                ControladorJ aux = null;

                for (int j = 0; j < cantJ; j++) {

                    ArrayList<ArrayList<String>> muestreo = new ArrayList<ArrayList<String>>();

                    muestreo.add(pasajePartida.get(0));
                    muestreo.add(pasajePartida.get(j+1));

                    aux = determinarControladorPorNombreJ(pasajePartida.get(pasajePartida.size()-1).get(j));

                    aux.crearVentanaJugador(muestreo);

                }

            } else if (estado.substring(0,2).compareTo("3-") == 0) {

                int cantJ = Integer.parseInt(estado.substring(2));

                refrescarInfoJugadores();

                ArrayList<ArrayList<String>> pasajePartida = partida.darInfoMesa();

                ControladorJ aux = null;

                for (int k = 0; k < cantJ; k++) {

                    ArrayList<ArrayList<String>> muestreo = new ArrayList<ArrayList<String>>();

                    muestreo.add(pasajePartida.get(0));
                    muestreo.add(pasajePartida.get(k+1));

                    aux = determinarControladorPorNombreJ(pasajePartida.get(pasajePartida.size()-1).get(k));

                    aux.actualizarVentanaJugador(muestreo);

                }

            } else if (estado.substring(0,2).compareTo("4-") == 0) {

                /*

                for (ControladorJ controlador: controladoresJ) {

                    System.out.println("Estoy en la iteracion de cierre");

                    controlador.cerrarVentanaJ();

                }
                 */

                central.derivadoTermino(estado.substring(2));

            } else if (estado.substring(0,2).compareTo("5-") == 0) {

                int cantJ = Integer.parseInt(estado.substring(2));

                controladoresJ = new ArrayList<>();

                cantJugadores = cantJ;
                ArrayList<String> nombresJ = partida.getNomJ();

                for (int i = 0; i < cantJugadores; i++) {

                    ControladorEJ ej = new ControladorEJ(this, nombresJ.get(i));

                }

            } else if (estado.compareTo("6-") == 0) {

                anular();

                central.derivadoTermino("PartidaGuardada");

            }

        } catch (RemoteException e) {

            e.printStackTrace();

        }

    }

    private void anular () {

        estado = "";
        cantJugadores = 0;

    }

}
