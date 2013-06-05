
package Control;

import Entidad.*;


public class CalculadorRangos {
        
    public DatosEstadisticaPrueba calcularRangos( double matriz[][][], MatrizDatos matrizDatos ) {
        final int N=matriz.length, M=matriz[0].length;
        int rankings[][][]=new int[N][M][];
        int matrizRij_[][]=new int[N][M];
        int vectorRi__[]=new int[N];
        int vectorR_j_[]=new int[M];
        int R___=0;
        for (int i=0; i<N; i++){
            for (int j = 0; j < M; j++) {
                rankings[i][j]=new int[matriz[i][j].length];
            }
        }
        for (int i=0; i<N; i++){
            int rango=1;
            for( DatoCelda dato : matrizDatos.getDatosBloqueOrdenados()[i] ) {
                rankings[i][dato.getJ()][dato.getK()]=rango;
                matrizRij_[i][dato.getJ()]+=rango;
                vectorRi__[i]+=rango;
                vectorR_j_[dato.getJ()]+=rango;
                rango++;
            }
            R___+=vectorRi__[i];
        }
        DatosEstadisticaPrueba datosEstadisticaRango=new DatosEstadisticaPrueba();
        datosEstadisticaRango.setMatrizRijk(rankings);
        datosEstadisticaRango.setMatrizRij_(matrizRij_);
        datosEstadisticaRango.setVectorRi__(vectorRi__);
        datosEstadisticaRango.setVectorR_j_(vectorR_j_);
        datosEstadisticaRango.setR___(R___);
        
        /*  ------------  debug ----------  */
        System.out.println("\nRankings\n");
        for( int i=0; i<N; i++ ) {
            for (int j = 0; j < M; j++) {
                System.out.print("celda " + i + " " + j + " : ");
                for (int k = 0; k < rankings[i][j].length; k++) {
                    System.out.print( rankings[i][j][k] + " ");
                }
                System.out.println("");
            }
        }
        System.out.println("\nMatriz Rij.\n");
        for( int i=0; i<N; i++ ) {
            for (int j = 0; j < M; j++) {
                System.out.println("celda " + i + " " + j + " : " + matrizRij_[i][j]);
            }
        }
        System.out.println("\nVector Ri..\n");
        for( int i=0; i<N; i++ ) {
            System.out.println("celda " + i + " : " + vectorRi__[i]);
        }
        System.out.println("\nVector R.j.\n");
        for( int j=0; j<M; j++ ) {
            System.out.println("celda " + j + " : " + vectorR_j_[j]);
        }        
        return datosEstadisticaRango;
    }
}
