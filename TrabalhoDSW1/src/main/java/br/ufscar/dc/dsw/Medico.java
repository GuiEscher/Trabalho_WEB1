package br.ufscar.dc.dsw;

public class Medico {
    private String CRM;
    private String Nome;
    private String Email;
    private String Senha;
    private String Especialidade;
    
    // Getter e Setter para Especialidade
    public String getEspecialidade() {
        return Especialidade;
    }
    public void setEspecialidade(String especialidade) {
        this.Especialidade = especialidade;
    }
    
    // Getter e Setter para Senha
    public String getSenha() {
        return Senha;
    }
    public void setSenha(String senha) {
        this.Senha = senha;
    }
    
    // Getter e Setter para Email
    public String getEmail() {
        return Email;
    }
    public void setEmail(String email) {
        this.Email = email;
    }
    
    // Getter e Setter para Nome
    public String getNome() {
        return Nome;
    }
    public void setNome(String nome) {
        this.Nome = nome;
    }
    
    // Getter e Setter para CRM
    public String getCRM() {
        return CRM;
    }
    public void setCRM(String crm) {
        this.CRM = crm;
    }
}