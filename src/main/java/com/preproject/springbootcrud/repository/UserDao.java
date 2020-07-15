package com.preproject.springbootcrud.repository;

import com.preproject.springbootcrud.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


import javax.transaction.Transactional;
import java.util.List;

public interface UserDao extends JpaRepository<User, Long> {
    List<User> findByLastName(String lastName);
    User findByUserName(String userName);

    @Modifying
    @Query("update User u set u = ?2 where u.id = ?1")
    int updateUserById(long id, User user);

    @Modifying
    @Transactional
    void deleteUserById(long id);
}
