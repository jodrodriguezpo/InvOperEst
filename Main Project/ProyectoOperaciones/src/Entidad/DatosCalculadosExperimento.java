
package Entidad;

public class DatosCalculadosExperimento {
    
    private DatosEstadisticaPrueba datosEstadisticaRango;
    private DatosEstadisticaSecuencias datosEstadisticaSecuencias;
    private double matrizXijk[][][];
    private int n, m, n_j[], ni_[], n__;
    private double matrizXij_[][];
    private double vectorXi__[];
    private double vectorX_j_[];
    private double X___;

    public DatosEstadisticaPrueba getDatosEstadisticaRango() {
        return datosEstadisticaRango;
    }

    public void setDatosEstadisticaRango(DatosEstadisticaPrueba datosEstadisticaRango) {
        this.datosEstadisticaRango = datosEstadisticaRango;
    }

    public DatosEstadisticaSecuencias getDatosEstadisticaSecuencias() {
        return datosEstadisticaSecuencias;
    }

    public void setDatosEstadisticaSecuencias(DatosEstadisticaSecuencias datosEstadisticaSecuencias) {
        this.datosEstadisticaSecuencias = datosEstadisticaSecuencias;
    }

    public double[][][] getMatrizXijk() {
        return matrizXijk;
    }

    public void setMatrizXijk(double[][][] matrizXijk) {
        this.matrizXijk = matrizXijk;
        n=matrizXijk.length;
        m=matrizXijk[0].length;
    }

    public int getN() {
        return n;
    }

    public int getM() {
        return m;
    }

    public int[] getN_j() {
        return n_j;
    }

    public void setN_j(int[] n_j) {
        this.n_j = n_j;
    }

    public int[] getNi_() {
        return ni_;
    }

    public void setNi_(int[] ni_) {
        this.ni_ = ni_;
    }

    public double[][] getMatrizXij_() {
        return matrizXij_;
    }

    public void setMatrizXij_(double[][] matrizXij_) {
        this.matrizXij_ = matrizXij_;
    }

    public double[] getVectorXi__() {
        return vectorXi__;
    }

    public void setVectorXi__(double[] vectorXi__) {
        this.vectorXi__ = vectorXi__;
    }

    public double[] getVectorX_j_() {
        return vectorX_j_;
    }

    public void setVectorX_j_(double[] vectorX_j_) {
        this.vectorX_j_ = vectorX_j_;
    }

    public double getX___() {
        return X___;
    }

    public void setX___(double X___) {
        this.X___ = X___;
    }

    public int getN__() {
        return n__;
    }

    public void setN__(int n__) {
        this.n__ = n__;
    }
}
