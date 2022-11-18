package br.com.cotiinformatica.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.com.cotiinformatica.entities.Usuario;
import br.com.cotiinformatica.factories.ConnectionFactory;

public class UsuarioRepository {

	// m�todo para cadastrar um usu�rio no banco de dados
	public void create(Usuario usuario) throws Exception {

		//abrindo conex�o com o banco de dados
		Connection connection = ConnectionFactory.createConnection();
		
		//escrevendo um comando SQL no banco de dados
		PreparedStatement statement = connection.prepareStatement("insert into usuario(nome, email, senha) values(?, ?, md5(?))");
		statement.setString(1, usuario.getNome());
		statement.setString(2, usuario.getEmail());
		statement.setString(3, usuario.getSenha());
		statement.execute();
		
		//fechando a conex�o
		connection.close();
	}
//-------------------------------------------------------------------------------------------------------
	
	
	
	//método de consulta no banco de dados através do email
	public Usuario findByEmail(String email)	throws Exception	{
		
		//abrindo conexão com banco de dados
		Connection connection	=  ConnectionFactory.createConnection();
		
		//escrevendo comando sql no banco de dados
		PreparedStatement	statement = connection.prepareStatement("select * from usuario where email = ?");
		statement.setString(1, email);
		ResultSet	resultSet = statement.executeQuery();
		
		Usuario usuario = null;
		
		//verificar se registro foi encontrado
		if (resultSet.next())	{
			usuario = new Usuario();	//instanciando o objeto
			
			usuario.setIdUsuario(resultSet.getInt("idusuario"));
			usuario.setNome(resultSet.getString("nome"));
			usuario.setEmail(resultSet.getString("email"));
		}
		
		connection.close(); //encerrando a conexão com banco de dados
		return usuario; //retornando o objeto
		
	}
	
//--------------------------------------------------------------------------------------------------------	
	
	
	//método de consulta no banco de dados através do email e senha
	public Usuario findByEmailAndSenha(String email, String senha)	throws Exception	{
		
		Connection connection = ConnectionFactory.createConnection();
		
		PreparedStatement statement = connection.prepareStatement("select * from usuario where email = ? and senha = md5(?)");
		statement.setString(1, email);
		statement.setString(2, senha);
		ResultSet resultSet = statement.executeQuery();
		
		Usuario usuario = null;
		
		//verificar se registro foi encontrado
		if (resultSet.next())	{
			
			usuario = new Usuario();
			
			usuario.setIdUsuario(resultSet.getInt("idusuario"));
			usuario.setNome(resultSet.getString("nome"));
			usuario.setEmail(resultSet.getString("email"));
			
		}
		
		connection.close();
		return usuario;
		
	}
	
//---------------------------------------------------------------------------------------------------
	
	//método para atualizar a senha de um usuário
	
	public void update(Integer idUsuario, String novaSenha) throws Exception {
		
		//abrindo conexão com o banco de dados
		Connection connection = ConnectionFactory.createConnection();
		
		PreparedStatement statement = connection.prepareStatement("update usuario set senha=md5(?) where idusuario=?");
		statement.setString(1, novaSenha);
		statement.setInt(2, idUsuario);
		statement.execute();
		
		connection.close();
	}	


	
		
		
}
	
	
	
	
	
	


