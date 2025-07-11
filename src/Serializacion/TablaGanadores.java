/* DEO GLORIA
 * CHRISTUS REX
 * AVE MARIA
 * Qui Ut Deus
 * Sancte Ioseph, dirige me
 */

//Ausqui Mateo Javier 190236

//Esta clase se utiliza para interactuar con el serializador y guardar a los ganadores historicos

package Serializacion;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class TablaGanadores implements Serializable {

    private ArrayList<RenglonTG> tabla;
    private Serializador serializador;

    public TablaGanadores () {

        tabla = new ArrayList<RenglonTG>();
        serializador = new Serializador("tablaGanadores.dat");

    }

    //Está actualizando mal al ganador si el ganador gano antes (revisar)



    //Asume que el archivo no está vacío
    private void recuperarTabla () {

        Object[] guardados = serializador.readObjects();

        RenglonTG aux;

        for (int i = 0; i < guardados.length; i++) {

            aux = (RenglonTG) guardados[i];

            tabla.add(aux);

        }

    }

    //Asume tabla ya cargada
    private int estaEnTablaEnPosicion (String buscado) {

        int estaEnPosicion = -1;

        int i = 0;

        while ((i < tabla.size()) && (estaEnPosicion == -1)) {

            if (buscado.compareTo(tabla.get(i).getNombre()) == 0) {

                estaEnPosicion = i;

            }

            i++;
        }

        return estaEnPosicion;
    }

    private void reordenar() {

        RenglonTG aux = new RenglonTG("mock");

        for(int i = 0; i < tabla.size(); i++) {

            for (int j = 1; j < tabla.size() - i; j++) {

                if (tabla.get(j-1).getGanadas() > tabla.get(j).getGanadas()) {

                    aux.igualar(tabla.get(j));
                    tabla.get(j).igualar(tabla.get(j-1));
                    tabla.get(j-1).igualar(aux);

                }

            }

        }

    }

    public boolean esVacia() {

        return serializador.readFirstObject() == null;
    }

    //Añade un punto de ganado a quien corresponda el nickname
    //Para ello primero carga la tabla, o la crea
    public void actualizarTabla (String nombreG) {

        if(esVacia()) {

            RenglonTG entrante = new RenglonTG(nombreG);
            serializador.writeOneObject(entrante);

        } else {

            recuperarTabla();

            int posG = estaEnTablaEnPosicion(nombreG);

            if (posG != -1) { //Está en archivo

                tabla.get(posG).ganar();
                reordenar();

            } else { //No está en archivo

                tabla.add(new RenglonTG(nombreG));

            }

            serializador.writeOneObject(tabla.get(0));

            for (int i = 1; i < tabla.size(); i++) {

                serializador.addOneObject(tabla.get(i));

            }

        }

    }

    public ArrayList<String> obtenerSTabla () {

        ArrayList<String> sTabla = new ArrayList<String>();

        if(!esVacia()) {

            Object[] guardados = serializador.readObjects();

            RenglonTG aux;

            for (int i = 0; i < guardados.length; i++) {

                aux = (RenglonTG) guardados[i];

                sTabla.add(aux.toString());

            }

        } else {

            sTabla.add("sinGanadores");

        }

        return sTabla;

    }


}
