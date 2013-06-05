
package Entidad;

public class DatoCelda implements Comparable<DatoCelda> {

    private int j, k;
    private double valor;

    public DatoCelda(int j, int k, double valor) {
        this.j = j;
        this.k = k;
        this.valor=valor;
    }


    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
    
    public int compareTo( DatoCelda o ) {
        return new Double(valor).compareTo(o.valor);
    }
    
}
