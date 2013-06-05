/*
 * Sinh.java
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package Entidad.Funciones;

import Entidad.Funcion;

/**
 * 
 * @version 0.1 <br>
 */
public class Sinh implements Funcion
{

    /*
     * (non-Javadoc)
     * 
     * @see edb.functions.Funcion#evaluate(double)
     */
    public double evaluate(double value)
    {
        return ((Math.exp(value)-Math.exp(-1*value))/2);
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
            double rr = ((Math.exp(value)-Math.exp(-1*value))/2);
        } catch (Exception e)
        {
            ret = false;
        }

        return ret;
    }
}
