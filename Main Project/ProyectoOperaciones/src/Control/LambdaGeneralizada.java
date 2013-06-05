/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

/**
 *
 * @author User
 */
public class LambdaGeneralizada {
    
    public static double funcionPercentil( double u, double lambda1, double lambda2,
            double lambda3, double lambda4 ) {
        return lambda1 + ( Math.pow(u, lambda3) - Math.pow(1-u, lambda4) )/( lambda2 );        
    }
    
    public static double funcionDensidad( double u, double lambda1, double lambda2,
            double lambda3, double lambda4 ) {
        return lambda2/( lambda3 * Math.pow(u, lambda3-1) + 
                lambda4*Math.pow(1-u, lambda4-1) );
    }
    
}
