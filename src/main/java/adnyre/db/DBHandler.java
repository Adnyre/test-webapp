package adnyre.db;

import java.sql.*;

/**
 * Created by andrii.novikov on 05.10.2016.
 */
public class DBHandler {
    private volatile static Connection con;
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        if (con == null) {
            synchronized (DBHandler.class) {
                if (con == null) {
                    Class.forName("org.postgresql.Driver");
                    con = DriverManager.getConnection(
                            "jdbc:postgresql://localhost:5432/test_db", "postgres", "pka1x010P");
                }
            }
        }
        return con;
     }
}
