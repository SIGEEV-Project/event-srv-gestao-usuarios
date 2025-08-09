CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TYPE perfil_usuario AS ENUM ('participante', 'promotor', 'administrador');

CREATE TABLE "Usuarios" (
  "usuarioId" UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  "primeiroNome" VARCHAR(50) NOT NULL,
  "ultimoNome" VARCHAR(100) NOT NULL,
  "email" VARCHAR(100) UNIQUE NOT NULL,
  "cpf" VARCHAR(11) UNIQUE NOT NULL,
  "telefone" VARCHAR(15),
  "dataNascimento" DATE,
  "logradouro" VARCHAR(100),
  "numeroEndereco" VARCHAR(10),
  "cidade" VARCHAR(50),
  "estado" CHAR(2),
  "cep" VARCHAR(9),
  "perfil" perfil_usuario NOT NULL DEFAULT 'participante',
  "status" VARCHAR(20) NOT NULL DEFAULT 'ativo',
  "dataCadastro" TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
  "dataUltimoLogin" TIMESTAMP WITH TIME ZONE,
  "dataAlteracao" TIMESTAMP WITH TIME ZONE,
  "dataExclusao" TIMESTAMP WITH TIME ZONE,
  "dataReativacao" TIMESTAMP WITH TIME ZONE,
  "tokenRecuperacao" TEXT,
  "tokenRecuperacaoExpiraEm" TIMESTAMP WITH TIME ZONE
);

CREATE TABLE "Senhas" (
  "senhaId" UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  "usuarioId" UUID NOT NULL,
  "senhaHash" TEXT NOT NULL,
  "dataCriacao" TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
  FOREIGN KEY ("usuarioId") REFERENCES "Usuarios" ("usuarioId") ON DELETE CASCADE
);

CREATE TABLE "HistoricoDePromocoes" (
  "historicoId" UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  "usuarioId" UUID NOT NULL,
  "adminId" UUID NOT NULL,
  "tipoAcao" VARCHAR(20) NOT NULL,
  "perfilAntigo" perfil_usuario,
  "perfilNovo" perfil_usuario NOT NULL,
  "dataAcao" TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
  FOREIGN KEY ("usuarioId") REFERENCES "Usuarios" ("usuarioId") ON DELETE CASCADE,
  FOREIGN KEY ("adminId") REFERENCES "Usuarios" ("usuarioId") ON DELETE CASCADE
);

-- Índices para melhor desempenho em consultas
CREATE INDEX idx_usuarios_email ON "Usuarios" ("email");
CREATE INDEX idx_usuarios_cpf ON "Usuarios" ("cpf");
CREATE INDEX idx_usuarios_status ON "Usuarios" ("status");
CREATE INDEX idx_senhas_usuario ON "Senhas" ("usuarioId");
CREATE INDEX idx_historico_usuario ON "HistoricoDePromocoes" ("usuarioId");
CREATE INDEX idx_historico_admin ON "HistoricoDePromocoes" ("adminId");
