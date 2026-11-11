package modelo;

import excepciones.NumeroEquiposInvalidoException;
import excepciones.SorteoInvalidoException;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Torneo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String tituloTorneo;
    private List<Equipo> listadoEquipos;
    private List<List<Partido>> fasesDelTorneo;

    public Torneo(String tituloTorneo) {
        this.tituloTorneo = tituloTorneo;
        this.listadoEquipos = new ArrayList<>();
        this.fasesDelTorneo = new ArrayList<>();
    }

    public void validarPotenciaDeDos() throws NumeroEquiposInvalidoException {
        int total = listadoEquipos.size();
        if (total != 16 && total != 8 && total != 4 && total != 2) {
            throw new NumeroEquiposInvalidoException("Cantidad de equipos no válida (" + total + "). Debe ser una potencia de dos (16, 8, 4, 2).");
        }
    }

    public void sortearTorneoRecursivo() throws NumeroEquiposInvalidoException, SorteoInvalidoException {
        validarPotenciaDeDos();
        fasesDelTorneo.clear();
        List<Equipo> copiaMezclada = new ArrayList<>(listadoEquipos);
        Collections.shuffle(copiaMezclada);

        List<Partido> partidosFaseActual = new ArrayList<>();
        ejecutarEmparejamientoRecursivo(copiaMezclada, partidosFaseActual);
    }

    private void ejecutarEmparejamientoRecursivo(List<Equipo> pendientes, List<Partido> acumulador) throws SorteoInvalidoException {
        if (pendientes.isEmpty()) {
            if (!acumulador.isEmpty()) {
                fasesDelTorneo.add(new ArrayList<>(acumulador));
            }
            return;
        }

        if (pendientes.size() < 2) {
            throw new SorteoInvalidoException("Error en la recursividad: quedó un equipo impar sin oponente.");
        }

        Equipo e1 = pendientes.remove(0);
        Equipo e2 = pendientes.remove(0);

        if (e1.getCodigo() == e2.getCodigo()) {
            throw new SorteoInvalidoException("Conflicto lógico: un equipo no puede emparejarse consigo mismo.");
        }

        acumulador.add(new Partido(e1, e2));
        ejecutarEmparejamientoRecursivo(pendientes, acumulador);
    }

    public void guardarTorneo(String rutaArchivo) throws IOException {
        try (ObjectOutputStream escritor = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
            escritor.writeObject(this);
        }
    }

    public static Torneo cargarTorneo(String rutaArchivo) throws IOException, ClassNotFoundException {
        try (ObjectInputStream lector = new ObjectInputStream(new FileInputStream(rutaArchivo))) {
            return (Torneo) lector.readObject();
        }
    }

    public String getTituloTorneo() {
        return tituloTorneo;
    }

    public List<Equipo> getEquipos() {
        return listadoEquipos;
    }

    public List<List<Partido>> getEtapasPartidos() {
        return fasesDelTorneo;
    }
}