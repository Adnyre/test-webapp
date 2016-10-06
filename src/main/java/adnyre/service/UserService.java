package adnyre.service;

import adnyre.model.*;
import java.util.List;

/**
 * Created by andrii.novikov on 06.10.2016.
 */
public interface UserService {
    public User getUserById(int id);

    public boolean deleteUser(int id);

    public List<User> getAllUsers();

    public void saveOrUpdateUser(User user) throws UserAlreadyExistsException;
}
