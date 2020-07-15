package com.preproject.springbootcrud.service;

import com.preproject.springbootcrud.model.User;
import com.preproject.springbootcrud.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService implements UserDetailsService{

    public final UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User findById(Long id){
        return userDao.getOne(id);
    }

    public List<User> findAll(){
        return userDao.findAll();
    }

    public User saveUser(User user){
        if (getUserByName(user.getUsername()) == null){
            userDao.save(user);
        }
        return null;
    }

    public User getUserByName(String name){
        return userDao.findByUserName(name);
    }

    public void updateUserById(long id, User user){
        if (getUserByName(user.getUsername()) == null){
            userDao.save(user);
        }
    }

    public void deleteUserById(long id){
        userDao.deleteUserById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return userDao.findByUserName(userName);
    }
}
