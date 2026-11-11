package controlador;

import excepciones.NumeroEquiposInvalidoException;
import excepciones.SorteoInvalidoException;
import modelo.Equipo;
import modelo.Partido;
import modelo.Torneo;

import java.io.IOException;
import java.util.List;

public class ControladorTorneo {
    private Torneo torneoActivo;

    public ControladorTorneo() {
        this.torneoActivo = new Torneo("Torneo Oficial de Eliminación Directa");
    }

    public void limpiarEquipos() {
        torneoActivo.getEquipos().clear();
    }

    public void agregarEquipo(int id, String nombre) {
        torneoActivo.getEquipos().add(new Equipo(id, nombre));
    }

    public void procesarSorteo() throws NumeroEquiposInvalidoException, SorteoInvalidoException {
        torneoActivo.sortearTorneoRecursivo();
    }

    public List<List<Partido>> consultarEtapasTorneo() {
        return torneoActivo.getEtapasPartidos();
    }

    public List<Equipo> obtenerListaEquipos() {
        return torneoActivo.getEquipos();
    }

    public void exportarDatosBinarios(String rutaArchivo) throws IOException {
        torneoActivo.guardarTorneo(rutaArchivo);
    }

    public void importarDatosBinarios(String rutaArchivo) throws IOException, ClassNotFoundException {
        Torneo torneoRecuperado = Torneo.cargarTorneo(rutaArchivo);
        if (torneoRecuperado != null) {
            this.torneoActivo = torneoRecuperado;
        }
    }
}