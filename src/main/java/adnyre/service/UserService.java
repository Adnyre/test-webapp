package adnyre.service;

import adnyre.exceptions.UserAlreadyExistsException;
import adnyre.exceptions.UserNotFoundException;
import adnyre.model.User;

import java.util.List;

/**
 * Created by andrii.novikov on 06.10.2016.
 */
public interface UserService {
    public User getUserById(int id) throws UserNotFoundException;

    public void deleteUser(int id) throws UserNotFoundException;

    public List<User> getAllUsers();

    public void saveOrUpdateUser(User user) throws UserAlreadyExistsException;
}
