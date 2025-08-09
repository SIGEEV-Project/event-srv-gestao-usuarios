# Especificação de Requisitos com Critérios de Aceite (SIGEEV)

---

## 1. Módulo de Usuários e Autenticação

### Caso de Uso 01 – Cadastro de Usuário

**Descrição resumida**

O usuário deve ser capaz de se registrar no sistema fornecendo os seus dados pessoais. O sistema deve validar a unicidade do CPF e do email, retornando um token JWT para autenticação.

**Requisito Funcional: RF001**

**Critérios de Aceite (BDD)**

* **Eu como** novo usuário
* **Quero** me registrar no sistema com os meus dados pessoais
* **Quando** eu enviar um formulário com dados válidos e únicos
* **Então** minha conta deve ser criada e um token JWT retornado

**Payload de Entrada/Requisição**

```json
{
  "usuario": {
    "primeiroNome": "Lucas",
    "ultimoNome": "Benjamin de Araújo Farias A. Costa",
    "documento": {
      "tipo": "CPF",
      "numero": "12345678900"
    },
    "credenciais": {
      "email": "lucas@email.com",
      "senha": "senha@123"
    },
    "contato": {
      "telefone": "81987654321",
      "emailContato": "lucas@email.com"
    },
    "dataNascimento": "1986-04-05T00:00:00Z"
  },
  "endereco": {
    "logradouro": "Rua das Flores",
    "numero": "123",
    "cidade": "Recife",
    "estado": "PE",
    "cep": "50000-000"
  }
}
```

**Respostas Possíveis**

* Sucesso: 201 Created + JWT Token
* CPF já cadastrado com outro email: 409 Conflict
* Email já cadastrado com outro CPF: 409 Conflict
* Dados inválidos: 400 Bad Request
* Erro interno: 500 Internal Server Error

**Payload de Resposta de sucesso**
```json
{
  "sucesso": true,
  "mensagem": "Usuário Lucas Benjamin cadastrado com sucesso!",
  "dados": {
    "usuarioId": "a1b2c3d4-e5f6-7890-1234-567890abcdef",
    "nomeCompleto": "Lucas Benjamin de Araújo Farias A. Costa",
    "email": "lucas@email.com",
    "tokenAcesso": "eyJhbGciOiJIUzI1NiIsInR5cCI6..."
  }
}
```

**Payload de Resposta de Erro**
```json
{
  "sucesso": false,
  "mensagem": "Erro ao cadastrar usuário.",
  "erros": [
    {
      "campo": "cpf",
      "mensagem": "CPF já cadastrado."
    },
    {
      "campo": "email",
      "mensagem": "E-mail já cadastrado."
    }

  ]
}
```

**Regras de Negócio:**
* O primeiro nome deve conter apenas letras e ter entre 2 e 50 caracteres.
* O último nome deve conter apenas letras e ter entre 2 e 100 caracteres.
* O CPF deve ser válido e único no sistema.
* O email deve ser válido, único e ter entre 5 e 100 caracteres.
* A senha deve ter pelo menos 8 caracteres, incluindo letras maiúsculas, minúsculas, números e caracteres especiais.
* O telefone deve seguir o formato brasileiro (com DDD).
* A data de nascimento deve ser válida e o usuário deve ter pelo menos 18 anos.
* O logradouro deve conter apenas letras, números e espaços, e ter entre 3 e 100 caracteres.
* O número do endereço deve ser um valor numérico válido.
* A cidade deve conter apenas letras e espaços, e ter entre 2 e 50 caracteres.
* O estado deve ser uma sigla válida de dois caracteres (ex: PE, SP, RJ).
* O CEP deve seguir o formato brasileiro (XXXXX-XXX) e ser válido.
* O sistema deve enviar um email de confirmação após o cadastro, contendo um link para ativação da conta.
* O sistema deve registrar a data e hora do cadastro do usuário.

----

### Caso de Uso 02 – Login de Usuário

**Requisito Funcional: RF002**

**Descrição resumida**
O usuário deve ser capaz de fazer login no sistema utilizando o seu email e senha.
O sistema deve validar as credenciais e retornar um token JWT para autenticação.

**Critérios de Aceite**

* **Eu como** usuário registrado e ativo
* **Quero** fazer login com meu email e senha
* **Quando** as credenciais forem válidas
* **Então** devo receber um token JWT de autenticação

**Payload de Entrada/Requisição**

```json
{
  "email": "lucas@email.com",
  "senha": "Senha@123"
}
```
***Respostas Possíveis**
* Sucesso: 200 OK + JWT Token
* Email ou senha inválidos: 401 Unauthorized
* Conta inativa: 403 Forbidden
* Erro interno: 500 Internal Server Error

**Payload de Resposta de sucesso**

```json
{
  "sucesso": true,
  "mensagem": "Login realizado com sucesso!",
  "dados": {
    "usuarioId": "a1b2c3d4-e5f6-7890-1234-567890abcdef",
    "perfil": "usuario",
    "nomeCompleto": "Lucas Benjamin de Araújo Farias A. Costa",
    "email": "lucas@email.com",
    "tokenAcesso": "eyJhbGciOiJIUzI1NiI...",
    "expiraEm": 3600
  }
}
```
***Payload de Resposta de Erro**

```json
{
  "sucesso": false,
  "mensagem": "Erro ao fazer login.",
  "erros": [
    {
      "campo": "email",
      "mensagem": "Email ou senha inválidos."
    }
  ]
}
```
**Regras de Negócio:**
* O email deve ser válido e registrado no sistema.
* A senha deve corresponder àquela cadastrada para o email informado.
* O usuário deve estar ativo (não inativo ou excluído).
* O token JWT deve ser gerado com um tempo de expiração configurável (ex: 1 hora).
* O token deve conter as informações do usuário, como ID, perfil e nome completo.
* O token deve ser assinado com uma chave secreta para garantir sua integridade e autenticidade.
* O sistema deve registrar a data e hora do último login do usuário.
* O sistema deve permitir tentativas de login com no máximo 5 falhas consecutivas antes de bloquear temporariamente o usuário por 15 minutos.
* O sistema deve registrar logs de acesso para auditoria e segurança.

-----

### Caso de Uso 02.1 – Renovação de Token (Refresh Token)

**Requisito Funcional: RF002.1**

**Descrição resumida**
O usuário deve ser capaz de renovar o seu token de autenticação sem precisar fazer login novamente, utilizando um refresh token. O sistema deve validar o refresh token e, se for válido, gerar um novo token de acesso e um novo refresh token.

**Critérios de Aceite (BDD)**

* **Eu como** usuário logado
* **Quero** renovar o meu token de autenticação
* **Quando** eu enviar um refresh token válido e não expirado
* **Então** devo receber um novo token de acesso e um novo refresh token
* **E** o token de acesso antigo e o refresh token antigo devem ser invalidados

**Payload de Entrada/Requisição**

```json
{
  "refreshToken": "seu_refresh_token_aqui"
}
```

**Respostas Possíveis**

* Sucesso: 200 OK + Novos tokens
* Token inválido ou expirado: 401 Unauthorized
* Erro interno: 500 Internal Server Error

**Payload de Resposta de sucesso**

```json
{
  "sucesso": true,
  "mensagem": "Tokens renovados com sucesso!",
  "dados": {
    "tokenAcesso": "novo_token_de_acesso",
    "refreshToken": "novo_refresh_token",
    "expiraEm": 3600
  }
}
```

**Payload de Resposta de Erro**

```json
{
  "sucesso": false,
  "mensagem": "Sessão expirada. Por favor, faça login novamente."
}
```

**Regras de Negócio:**

* O refresh token deve ser gerado no momento do login do usuário e ter uma validade maior que o token de acesso (ex: 7 dias).
* O refresh token deve ser único e armazenado no banco de dados, associado ao usuário.
* O sistema deve validar se o refresh token enviado existe no banco de dados e se não está expirado ou revogado.
* Se o refresh token for válido, o sistema deve gerar um novo par de `tokenAcesso` e `refreshToken`.
* O refresh token antigo deve ser invalidado no banco de dados após a emissão de um novo token.
* A nova data de expiração (`expiraEm`) deve ser retornada no payload de sucesso.
* O sistema deve registrar logs de auditoria para todas as tentativas de renovação de token.

----

### Caso de uso 03 - Alteração de Perfil

**Requisito Funcional: RF003**

**Descrição resumida**
O usuário deve ser capaz de alterar os dados pessoais do seu perfil tais como:nome, telefone, data de nascimento e endereço.
O sistema deve validar as alterações e garantir a integridade dos dados.

**Critérios de Aceite (BDD)**

* **Eu como** usuário logado
* **Quero** alterar meus dados pessoais
* **Quando** eu enviar os novos dados válidos
* **Então** meu perfil deve ser atualizado corretamente

**Payload de Entrada/Requisição**
```json
{
  "usuario": {
    "usuarioId": "a1b2c3d4-e5f6-7890-1234-567890abcdef",
    "primeiroNome": "Lucas",
    "ultimoNome": "Benjamin de Araújo Farias A. Costa",
    "contato": {
      "telefone": "81987654321"
    },
    "dataNascimento": "1985-01-20"
  },
  "endereco": {
    "logradouro": "Rua das Flores",
    "numero": "123",
    "cidade": "Recife",
    "estado": "PE",
    "cep": "50000-000"
  }
}
```

**Respostas Possíveis**
* Sucesso: 200 OK
* Dados inválidos: 400 Bad Request
* Erro interno: 500 Internal Server Error

**Payload de Resposta de sucesso**
````json
{
  "sucesso": true,
  "mensagem": "Usuário Lucas Benjamin alterado com sucesso!"
}
````
**Payload de Resposta de Erro**
```json
{
  "sucesso": false,
  "mensagem": "Erro ao alterar usuário.",
  "erros": [
    {
      "campo": "telefone",
      "mensagem": "Telefone inválido."
    }
  ]
}
```
**Regras de Negócio**
* O primeiro nome deve conter apenas letras e ter entre 2 e 50 caracteres.
* O último nome deve conter apenas letras e ter entre 2 e 100 caracteres.
* O telefone deve seguir o formato brasileiro (com DDD).
* A data de nascimento deve ser válida e o usuário deve ter pelo menos 18 anos.
* O logradouro deve conter apenas letras, números e espaços, e ter entre 3 e 100 caracteres.
* O número do endereço deve ser um valor numérico válido.
* A cidade deve conter apenas letras e espaços, e ter entre 2 e 50 caracteres.
* O estado deve ser uma sigla válida de dois caracteres (ex: PE, SP, RJ).
* O CEP deve seguir o formato brasileiro (XXXXX-XXX) e ser válido.
* O sistema deve registrar a data e hora da alteração do perfil do usuário.
* O sistema deve enviar um email de notificação ao usuário informando sobre a alteração dos seus dados pessoais.
* O sistema deve garantir que o usuário não possa alterar o seu email ou CPF, apenas os dados pessoais e de contato.

### Caso de Uso 04 – Promoção de Perfil

**Requisito Funcional: RF004**

**Descrição resumida**
O administrador deve ser capaz de promover um usuário a promotor de evento, alterando o seu perfil para "promotor".
O sistema deve validar se o usuário existe e atualizar o seu perfil corretamente.

**Critérios de Aceite**

* **Eu como** administrador
* **Quero** promover um usuário a promotor de evento
* **Quando** eu enviar o ID do usuário e o novo perfil
* **Então** o perfil do usuário deve ser atualizado corretamente

**Payload de Entrada/Requisição**

```json
{
  "usuarioId": "uuid-do-usuario",
  "novoPerfil": "promotor"
}
```
**Respostas Possíveis**
* Sucesso: 200 OK
* Usuário não encontrado: 404 Not Found
* Erro interno: 500 Internal Server Error
* Perfil inválido: 400 Bad Request
* Usuário já é promotor: 409 Conflict
* Usuário inativo: 403 Forbidden
* Usuário excluído: 410 Gone

**Payload de Resposta de sucesso**
```json
{
  "sucesso": true,
  "mensagem": "Usuário promovido a promotor com sucesso!"
}
```

**Payload de Resposta de Erro**
```json
{
  "sucesso": false,
  "mensagem": "Erro ao promover usuário.",
  "erros": [
    {
      "campo": "usuarioId",
      "mensagem": "Usuário não encontrado."
    }
  ]
}
```
**Regras de Negócio**
* O usuário deve existir no sistema e estar ativo.
* O novo perfil deve ser "promotor" ou "participante".
* O sistema deve registrar a data e hora da promoção do usuário.
* O sistema deve enviar um email de notificação ao usuário informando sobre a promoção do seu perfil.
* O sistema deve garantir que apenas administradores possam promover usuários.
* O sistema deve verificar se o usuário já é promotor antes de tentar promover novamente.
* O sistema deve garantir que o usuário não possa promover a si mesmo, apenas administradores podem fazer isso.
* O sistema deve registrar logs de auditoria para todas as promoções de usuários.
* O Sistema deve registar o ID do administrador que realizou a promoção.

---

### Caso de Uso 05 – Rebaixar Perfil de Promotor para Participante

**Requisito Funcional: RF005**

**Descrição resumida**
O administrador deve ser capaz de rebaixar um usuário de promotor para participante, alterando o seu perfil.
O sistema deve validar se o usuário existe e atualizar o seu perfil corretamente.

**Critérios de Aceite**
* **Eu como** administrador
* **Quero** rebaixar um promotor para participante
* **Quando** eu enviar o ID do usuário e o novo perfil
* **Então** o perfil do usuário deve ser atualizado corretamente

**Payload de Entrada/Requisição**

```json
{
  "usuarioId": "uuid-do-usuario",
  "novoPerfil": "participante"
}
```
**Respostas Possíveis**
* Sucesso: 200 OK
* Usuário não encontrado: 404 Not Found
* Erro interno: 500 Internal Server Error
* Perfil inválido: 400 Bad Request
* Usuário já é participante: 409 Conflict
* Usuário inativo: 403 Forbidden
* Usuário excluído: 410 Gone

**Payload de Resposta de sucesso**
```json
{
  "sucesso": true,
  "mensagem": "Usuário rebaixado para participante com sucesso!"
}
``` 

**Payload de Resposta de Erro**
```json
{
  "sucesso": false,
  "mensagem": "Erro ao rebaixar usuário.",
  "erros": [
    {
      "campo": "usuarioId",
      "mensagem": "Usuário não encontrado."
    }
  ]
}
```

**Regras de Negócio**
* O usuário deve existir no sistema e estar ativo.
* O novo perfil deve ser "participante".
* O sistema deve registrar a data e hora do rebaixamento do usuário.
* O sistema deve enviar um email de notificação ao usuário informando sobre o rebaixamento do seu perfil.
* O sistema deve garantir que apenas administradores possam rebaixar usuários.
* O sistema deve verificar se o usuário já é participante antes de tentar rebaixar novamente.
* O sistema deve garantir que o usuário não possa rebaixar a si mesmo, apenas administradores podem fazer isso.
* O sistema deve registrar logs de auditoria para todas as rebaixamentos de usuários.
* O sistema deve registrar o ID do administrador que realizou o rebaixamento.

### Caso de Uso 06 – Exclusão de Conta

**Requisito Funcional: RF006**

**Descrição resumida**
O usuário deve ser capaz de solicitar a exclusão da sua conta.
O sistema deve marcar a conta como inativa, realizando uma exclusão lógica.

**Critérios de Aceite:**

* **Eu como** usuário logado
* **Quero** excluir minha conta
* **Quando** eu solicitar a exclusão
* **Então** minha conta deve ser marcada como inativa (exclusão lógica)

**Payload de Entrada/Requisição**
```json
{
  "usuarioId": "uuid-do-usuario"
}
```
**Respostas Possíveis**
* Sucesso: 200 OK
* Usuário não encontrado: 404 Not Found
* Erro interno: 500 Internal Server Error
* Conta já inativa: 409 Conflict

**Payload de Resposta de sucesso**
```json
{
  "sucesso": true,
  "mensagem": "Conta excluída com sucesso!"
}
```

**Payload de Resposta de Erro**
```json
{
  "sucesso": false,
  "mensagem": "Erro ao excluir conta.",
  "erros": [
    {
      "campo": "usuarioId",
      "mensagem": "Usuário não encontrado."
    }
  ]
}
```

**Regras de Negócio:**
* O usuário deve existir no sistema e estar ativo.
* O sistema deve registrar a data e hora da solicitação de exclusão.
* O sistema deve enviar um email de confirmação ao usuário informando sobre a exclusão da conta.
* O sistema deve garantir que a exclusão seja lógica, mantendo os dados do usuário para fins de auditoria.
* O sistema deve garantir que o usuário não possa realizar login após a exclusão.
* O sistema deve registrar logs de auditoria para todas as solicitações de exclusão de contas.
* O sistema deve permitir que o usuário reative a conta dentro de um período de 30 dias após a exclusão, caso deseje voltar a utilizar o sistema.
* O sistema deve registrar o ID do usuário que solicitou a exclusão e o motivo, se fornecido.

----

### Caso de Uso 07 – Trocar Senha

**Requisito Funcional: RF007**

**Descrição resumida**
O usuário deve ser capaz de trocar a sua senha atual por uma nova.
O sistema deve validar a senha atual e garantir que a nova senha atenda aos critérios de segurança

**Critérios de Aceite:**
* **Eu como** usuário logado
* **Quero** trocar minha senha
* **Quando** eu enviar a senha atual e a nova senha
* **Então** minha senha deve ser atualizada se a senha atual for válida

**Payload de Entrada/Requisição**
```json
{
  "usuarioId": "uuid-do-usuario",
  "senhaAtual": "Senha@123",
  "novaSenha": "NovaSenha@456"
}
```

**Respostas Possíveis**
* Sucesso: 200 OK
* Senha atual inválida: 401 Unauthorized
* Nova senha inválida: 400 Bad Request
* Erro interno: 500 Internal Server Error

**Payload de Resposta de sucesso**
```json
{
  "sucesso": true,
  "mensagem": "Senha alterada com sucesso!"
}
```

**Payload de Resposta de Erro**
```json
{
  "sucesso": false,
  "mensagem": "Erro ao trocar senha.",
  "erros": [
    {
      "campo": "senhaAtual",
      "mensagem": "Senha atual inválida."
    }
  ]
}
```

**Regras de Negócio:**
* O usuário deve existir no sistema e estar ativo.
* A senha atual deve ser válida e corresponder àquela cadastrada.
* A nova senha deve atender aos critérios de segurança (mínimo de 8 caracteres, incluindo letras maiúsculas, minúsculas, números e caracteres especiais).
* O sistema deve enviar um email de notificação ao usuário informando sobre a troca de senha.
* O sistema deve garantir que o usuário não possa reutilizar a senha atual como nova senha.
* O sistema deve registrar logs de auditoria para todas as trocas de senha.

----

### Caso de Uso 08 – Esqueci miha Senha

**Requisito Funcional: RF008**

**Descrição resumida**
O usuário deve ser capaz de solicitar a recuperação da senha caso tenha esquecido.
O sistema deve enviar um email com um link para redefinir a senha.

**Critérios de Aceite:**
* **Eu como** usuário que esqueceu a senha
* **Quero** solicitar a recuperação da senha
* **Quando** eu enviar meu email cadastrado
* **Então** devo receber um email com instruções para redefinir minha senha
* **E** o link deve expirar em 1 hora

**Payload de Entrada/Requisição**
```json
{
  "email": "lucas@email.com"
}
```
**Respostas Possíveis**
* Sucesso: 200 OK
* Email não cadastrado: 404 Not Found
* Erro interno: 500 Internal Server Error

**Payload de Resposta de sucesso**
```json
{
  "sucesso": true,
  "mensagem": "Email enviado com instruções para redefinir a senha."
}
```

**Payload de Resposta de Erro**
```json
{
  "sucesso": false,
  "mensagem": "Erro ao solicitar recuperação de senha.",
  "erros": [
    {
      "campo": "email",
      "mensagem": "Email não cadastrado."
    }
  ]
}
```

**Regras de Negócio:**
* O email deve ser válido e cadastrado no sistema.
* O sistema deve gerar um token de recuperação com validade de 1 hora.
* O sistema deve enviar um email com um link contendo o token de recuperação.
* O link deve direcionar o usuário para uma página onde ele pode definir uma nova senha.
* O sistema deve garantir que o token de recuperação não possa ser reutilizado após a troca de senha.
* O sistema deve registrar logs de auditoria para todas as solicitações de recuperação de senha.

----





