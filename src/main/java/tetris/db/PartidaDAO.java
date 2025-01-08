package tetris.db;

import java.sql.*;
import java.util.LinkedList;

public class PartidaDAO {
    private final Connection connection;

    public PartidaDAO() {
        this.connection = DatabaseManager.getConnection();
    }

    public boolean createPartida(int puntaje, boolean ganada, String username) {
        String query = "INSERT INTO partida (puntaje, ganada, username) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, puntaje);
            stmt.setBoolean(2, ganada);
            stmt.setString(3, username);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public LinkedList<Partida> getPartidasByUser(String username) {
        LinkedList<Partida> partidas = new LinkedList<>();
        String query = "SELECT * FROM partida WHERE username = ? ORDER BY fecha DESC"; // Ordenar por fecha o puntaje
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id_partida");
                int puntaje = rs.getInt("puntaje");
                boolean ganada = rs.getBoolean("ganada");
                Timestamp fecha = rs.getTimestamp("fecha");
                partidas.add(new Partida(id, puntaje, ganada, fecha));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return partidas;
    }

    // Método para ordenar las partidas (por fecha o puntaje)
    public LinkedList<Partida> getAllPartidasOrdered(String sortBy) {
        LinkedList<Partida> partidas = new LinkedList<>();
        String query = "SELECT * FROM partida ORDER BY " + sortBy; // Ordena por fecha o puntaje
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id_partida");
                int puntaje = rs.getInt("puntaje");
                boolean ganada = rs.getBoolean("ganada");
                Timestamp fecha = rs.getTimestamp("fecha");
                partidas.add(new Partida(id, puntaje, ganada, fecha));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return partidas;
    }
}
