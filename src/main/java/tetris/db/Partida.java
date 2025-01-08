package tetris.db;

import java.sql.Timestamp;

public class Partida {
    private int id;
    private int puntaje;
    private boolean ganada;
    private Timestamp fecha;

    // Constructor
    public Partida(int id, int puntaje, boolean ganada, Timestamp fecha) {
        this.id = id;
        this.puntaje = puntaje;
        this.ganada = ganada;
        this.fecha = fecha;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    public boolean isGanada() {
        return ganada;
    }

    public void setGanada(boolean ganada) {
        this.ganada = ganada;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Partida{id=" + id + ", puntaje=" + puntaje + ", ganada=" + ganada + ", fecha=" + fecha + '}';
    }
}
