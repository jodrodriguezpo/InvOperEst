package Entidad.Funciones;

import Entidad.Funcion;

/**
 * 
 * @version 0.1 <br>
 */
public class Cos implements Funcion
{

    /*
     * (non-Javadoc)
     * 
     * @see edb.functions.Funcion#evaluate(double)
     */
    public double evaluate(double value)
    {
        return Math.cos(value);
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
          double rr = Math.cos(value);
        } catch (Exception e)
        {
            ret = false;
        }

        return ret;
    }
}
