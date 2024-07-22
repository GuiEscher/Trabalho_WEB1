Site desenvolvido para a disciplina de DSW1 da UFSCar

Para executar crie o banco do postgree de acordo com as instruções abaixo e acesse a pagina
http://localhost:8080/home/homepage
Ele deve mostrar todos medicos cadastrados

Faça o login com um dos pacientes para ter acesso a:
  - listagem das consultas cadastradas para ele
  - Pagina de nova consulta

Faça o login com um dos medicos para ter acesso a:
  - listagem das consultas cadastradas para ele

Faça login como admin para
  - listagem de todos medicos e pacientes
  - formulários de criação e edição de medicos e pacientes
  - botão para remoção de medicos e pacientes

## Aplicações Utilizadas
### Eclipse
[Eclipse](www.eclipse.org)

### Maven
[Maven](https://maven.apache.org/)

### Tomcat
[Tomcat](https://tomcat.apache.org/)

### PostgreSQL
[PostgreSQL](https://www.postgresql.org/)
Para criar o banco, utilizar o seguinte query:
```sql
CREATE TABLE if not exists PACIENTE(
CPF VARCHAR(11) NOT NULL UNIQUE,
Nome VARCHAR(50) NOT NULL,
Telefone VARCHAR(15) NOT NULL,
Email VARCHAR(60) NOT NULL UNIQUE,
Senha VARCHAR(20) NOT NULL,
DataNascimento VARCHAR(15) NOT NULL,
Sexo VARCHAR(1) NOT NULL,
PRIMARY KEY(CPF)
);

CREATE TABLE if not exists MEDICO(
CRM VARCHAR(10) NOT NULL UNIQUE,
Nome VARCHAR(50) NOT NULL,
Email VARCHAR(60) NOT NULL UNIQUE,
Senha VARCHAR(20) NOT NULL,
Especialidade VARCHAR(20) NOT NULL,
PRIMARY KEY(CRM)
);

CREATE TABLE if not exists CONSULTA(
CPF_Paciente VARCHAR(11) NOT NULL,
CRM_Medico VARCHAR(10) NOT NULL, 
Horario VARCHAR(15) NOT NULL,
DataConsulta VARCHAR(15) NOT NULL,
FOREIGN KEY(CPF_Paciente) REFERENCES PACIENTE(CPF)
ON UPDATE CASCADE
    ON DELETE CASCADE,
FOREIGN KEY(CRM_Medico) REFERENCES MEDICO(CRM)
ON UPDATE CASCADE
    ON DELETE CASCADE,
PRIMARY KEY(CPF_Paciente, CRM_Medico, Horario, DataConsulta) 
);

CREATE TABLE if not exists ADMIN(
ADM_KEY VARCHAR(10) NOT NULL UNIQUE,
Email VARCHAR(60) NOT NULL,
Senha VARCHAR(20) NOT NULL,
PRIMARY KEY(ADM_KEY)
);
```
Em seguida, pode-se popular o banco com os seguintes dados (note que o login de admin deve ser criado para adicionar mais médicos ou pacientes pelo site):
```sql
INSERT INTO ADMIN (ADM_KEY, Email, Senha) VALUES ('admin_key', 'admin', 'admin');

INSERT INTO PACIENTE (CPF, Nome, Telefone, Email, Senha, DataNascimento, Sexo) VALUES
('12345678901', 'Ana Silva', '11987654321', 'ana.silva@example.com', 'senha123', '12/04/1985', 'F'),
('23456789012', 'Bruno Souza', '21987654321', 'bruno.souza@example.com', 'senha123', '25/07/1990', 'M'),
('34567890123', 'Carlos Pereira', '31987654321', 'carlos.pereira@example.com', 'senha123', '15/01/1983', 'M'),
('45678901234', 'Daniela Lima', '41987654321', 'daniela.lima@example.com', 'senha123', '30/05/1995', 'F'),
('56789012345', 'Eduardo Rocha', '51987654321', 'eduardo.rocha@example.com', 'senha123', '10/02/1988', 'M'),
('67890123456', 'Fernanda Alves', '61987654321', 'fernanda.alves@example.com', 'senha123', '20/03/1993', 'F'),
('78901234567', 'Gabriel Oliveira', '71987654321', 'gabriel.oliveira@example.com', 'senha123', '08/08/1987', 'M'),
('89012345678', 'Helena Costa', '81987654321', 'helena.costa@example.com', 'senha123', '11/11/1992', 'F'),
('90123456789', 'Igor Fernandes', '91987654321', 'igor.fernandes@example.com', 'senha123', '19/12/1984', 'M'),
('01234567890', 'Juliana Martins', '11987653210', 'juliana.martins@example.com', 'senha123', '16/06/1991', 'F');

INSERT INTO MEDICO (CRM, Nome, Email, Senha, Especialidade) VALUES
('1234567890', 'Dr. João Almeida', 'joao.almeida@example.com', 'senha123', 'Cardiologia'),
('2345678901', 'Dra. Maria Santos', 'maria.santos@example.com', 'senha123', 'Pediatria'),
('3456789012', 'Dr. Pedro Costa', 'pedro.costa@example.com', 'senha123', 'Ortopedia'),
('4567890123', 'Dra. Renata Nunes', 'renata.nunes@example.com', 'senha123', 'Dermatologia'),
('5678901234', 'Dr. Lucas Ramos', 'lucas.ramos@example.com', 'senha123', 'Neurologia'),
('6789012345', 'Dra. Aline Mendes', 'aline.mendes@example.com', 'senha123', 'Ginecologia'),
('7890123456', 'Dr. Rafael Teixeira', 'rafael.teixeira@example.com', 'senha123', 'Psiquiatria'),
('8901234567', 'Dra. Carla Oliveira', 'carla.oliveira@example.com', 'senha123', 'Oftalmologia'),
('9012345678', 'Dr. Fernando Silva', 'fernando.silva@example.com', 'senha123', 'Endocrinologia'),
('0123456789', 'Dra. Bianca Lima', 'bianca.lima@example.com', 'senha123', 'Gastroenterologia');

INSERT INTO CONSULTA (CPF_Paciente, CRM_Medico, Horario, DataConsulta) VALUES
('12345678901', '1234567890', '10:00', '01/08/2024'),
('23456789012', '2345678901', '11:00', '01/08/2024'),
('34567890123', '3456789012', '14:00', '02/08/2024'),
('45678901234', '4567890123', '15:00', '02/08/2024'),
('56789012345', '5678901234', '16:00', '03/08/2024'),
('67890123456', '6789012345', '09:00', '03/08/2024'),
('78901234567', '7890123456', '10:00', '04/08/2024'),
('89012345678', '8901234567', '11:00', '04/08/2024'),
('90123456789', '9012345678', '14:00', '05/08/2024'),
('01234567890', '0123456789', '15:00', '05/08/2024');
