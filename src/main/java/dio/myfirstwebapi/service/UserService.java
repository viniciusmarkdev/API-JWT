package dio.myfirstwebapi.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dio.myfirstwebapi.model.User;
import dio.myfirstwebapi.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;
    
    @Autowired
    private PasswordEncoder encoder;
    
    public void createUser(User user){
        String pass = user.getPassword();
        //criptografando antes de salvar no banco
        user.setPassword(encoder.encode(pass));
        
        repository.save(user);
    }
}
