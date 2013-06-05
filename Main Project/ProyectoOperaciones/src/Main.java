
import Control.IteradorEstadisticas;

public class Main {
 
    public static void main(String[] args) {
        
        IteradorEstadisticas iterador=new IteradorEstadisticas();
        String[] estadisticas={"sum( i,  1, n, 1, sum(j, 1, m, 1,  sum(k, 1, n{i, j}, 1, X{i,j,k} ) ) )", 
                                " mult( i, 1, n, 1, sum(j, 1, m, 1,  mult(k, 1, n{i, j}, 1, X{i,j,k} ) ) ) ",
                                "sum(i, 1, 5, 1, i)"};
        if( iterador.ingresoDatos(new int[][]{{1,2}, {2,5}}, new double[]{5,5}, 
                new double[]{5,5}, new double[][][]{{{4,8,8,4},{4,8,8,4}},{{4,8,8,4},{4,8,8,4}}}, 5.5, 0.05, estadisticas, 3) == null ){
            
            System.out.println(iterador.getStrError());
        }
        
    }
    
}
