package tetris.db;

public class TestDbConnection {
    public static void main(String[] args) {
        try {
            if (DatabaseManager.getConnection() != null) {
                System.out.println("Conexión exitosa con la base de datos.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
