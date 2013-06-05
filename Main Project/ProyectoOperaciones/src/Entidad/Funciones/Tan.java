package Entidad.Funciones;

import Entidad.Funcion;

/**
 * @author Edwin Fabian Perez Poveda - <b>efperezp@unal.edu.co </b> <br>
 * 
 * @version 0.1 <br>
 */
public class Tan implements Funcion
{

    /*
     * (non-Javadoc)
     * 
     * @see edb.functions.Funcion#evaluate(double)
     */
    public double evaluate(double value)
    {
        return Math.tan(value);
    }

    /*
     * (non-Javadoc)
     * 
     * @see edb.functions.Funcion#isValue(double)
     */
    public boolean isValue(double value)
    {
        boolean ret = true;

        try
        {
            double rr = Math.tan(value);
        } catch (Exception e)
        {
            ret = false;
        }

        return ret;
    }
}
