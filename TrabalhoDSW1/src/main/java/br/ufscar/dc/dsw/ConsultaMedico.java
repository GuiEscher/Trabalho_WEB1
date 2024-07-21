package br.ufscar.dc.dsw;

// Feita para a area de medico, usada para armazenar cada consulta registrada pro medico e 
// depois o nome do paciente daquela consulta

public class ConsultaMedico {
    private String CRM_Medico;
    private String NomePaciente;
    private String CPF_Paciente;
    private String DataConsulta;
    private String Horario;
    
    public String getCRM_Medico() {
        return CRM_Medico;
    }
    public void setCRM_Medico(String cRM_Medico) {
        CRM_Medico = cRM_Medico;
    }
    
    public String getCPF_Paciente() {
        return CPF_Paciente;
    }
    public void setCPF_Paciente(String cPF_Paciente) {
        CPF_Paciente = cPF_Paciente;
    }
    
    public String getData() {
        return DataConsulta;
    }
    public void setData(String data) {
        DataConsulta = data;
    }
    
    public String getHorario() {
        return Horario;
    }
    public void setHorario(String horario) {
        Horario = horario;
    }
	public String getNomePaciente() {
		return NomePaciente;
	}
	public void setNomePaciente(String nomePaciente) {
		NomePaciente = nomePaciente;
	}
}