package Control;

/**
 * Clase encargada de evaluar una expresion que puede contener variables. <br>
 * Las funciones que puede evaluar, son de las clases que implementan la
 * interfaz
 * <code>Funcion</code> y se encuentran en
 * <code>ebd.functions</code>.
 * <br>
 * <br>
 *
 *
 *
 *
 * @version 1.5
 */
import Entidad.Funcion;
import java.util.HashMap;
import java.util.Map;
import Entidad.DatosCalculadosExperimento;
import java.util.ArrayList;
import java.util.List;

public class Evaluador {

    /**
     * Operadores soportados, que definen el orden de prioridad <br>
     */
    private final String OPERS = "+-*/^";
    /**
     * Caracteres Invalidos como nombre de una funcion. <br>
     */
    private final String NOCARAC = OPERS + "()";
    /**
     * Expresion a evaluar. <br>
     */
    private String expresion = "";
    private Map<String, Double> varValor;
    private DatosCalculadosExperimento datos;

    public void setDatos(DatosCalculadosExperimento datos) {
        this.datos = datos;
    }

    /**
     * Retorna el valor de la variable por index <br>
     *
     * @param index - index de la variable <br>
     * @return valor
     */
    public Double getValorVariable(String exp) {
        exp = exp.trim();
        Double ret = varValor.get(exp);
        if (ret != null) {
            return ret;
        }
        if (exp.isEmpty()) {
            return null;
        }
        int index = 0;
        while (index < exp.length() && exp.charAt(index) != '{') {
            index++;
        }
        if (index == exp.length() ) {
            return null;
        }
        for( int i=0; i<index; i++ ) {
            if( NOCARAC.indexOf(exp.charAt(i))>=0 ) {
                return null;
            }
        }
        int count=0;
        for( int i=index; i<exp.length(); i++ ) {
            if( exp.charAt(i)=='{' ) {
                count--;
            } else if(exp.charAt(i)=='}' ) {
                count++;
            }
            if( count==0 && i<exp.length()-1 || count>0 ){
                return null;
            }
        }
        if( count<0 ) {
            return null;
        }
        String name = exp.substring(0, index);
        String arg = exp.substring(index + 1, exp.length() - 1);
        String toks[] = separarPorComas(arg);
        if( error ) {
            return null;
        }
        if (name.equals("n") || name.equals("L")) {
            if (toks.length != 2) {
                error = true;
                strError.append("La variable ").append(name).append(" debe contener dos parametros para indexarla.\n");
                return null;
            }
        } else if (name.equals("X") || name.equals("R") || name.equals("r")
                || name.equals("R_") || name.equals("r_")) {
            if (toks.length != 3) {
                error = true;
                strError.append("La variable ").append(name).append(" debe contener tres parametros para indexarla.\n");
                return null;
            }

        } else {
            error = true;
            strError.append("La variable \"").append(name).append("\" no se puede indexar.\n");
            return null;
        }
        int i, j, k;
        double i1, i2, i3;
        switch (name) {
            case "n":
                if (toks[0].equals(".")) {
                    if (toks[1].equals(".")) {
                        return (double) datos.getN__();
                    }
                    i2 = evaluate(toks[1]);
                    if (error) {
                        return null;
                    }
                    if (!isInteger(i2)) {
                        error = true;
                        strError.append("El índice \"").append(toks[1]).append("\" correspondiente a \"").
                                append(exp).append("\" no es un entero.\n");
                        return null;
                    }
                    j = (int) i2;
                    if (j < 1 || j > datos.getM()) {
                        error = true;
                        strError.append("El índice \"").append(toks[1]).append("\" correspondiente a \"").
                                append(exp).append("\" no está dentro del rango válido.\n");
                        return null;
                    }
                    return (double) datos.getN_j()[j - 1];
                } else {
                    i1 = evaluate(toks[0]);
                    if (error) {
                        return null;
                    }
                    if (!isInteger(i1)) {
                        error = true;
                        strError.append("El índice \"").append(toks[0]).append("\" correspondiente a \"").
                                append(exp).append("\" no es un entero.\n");
                        return null;
                    }
                    i = (int) i1;
                    if (i < 1 || i > datos.getN()) {
                        error = true;
                        strError.append("El índice \"").append(toks[0]).append("\" correspondiente a \"").
                                append(exp).append("\" no está dentro del rango válido.\n");
                        return null;
                    }

                    if (toks[1].equals(".")) {
                        return (double) datos.getNi_()[i - 1];
                    }
                    i2 = evaluate(toks[1]);
                    if (error) {
                        return null;
                    }
                    if (!isInteger(i2)) {
                        error = true;
                        strError.append("El índice \"").append(toks[1]).append("\" correspondiente a \"").
                                append(exp).append("\" no es un entero.\n");
                        return null;
                    }
                    j = (int) i2;
                    if (j < 1 || j > datos.getM()) {
                        error = true;
                        strError.append("El índice \"").append(toks[1]).append("\" correspondiente a \"").
                                append(exp).append("\" no está dentro del rango válido.\n");
                        return null;
                    }
                    return (double) datos.getMatrizXijk()[i - 1][j - 1].length;
                }
            case "L":
                if (toks[0].equals(".")) {
                    error = true;
                    strError.append("La expresión ").append(exp).append(" no se puede indexar.\n");
                    return null;
                }
                i1 = evaluate(toks[0]);
                if (error) {
                    return null;
                }
                if (!isInteger(i1)) {
                    error = true;
                    strError.append("El índice \"").append(toks[0]).append("\" correspondiente a \"").
                            append(exp).append("\" no es un entero.\n");
                    return null;
                }
                i = (int) i1;
                if (i < 1 || i > datos.getN()) {
                    error = true;
                    strError.append("El índice \"").append(toks[0]).append("\" correspondiente a \"").
                            append(exp).append("\" no está dentro del rango válido.\n");
                    return null;
                }
                if (toks[1].equals(".")) {
                    return (double) datos.getNi_()[i - 1];
                }
                i2 = evaluate(toks[1]);
                if (error) {
                    return null;
                }
                if (!isInteger(i2)) {
                    error = true;
                    strError.append("El índice \"").append(toks[1]).append("\" correspondiente a \"").
                            append(exp).append("\" no es un entero.\n");
                    return null;
                }
                j = (int) i2;
                if (j < 1 || j > datos.getDatosEstadisticaSecuencias().getBloquesL()[i - 1].size()) {
                    error = true;
                    strError.append("El índice \"").append(toks[1]).append("\" correspondiente a \"").
                            append(exp).append("\" no está dentro del rango válido.\n");
                    return null;
                }
                return (double) datos.getDatosEstadisticaSecuencias().getBloquesL()[i - 1].get(j - 1);
            case "X":
                if (toks[0].equals(".") && toks[1].equals(".") && toks[2].equals(".")) {
                    return datos.getX___();
                }
                if (toks[0].equals(".") && !toks[1].equals(".") && toks[2].equals(".")) {
                    i2 = evaluate(toks[1]);
                    if (error) {
                        return null;
                    }
                    if (!isInteger(i2)) {
                        error = true;
                        strError.append("El índice \"").append(toks[1]).append("\" correspondiente a \"").
                                append(exp).append("\" no es un entero.\n");
                        return null;
                    }
                    j = (int) i2;
                    if (j < 1 || j > datos.getVectorX_j_().length) {
                        error = true;
                        strError.append("El índice \"").append(toks[1]).append("\" correspondiente a \"").
                                append(exp).append("\" no está dentro del rango válido.\n");
                        return null;
                    }
                    return datos.getVectorX_j_()[j - 1];
                }
                if (!toks[0].equals(".") && toks[1].equals(".") && toks[2].equals(".")) {
                    i1 = evaluate(toks[0]);
                    if (error) {
                        return null;
                    }
                    if (!isInteger(i1)) {
                        error = true;
                        strError.append("El índice \"").append(toks[0]).append("\" correspondiente a \"").
                                append(exp).append("\" no es un entero.\n");
                        return null;
                    }
                    i = (int) i1;
                    if (i < 1 || i > datos.getVectorXi__().length) {
                        error = true;
                        strError.append("El índice \"").append(toks[0]).append("\" correspondiente a \"").
                                append(exp).append("\" no está dentro del rango válido.\n");
                        return null;
                    }
                    return datos.getVectorXi__()[i - 1];
                }
                if (!toks[0].equals(".") && !toks[1].equals(".") && toks[2].equals(".")) {
                    i1 = evaluate(toks[0]);
                    if (error) {
                        return null;
                    }
                    if (!isInteger(i1)) {
                        error = true;
                        strError.append("El índice \"").append(toks[0]).append("\" correspondiente a \"").
                                append(exp).append("\" no es un entero.\n");
                        return null;
                    }
                    i = (int) i1;
                    if (i < 1 || i > datos.getVectorXi__().length) {
                        error = true;
                        strError.append("El índice \"").append(toks[0]).append("\" correspondiente a \"").
                                append(exp).append("\" no está dentro del rango válido.\n");
                        return null;
                    }
                    i2 = evaluate(toks[1]);
                    if (error) {
                        return null;
                    }
                    if (!isInteger(i2)) {
                        error = true;
                        strError.append("El índice \"").append(toks[1]).append("\" correspondiente a \"").
                                append(exp).append("\" no es un entero.\n");
                        return null;
                    }
                    j = (int) i2;
                    if (j < 1 || j > datos.getVectorX_j_().length) {
                        error = true;
                        strError.append("El índice \"").append(toks[1]).append("\" correspondiente a \"").
                                append(exp).append("\" no está dentro del rango válido.\n");
                        return null;
                    }
                    return datos.getMatrizXij_()[i - 1][j - 1];
                }
                if (!toks[0].equals(".") && !toks[1].equals(".") && !toks[2].equals(".")) {
                    i1 = evaluate(toks[0]);
                    if (error) {
                        return null;
                    }
                    if (!isInteger(i1)) {
                        error = true;
                        strError.append("El índice \"").append(toks[0]).append("\" correspondiente a \"").
                                append(exp).append("\" no es un entero.\n");
                        return null;
                    }
                    i = (int) i1;
                    if (i < 1 || i > datos.getVectorXi__().length) {
                        error = true;
                        strError.append("El índice \"").append(toks[0]).append("\" correspondiente a \"").
                                append(exp).append("\" no está dentro del rango válido.\n");
                        return null;
                    }
                    i2 = evaluate(toks[1]);
                    if (error) {
                        return null;
                    }
                    if (!isInteger(i2)) {
                        error = true;
                        strError.append("El índice \"").append(toks[1]).append("\" correspondiente a \"").
                                append(exp).append("\" no es un entero.\n");
                        return null;
                    }
                    j = (int) i2;
                    if (j < 1 || j > datos.getVectorX_j_().length) {
                        error = true;
                        strError.append("El índice \"").append(toks[1]).append("\" correspondiente a \"").
                                append(exp).append("\" no está dentro del rango válido.\n");
                        return null;
                    }
                    i3 = evaluate(toks[2]);
                    if (error) {
                        return null;
                    }
                    if (!isInteger(i3)) {
                        error = true;
                        strError.append("El índice \"").append(toks[2]).append("\" correspondiente a \"").
                                append(exp).append("\" no es un entero.\n");
                        return null;
                    }
                    k = (int) i3;
                    if (k < 1 || k > datos.getMatrizXijk()[i - 1][j - 1].length) {
                        error = true;
                        strError.append("El índice \"").append(toks[2]).append("\" correspondiente a \"").
                                append(exp).append("\" no está dentro del rango válido.\n");
                        return null;
                    }
                    return datos.getMatrizXijk()[i - 1][j - 1][k - 1];
                }
                error = true;
                strError.append("La expresión \"").append(exp).append("\" no es ").
                        append(" una expresión válida.\n");
                return null;
            case "R":
                if (toks[0].equals(".") && toks[1].equals(".") && toks[2].equals(".")) {
                    return (double) datos.getDatosEstadisticaRango().getR___();
                }
                if (toks[0].equals(".") && !toks[1].equals(".") && toks[2].equals(".")) {
                    i2 = evaluate(toks[1]);
                    if (error) {
                        return null;
                    }
                    if (!isInteger(i2)) {
                        error = true;
                        strError.append("El índice \"").append(toks[1]).append("\" correspondiente a \"").
                                append(exp).append("\" no es un entero.\n");
                        return null;
                    }
                    j = (int) i2;
                    if (j < 1 || j > datos.getDatosEstadisticaRango().getVectorR_j_().length) {
                        error = true;
                        strError.append("El índice \"").append(toks[1]).append("\" correspondiente a \"").
                                append(exp).append("\" no está dentro del rango válido.\n");
                        return null;
                    }
                    return (double) datos.getDatosEstadisticaRango().getVectorR_j_()[j - 1];
                }
                if (!toks[0].equals(".") && toks[1].equals(".") && toks[2].equals(".")) {
                    i1 = evaluate(toks[0]);
                    if (error) {
                        return null;
                    }
                    if (!isInteger(i1)) {
                        error = true;
                        strError.append("El índice \"").append(toks[0]).append("\" correspondiente a \"").
                                append(exp).append("\" no es un entero.\n");
                        return null;
                    }
                    i = (int) i1;
                    if (i < 1 || i > datos.getDatosEstadisticaRango().getVectorRi__().length) {
                        error = true;
                        strError.append("El índice \"").append(toks[0]).append("\" correspondiente a \"").
                                append(exp).append("\" no está dentro del rango válido.\n");
                        return null;
                    }
                    return (double) datos.getDatosEstadisticaRango().getVectorRi__()[i - 1];
                }
                if (!toks[0].equals(".") && !toks[1].equals(".") && toks[2].equals(".")) {
                    i1 = evaluate(toks[0]);
                    if (error) {
                        return null;
                    }
                    if (!isInteger(i1)) {
                        error = true;
                        strError.append("El índice \"").append(toks[0]).append("\" correspondiente a \"").
                                append(exp).append("\" no es un entero.\n");
                        return null;
                    }
                    i = (int) i1;
                    if (i < 1 || i > datos.getDatosEstadisticaRango().getVectorRi__().length) {
                        error = true;
                        strError.append("El índice \"").append(toks[0]).append("\" correspondiente a \"").
                                append(exp).append("\" no está dentro del rango válido.\n");
                        return null;
                    }
                    i2 = evaluate(toks[1]);
                    if (error) {
                        return null;
                    }
                    if (!isInteger(i2)) {
                        error = true;
                        strError.append("El índice \"").append(toks[1]).append("\" correspondiente a \"").
                                append(exp).append("\" no es un entero.\n");
                        return null;
                    }
                    j = (int) i2;
                    if (j < 1 || j > datos.getDatosEstadisticaRango().getVectorR_j_().length) {
                        error = true;
                        strError.append("El índice \"").append(toks[1]).append("\" correspondiente a \"").
                                append(exp).append("\" no está dentro del rango válido.\n");
                        return null;
                    }
                    return (double) datos.getDatosEstadisticaRango().getMatrizRij_()[i - 1][j - 1];
                }
                if (!toks[0].equals(".") && !toks[1].equals(".") && !toks[2].equals(".")) {
                    i1 = evaluate(toks[0]);
                    if (error) {
                        return null;
                    }
                    if (!isInteger(i1)) {
                        error = true;
                        strError.append("El índice \"").append(toks[0]).append("\" correspondiente a \"").
                                append(exp).append("\" no es un entero.\n");
                        return null;
                    }
                    i = (int) i1;
                    if (i < 1 || i > datos.getDatosEstadisticaRango().getVectorRi__().length) {
                        error = true;
                        strError.append("El índice \"").append(toks[0]).append("\" correspondiente a \"").
                                append(exp).append("\" no está dentro del rango válido.\n");
                        return null;
                    }
                    i2 = evaluate(toks[1]);
                    if (error) {
                        return null;
                    }
                    if (!isInteger(i2)) {
                        error = true;
                        strError.append("El índice \"").append(toks[1]).append("\" correspondiente a \"").
                                append(exp).append("\" no es un entero.\n");
                        return null;
                    }
                    j = (int) i2;
                    if (j < 1 || j > datos.getDatosEstadisticaRango().getVectorR_j_().length) {
                        error = true;
                        strError.append("El índice \"").append(toks[1]).append("\" correspondiente a \"").
                                append(exp).append("\" no está dentro del rango válido.\n");
                        return null;
                    }
                    i3 = evaluate(toks[2]);
                    if (error) {
                        return null;
                    }
                    if (!isInteger(i3)) {
                        error = true;
                        strError.append("El índice \"").append(toks[2]).append("\" correspondiente a \"").
                                append(exp).append("\" no es un entero.\n");
                        return null;
                    }
                    k = (int) i3;
                    if (k < 1 || k > datos.getDatosEstadisticaRango().getMatrizRijk()[i - 1][j - 1].length) {
                        error = true;
                        strError.append("El índice \"").append(toks[2]).append("\" correspondiente a \"").
                                append(exp).append("\" no está dentro del rango válido.\n");
                        return null;
                    }
                    return (double) datos.getDatosEstadisticaRango().getMatrizRijk()[i - 1][j - 1][k - 1];
                }
                error = true;
                strError.append("La expresión \"").append(exp).append("\" no es ").
                        append(" una expresión válida.\n");
                return null;
            case "r":
                if (toks[0].equals(".") && toks[1].equals(".") && toks[2].equals(".")) {
                    return (double) datos.getDatosEstadisticaSecuencias().getR___();
                }
                if (toks[0].equals(".") && !toks[1].equals(".") && toks[2].equals(".")) {
                    i2 = evaluate(toks[1]);
                    if (error) {
                        return null;
                    }
                    if (!isInteger(i2)) {
                        error = true;
                        strError.append("El índice \"").append(toks[1]).append("\" correspondiente a \"").
                                append(exp).append("\" no es un entero.\n");
                        return null;
                    }
                    j = (int) i2;
                    if (j < 1 || j > datos.getDatosEstadisticaSecuencias().getVectorR_j_().length) {
                        error = true;
                        strError.append("El índice \"").append(toks[1]).append("\" correspondiente a \"").
                                append(exp).append("\" no está dentro del rango válido.\n");
                        return null;
                    }
                    return (double) datos.getDatosEstadisticaSecuencias().getVectorR_j_()[j - 1];
                }
                if (!toks[0].equals(".") && toks[1].equals(".") && toks[2].equals(".")) {
                    i1 = evaluate(toks[0]);
                    if (error) {
                        return null;
                    }
                    if (!isInteger(i1)) {
                        error = true;
                        strError.append("El índice \"").append(toks[0]).append("\" correspondiente a \"").
                                append(exp).append("\" no es un entero.\n");
                        return null;
                    }
                    i = (int) i1;
                    if (i < 1 || i > datos.getDatosEstadisticaSecuencias().getVectorRi__().length) {
                        error = true;
                        strError.append("El índice \"").append(toks[0]).append("\" correspondiente a \"").
                                append(exp).append("\" no está dentro del rango válido.\n");
                        return null;
                    }
                    return (double) datos.getDatosEstadisticaSecuencias().getVectorRi__()[i - 1];
                }
                if (!toks[0].equals(".") && !toks[1].equals(".") && toks[2].equals(".")) {
                    i1 = evaluate(toks[0]);
                    if (error) {
                        return null;
                    }
                    if (!isInteger(i1)) {
                        error = true;
                        strError.append("El índice \"").append(toks[0]).append("\" correspondiente a \"").
                                append(exp).append("\" no es un entero.\n");
                        return null;
                    }
                    i = (int) i1;
                    if (i < 1 || i > datos.getDatosEstadisticaSecuencias().getVectorRi__().length) {
                        error = true;
                        strError.append("El índice \"").append(toks[0]).append("\" correspondiente a \"").
                                append(exp).append("\" no está dentro del rango válido.\n");
                        return null;
                    }
                    i2 = evaluate(toks[1]);
                    if (error) {
                        return null;
                    }
                    if (!isInteger(i2)) {
                        error = true;
                        strError.append("El índice \"").append(toks[1]).append("\" correspondiente a \"").
                                append(exp).append("\" no es un entero.\n");
                        return null;
                    }
                    j = (int) i2;
                    if (j < 1 || j > datos.getDatosEstadisticaSecuencias().getVectorR_j_().length) {
                        error = true;
                        strError.append("El índice \"").append(toks[1]).append("\" correspondiente a \"").
                                append(exp).append("\" no está dentro del rango válido.\n");
                        return null;
                    }
                    return (double) datos.getDatosEstadisticaSecuencias().getMatrizRij_()[i - 1][j - 1];
                }
                if (!toks[0].equals(".") && !toks[1].equals(".") && !toks[2].equals(".")) {
                    i1 = evaluate(toks[0]);
                    if (error) {
                        return null;
                    }
                    if (!isInteger(i1)) {
                        error = true;
                        strError.append("El índice \"").append(toks[0]).append("\" correspondiente a \"").
                                append(exp).append("\" no es un entero.\n");
                        return null;
                    }
                    i = (int) i1;
                    if (i < 1 || i > datos.getDatosEstadisticaSecuencias().getVectorRi__().length) {
                        error = true;
                        strError.append("El índice \"").append(toks[0]).append("\" correspondiente a \"").
                                append(exp).append("\" no está dentro del rango válido.\n");
                        return null;
                    }
                    i2 = evaluate(toks[1]);
                    if (error) {
                        return null;
                    }
                    if (!isInteger(i2)) {
                        error = true;
                        strError.append("El índice \"").append(toks[1]).append("\" correspondiente a \"").
                                append(exp).append("\" no es un entero.\n");
                        return null;
                    }
                    j = (int) i2;
                    if (j < 1 || j > datos.getDatosEstadisticaSecuencias().getVectorR_j_().length) {
                        error = true;
                        strError.append("El índice \"").append(toks[1]).append("\" correspondiente a \"").
                                append(exp).append("\" no está dentro del rango válido.\n");
                        return null;
                    }
                    i3 = evaluate(toks[2]);
                    if (error) {
                        return null;
                    }
                    if (!isInteger(i3)) {
                        error = true;
                        strError.append("El índice \"").append(toks[2]).append("\" correspondiente a \"").
                                append(exp).append("\" no es un entero.\n");
                        return null;
                    }
                    k = (int) i3;
                    if (k < 1 || k > datos.getDatosEstadisticaSecuencias().getMatrizRijk()[i - 1][j - 1].length) {
                        error = true;
                        strError.append("El índice \"").append(toks[2]).append("\" correspondiente a \"").
                                append(exp).append("\" no está dentro del rango válido.\n");
                        return null;
                    }
                    return (double) datos.getDatosEstadisticaSecuencias().getMatrizRijk()[i - 1][j - 1][k - 1];
                }
                error = true;
                strError.append("La expresión \"").append(exp).append("\" no es ").
                        append(" una expresión válida.\n");
                return null;
            case "R_":
                if (toks[0].equals(".") && toks[1].equals(".") && toks[2].equals(".")) {
                    return (double) datos.getDatosEstadisticaRango().getR___() / datos.getN__();
                }
                if (toks[0].equals(".") && !toks[1].equals(".") && toks[2].equals(".")) {
                    i2 = evaluate(toks[1]);
                    if (error) {
                        return null;
                    }
                    if (!isInteger(i2)) {
                        error = true;
                        strError.append("El índice \"").append(toks[1]).append("\" correspondiente a \"").
                                append(exp).append("\" no es un entero.\n");
                        return null;
                    }
                    j = (int) i2;
                    if (j < 1 || j > datos.getDatosEstadisticaRango().getVectorR_j_().length) {
                        error = true;
                        strError.append("El índice \"").append(toks[1]).append("\" correspondiente a \"").
                                append(exp).append("\" no está dentro del rango válido.\n");
                        return null;
                    }
                    return (double) datos.getDatosEstadisticaRango().getVectorR_j_()[j - 1] / datos.getN_j()[j - 1];
                }
                if (!toks[0].equals(".") && toks[1].equals(".") && toks[2].equals(".")) {
                    i1 = evaluate(toks[0]);
                    if (error) {
                        return null;
                    }
                    if (!isInteger(i1)) {
                        error = true;
                        strError.append("El índice \"").append(toks[0]).append("\" correspondiente a \"").
                                append(exp).append("\" no es un entero.\n");
                        return null;
                    }
                    i = (int) i1;
                    if (i < 1 || i > datos.getDatosEstadisticaRango().getVectorRi__().length) {
                        error = true;
                        strError.append("El índice \"").append(toks[0]).append("\" correspondiente a \"").
                                append(exp).append("\" no está dentro del rango válido.\n");
                        return null;
                    }
                    return (double) datos.getDatosEstadisticaRango().getVectorRi__()[i - 1] / datos.getNi_()[i - 1];
                }
                if (!toks[0].equals(".") && !toks[1].equals(".") && toks[2].equals(".")) {
                    i1 = evaluate(toks[0]);
                    if (error) {
                        return null;
                    }
                    if (!isInteger(i1)) {
                        error = true;
                        strError.append("El índice \"").append(toks[0]).append("\" correspondiente a \"").
                                append(exp).append("\" no es un entero.\n");
                        return null;
                    }
                    i = (int) i1;
                    if (i < 1 || i > datos.getDatosEstadisticaRango().getVectorRi__().length) {
                        error = true;
                        strError.append("El índice \"").append(toks[0]).append("\" correspondiente a \"").
                                append(exp).append("\" no está dentro del rango válido.\n");
                        return null;
                    }
                    i2 = evaluate(toks[1]);
                    if (error) {
                        return null;
                    }
                    if (!isInteger(i2)) {
                        error = true;
                        strError.append("El índice \"").append(toks[1]).append("\" correspondiente a \"").
                                append(exp).append("\" no es un entero.\n");
                        return null;
                    }
                    j = (int) i2;
                    if (j < 1 || j > datos.getDatosEstadisticaRango().getVectorR_j_().length) {
                        error = true;
                        strError.append("El índice \"").append(toks[1]).append("\" correspondiente a \"").
                                append(exp).append("\" no está dentro del rango válido.\n");
                        return null;
                    }
                    return (double) datos.getDatosEstadisticaRango().getMatrizRij_()[i - 1][j - 1] / datos.getMatrizXijk()[i - 1][j - 1].length;
                }
                error = true;
                strError.append("La expresión \"").append(exp).append("\" no es ").
                        append(" una expresión válida.\n");
                return null;
            case "r_":
                if (toks[0].equals(".") && toks[1].equals(".") && toks[2].equals(".")) {
                    return (double) datos.getDatosEstadisticaSecuencias().getR___() / datos.getN__();
                }
                if (toks[0].equals(".") && !toks[1].equals(".") && toks[2].equals(".")) {
                    i2 = evaluate(toks[1]);
                    if (error) {
                        return null;
                    }
                    if (!isInteger(i2)) {
                        error = true;
                        strError.append("El índice \"").append(toks[1]).append("\" correspondiente a \"").
                                append(exp).append("\" no es un entero.\n");
                        return null;
                    }
                    j = (int) i2;
                    if (j < 1 || j > datos.getDatosEstadisticaSecuencias().getVectorR_j_().length) {
                        error = true;
                        strError.append("El índice \"").append(toks[1]).append("\" correspondiente a \"").
                                append(exp).append("\" no está dentro del rango válido.\n");
                        return null;
                    }
                    return (double) datos.getDatosEstadisticaSecuencias().getVectorR_j_()[j - 1] / datos.getN_j()[j - 1];
                }
                if (!toks[0].equals(".") && toks[1].equals(".") && toks[2].equals(".")) {
                    i1 = evaluate(toks[0]);
                    if (error) {
                        return null;
                    }
                    if (!isInteger(i1)) {
                        error = true;
                        strError.append("El índice \"").append(toks[0]).append("\" correspondiente a \"").
                                append(exp).append("\" no es un entero.\n");
                        return null;
                    }
                    i = (int) i1;
                    if (i < 1 || i > datos.getDatosEstadisticaSecuencias().getVectorRi__().length) {
                        error = true;
                        strError.append("El índice \"").append(toks[0]).append("\" correspondiente a \"").
                                append(exp).append("\" no está dentro del rango válido.\n");
                        return null;
                    }
                    return (double) datos.getDatosEstadisticaSecuencias().getVectorRi__()[i - 1] / datos.getNi_()[i - 1];
                }
                if (!toks[0].equals(".") && !toks[1].equals(".") && toks[2].equals(".")) {
                    i1 = evaluate(toks[0]);
                    if (error) {
                        return null;
                    }
                    if (!isInteger(i1)) {
                        error = true;
                        strError.append("El índice \"").append(toks[0]).append("\" correspondiente a \"").
                                append(exp).append("\" no es un entero.\n");
                        return null;
                    }
                    i = (int) i1;
                    if (i < 1 || i > datos.getDatosEstadisticaSecuencias().getVectorRi__().length) {
                        error = true;
                        strError.append("El índice \"").append(toks[0]).append("\" correspondiente a \"").
                                append(exp).append("\" no está dentro del rango válido.\n");
                        return null;
                    }
                    i2 = evaluate(toks[1]);
                    if (error) {
                        return null;
                    }
                    if (!isInteger(i2)) {
                        error = true;
                        strError.append("El índice \"").append(toks[1]).append("\" correspondiente a \"").
                                append(exp).append("\" no es un entero.\n");
                        return null;
                    }
                    j = (int) i2;
                    if (j < 1 || j > datos.getDatosEstadisticaSecuencias().getVectorR_j_().length) {
                        error = true;
                        strError.append("El índice \"").append(toks[1]).append("\" correspondiente a \"").
                                append(exp).append("\" no está dentro del rango válido.\n");
                        return null;
                    }
                    return (double) datos.getDatosEstadisticaSecuencias().getMatrizRij_()[i - 1][j - 1] / datos.getMatrizXijk()[i - 1][j - 1].length;
                }
                error = true;
                strError.append("La expresión \"").append(exp).append("\" no es ").
                        append(" una expresión válida.\n");
                return null;
        }
        error = true;
        strError.append("La expresión \"").append(exp).append("\" no es ").
                append(" válida para indexar.\n");
        return null;
    }

    /**
     * Asigna elm valor de la variable por el index <br>
     *
     * @param index - index de la variable <br>
     * @param valor - valor asignar <br>
     */
    public void setValorVariable(String var, double valor) {
        varValor.put(var.trim(), valor);
    }

    /**
     * Define la expresion a operar. <br>
     *
     * @param expresion - String con la expresion. <br>
     * @return - true <br>
     */
    public boolean setExpresion(String expresion) {
        if (expresion == null) {
            expresion = "";
        }

        this.expresion = expresion;
        varValor = new HashMap<>();
        setValorVariable("n", datos.getN());
        setValorVariable("m", datos.getM());
        return true;
    }

    /**
     * Define las variables utlizadas en la expresion, separadas por coma. <br>
     * Inicializa los valores de las variables en cero. <br>
     *
     * @param variables - String con el nombre de las variables separados por
     * comas.
     * <br>
     * @return - true. <br>
     */
    public boolean setVariables(String variables) {
        String toks[] = variables.trim().split("[ ]*,[ ]*");
        for (String str : toks) {
            setValorVariable(str, 0);
        }
        return true;
    }

    /**
     * Define el valor de una variable existente. <br>
     *
     * @param variable - nombre de la variable. <br>
     * @param value - valor de la variable <br>
     * @return - Retorna <code>true</code> si se realizo la asignacion del
     * valor. <br>
     */
    public boolean setVariableValue(String variable, double value) {
        variable = variable.trim();
        if (varValor.containsKey(variable)) {
            varValor.put(variable, value);
            return true;
        }

        return false;
    }

    /**
     * Evalua la expresion, utilizando los valores de las constantes ya
     * definidas. <br>
     *
     * @return - valor de operacion <br>
     */
    public double evaluate() {
        error = false;
        strError = new StringBuilder();

        double ret = evaluate(expresion);

        return (error) ? 0 : ret;
    }

    /**
     * Evalua una expresion dada. <br>
     *
     * @param expresion - expresion a operar. <br>
     * @return - valor de operacion <br>
     */
    private double evaluate(String expresion) {

        if (error && !allErrors) {
            return 0;
        }

        expresion = eliminarParentesis(expresion);
        if (expresion.isEmpty()) {
            strError.append("Error: Expresion Vacia!\n");
            error = true;
            return 0;
        }
        if (expresion.charAt(0) == '-') {
            expresion = "0" + expresion;
        }

        // ----------------------------------------------

        Double ret = getValorVariable(expresion);
        if (ret != null) {
            return ret;
        }
        if( error ) return 0;

        // ----------------------------------------------

        if (isNumber(expresion)) {
            return Double.parseDouble(expresion);
        }

        // ----------------------------------------------

        String fun = dataFunction(expresion);

        if (fun != null) {
            Funcion f = null;
            String toks[] = fun.split(";");
            String name = toks[0];
            String expresion2 = toks[1];

            name = Character.toUpperCase(name.charAt(0))
                    + name.substring(1).toLowerCase();

            double rr = 0;
            switch (name) {
                case "Sum":
                    return evaluateSum(expresion2);
                case "Mult":
                    return evaluateMult(expresion2);
                default:
                    try {
                        f = (Funcion) Class.forName("Entidad.Funciones." + name).newInstance();

                    } catch (Exception e) {
                        error = true;
                        strError.append("Error: Funcion no definida \"").append(name).append("\"\n");
                        rr = evaluate(expresion2);
                        return 0;
                    }

                    rr = evaluate(expresion2);

                    if (f.isValue(rr)) {
                        return f.evaluate(rr);
                    } else {
                        strError.append("Error:  Parametro no aceptado \"").append(name).append("(").append(rr).append(")\"\n");
                        error = true;
                        return 0;
                    }
            }
        }

        // -----------------------------------------------

        int prioridad = 0;

        int posoper = -1;
        char oper = '?';
        int priop = Integer.MAX_VALUE;
        boolean ispar = false;

        for (int i = 0; i < expresion.length(); i++) {
            if (expresion.charAt(i) == '(') {
                prioridad++;
                ispar = true;
            }
            if (expresion.charAt(i) == ')') {
                prioridad--;
                ispar = true;
            }

            int po = OPERS.indexOf(Character.toString(expresion.charAt(i)));
            if (po >= 0 && po + 10 * prioridad <= priop) {
                oper = expresion.charAt(i);
                posoper = i;
                priop = po + 10 * prioridad;
            }
        }

        String s1 = "";
        String s2 = "";

        if (oper == '?') {
            error = true;

            if (ispar) {
                strError.append("Error:  Expresion no definida \"").append(expresion).append("\"\n");
            } else {
                strError.append("Error:  Variable no definida \"").append(expresion).append("\"\n");
            }

            return 0;

        } else {
            s1 = expresion.substring(0, posoper);
            s2 = expresion.substring(posoper + 1);
        }

        return Operate(s1, expresion.charAt(posoper), s2);
    }

    
    private String[] separarPorComas(  String exp ){
        if( exp.isEmpty() ) {
            error=true;
            strError.append("Expresión vacía.\n");
            return null;
        }
        exp=exp.trim();
        List<String> ans=new ArrayList<>();
        if( exp.charAt(0)=='(' && exp.charAt(exp.length()-1)==')' ) {
            exp=exp.substring(1, exp.length()-1);
        }
        int count=0, count2=0, last=0;
        for( int i=0; i<exp.length(); i++ ) {
            if( exp.charAt(i)=='{' ) {
                count--;
            } else if( exp.charAt(i)=='}' ) {
                count++;
            } else if( exp.charAt(i)=='(' ) {
                count2--;
            } else if( exp.charAt(i)==')' ) {
                count2++;
            }
            if( count>0 ) {
                error=true;
                strError.append("Parentesis { faltante en la expresión ").
                        append(exp).append(".\n");
                return null;
            }
            if( count2>0 ) {
                error=true;
                strError.append("Parentesis ( faltante en la expresión ").
                        append(exp).append(".\n");
                return null;
            }
            if( count==0 && count2==0 && exp.charAt(i)==',' ) {
                ans.add(exp.substring(last, i).trim());
                last=i+1;
            }
        }
        if( count<0 ) {
            error=true;
            strError.append("Parentesis } faltante en la expresión ").
            append(exp).append(".\n");
            return null;
        }
        if( count2<0 ) {
            error=true;
            strError.append("Parentesis ( faltante en la expresión ").
            append(exp).append(".\n");
            return null;
        }
        ans.add(exp.substring(last, exp.length()).trim());
        String ret[]=new String[ans.size()];
        for( int i=0; i<ret.length; i++ ) {
            ret[i]=ans.get(i);
        }
        return ret;
    }
    
    private String[] obtenerLimites(String exp) {
        String toks[] = separarPorComas(exp);
        if( toks==null ) {
            return null;
        }
        if (toks.length != 5) {
            error = true;
            strError.append("Error: Número incorrecto de parametros para la expresión \"(").append(exp).append(")\".\n");
            return null;
        }
        String varIndex = toks[0];
        int limInf, limSup, inc;
        String arg = toks[4];
        double value = evaluate(toks[1]);
        if (error) {
            return null;
        }
        if (isInteger(value)) {
            limInf = (int) (value);
        } else {
            error = true;
            strError.append("Error: El límite inicial ").append(toks[1])
                    .append(" para la expresión \"(").append(exp).append(")\" no corresponde a un valor entero.\n");
            return null;
        }
        value = evaluate(toks[2]);
        if (error) {
            return null;
        }
        if (isInteger(value)) {
            limSup = (int) (value);
        } else {
            error = true;
            strError.append("Error: El límite final ").append(toks[2])
                    .append(" para la expresión \"(").append(exp).append(")\" no corresponde a un valor entero.\n");
            return null;
        }
        value = evaluate(toks[3]);
        if (error) {
            return null;
        }
        if (isInteger(value)) {
            inc = (int) (value);
        } else {
            error = true;
            strError.append("Error: El incremento ").append(toks[3])
                    .append(" para la expresión \"(").append(exp).append(")\" no corresponde a un valor entero.\n");
            return null;
        }
        return new String[]{varIndex, String.valueOf(limInf), String.valueOf(limSup), String.valueOf(inc), arg};
    }

    private double evaluateSum(String exp) {
        String valores[] = obtenerLimites(exp);
        if (valores == null) {
            return 0; // error
        }
        String varIndex = valores[0], arg = valores[4];
        if (varValor.containsKey(varIndex)) {
            error = true;
            strError.append("Error: La variable ").append(varIndex)
                    .append(" esta definida para expresiones anidadas.\n");
            return 0;
        }
        int limInf = Integer.parseInt(valores[1]);
        int limSup = Integer.parseInt(valores[2]);
        int inc = Integer.parseInt(valores[3]);
        if( limInf<=limSup && inc<0 || limInf>limSup && inc>0 || inc==0 ) {
            error = true;
            strError.append("Error: Los argumentos (limIni=").append(limInf).append(", limFin=").append(limSup).append(", inc=").append(inc)
                    .append(") esta definiendo una sumatoria sin límite en la expresión ")
                    .append("sum(").append(exp).append(").\n");
            return 0;
        }
        double sum = 0;
        for (int i = limInf; i <= limSup; i += inc) {
            setValorVariable(varIndex, i);
            sum += evaluate(arg);
        }
        varValor.remove(varIndex);
        return sum;
    }

    private double evaluateMult(String exp) {
        String valores[] = obtenerLimites(exp);
        if (valores == null) {
            return 0; // error
        }
        String varIndex = valores[0], arg = valores[4];
        if (varValor.containsKey(varIndex)) {
            error = true;
            strError.append("Error: La variable ").append(varIndex)
                    .append(" esta definida para expresiones anidadas.\n");
            return 0;
        }
        int limInf = Integer.parseInt(valores[1]);
        int limSup = Integer.parseInt(valores[2]);
        int inc = Integer.parseInt(valores[3]);
        if( limInf<=limSup && inc<0 || limInf>limSup && inc>0 || inc==0 ) {
            error = true;
            strError.append("Error: Los argumentos (limIni=").append(limInf).append(", limFin=").append(limSup).append(", inc=").append(inc)
                    .append(") esta definiendo una multiplicatoria sin límite en la expresión ")
                    .append("sum(").append(exp).append(").\n");
            return 0;
        }
        double mult = 1;
        for (int i = limInf; i <= limSup; i += inc) {
            setValorVariable(varIndex, i);
            mult *= evaluate(arg);
        }
        varValor.remove(varIndex);
        return mult;
    }

    /**
     * Realiza una operacion aritmetica entre dos expresiones. <br>
     *
     * @param s1 - expresion1 <br>
     * @param operador - Operador <br>
     * @param s2 - expresion2 <br>
     * @return - valor de la operacion <br>
     */
    private double Operate(String s1, char operador, String s2) {
        double ret = 0;

        double v1 = evaluate(s1);
        double v2 = evaluate(s2);

        switch (operador) {
            case '+':
                ret = v1 + v2;
                break;

            case '-':
                ret = v1 - v2;
                break;

            case '*':
                ret = v1 * v2;
                break;

            case '/':
                ret = v1 / v2;
                break;

            case '^':
                ret = Math.pow(v1, v2);
                break;
        }

        return ret;
    }

    /**
     * Verifica si la expresion es una Funcion y retorna el nombre y la
     * expresion de parametro separados por punto y coma <br>
     *
     * @param expresion - expresion
     * @return - nombre;expresion
     */
    private String dataFunction(String expresion) {
        int i = expresion.indexOf("(");
        int j = expresion.length() - 1;

        if (expresion.charAt(j) != ')') {
            j = -1;
        }

        if (i < 0 || j == -1) {
            return null;
        }

        String data = expresion.substring(i + 1, j);
        if (!okParentesis(data)) {
            return null;
        }

        String name = expresion.substring(0, i);

        for (int k = 0; k < name.length(); k++) {
            if (NOCARAC.indexOf(expresion.substring(k, k + 1)) >= 0) {
                return null;
            }
        }

        return name.trim() + ";" + data;
    }

    /**
     * Elimina los parentesis y espacios innecesarios de una expresion. <br>
     *
     * @param str - expresion <br>
     * @return - expresion corregida <br>
     */
    private String eliminarParentesis(String str) {
        str = str.trim();

        if (str.length() > 1) {
            while ((str.charAt(0) == '(') && (str.charAt(str.length() - 1) == ')')) {
                String tt = str.substring(1, str.length() - 1);
                if (tt.isEmpty()) {
                    strError.append("Error: Parentesis Vacios! \n");
                    error = true;
                    return "1";
                }

                if (okParentesis(tt)) {
                    str = tt;
                } else {
                    return str;
                }
            }
        }
        return str;
    }

    /**
     * Define si los parentesis de una expresionestan bien! <br>
     * Una expresion esta bien definida con parentesis, si parea cada parenesis
     * que se abre existe un parentesis que se cierra. <br>
     *
     * @param str - expresion. <br>
     * @return - Retorna <code>true</code> si esta bien definido.
     */
    private boolean okParentesis(String str) {
        int par = 0;

        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '(') {
                par++;
            }
            if (str.charAt(i) == ')') {
                par--;
            }

            if (par < 0) {
                return false;
            }
        }

        return (par == 0);
    }

    /**
     * Define si una expresion es un numero <br>.
     *
     * @param expresion
     * @return Retorna <code>true</code> si es un numero. <br>
     */
    private boolean isNumber(String expresion) {

        boolean ret = false;
        try {
            double rr = Double.parseDouble(expresion);
            ret = true;

        } catch (NumberFormatException e) {
        }
        return ret;

    }

    private boolean isInteger(double num) {
        long integer = (long) num;
        return integer == num;
    }

    /**
     * Define si existo algun error en la ultima Evaluacion de expresion
     * <code>evaluate()</code>.<br>
     *
     * @return - Retorna <code>true</code> si se genero algun error. <br>
     */
    public boolean isError() {
        return error;
    }

    /**
     * Retorna la cadena con la especificacion de los errores ocurridos en la
     * evaluacion. <br>
     *
     * @return - Errores ocurridos. <br>
     */
    public String getErrores() {
        return strError.toString();
    }
    /**
     * Determina si hay error <br>
     */
    private boolean error;
    /**
     * String de errores. <br>
     */
    private StringBuilder strError;

    /**
     * Ver todos los errores. <br>
     *
     * @return - AllErrors <br>
     */
    public boolean getAllErrors() {
        return allErrors;
    }

    /**
     * Define si se van a mostrar todos los errores.
     *
     * @param allErrors - boolean <br>
     * @return -<code>true</code>
     */
    public boolean setAllErrors(boolean allErrors) {
        this.allErrors = allErrors;

        return true;
    }
    /**
     * Define si se van ha mostrar todos los errores <br>
     */
    private boolean allErrors = false;
}