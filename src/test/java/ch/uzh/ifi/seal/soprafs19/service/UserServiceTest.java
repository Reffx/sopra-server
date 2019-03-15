package ch.uzh.ifi.seal.soprafs19.service;

import ch.uzh.ifi.seal.soprafs19.Application;
import ch.uzh.ifi.seal.soprafs19.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.repository.UserRepository;
import ch.uzh.ifi.seal.soprafs19.controller.NonExistentUserException;
import ch.uzh.ifi.seal.soprafs19.controller.DuplicateException;
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
public class UserServiceTest {


    @Qualifier("userRepository")
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    public void createUser() {
        Assert.assertNull(userRepository.findByUsername("testUsername"));
        User testUser = new User();
        testUser.setUsername("testUsername");
        testUser.setPassword("test");
        testUser.setBirthday("16.03.1994");
        testUser.setDate("17-02-2019");

        User createdUser = userService.createUser(testUser);

        Assert.assertNotNull(createdUser.getToken());
        Assert.assertEquals(createdUser.getStatus(),UserStatus.OFFLINE);
        Assert.assertEquals(createdUser, userRepository.findByToken(createdUser.getToken()));
        userRepository.deleteAll();
    }

    @Test(expected = NonExistentUserException.class)
    public void checkUserErr() {
        userRepository.deleteAll();
        User testUser = new User();

        userService.checkUser(testUser); //try to log in a user that wasn't created before
    }

    @Test(expected = DuplicateException.class)
    public void createUserErr() {
        userRepository.deleteAll();
        User testUser = new User();
        testUser.setUsername("testUsername");
        testUser.setPassword("test");
        testUser.setBirthday("16.03.1994");
        testUser.setDate("17-02-2019");
        User createdUser = userService.createUser(testUser);

        User testUser2 = new User();
        testUser2.setUsername("testUsername");
        testUser2.setPassword("test2");
        testUser2.setBirthday("16.03.1995");
        testUser2.setDate("17-02-2019");
        User createdUser2 = userService.createUser(testUser2);
    }


}
