package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataBaseConnection {
    private Connection connection;

    public void connect(){
        try {
            String url = "jdbc:postgresql://localhost:5432/bookstore_db";
            Properties props = new Properties();
            props.setProperty("user", "SEU_USUARIO");
            props.setProperty("password", "SUA_SENHA");
            this.connection = DriverManager.getConnection(url, props);
        } catch (SQLException e){
            throw new RuntimeException("Conexão ao banco de dados sem sucesso", e);
        }
    }
    public Connection getConnection() {
        return connection;
    }
}
