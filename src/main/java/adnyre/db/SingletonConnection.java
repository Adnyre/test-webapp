package adnyre.db;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class SingletonConnection {

    private static SingletonConnection instance;

    private Connection con;

    private SingletonConnection() {
        try {
            Properties props = new Properties();
            InputStream is = SingletonConnection.class.getClassLoader().getResourceAsStream("db" + File.separator
                    + "db_credentials.properties");
            props.load(is);
            Class.forName(props.getProperty("db.driver"));
            con = DriverManager.getConnection(props.getProperty("db.url"), props.getProperty("db.user_name"),
                    props.getProperty("db.password"));
        } catch (ClassNotFoundException | IOException e) {
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
