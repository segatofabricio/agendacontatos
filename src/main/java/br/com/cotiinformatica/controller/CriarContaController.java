package br.com.cotiinformatica.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.cotiinformatica.entities.Usuario;
import br.com.cotiinformatica.repositories.UsuarioRepository;

@Controller
public class CriarContaController {

	@RequestMapping(value = "/criarconta") // URL da p�gina
	public ModelAndView criarConta() {

		// WEB-INF/views/criarconta.jsp
		ModelAndView modelAndView = new ModelAndView("criarconta");
		return modelAndView;
	}

	
	//---------------------------------------------------------------------------------------
	
	
	//m�todo para capturar os dados enviados pelo formul�rio
	@RequestMapping(value = "/cadastrar-usuario", method = RequestMethod.POST) 
	public ModelAndView cadastrarUsuario(HttpServletRequest request) {

		// WEB-INF/views/criarconta.jsp
		ModelAndView modelAndView = new ModelAndView("criarconta");
		
		try {
			
			Usuario usuario = new Usuario();
			
			//capturar os dados enviados pelo formul�rio
			usuario.setNome(request.getParameter("nome"));
			usuario.setEmail(request.getParameter("email"));
			usuario.setSenha(request.getParameter("senha"));
			
			//gravando o usu�rio no banco de dados
			
			UsuarioRepository usuarioRepository = new UsuarioRepository();
	//-------------------------------------------------------------------------------		
			//verificar se o email ja está cadastrado
			
			if (usuarioRepository.findByEmail(usuario.getEmail()) != null) {
				
				modelAndView.addObject("mensagem", "O email " + usuario.getEmail() + ", já está cadastrado no sistema. Tente outro.");

			}
			
			else {
				
				usuarioRepository.create(usuario);
				
				modelAndView.addObject("mensagem", "Parab�ns " + usuario.getNome() + ", sua conta foi criada com sucesso!");
			}
	//-------------------------------------------------------------------------------------		
			
		}
		catch(Exception e) {
			modelAndView.addObject("mensagem", "Falha ao cadastrar: " + e.getMessage());
		}
		
		return modelAndView;
	}
}
