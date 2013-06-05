
package Control;

import Entidad.DatoCelda;
import Entidad.DatosEstadisticaSecuencias;
import Entidad.MatrizDatos;
import java.util.ArrayList;

public class CalculadorSecuencias {
    
    public DatosEstadisticaSecuencias calcularSecuencias( double matriz[][][], MatrizDatos matrizDatos ) {
        final int N=matriz.length, M=matriz[0].length;
        int numSecuencia[][][]=new int[N][M][];
        int matrizRij_[][]=new int[N][M];
        int vectorRi__[]=new int[N];
        int vectorR_j_[]=new int[M];
        int R___=0;
        for (int i=0; i<N; i++){
            for (int j = 0; j < M; j++) {
                numSecuencia[i][j]=new int[matriz[i][j].length];
            }
        }
        ArrayList<Integer> bloquesL[]=new ArrayList[N];
        for( int i=0; i<N; i++ ) {
            ArrayList<Integer> longitudesL=new ArrayList<>();
            int posicionTrat=-1, longitud=0, posicionAP=1;
            for( DatoCelda dato : matrizDatos.getDatosBloqueOrdenados()[i] ) {
                if( posicionTrat<0 ) {
                    posicionTrat=dato.getJ();
                    longitud=1;
                } else if( posicionTrat!=dato.getJ() ) {
                    longitudesL.add(longitud);
                    posicionTrat=dato.getJ();
                    longitud=1;
                    posicionAP++;
                } else {
                    longitud++;
                }
                numSecuencia[i][dato.getJ()][dato.getK()]=posicionAP;
                matrizRij_[i][dato.getJ()]+=posicionAP;
                vectorRi__[i]+=posicionAP;
                vectorR_j_[dato.getJ()]+=posicionAP;
            }
            longitudesL.add(longitud);
            bloquesL[i]=longitudesL;
            R___+=vectorRi__[i];
        }
        DatosEstadisticaSecuencias datosEstadisticaSecuencia=new DatosEstadisticaSecuencias();
        datosEstadisticaSecuencia.setBloquesL(bloquesL);
        datosEstadisticaSecuencia.setMatrizRijk(numSecuencia);
        datosEstadisticaSecuencia.setMatrizRij_(matrizRij_);
        datosEstadisticaSecuencia.setVectorRi__(vectorRi__);
        datosEstadisticaSecuencia.setVectorR_j_(vectorR_j_);
        datosEstadisticaSecuencia.setR___(R___);
        
        /*  ------------  debug ----------  */
        System.out.println("\nSecuencias\n");
        for( int i=0; i<N; i++ ) {
            for (int j = 0; j < M; j++) {
                System.out.print("celda " + i + " " + j + " : ");
                for (int k = 0; k < numSecuencia[i][j].length; k++) {
                    System.out.print( numSecuencia[i][j][k] + " ");
                }
                System.out.println("");
            }
        }
        System.out.println("\nMatriz rij.\n");
        for( int i=0; i<N; i++ ) {
            for (int j = 0; j < M; j++) {
                System.out.println("celda " + i + " " + j + " : " + matrizRij_[i][j]);
            }
        }
        System.out.println("\nVector ri..\n");
        for( int i=0; i<N; i++ ) {
            System.out.println("celda " + i + " : " + vectorRi__[i]);
        }
        System.out.println("\nVector r.j.\n");
        for( int j=0; j<M; j++ ) {
            System.out.println("celda " + j + " : " + vectorR_j_[j]);
        }
        
        System.out.println("\nL's\n");
        for (int i = 0; i < bloquesL.length; i++) {
            System.out.print("L " + i + " : " );
            for( int j : bloquesL[i] ) System.out.print( j + " ");
            System.out.println("");
        }
        System.out.println("**************************************");
        return datosEstadisticaSecuencia;
    }
    
}
