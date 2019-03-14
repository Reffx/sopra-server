package ch.uzh.ifi.seal.soprafs19.service;

import ch.uzh.ifi.seal.soprafs19.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs19.controller.DuplicateException;
import ch.uzh.ifi.seal.soprafs19.controller.NonexistentUserException;
import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public Iterable<User> getUsers() {
        return this.userRepository.findAll();
    }


    //registration
    public User createUser(User newUser) {
        if(userRepository.findByUsername(newUser.getUsername())!=null) {
            throw new DuplicateException("Duplicate Exception with Username: "+newUser.getUsername());
        }
        newUser.setToken(UUID.randomUUID().toString());
        newUser.setStatus(UserStatus.OFFLINE);
        newUser.setBirthday(newUser.getBirthday());
        userRepository.save(newUser);
        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }

    //for login
    public User checkUser(User newUser) {
        //fetches a user with the password/null and also checks if that user has the same username -> fixes error of logging with pw of diff user
        User loginUser = userRepository.findByPassword(newUser.getPassword());
        if(loginUser != null && loginUser.getUsername().equals(newUser.getUsername())) {
            User tempUser = userRepository.findByUsername(newUser.getUsername());
            tempUser.setStatus(UserStatus.ONLINE);
            tempUser.setToken(UUID.randomUUID().toString());
            userRepository.save(tempUser);
            return tempUser;
        }
        throw new NonexistentUserException("Name: "+newUser.getPassword()+" Username: "+newUser.getUsername());
    }

    //for logout
    public User logoutUser(User newUser) {
        User tempUser = userRepository.findByToken(newUser.getToken());
        tempUser.setStatus(UserStatus.OFFLINE);
        userRepository.save(tempUser);
        return tempUser;
    }

    //for displaying profile
    public User getUser(long id) {
        User tempUser = userRepository.findById(id);
        if(tempUser !=null) {
            return tempUser;
        }
        else {
            throw new NonexistentUserException("");
        }
    }

    //for updating the Username and or Birthday
    public User updateUser(User newUser) {
        User tempUser = userRepository.findByToken(newUser.getToken());
        if(userRepository.findByUsername(newUser.getUsername()) != null && userRepository.findByUsername(newUser.getUsername())!=tempUser) {
            throw new DuplicateException("User with that Username already exists, cannot change to it.");
        }
        else {
            tempUser.setBirthday(newUser.getBirthday());
            tempUser.setUsername(newUser.getUsername());
            userRepository.save(tempUser);
            return newUser;
        }
    }

}
