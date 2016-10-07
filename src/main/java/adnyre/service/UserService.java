package adnyre.service;

import adnyre.dao.UserDAO;
import adnyre.exceptions.UserAlreadyExistsException;
import adnyre.exceptions.UserNotFoundException;
import adnyre.model.User;

import java.util.List;

public interface UserService {

    void setUserDao(UserDAO dao);

    User getUserById(int id) throws UserNotFoundException;

    void deleteUser(int id) throws UserNotFoundException;

    List<User> getAllUsers();

    void saveOrUpdateUser(User user) throws UserAlreadyExistsException;
}
