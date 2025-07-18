/* DEO GLORIA
 * CHRISTUS REX
 * AVE MARIA
 * Qui Ut Deus
 * Sancte Ioseph, dirige me
 */

//Ausqui Mateo Javier 190236

//Clase provista por los profesores para proveer uso de serializacion

package Serializacion;

import java.io.*;
import java.util.ArrayList;

public class Serializador {

    private String fileName;

    public Serializador (String fileName) {

        super();
        this.fileName = fileName;

    }

    public boolean writeOneObject(Object obj) {

        boolean respuesta = false;

        try {

            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName));
            oos.writeObject(obj);
            oos.close();
            respuesta = true;

        } catch (FileNotFoundException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }

        return respuesta;


    }

    public boolean addOneObject (Object obj) {

        boolean respuesta = false;

        try {

            AddableObjectOutputStream oos = new AddableObjectOutputStream (new FileOutputStream(fileName, true));
            oos.writeObject(obj);
            oos.close();
            respuesta = true;

        } catch (FileNotFoundException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }

        return respuesta;

    }

    public Object readFirstObject() {

        Object respuesta = null;

        try {

            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName));
            respuesta = ois.readObject();
            ois.close();

        } catch (FileNotFoundException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        } catch (ClassNotFoundException e) {

            e.printStackTrace();

        }

        return respuesta;

    }

    public Object[] readObjects() {

        Object[] respuesta;
        ArrayList<Object> listOfObject = new ArrayList<Object>();

        try {

            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName));
            Object r = ois.readObject();

            while (r != null) {

                listOfObject.add(r);
                r = ois.readObject();

            }

            ois.close();

        } catch (EOFException e) {

            System.out.println("Lectura completada");

        } catch (FileNotFoundException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        } catch (Exception e) {

            e.printStackTrace();

        }

        if (!listOfObject.isEmpty()) {

            respuesta = new Object[listOfObject.size()];
            int count = 0;
            for (Object o: listOfObject) {

                respuesta[count++] = o;

            }

        } else {

            respuesta = null;

        }

        return respuesta;

    }

}
