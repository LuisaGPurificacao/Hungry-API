# Hungry-API

Uma API para sistema de controle de alimentos em centros de distribuições

> A empresa irá disponibilizar a quantidade e em qual centro de distribuição o alimento será disponibilizado para que o usuário final consiga usufluir desse alimento. O centro de distribuição por sua vez, é responsável por gerenciar esses alimentos.

---

## Endpoints

- Empresas
  - [cadastrar](#cadastrar-empresas)
  - [atualizar](#atualizar-empresas)
  - [mostrar detalhes](#mostrar-detalhes-empresa)
  - [apagar](#apagar-empresas)
- Centros de distribuição
  - cadastrar
  - atualizar
  - mostrar detalhes
  - listar todos
  - apagar
  - desativar/ ativar
- Alimentos
  - cadastrar
  - atualizar
  - listar top 3
  - listar todos
  - apagar

---

### Cadastrar Empresas

`POST` /hungry/api/empresas

> Endpoint para a empresa se cadastrar no nosso sistema

**Campos da requisição**

| campo         | tipo   | obrigatório | descrição                                                                         |
|---------------|--------|:-----------:|-----------------------------------------------------------------------------------|
| nome          | String |     sim     | o nome da empresa                                                                 |
| nome fantasia | String |     não     | o nome fantasia da empresa                                                        |
| cnpj          | String |     sim     | o CNPJ da empresa, deve ser validado no formato 'XX.XXX.XXX/XXXX-XX'              |
| email         | String |     sim     | o e-mail da empresa, deve ser um e-mail válido                                    |
| descricao     | String |     não     | uma descrição sobre a empresa                                                     |
| cep           | String |     sim     | o CEP de onde fica localizada a empresa, deve ser validado no formato 'XXXXX-XXX' |
| país          | String |     sim     | o país onde fica localizada a empresa                                             |
| estado        | String |     sim     | o estado onde fica localizada a empresa                                           |
| cidade        | String |     sim     | a cidade onde fica localizada a empresa                                           |
| bairro        | String |     sim     | o bairro onde fica localizada a empresa                                           |
| logradouro    | String |     sim     | o logradouro onde fica localizada a empresa                                       |
| numero        | int    |     sim     | o número do logradouro onde fica localizada a empresa                             |
| complemento   | String |     não     | o complemento de onde fica localizada a empresa                                   |

**Exemplo de corpo de requisição**

```js
{
    "nome": "Bunge Alimentos S/A",
    "nome_fantasia": "Bunge",
    "cnpj": "12.345.678/0233-90",
    "email": "bunge@alimentos.com",
    "descricao": "Na Bunge, nosso propósito é conectar agricultores e consumidores para fornecer alimentos e ingredientes essenciais para o mundo.",
    "endereco": {
       "cep": "02011-222",
       "pais": "Brasil",
       "estado": "SP",
       "cidade": "São Paulo",
       "bairro": "Luz",
       "logradouro": "Rua Consolação",
       "numero": 123,
       "complemento": "Prédio azul"
    }
}
```

**Códigos de Respostas**

| código | descrição                                              |
|--------|--------------------------------------------------------|
| 201    | empresa cadastrada com sucesso                         |
| 400    | campos inválidos                                       |
| 409    | conflito (caso o CNPJ já esteja cadastrado no sistema) |

---

### Atualizar Empresas

`PUT` /hungry/api/empresas

> Endpoint para a empresa atualizar o seu perfil

**Campos da requisição**

| campo         | tipo   | obrigatório | descrição                                                                         |
|---------------|--------|:-----------:|-----------------------------------------------------------------------------------|
| nome          | String |     sim     | o nome da empresa                                                                 |
| nome fantasia | String |     não     | o nome fantasia da empresa                                                        |
| email         | String |     sim     | o e-mail da empresa, deve ser um e-mail válido                                    |
| descricao     | String |     não     | uma descrição sobre a empresa                                                     |
| cep           | String |     sim     | o CEP de onde fica localizada a empresa, deve ser validado no formato 'XXXXX-XXX' |
| país          | String |     sim     | o país onde fica localizada a empresa                                             |
| estado        | String |     sim     | o estado onde fica localizada a empresa                                           |
| cidade        | String |     sim     | a cidade onde fica localizada a empresa                                           |
| bairro        | String |     sim     | o bairro onde fica localizada a empresa                                           |
| logradouro    | String |     sim     | o logradouro onde fica localizada a empresa                                       |
| numero        | int    |     sim     | o número do logradouro onde fica localizada a empresa                             |
| complemento   | String |     não     | o complemento de onde fica localizada a empresa                                   |

**Exemplo de corpo de requisição**

```js
{
    "nome": "Bunge Alimentos S/A",
    "nome_fantasia": "Bunge Brasil",
    "email": "bunge@alimentos.com.br",
    "descricao": "Na Bunge, nosso propósito é conectar agricultores e consumidores para fornecer alimentos e ingredientes essenciais para o mundo.",
    "endereco": {
       "cep": "02011-222",
       "pais": "Brasil",
       "estado": "SP",
       "cidade": "São Paulo",
       "bairro": "Luz",
       "logradouro": "Rua Consolação",
       "numero": 948,
       "complemento": "Prédio verde"
    }
}
```

**Códigos de Respostas**

| código | descrição                      |
|--------|--------------------------------|
| 201    | empresa atualizada com sucesso |
| 400    | campos inválidos               |

---

### Mostrar Detalhes Empresa

`GET` /hungry/api/empresas/{id}

> Endpoint para a empresa visualizar o seu perfil com todos seus dados

**Exemplo de corpo de resposta**

```js
{
    "id": 198
    "nome": "Bunge Alimentos S/A",
    "nome_fantasia": "Bunge",
    "cnpj": "12.345.678/0233-90",
    "email": "bunge@alimentos.com",
    "descricao": "Na Bunge, nosso propósito é conectar agricultores e consumidores para fornecer alimentos e ingredientes essenciais para o mundo.",
    "endereco": {
       "cep": "02011-222",
       "pais": "Brasil",
       "estado": "SP",
       "cidade": "São Paulo",
       "bairro": "Luz",
       "logradouro": "Rua Consolação",
       "numero": 123,
       "complemento": "Prédio azul"
    }
}
```

**Códigos de Respostas**

| código | descrição                             |
|--------|---------------------------------------|
| 200    | dados da empresa retornados           |
| 404    | não existe empresa com o ID informado |

---

### Apagar Empresas

`DELETE` /hungry/api/empresas/{id}

> Endpoint para a empresa apagar o seu perfil no nosso sistema

**Códigos de Respostas**

| código | descrição                             |
|--------|---------------------------------------|
| 204    | empresa apagada com sucesso           |
| 404    | não existe empresa com o ID informado |

---

