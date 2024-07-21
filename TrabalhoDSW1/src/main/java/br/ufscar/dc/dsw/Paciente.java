package br.ufscar.dc.dsw;

import java.util.Date;

public class Paciente {
	private String CPF;
	private String Nome;
	private String Email;
	private String Senha;
	private String Telefone;
	private String Sexo;
	private String Data_nascimento;
	
	public String getCPF() {
		return CPF;
	}
	public void setCPF(String cPF) {
		CPF = cPF;
	}
	
	public String getNome() {
		return Nome;
	}
	public void setNome(String nome) {
		Nome = nome;
	}
	
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	
	public String getSenha() {
		return Senha;
	}
	public void setSenha(String senha) {
		Senha = senha;
	}
	
	public String getTelefone() {
		return Telefone;
	}
	public void setTelefone(String telefone) {
		Telefone = telefone;
	}
	
	public String getSexo() {
		return Sexo;
	}
	public void setSexo(String sexo) {
		Sexo = sexo;
	}
	
	public String getData_nascimento() {
		return Data_nascimento;
	}
	public void setData_nascimento(String data_nascimento) {
		Data_nascimento = data_nascimento;
	}
	
}
