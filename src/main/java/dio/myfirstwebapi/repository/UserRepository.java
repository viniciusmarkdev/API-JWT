package dio.myfirstwebapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import dio.myfirstwebapi.model.User;



public interface UserRepository extends JpaRepository<User, Long> {
	
	@Query("SELECT u FROM tab_user u WHERE u.username = :username")
    public User findByUsername(@Param("username") String username);
    
    boolean existsByUsername(String username);
}