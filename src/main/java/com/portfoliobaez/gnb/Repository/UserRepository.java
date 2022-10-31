
package com.portfoliobaez.gnb.Repository;

import com.portfoliobaez.gnb.Entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer>{
    Optional<User> findByUsernameAndPassword(String username, String password);
    
}
