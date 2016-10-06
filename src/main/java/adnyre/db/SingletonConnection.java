package adnyre.db;

import java.sql.*;
import java.io.*;
import java.util.Properties;

public class SingletonConnection {

    private static SingletonConnection instance;

    private Connection con;

    private SingletonConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            InputStream is = SingletonConnection.class.getClassLoader().getResourceAsStream("db/db_credentials.properties");
            Properties props = new Properties();
            props.load(is);
            con = DriverManager.getConnection(props.getProperty("url"), props.getProperty("user_name"),
                    props.getProperty("password"));
        } catch (ClassNotFoundException|IOException e) {
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
