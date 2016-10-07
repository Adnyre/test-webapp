package adnyre.dao;

import adnyre.db.SingletonConnection;
import adnyre.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    @Override
    public User getUserById(int id) throws SQLException {
        String query = "SELECT first_name AS fn, last_name AS ln FROM user_tbl WHERE id = ?";
        PreparedStatement stmt = SingletonConnection.getInstance().getCon().prepareStatement(query);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery(query);
        if (rs.next()) {
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
        return stmt.executeUpdate(query) > 0;
    }

    @Override
    public boolean deleteUser(int id) throws SQLException {
        String query =
                String.format("DELETE FROM user_tbl WHERE id=%d", id);
        Connection con = SingletonConnection.getInstance().getCon();
        Statement stmt = con.createStatement();
        return stmt.executeUpdate(query) > 0;
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        List<User> userList = new ArrayList<>();
        String query = "SELECT id, first_name, last_name AS fn, last_name AS ln FROM user_tbl";
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
                String.format("UPDATE user_tbl SET first_name='%s', last_name='%s' WHERE id=%d", fn, ln, userId);
        Connection con = SingletonConnection.getInstance().getCon();
        Statement stmt = con.createStatement();
        return stmt.executeUpdate(query) > 0;
    }

    @Override
    public boolean checkUserExistsByName(User user) throws SQLException {
        String query =
                String.format("SELECT id FROM user_tbl WHERE first_name='%s' AND last_name='%s'", user.getFirstName(),
                        user.getLastName());
        Connection con = SingletonConnection.getInstance().getCon();
        Statement stmt = con.createStatement();
        return stmt.executeQuery(query).next();
    }
}
