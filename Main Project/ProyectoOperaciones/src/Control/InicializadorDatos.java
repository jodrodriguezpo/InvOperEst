
package Control;

import Entidad.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class InicializadorDatos {
    
    private MatrizDatos matrizDatos=new MatrizDatos();
    private CalculadorRangos calculadorRangos=new CalculadorRangos();
    private CalculadorSecuencias calculadorSecuencias=new CalculadorSecuencias();
    private DatosEstadisticaPrueba datosEstadisticaRango;
    private DatosEstadisticaSecuencias datosEstadisticaSecuencias;
   
    public DatosCalculadosExperimento inicializarDatos( int tamaños[][], double vectorTratamientos[], double vectorBloques[],
            double lambdas[][][], double granMedia ) {
        final int n=tamaños.length, m=tamaños[0].length;
        int ni_[]=new int[n], n_j[]=new int[m], n__=0;
        double matriz[][][]=new double[n][m][];
        double matrizXij_[][]=new double[n][m];
        double vectorXi__[]=new double[n];
        double vectorX_j_[]=new double[m];
        double X___=0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                ni_[i]+=tamaños[i][j];
                n_j[j]+=tamaños[i][j];
                matriz[i][j]=new double[tamaños[i][j]];
                for (int k = 0; k < tamaños[i][j]; k++) {
                   matriz[i][j][k]=granMedia+vectorBloques[i]+
                           vectorTratamientos[j]+epsilon(i, j, lambdas);
                   X___+=matriz[i][j][k];
                   matrizXij_[i][j]+=matriz[i][j][k];
                   vectorXi__[i]+=matriz[i][j][k];
                   vectorX_j_[j]+=matriz[i][j][k];                   
                }
            }
            n__+=ni_[i];
        }
        System.out.println("*********************************");
        System.out.println("Matriz Datos");
        for( int i=0; i<n; i++ ) {
            for( int j=0; j<m; j++ ) {
                System.out.print("celda " + i +" " + j +" : ");
                for (int k = 0; k < tamaños[i][j]; k++) {
                    System.out.printf("%.3f ", matriz[i][j][k]);
                }
                System.out.println("");
            }
        }
        inicializarDatosEstadisticas(matriz);
        
        DatosCalculadosExperimento respuesta=new DatosCalculadosExperimento();
        respuesta.setMatrizXijk(matriz);
        respuesta.setNi_(ni_);
        respuesta.setN_j(n_j);
        respuesta.setDatosEstadisticaSecuencias(datosEstadisticaSecuencias);
        respuesta.setDatosEstadisticaRango(datosEstadisticaRango);
        respuesta.setMatrizXij_(matrizXij_);
        respuesta.setVectorX_j_(vectorX_j_);
        respuesta.setVectorXi__(vectorXi__);
        respuesta.setX___(X___);
        respuesta.setN__(n__);
        return respuesta;
    }
    
    private void inicializarDatosEstadisticas( double matriz[][][] ) {
        final int n=matriz.length, m=matriz[0].length;
        ArrayList<DatoCelda> datosPorBloqueOrdenados[]=new ArrayList[n];
        ArrayList<DatoCelda> datosBloque;
        for( int i=0; i<n; i++ ) { 
            datosBloque=new ArrayList<>();
            for( int j=0; j<m; j++ ) {
                for( int k=0; k<matriz[i][j].length; k++ ) {
                    datosBloque.add(new DatoCelda(j, k, matriz[i][j][k]));
                }
            }
            Collections.sort(datosBloque);
            datosPorBloqueOrdenados[i]=datosBloque;
        }
        matrizDatos.setDatosBloqueOrdenados(datosPorBloqueOrdenados);
        
        datosEstadisticaRango=calculadorRangos.calcularRangos(matriz, matrizDatos);
        datosEstadisticaSecuencias=calculadorSecuencias.calcularSecuencias(matriz, matrizDatos);
    }
    
    private double epsilon( int i, int j, double lambda[][][] ) {
        return LambdaGeneralizada.funcionPercentil(new Random().nextDouble(), 
                lambda[i][j][0], lambda[i][j][1], lambda[i][j][2], lambda[i][j][3]);
    }
    
}
