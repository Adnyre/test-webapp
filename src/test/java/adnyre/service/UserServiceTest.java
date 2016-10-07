package adnyre.service;

import adnyre.dao.UserDAO;
import adnyre.exceptions.UserAlreadyExistsException;
import adnyre.exceptions.UserNotFoundException;
import adnyre.model.User;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    @Test(expected = UserNotFoundException.class)
    public void expectUserNotFoundExceptionInGetUserByIdTest() throws Exception {
        //given
        UserDAO mockDao = mock(UserDAO.class);
        UserService instance = new UserServiceImpl();
        instance.setUserDao(mockDao);

        //when
        when(mockDao.getUserById(0)).thenReturn(null);

        //then
        try {
            instance.getUserById(0);
        } catch (UserNotFoundException e) {
            verify(mockDao).getUserById(0);

            throw e;
        }
    }

    @Test(expected = RuntimeException.class)
    public void expectRuntimeExceptionInGetUserByIdTest() throws Exception {
        //given
        UserDAO mockDao = mock(UserDAO.class);
        UserService instance = new UserServiceImpl();
        instance.setUserDao(mockDao);

        //when
        when(mockDao.getUserById(0)).thenThrow(new SQLException());

        //then
        try {
            instance.getUserById(0);
        } catch (RuntimeException e) {
            verify(mockDao).getUserById(0);

            throw e;
        }
    }

    @Test
    public void getUserByIdTest() throws Exception {
        //given
        UserDAO mockDao = mock(UserDAO.class);
        User mockUser = mock(User.class);
        UserService instance = new UserServiceImpl();
        instance.setUserDao(mockDao);

        //when
        when(mockDao.getUserById(0)).thenReturn(mockUser);

        //then
        User returned = instance.getUserById(0);
        assertSame(returned, mockUser);
        verify(mockDao).getUserById(0);
    }

    @Test
    public void deleteUserTest() throws Exception {
        //given
        UserDAO mockDao = mock(UserDAO.class);
        UserService instance = new UserServiceImpl();
        instance.setUserDao(mockDao);

        //when
        when(mockDao.deleteUser(0)).thenReturn(true);

        //then
        instance.deleteUser(0);
        verify(mockDao).deleteUser(0);
    }

    @Test(expected = UserNotFoundException.class)
    public void expectUserNotFoundExceptionInDeleteUserTest() throws Exception {
        //given
        UserDAO mockDao = mock(UserDAO.class);
        UserService instance = new UserServiceImpl();
        instance.setUserDao(mockDao);

        //when
        when(mockDao.deleteUser(0)).thenReturn(false);

        //then
        try {
            instance.deleteUser(0);
        } catch (UserNotFoundException e) {
            verify(mockDao).deleteUser(0);
            throw e;
        }
    }

    @Test(expected = RuntimeException.class)
    public void expectRuntimeExceptionInDeleteUserTest() throws Exception {
        //given
        UserDAO mockDao = mock(UserDAO.class);
        UserService instance = new UserServiceImpl();
        instance.setUserDao(mockDao);

        //when
        when(mockDao.deleteUser(0)).thenThrow(new SQLException());

        //then
        try {
            instance.deleteUser(0);
        } catch (RuntimeException e) {
            verify(mockDao).deleteUser(0);
            throw e;
        }
    }

    @Test(expected = RuntimeException.class)
    public void expectRuntimeExceptionInGetAllUsersTest() throws Exception {
        //given
        UserDAO mockDao = mock(UserDAO.class);
        UserService instance = new UserServiceImpl();
        instance.setUserDao(mockDao);

        //when
        when(mockDao.getAllUsers()).thenThrow(new SQLException());

        //then
        try {
            instance.getAllUsers();
        } catch (RuntimeException e) {
            verify(mockDao).getAllUsers();
            throw e;
        }
    }

    @Test
    public void getAllUsersTest() throws Exception {
        //given
        UserDAO mockDao = mock(UserDAO.class);
        @SuppressWarnings("unchecked")
        List<User> mockList = (List<User>) mock(List.class);
        UserService instance = new UserServiceImpl();
        instance.setUserDao(mockDao);

        //when
        when(mockDao.getAllUsers()).thenReturn(mockList);

        //then
        List<User> result = instance.getAllUsers();
        assertSame(mockList, result);
    }

    @Test(expected = UserAlreadyExistsException.class)
    public void expectUserAlreadyExistsExceptionInSaveOrUpdateUserTest() throws Exception {
        //given
        UserDAO mockDao = mock(UserDAO.class);
        User mockUser = mock(User.class);
        UserService instance = new UserServiceImpl();
        instance.setUserDao(mockDao);

        //when
        when(mockUser.getId()).thenReturn(0);
        when(mockDao.checkUserExistsByName(mockUser)).thenReturn(true);

        //then
        try {
            instance.saveOrUpdateUser(mockUser);
        } catch (UserAlreadyExistsException e) {
            verify(mockDao).checkUserExistsByName(mockUser);
            verifyNoMoreInteractions(mockDao);
            throw e;
        }
    }

    @Test
    public void updateUserTest() throws Exception {
        //given
        UserDAO mockDao = mock(UserDAO.class);
        User mockUser = mock(User.class);
        UserService instance = new UserServiceImpl();
        instance.setUserDao(mockDao);

        //when
        when(mockUser.getId()).thenReturn(1);

        //then
        instance.saveOrUpdateUser(mockUser);
        verify(mockDao).updateUser(mockUser);
        verifyNoMoreInteractions(mockDao);
    }

    @Test
    public void saveUserTest() throws Exception {
        //given
        UserDAO mockDao = mock(UserDAO.class);
        User mockUser = mock(User.class);
        UserService instance = new UserServiceImpl();
        instance.setUserDao(mockDao);

        //when
        when(mockUser.getId()).thenReturn(0);
        when(mockDao.checkUserExistsByName(mockUser)).thenReturn(false);

        //then
        instance.saveOrUpdateUser(mockUser);
        verify(mockDao).saveUser(mockUser);
    }

    @Test(expected = RuntimeException.class)
    public void expectRuntimeExceptionInSaveOrUpdateUserTest() throws Exception {
        //given
        UserDAO mockDao = mock(UserDAO.class);
        User mockUser = mock(User.class);
        UserService instance = new UserServiceImpl();
        instance.setUserDao(mockDao);

        //when
        when(mockUser.getId()).thenReturn(1);
        when(mockDao.updateUser(mockUser)).thenThrow(new SQLException());

        //then
        try {
            instance.saveOrUpdateUser(mockUser);
        } catch (RuntimeException e) {
            verify(mockDao).updateUser(mockUser);
            verifyNoMoreInteractions(mockDao);
            throw e;
        }
    }
}