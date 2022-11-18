package br.com.cotiinformatica.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.cotiinformatica.entities.Usuario;
import br.com.cotiinformatica.repositories.UsuarioRepository;

@Controller
public class AcessarContaController {

	// m�todo para mapear qual � a p�gina que o controlador
	// ir� abrir / gerenciar no projeto
	
	
	@RequestMapping(value = "/") // p�gina raiz do projeto
	
	
	public ModelAndView acessarConta() {

		// definir qual � a p�gina que ser� acessada pelo controlador
		// WEB-INF/views/acessarconta.jsp
		ModelAndView modelAndView = new ModelAndView("acessarconta");
		return modelAndView;
	}
//------------------------------------------------------------------------------------
	
	
	//método para capturar a requisição SUBMIT do formulário
	
	@RequestMapping (value = "/autenticar-usuario", method = RequestMethod.POST)	
	public ModelAndView autenticarUsuario(HttpServletRequest request) {

		// definir qual a página que será acessada pelo controlador
		// WEB-INF/views/acessarconta.jsp
		ModelAndView modelAndView = new ModelAndView("acessarconta");
		
		try {
			
			//capturar os dados enviados pelo formulario
			String email = request.getParameter("email");
			String senha	= request.getParameter("senha");
			
			//consultando o usuario no banco de dados através do email e da senha
			UsuarioRepository usuarioRepository = new UsuarioRepository();
			Usuario usuario = usuarioRepository.findByEmailAndSenha(email, senha);
			
			//verificar se o usuário foi encontrado
			
			if(usuario != null /*se diferente de vazio*/) {
				
				//salvar os dados do usuário em uma sessão
				request.getSession().setAttribute("auth_usuario", usuario);
				
				//se encontrado, redirecionar o usuário para a página inicial da agenda
				modelAndView.setViewName("redirect:admin/dashboard");
			}
			
			else {
				//se não encontrado, erro.
				modelAndView.addObject("mensagem", "Acesso Negado, usuário inválido.");
			}

			
		}
		
		
		catch(Exception e) {
			
			modelAndView.addObject("mensagem", "Falha ao cadastrar: " + e.getMessage());
			
			
		}

	
		return modelAndView;
	
	
	
	}
	
	@RequestMapping (value = "/logout")
	public ModelAndView logout(HttpServletRequest request)	{
		
		//apagar os dados do usuario que foram gravados em sessão
		
		
		request.getSession().removeAttribute("auth_usuario");
		
		//redirecionar para a página de login
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/");
		return modelAndView;
		
		
		
		
		
		
		
		
	}
	
}
