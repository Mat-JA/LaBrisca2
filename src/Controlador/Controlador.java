/* DEO GLORIA
 * CHRISTUS REX
 * AVE MARIA
 * Qui Ut Deus
 * Sancte Ioseph, dirige me
 */

//Ausqui Mateo Javier 190236

//Mètodos comunes a los controladores

package Controlador;

import Vista.Ventana;

import java.util.ArrayList;

public abstract class Controlador {

    protected Ventana v;

    public Controlador () {



    }

    //Asigna una ventana al controlador
    public void setVentana(Ventana v) {

        this.v = v;

    }

    //Utilizado para procesar una entrada de la ventana
    public void procesarEntrada(String cadena) {}

    //Utilizado para validar una entrada de la ventana
    //La ventana provee las opciones correctas
    public boolean validarEntrada(String entrada, ArrayList<String> entradasValidas) {

        int i = 0;

        boolean entradaEsValida = false;

        while ((i < entradasValidas.size()) && (!entradaEsValida)) {

            if (entradasValidas.get(i).compareTo(entrada) == 0) {

                entradaEsValida = true;

            }

            i++;
        }

        return entradaEsValida;
    }

    //Utilizado por ventanas para indicar que termino su proceso
    //Recibe String que luego es procesado según la implementación del controlador
    public void ventanaTermino(String cadena) {}

    public boolean tieneVentana () {

        return v != null;
    }
    
}
