package adnyre.service;

import adnyre.dao.UserDAO;
import adnyre.exceptions.UserAlreadyExistsException;
import adnyre.exceptions.UserNotFoundException;
import adnyre.model.User;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by andrii.novikov on 06.10.2016.
 */
public class UserServiceImpl implements UserService {

    private UserDAO dao;

    public UserServiceImpl(UserDAO dao) {
        this.dao = dao;
    }

    public User getUserById(int id) throws UserNotFoundException {
        try {
            User user = dao.getUserById(id);
            if (user == null)
                throw new UserNotFoundException();
            else
                return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteUser(int id) throws UserNotFoundException {
        try {
            if (!dao.deleteUser(id))
                throw new UserNotFoundException();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        try {
            return dao.getAllUsers();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveOrUpdateUser(User user) throws UserAlreadyExistsException {
        try {
            if (user.getId() != 0) {
                dao.updateUser(user);
            } else {
                if (dao.checkUserExistsByName(user)) {
                    throw new UserAlreadyExistsException();
                } else {
                    dao.saveUser(user);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
