package dio.myfirstwebapi.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import dio.myfirstwebapi.model.User;


import dio.myfirstwebapi.repository.UserRepositoryTest;
import dio.myfirstwebapi.service.UserService;

import java.util.List;
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepositoryTest repository;
    
    @Autowired
    private UserService service;
    
    @PostMapping("/postar")
    public void postUser(@RequestBody User user) {
    	
    	service.createUser(user);
    	
    }
    
    @GetMapping
    public List<User> list(){
        return repository.findAll();
    }
 
    @PutMapping
    public void update(@RequestBody User user){
        repository.save(user);
    }
    @GetMapping("/{username}")
    public User find(@PathVariable("/username") String username){
        return repository.findByUsername(username);
    }
    
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id){
        repository.deleteById(id);
    }
    
  
    
   
}

