package br.org.catolicasc.pessoa;

import br.org.catolicasc.pessoa.db.PessoaDB;
import br.org.catolicasc.pessoa.model.Pessoa;

public class Principal {

	public static void main(String[] args) {
		PessoaDB pdb = new PessoaDB();
		
		Pessoa p = new Pessoa(0, "Fulano", "Silva", "fulano@naoexiste.com", "2222-2222");
		
		pdb.addPessoa(p);
		
		for (Pessoa pessoa : pdb.getPessoas()) {
			System.out.println(pessoa);
			System.out.println("----------------------------------");
		}
	}

}
