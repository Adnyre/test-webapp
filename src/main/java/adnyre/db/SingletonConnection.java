package adnyre.db;

import java.sql.*;

public class SingletonConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/test_db";
    private static final String USER_NAME = "postgres";
    private static final String PASSWORD = "pka1x010P";

    private static SingletonConnection instance;

    private Connection con;

    private SingletonConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static SingletonConnection getInstance() {
        if (instance == null) {
            synchronized (SingletonConnection.class) {
                if (instance == null) {
                    instance = new SingletonConnection();
                }
            }
        }
        return instance;
    }

    public Connection getCon() {
        return con;
    }

    @Override
    protected void finalize() throws Throwable {
        close();
    }

    public void close() {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
