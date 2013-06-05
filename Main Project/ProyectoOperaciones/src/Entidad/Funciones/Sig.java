package Entidad.Funciones;

import Entidad.Funcion;

/*
 * Sig.java
 *
 */

/**
 * Funci�n sigmoide  1/(1+e^-aX)
 * 
 */


public class Sig implements Funcion
{
    /*
     * (non-Javadoc)
     * 
     * @see edb.functions.Funcion#evaluate(double)
     */
    public double evaluate(double value)
    { 
        return (1  /  (  1 + (Math.pow(Math.E, (-1.0)* value ))  )    ) ;
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
            double rr = (1  /  (  1 + (Math.pow(Math.E, (-1.0)* value ))  )    ) ;
        } catch (Exception e)
        {
            ret = false;
        }

        return ret;
    }
   
    
}
