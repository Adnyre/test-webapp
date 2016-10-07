package adnyre.service;

import adnyre.dao.UserDAO;
import adnyre.exceptions.UserNotFoundException;
import adnyre.model.User;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    @Test(expected = UserNotFoundException.class)
    public void testExpectUserNotFoundExceptionInGetUserById() throws Exception {
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
            //verify
            verify(mockDao, times(1)).getUserById(0);

            throw e;
        }
    }

    @Test(expected = RuntimeException.class)
    public void testExpectRuntimeExceptionInGetUserById() throws Exception {
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
            //verify
            verify(mockDao, times(1)).getUserById(0);

            throw e;
        }
    }

    @Test
    public void testExpectReturnUserInGetUserById() throws Exception {
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

        //verify
        verify(mockDao, times(1)).getUserById(0);
    }
}