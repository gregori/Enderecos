package br.org.catolicasc.pessoa.model;

public class Pessoa {
	private int pessoaID;
	private String nome;
	private String sobrenome;
	private String email;
	private String telefone;
	
	public Pessoa() {
	}

	public Pessoa(int pessoaID, String nome, String sobrenome, String email, String telefone) {
		this.pessoaID = pessoaID;
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.email = email;
		this.telefone = telefone;
	}

	public int getPessoaID() {
		return pessoaID;
	}

	public void setPessoaID(int pessoaID) {
		this.pessoaID = pessoaID;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	@Override
	public String toString() {
		return  "Id: " + getPessoaID() + "\n"
				+ "Nome: " + getNome() + "\n"
				+ "Sobrenome: " + getSobrenome() + "\n"
				+ "Email: " + getEmail() + "\n"
				+ "Telefone: " + getTelefone() + "\n";
	}
}
