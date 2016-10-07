package adnyre.dao;

import adnyre.db.SingletonConnection;
import adnyre.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    @Override
    public User getUserById(int id) throws SQLException {
        String query = "SELECT first_name, last_name FROM user_tbl WHERE id = ?";
        PreparedStatement stmt = SingletonConnection.getInstance().getCon().prepareStatement(query);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");
            return new User(id, firstName, lastName);
        } else {
            return null;
        }
    }

    @Override
    public boolean saveUser(User user) throws SQLException {
        String fn = user.getFirstName();
        String ln = user.getLastName();
        String query = "INSERT INTO user_tbl (first_name, last_name) VALUES (?, ?)";
        PreparedStatement stmt = SingletonConnection.getInstance().getCon().prepareStatement(query);
        stmt.setString(1, fn);
        stmt.setString(2, ln);
        return stmt.executeUpdate() > 0;
    }

    @Override
    public boolean deleteUser(int id) throws SQLException {
        String query = "DELETE FROM user_tbl WHERE id=?";
        PreparedStatement stmt = SingletonConnection.getInstance().getCon().prepareStatement(query);
        stmt.setInt(1, id);
        return stmt.executeUpdate() > 0;
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        List<User> userList = new ArrayList<>();
        String query = "SELECT id, first_name, last_name FROM user_tbl";
        PreparedStatement stmt = SingletonConnection.getInstance().getCon().prepareStatement(query);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            int userId = rs.getInt("id");
            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");
            userList.add(new User(userId, firstName, lastName));
        }
        return userList;
    }

    @Override
    public boolean updateUser(User user) throws SQLException {
        int userId = user.getId();
        String fn = user.getFirstName();
        String ln = user.getLastName();
        String query = "UPDATE user_tbl SET first_name=?, last_name=? WHERE id=?";
        PreparedStatement stmt = SingletonConnection.getInstance().getCon().prepareStatement(query);
        stmt.setString(1, fn);
        stmt.setString(2, ln);
        stmt.setInt(3, userId);
        return stmt.executeUpdate() > 0;
    }

    @Override
    public boolean checkUserExistsByName(User user) throws SQLException {
        String query = "SELECT id FROM user_tbl WHERE first_name=? AND last_name=?";
        PreparedStatement stmt = SingletonConnection.getInstance().getCon().prepareStatement(query);
        stmt.setString(1, user.getFirstName());
        stmt.setString(2, user.getLastName());
        return stmt.executeQuery().next();
    }
}
