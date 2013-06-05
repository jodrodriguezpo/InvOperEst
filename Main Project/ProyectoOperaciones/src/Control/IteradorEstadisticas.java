package Control;

import Entidad.CaracteristicaEstadistica;
import Entidad.DatosCalculadosExperimento;

public class IteradorEstadisticas {

    private InicializadorDatos inicializadorDatos = new InicializadorDatos();
    private Evaluador evaluador = new Evaluador();
    private StringBuilder strError = new StringBuilder();

    public CaracteristicaEstadistica[] ingresoDatos(int tamaños[][],
            double vectorTratamientos[], double vectorBloques[],
            double lambdas[][][], double granMedia, double alpha,
            String estadisticas[], final int S) {

        /* Verificación de los lambdas */

        for (int i = 0; i < tamaños.length; i++) {
            for (int j = 0; j < tamaños[i].length; j++) {
                if (lambdas[i][j][1] <= 0) {
                    strError.append("Lambda2=").append(lambdas[i][j][1]).
                            append(" en la celda i=").append(i + 1).append(", j=").
                            append(j + 1).append(" no es mayor que cero.\n");
                    return null;
                }
                if (lambdas[i][j][2] == 0 && lambdas[i][j][3] == 0) {
                    strError.append("Lambda3=").append(lambdas[i][j][2]).append(" y Lambda4=").append(lambdas[i][j][3]).
                            append(" en la celda i=").append(i + 1).append(", j=").
                            append(j + 1).append(" son ambas iguaes a cero.\n");
                    return null;
                }
                if (lambdas[i][j][2] * lambdas[i][j][3] < 0) {
                    if (Math.abs(lambdas[i][j][2]) < 1) {
                        strError.append("Lambda3=").append(lambdas[i][j][2]).
                                append(" en la celda i=").append(i + 1).append(", j=").
                                append(j + 1).append(" no cumple abs(Lambda3)>=1.\n");
                        return null;
                    }
                    if (Math.abs(lambdas[i][j][3]) < 1) {
                        strError.append("Lambda4=").append(lambdas[i][j][3]).
                                append(" en la celda i=").append(i + 1).append(", j=").
                                append(j + 1).append(" no cumple abs(Lambda4)>=1.\n");
                        return null;
                    }
                }
            }
        }

        final int r = estadisticas.length;
        strError = new StringBuilder();
        CaracteristicaEstadistica caracteristicaEstadisticas[] = new CaracteristicaEstadistica[r];
        DatosCalculadosExperimento datosCalculadosExperimento;
        double tablaEstadisticas[][] = new double[S][r];
        for (int i = 0; i < S && !evaluador.isError(); i++) {
            datosCalculadosExperimento = inicializadorDatos.inicializarDatos(
                    tamaños, vectorTratamientos, vectorBloques, lambdas, granMedia);
            evaluador.setDatos(datosCalculadosExperimento);
            for (int j = 0; j < r && !evaluador.isError(); j++) {
                evaluador.setAllErrors(false);
                evaluador.setExpresion(estadisticas[j]);
                tablaEstadisticas[i][j] = evaluador.evaluate();
            }
        }
        if (evaluador.isError()) {
            strError.append(evaluador.getErrores());
            return null;
        }


        System.out.println("\n\n Tabla Estadísticas \n");
        for (int i = 0; i < S; i++) {
            for (int j = 0; j < r; j++) {
                System.out.printf("%.3f ", tablaEstadisticas[i][j]);
            }
            System.out.println("");
        }



        // DLG



        return caracteristicaEstadisticas;
    }

    public StringBuilder getStrError() {
        return strError;
    }
}
