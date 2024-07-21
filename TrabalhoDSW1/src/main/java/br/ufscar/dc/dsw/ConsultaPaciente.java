package br.ufscar.dc.dsw;

public class ConsultaPaciente {
    private String CRM_Medico;
    private String CPF_Paciente;
    private String DataConsulta;
    private String Horario;
    private String Nome_Medico;
    
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

	public String getDataConsulta() {
		return DataConsulta;
	}
	public void setDataConsulta(String dataConsulta) {
		DataConsulta = dataConsulta;
	}
	
	public String getHorario() {
		return Horario;
	}
	public void setHorario(String horario) {
		Horario = horario;
	}
	public String getNome_Medico() {
		return Nome_Medico;
	}
	public void setNome_Medico(String nome_Medico) {
		Nome_Medico = nome_Medico;
	}
}
