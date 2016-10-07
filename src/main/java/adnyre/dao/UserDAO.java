package adnyre.dao;

import adnyre.model.User;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by andrii.novikov on 06.10.2016.
 */
public interface UserDAO {
    User getUserById(int id) throws SQLException;

    boolean saveUser(User user) throws SQLException;

    boolean deleteUser(int id) throws SQLException;

    List<User> getAllUsers() throws SQLException;

    boolean updateUser(User user) throws SQLException;

    boolean checkUserExistsByName(User user) throws SQLException;
}
