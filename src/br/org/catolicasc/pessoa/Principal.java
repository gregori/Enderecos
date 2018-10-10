package br.org.catolicasc.pessoa;

import br.org.catolicasc.pessoa.db.PessoaDB;
import br.org.catolicasc.pessoa.model.Pessoa;

public class Principal {

	public static void main(String[] args) {
		PessoaDB pdb = new PessoaDB();
		
		Pessoa p = new Pessoa(0, "Jose", "Carlos", "jose@naoexiste.com", "2222-2222");
		Pessoa q = new Pessoa(0, "Pedro", "Costa", "pedro@naoexiste.com", "2222-2222");
		
		pdb.addPessoa(p);
		pdb.addPessoa(q);
		
		System.out.println("Base Inicial: ");
		for (Pessoa pessoa : pdb.getPessoas()) {
			System.out.println(pessoa);
			System.out.println("----------------------------------");
		}
		
		q.setTelefone("3333-3333");
		p.setEmail("jose.carlos@naoexiste.com");
		
		pdb.updatePessoa(p);
		pdb.updatePessoa(q);
		
		System.out.println("Pós Update: ");
		for (Pessoa pessoa : pdb.getPessoas()) {
			System.out.println(pessoa);
			System.out.println("----------------------------------");
		}
		
		pdb.deletePessoa(1);
		
		System.out.println("Pós delete: ");
		for (Pessoa pessoa : pdb.getPessoas()) {
			System.out.println(pessoa);
			System.out.println("----------------------------------");
		}
	}

}

