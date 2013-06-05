
package Entidad;

import java.util.ArrayList;

public class MatrizDatos {
 
    private ArrayList<DatoCelda> datosBloqueOrdenados[];
    
    public ArrayList<DatoCelda>[] getDatosBloqueOrdenados() {
        return datosBloqueOrdenados;
    }

    public void setDatosBloqueOrdenados(ArrayList<DatoCelda>[] datosBloqueOrdenados) {
        this.datosBloqueOrdenados = datosBloqueOrdenados;
    }
}
