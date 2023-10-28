package dio.myfirstwebapi.handler;

import java.util.Objects;

public class CampoObrigatorioException extends  BusinessException{

	public CampoObrigatorioException(String campo) {
		super("O campo %s é obrigatório " , campo);
		// TODO Auto-generated constructor stub
	}
	
	

}
