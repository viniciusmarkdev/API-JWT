package dio.myfirstwebapi.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dio.myfirstwebapi.dto.Login;
import dio.myfirstwebapi.dto.Sessao;
import dio.myfirstwebapi.model.User;
import dio.myfirstwebapi.repository.UserRepository;
import dio.myfirstwebapi.security.SecurityConfig;
import dio.myfirstwebapi.tokenJWT.JWTCreator;
import dio.myfirstwebapi.tokenJWT.JWTObject;

import java.util.Date;
@RestController
public class LoginController {
    @Autowired
    private PasswordEncoder encoder;
    
    @Autowired
    private SecurityConfig securityConfig;
    
    @Autowired
    private UserRepository repository;
    
    @PostMapping("/login")
    public Sessao logar(@RequestBody Login login){
    	
        User user = repository.findByUsername(login.getUserName());
        
        if(user==null) {
        	
        	 throw new RuntimeException("Usuário nulo ");
        }
        
        if(user!=null) {
           
        	boolean passwordOk =  encoder.matches(login.getPassword(), user.getPassword());
        	
            
            if (!passwordOk) {
            	
                throw new RuntimeException("Senha inválida para o login: " + login.getUserName());
            }
            //Estamos enviando um objeto Sessão para retornar mais informações do usuário
            Sessao sessao = new Sessao();
            sessao.setLogin(user.getUsername());

            JWTObject jwtObject = new JWTObject();
            jwtObject.setIssuedAt(new Date(System.currentTimeMillis()));
            jwtObject.setExpiration((new Date(System.currentTimeMillis() + SecurityConfig.EXPIRATION)));
            jwtObject.setRoles(user.getRoles());
            sessao.setToken(JWTCreator.create(SecurityConfig.PREFIX, SecurityConfig.KEY, jwtObject));
            return sessao;
            
        }else {
            throw new RuntimeException("Erro ao tentar fazer login");
        }
    }
}
