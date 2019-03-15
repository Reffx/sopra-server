package ch.uzh.ifi.seal.soprafs19.controller;

import ch.uzh.ifi.seal.soprafs19.Application;
import ch.uzh.ifi.seal.soprafs19.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.repository.UserRepository;
import ch.uzh.ifi.seal.soprafs19.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Test class for the UserResource REST resource.
 *
 * @see UserService
 */
@WebAppConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(classes= Application.class)
public class UserControllerTest {


    @Qualifier("userRepository")
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserController userController;

    @Test
    public void checkUser() {
        userRepository.deleteAll();
        User testUser = new User();
        testUser.setUsername("testUsername");
        testUser.setPassword("test");
        testUser.setBirthday("16.03.1994");
        testUser.setDate("17-02-2019");
        userController.createUser(testUser);
        Assert.assertEquals(testUser.getStatus(), UserStatus.OFFLINE);
        testUser = userController.checkUser(testUser);
        Assert.assertEquals(testUser.getStatus(), UserStatus.ONLINE);
    }

    @Test
    public void logoutUser() {
        User testUser = new User();
        testUser.setUsername("testUsername");
        testUser.setPassword("test");
        testUser.setBirthday("16.03.1994");
        testUser.setDate("17-02-2019");
        userController.createUser(testUser);
        Assert.assertEquals(testUser.getStatus(), UserStatus.OFFLINE);
        testUser = userController.checkUser(testUser);
        Assert.assertEquals(testUser.getStatus(), UserStatus.ONLINE);
        testUser = userController.logoutUser(testUser);
        Assert.assertEquals(testUser.getStatus(), UserStatus.OFFLINE);
        userRepository.deleteAll();
    }

    @Test
    public void getUser() {
        userRepository.deleteAll();
        User testUser = new User();
        testUser.setPassword("test");
        testUser.setUsername("testUsername");
        testUser.setBirthday("16.03.1994");
        testUser.setDate("17-02-2019");

        userController.createUser(testUser);
        String id = String.valueOf(userRepository.findByUsername("testUsername").getId());
        Assert.assertEquals(userController.getUser(id), testUser);
        userRepository.deleteAll();
    }

}
