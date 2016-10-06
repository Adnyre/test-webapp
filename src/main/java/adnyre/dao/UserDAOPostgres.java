package adnyre.dao;

import adnyre.db.SingletonConnection;
import adnyre.model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrii.novikov on 06.10.2016.
 */
public class UserDAOPostgres implements UserDAO {

    @Override
    public User getUserById(int id) throws SQLException {
        String query =
                String.format("SELECT first_name AS fn, last_name AS ln FROM user_tbl WHERE id = %s", id);
        Connection con = SingletonConnection.getInstance().getCon();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        if (rs.next()){
            String firstName = rs.getString("fn");
            String lastName = rs.getString("ln");
            return new User(id, firstName, lastName);
        } else {
            return null;
        }
    }

    @Override
    public boolean saveUser(User user) throws SQLException {
        String fn = user.getFirstName();
        String ln = user.getLastName();
        String query =
                String.format("INSERT INTO user_tbl (first_name, last_name) VALUES ('%s', '%s')", fn, ln);
        Connection con = SingletonConnection.getInstance().getCon();
        Statement stmt = con.createStatement();
        if (stmt.executeUpdate(query) > 0){
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteUser(int id) throws SQLException {
        String query =
                String.format("DELETE FROM user_tbl WHERE id=%d", id);
        Connection con = SingletonConnection.getInstance().getCon();
        Statement stmt = con.createStatement();
        if (stmt.executeUpdate(query) > 0){
            return true;
        }
        return false;
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        List<User> userList = new ArrayList<>();
        String query =
                String.format("SELECT id, first_name, last_name AS fn, last_name AS ln FROM user_tbl");
        Connection con = SingletonConnection.getInstance().getCon();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            int userId = rs.getInt("id");
            String firstName = rs.getString("fn");
            String lastName = rs.getString("ln");
            userList.add(new User(userId, firstName, lastName));
        }
        return userList;
    }

    @Override
    public boolean updateUser(User user) throws SQLException {
            int userId = user.getId();
            String fn = user.getFirstName();
            String ln = user.getLastName();
            String query =
                    String.format("UPDATE user_tbl first_name='%s', last_name='%s' WHERE id=%d", fn, ln, userId);
            Connection con = SingletonConnection.getInstance().getCon();
            Statement stmt = con.createStatement();
            if (stmt.executeUpdate(query) > 0){
                return true;
            }
            return false;
    }

    @Override
    public boolean checkUserExistsByName(User user) throws SQLException {
        String query =
                String.format("SELECT id FROM user_tbl WHERE first_name='%s' AND last_name='%s'", user.getFirstName(),
                        user.getLastName());
        Connection con = SingletonConnection.getInstance().getCon();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        return rs.next();
    }

    public static void main(String[] args) throws SQLException{
        UserDAO dao = new UserDAOPostgres();

    }
}
