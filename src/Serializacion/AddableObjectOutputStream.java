/* DEO GLORIA
 * CHRISTUS REX
 * AVE MARIA
 * Qui Ut Deus
 * Sancte Ioseph, dirige me
 */

//Ausqui Mateo Javier 190236

//Clase provista por los profesores para proveer uso de serializacion

package Serializacion;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class AddableObjectOutputStream extends ObjectOutputStream {

    public AddableObjectOutputStream (OutputStream out) throws IOException {

        super(out);

    }

    protected AddableObjectOutputStream() throws IOException, SecurityException {
        super();
    }

    protected void writeStreamHeader() throws IOException {}

}
