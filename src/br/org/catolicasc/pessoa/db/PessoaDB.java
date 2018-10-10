package br.org.catolicasc.pessoa.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.org.catolicasc.pessoa.model.Pessoa;

public class PessoaDB {
	private static final String URL = "jdbc:sqlite:agenda.db";
	private static final String TABLE = "pessoa";
	private Connection conn; // gerencia a conexã o
	
	private PreparedStatement selectTodasAsPessoas; 
	private PreparedStatement selectPessoasPorSobrenome;
	private PreparedStatement insertNovaPessoa; 
	private PreparedStatement updateEmailPessoaPorId;
	private PreparedStatement updatePessoa;
	private PreparedStatement deletePessoa;
	
	public PessoaDB() {
		try {
			conn = DriverManager.getConnection(URL);
			
			createTable();
			
			selectTodasAsPessoas = conn.prepareStatement(
					"SELECT * FROM pessoa");
			
			selectPessoasPorSobrenome = conn.prepareStatement(
					"SELECT * FROM pessoa WHERE sobrenome = ?");
			
			insertNovaPessoa = conn.prepareStatement(
					"INSERT INTO pessoa " +
					"(nome, sobrenome, email, telefone) " +
					"VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			
			updateEmailPessoaPorId = conn.prepareStatement(
					"UPDATE pessoa " +
					"SET email = ? WHERE pessoaID = ?");
			
			updatePessoa = conn.prepareStatement(
					"UPDATE pessoa " +
					"SET nome = ?, sobrenome = ?, " +
					"email = ?, telefone = ? WHERE pessoaID = ?");
			
			deletePessoa = conn.prepareStatement(
					"DELETE FROM pessoa WHERE pessoaID = ?");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	private void createTable() throws SQLException {
	    String sqlCreate = "CREATE TABLE IF NOT EXISTS " + TABLE
	            + "  (pessoaID           INTEGER,"
	            + "   nome            VARCHAR(50),"
	            + "   sobrenome          VARCHAR(255),"
	            + "   email           VARCHAR(100),"
	            + "   telefone           VARCHAR(20),"
	            + "   PRIMARY KEY (pessoaID))";

	    Statement stmt = conn.createStatement();
	    stmt.execute(sqlCreate);
	}
	
	private Pessoa getPessoaFromRs(ResultSet rs) throws SQLException {
		return new Pessoa(
				rs.getInt("pessoaID"),
				rs.getString("nome"),
				rs.getString("sobrenome"),
				rs.getString("email"),
				rs.getString("telefone")
				);
	}
	
	private List<Pessoa> executaSelect(PreparedStatement stmt) {
		List<Pessoa> resultado = null;
		ResultSet rs = null;
		
		try {
			rs = stmt.executeQuery();
			resultado = new ArrayList<>();
			
			while (rs.next()) {
				resultado.add(getPessoaFromRs(rs));
			}
		}  catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
				close();
			}
		}
		
		return resultado;
	}
	
	// obtém todas as pessoas
	public List<Pessoa> getPessoas() {
		return executaSelect(selectTodasAsPessoas);		
	}
	
	// obtém pessoa pelo sobreonome
	public List<Pessoa> getPessoasPorSobrenome(String sobrenome) {
		try {
			selectPessoasPorSobrenome.setString(1, sobrenome);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return executaSelect(selectPessoasPorSobrenome);
	}
	
	public int updatePessoa(Pessoa p) {
		int resultado = 0;
		
		try {
			updatePessoa.setString(1, p.getNome());
			updatePessoa.setString(2, p.getSobrenome());
			updatePessoa.setString(3, p.getEmail());
			updatePessoa.setString(4, p.getTelefone());
			updatePessoa.setInt(5, p.getPessoaID());
			
			resultado = updatePessoa.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		return resultado;
	}
	
	public int deletePessoa(int id) {
		int resultado = 0;
		
		try {
			deletePessoa.setInt(1, id);
			
			resultado = deletePessoa.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		return resultado;
	}
	
	
	public int updateEmailPessoaPorId(int id, String email) {
		int resultado = 0;
		
		try {
			updateEmailPessoaPorId.setString(1, email);
			updateEmailPessoaPorId.setInt(2, id);
			
			resultado = updateEmailPessoaPorId.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		return resultado;
	}
	
	public int addPessoa(Pessoa p) {
		int id = addPessoa(p.getNome(), p.getSobrenome(), p.getEmail(), p.getTelefone());
		
		p.setPessoaID(id);
		
		return id;
	}
	
	// adiciona uma pessoa
	public int addPessoa(String nome, String sobrenome, String email, String telefone) {
		int resultado = 0;
		
		try {
			insertNovaPessoa.setString(1, nome);
			insertNovaPessoa.setString(2, sobrenome);
			insertNovaPessoa.setString(3, email);
			insertNovaPessoa.setString(4, telefone);
			
			// insere e retorna o numero de linhas atualizadas
			resultado = insertNovaPessoa.executeUpdate();
			
			ResultSet rs = insertNovaPessoa.getGeneratedKeys();
			if (rs.next())
				resultado = rs.getInt(1);
		} catch (SQLException ex) {
			ex.printStackTrace();
			close();
		}
		
		return resultado;
	}
	
	public void close() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
