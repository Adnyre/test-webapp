package adnyre.db;

import java.sql.*;

/**
 * Created by andrii.novikov on 05.10.2016.
 */
public class DBHandler {
    private static String url = "jdbc:postgresql://localhost:5432/test_db";
    private static String userName = "postgres";
    private static String pswd = "pka1x010P";

    private volatile static Connection con;
    public static Connection getConnection() {
        if (con == null) {
            synchronized (DBHandler.class) {
                if (con == null) {
                    try {
                        Class.forName("org.postgresql.Driver");
                        con = DriverManager.getConnection(
                                url, userName, pswd);
                    } catch (ClassNotFoundException|SQLException e) {
                        System.err.println(e);
                    }
                }
            }
        }
        return con;
     }
}
