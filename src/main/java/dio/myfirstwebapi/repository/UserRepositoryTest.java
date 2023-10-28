package dio.myfirstwebapi.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import dio.myfirstwebapi.handler.BusinessException;
import dio.myfirstwebapi.handler.CampoObrigatorioException;
import dio.myfirstwebapi.model.User;


@Repository
public class UserRepositoryTest {
	
	 public void save(User usuario){
		 
		  if(usuario.getUsername()==null) {
			 
			  throw new  CampoObrigatorioException("O campo login é obrigatório");
		 }
		  
		  if(usuario.getPassword()==null) {
				 
			  throw new  CampoObrigatorioException("O campo login é obrigatório");
		 }
		 
	        if(usuario.getId()==null)
	          System.out.println("SAVE - Recebendo o usuário na camada de repositório");
	        else
	         System.out.println("UPDATE - Recebendo o usuário na camada de repositório");
	        
	        System.out.println(usuario);
	    }
	 
	    public void deleteById(Integer id){
	        System.out.println(String.format("DELETE/id - Recebendo o id: %d para excluir um usuário", id));
	        System.out.println(id);
	    }
	    
	    public List<User> findAll(){
	        System.out.println("LIST - Listando os usários do sistema");
	        List<User> usuarios = new ArrayList<>();
	        usuarios.add(new User());
	        usuarios.add(new User("frank","masterpass"));
	        return usuarios;
	    }
	    public User findById(Integer id){
	        System.out.println(String.format("FIND/id - Recebendo o id: %d para localizar um usuário", id));
	        return new User("gleyson","password");
	    }
	     public User findByUsername(String username){
	        System.out.println(String.format("FIND/username - Recebendo o usernamae: %s para localizar um usuário", username));
	        return new User("gleyson","password");
	    }
	

}
