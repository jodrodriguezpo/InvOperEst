/*
 * 
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package Entidad.Funciones;

import Entidad.Funcion;

/**
 * @author GATOLOCOTM
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class Ln implements Funcion {

	/*
	 * (non-Javadoc)
	 * 
	 * @see edb.evaluate.Funcion#evaluate(double)
	 */
	public double evaluate(double value) {
		// TODO Auto-generated method stub
		return Math.log(value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edb.evaluate.Funcion#isValue(double)
	 */
	public boolean isValue(double value) {
		// TODO Auto-generated method stub
		return (value > 0);
	}

}
