# Hungry-API

Uma API para sistema de controle de alimentos em centros de distribuições

> A empresa irá disponibilizar a quantidade e em qual centro de distribuição o alimento será disponibilizado para que o
> usuário final consiga usufluir desse alimento. O centro de distribuição por sua vez, é responsável por gerenciar esses
> alimentos.

---

## Endpoints

- Empresas
    - [cadastrar](#cadastrar-empresas)
    - [atualizar](#atualizar-empresas)
    - [mostrar detalhes](#mostrar-detalhes-empresa)
    - [apagar](#apagar-empresas)
- Centros de distribuição
    - [cadastrar](#cadastrar-centros-de-distribuição)
    - [atualizar](#atualizar-centros-de-distribuição)
    - [mostrar detalhes](#mostrar-detalhes-centro-de-distribuição)
    - [listar todos](#listar-todos-centros-de-distribuição)
    - [apagar](#apagar-centros-de-distribuição)
    - [desativar/ ativar](#desativar-ativar-centros-de-distribuição)
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

| campo         | tipo   | obrigatório | descrição                                                                          |
|---------------|--------|:-----------:|------------------------------------------------------------------------------------|
| nome          | String |     sim     | o nome da empresa                                                                  |
| nome fantasia | String |     não     | o nome fantasia da empresa                                                         |
| cnpj          | long   |     sim     | o CNPJ da empresa, deve ser validado com 14 números                                |
| email         | String |     sim     | o e-mail da empresa, deve ser um e-mail válido                                     |
| descricao     | String |     não     | uma descrição sobre a empresa                                                      |
| cep           | int    |     sim     | o CEP de onde fica localizada a empresa, deve ser validado com 8 números           |
| país          | String |     sim     | o país onde fica localizada a empresa                                              |
| estado        | String |     sim     | o estado onde fica localizada a empresa                                            |
| cidade        | String |     sim     | a cidade onde fica localizada a empresa                                            |
| bairro        | String |     sim     | o bairro onde fica localizada a empresa                                            |
| logradouro    | String |     sim     | o logradouro onde fica localizada a empresa                                        |
| numero        | int    |     sim     | o número do logradouro onde fica localizada a empresa, deve ser um número positivo |
| complemento   | String |     não     | o complemento de onde fica localizada a empresa                                    |

**Exemplo de corpo de requisição**

```js
{
    "nome": "Bunge Alimentos S/A",
    "nome_fantasia": "Bunge",
    "cnpj": 12345678023390,
    "email": "bunge@alimentos.com",
    "descricao": "Na Bunge, nosso propósito é conectar agricultores e consumidores para fornecer alimentos e ingredientes essenciais para o mundo.",
    "endereco": {
       "cep": 02011222,
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

| campo         | tipo   | obrigatório | descrição                                                                          |
|---------------|--------|:-----------:|------------------------------------------------------------------------------------|
| nome          | String |     não     | o nome da empresa                                                                  |
| nome fantasia | String |     não     | o nome fantasia da empresa                                                         |
| email         | String |     não     | o e-mail da empresa, deve ser um e-mail válido                                     |
| descricao     | String |     não     | uma descrição sobre a empresa                                                      |
| cep           | int    |     não     | o CEP de onde fica localizada a empresa, deve ser validado com 8 números           |
| país          | String |     não     | o país onde fica localizada a empresa                                              |
| estado        | String |     não     | o estado onde fica localizada a empresa                                            |
| cidade        | String |     não     | a cidade onde fica localizada a empresa                                            |
| bairro        | String |     não     | o bairro onde fica localizada a empresa                                            |
| logradouro    | String |     não     | o logradouro onde fica localizada a empresa                                        |
| numero        | int    |     não     | o número do logradouro onde fica localizada a empresa, deve ser um número positivo |
| complemento   | String |     não     | o complemento de onde fica localizada a empresa                                    |

**Exemplo de corpo de requisição**

```js
{
    "nome": "Bunge Alimentos S/A",
    "nome_fantasia": "Bunge Brasil",
    "email": "bunge@alimentos.com.br",
    "descricao": "Na Bunge, nosso propósito é conectar agricultores e consumidores para fornecer alimentos e ingredientes essenciais para o mundo.",
    "endereco": {
       "cep": 02011222,
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
    "cnpj": 12345678023390,
    "email": "bunge@alimentos.com",
    "descricao": "Na Bunge, nosso propósito é conectar agricultores e consumidores para fornecer alimentos e ingredientes essenciais para o mundo.",
    "endereco": {
       "cep": 02011222,
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

### Cadastrar Centros de Distribuição

`POST` /hungry/api/centros

> Endpoint para o centro de distribuição se cadastrar no nosso sistema

**Campos da requisição**

| campo         | tipo   | obrigatório | descrição                                                               |
|---------------|--------|:-----------:|-------------------------------------------------------------------------|
| nome          | String |     sim     | o nome do centro de distribuição                                        |
| descricao     | String |     não     | uma descrição sobre o centro de distribuição                            |
| email         | String |     sim     | o e-mail do centro de distribuição, deve ser um e-mail válido           |
| funcionamento | String |     sim     | os horários e os dias de funcionamento do centro                        |
| capacidade    | String |     sim     | a quantidade de capacidade de alimentos do centro                       |
| armazenamento | String |     sim     | a quantidade do armazenamento de alimentos do centro                    |
| cep           | int    |     sim     | o CEP de onde fica localizado o centro, deve ser validado com 8 números |
| país          | String |     sim     | o país onde fica localizado o centro                                    |
| estado        | String |     sim     | o estado onde fica localizado o centro                                  |
| cidade        | String |     sim     | a cidade onde fica localizado o centro                                  |
| bairro        | String |     sim     | o bairro onde fica localizado o centro                                  |
| logradouro    | String |     sim     | o logradouro onde fica localizado o centro                              |
| numero        | int    |     sim     | o número do logradouro onde fica localizado o centro                    |
| complemento   | String |     não     | o complemento de onde fica localizado o centro                          |

**Exemplo de corpo de requisição**

```js
{
    "nome": "Santarém Alimentos LTDS",
    "nome_fantasia": "Santarém",
    "descricao": "Serviço de distribuição em São Paulo",
    "email": "santarem.alimentos@gmail.com",
    "funcionamento": "Aberto das 9:00 às 20:00, não abrimos nos domingos e feriados.",
    "capacidade": "2 toneladas",
    "armazenamento": "300kg",
    "endereco": {
       "cep": 02394881,
       "pais": "Brasil",
       "estado": "SP",
       "cidade": "São Paulo",
       "bairro": "Centro Histórico de São Paulo",
       "logradouro": "Rua Centro",
       "numero": 6969,
       "complemento": "Perto da praça"
    }
}
```

**Códigos de Respostas**

| código | descrição                                              |
|--------|--------------------------------------------------------|
| 201    | centro de distribuição cadastrado com sucesso          |
| 400    | campos inválidos                                       |

---

### Atualizar Centros de Distribuição

`PUT` /hungry/api/centros

> Endpoint para o centro de distribuição atualizar o seu perfil

**Campos da requisição**

| campo         | tipo   | obrigatório | descrição                                                               |
|---------------|--------|:-----------:|-------------------------------------------------------------------------|
| nome          | String |     não     | o nome do centro de distribuição                                        |
| descricao     | String |     não     | uma descrição sobre o centro de distribuição                            |
| email         | String |     não     | o e-mail do centro de distribuição, deve ser um e-mail válido           |
| funcionamento | String |     não     | os horários e os dias de funcionamento do centro                        |
| capacidade    | String |     não     | a quantidade de capacidade de alimentos do centro                       |
| armazenamento | String |     não     | a quantidade do armazenamento de alimentos do centro                    |
| cep           | int    |     não     | o CEP de onde fica localizado o centro, deve ser validado com 8 números |
| país          | String |     não     | o país onde fica localizado o centro                                    |
| estado        | String |     não     | o estado onde fica localizado o centro                                  |
| cidade        | String |     não     | a cidade onde fica localizado o centro                                  |
| bairro        | String |     não     | o bairro onde fica localizado o centro                                  |
| logradouro    | String |     não     | o logradouro onde fica localizado o centro                              |
| numero        | int    |     não     | o número do logradouro onde fica localizado o centro                    |
| complemento   | String |     não     | o complemento de onde fica localizado o centro                          |

**Exemplo de corpo de requisição**

```js
{
    "nome": "Santarém Alimentos LTDS",
    "nome_fantasia": "Santarém",
    "descricao": "Serviço de distribuição em São Paulo",
    "email": "santarem.alimentos@gmail.com",
    "funcionamento": "Aberto das 9:00 às 20:00, não abrimos nos domingos e feriados.",
    "capacidade": "2 toneladas",
    "armazenamento": "500kg",
    "endereco": {
       "cep": 02394881,
       "pais": "Brasil",
       "estado": "SP",
       "cidade": "São Paulo",
       "bairro": "Centro Histórico de São Paulo",
       "logradouro": "Rua Centro",
       "numero": 6969,
       "complemento": "Perto da praça"
    }
}
```

**Códigos de Respostas**

| código | descrição                                     |
|--------|-----------------------------------------------|
| 201    | centro de distribuição atualizado com sucesso |
| 400    | campos inválidos                              |

---

### Mostrar Detalhes Centro de Distribuição

`GET` /hungry/api/centros/{id}

> Endpoint para o centro de distribuição visualizar o seu perfil com todos seus dados

**Exemplo de corpo de resposta**

```js
{
    "id": 12
    "nome": "Santarém Alimentos LTDS",
    "nome_fantasia": "Santarém",
    "descricao": "Serviço de distribuição em São Paulo",
    "email": "santarem.alimentos@gmail.com",
    "funcionamento": "Aberto das 9:00 às 20:00, não abrimos nos domingos e feriados.",
    "capacidade": "2 toneladas",
    "armazenamento": "500kg",
    "ativo": true,
    "endereco": {
       "cep": 02394881,
       "pais": "Brasil",
       "estado": "SP",
       "cidade": "São Paulo",
       "bairro": "Centro Histórico de São Paulo",
       "logradouro": "Rua Centro",
       "numero": 6969,
       "complemento": "Perto da praça"
    }
}
```

**Códigos de Respostas**

| código | descrição                                            |
|--------|------------------------------------------------------|
| 200    | dados do centro de distribuição retornados           |
| 404    | não existe centro de distribuição com o ID informado |

---

### Listar Todos Centros de Distribuição

`GET` /hungry/api/centros

> Endpoint para visualizar todos os centros de distribuição cadastrados no sistema

**Exemplo de corpo de resposta**

```js
[
    {
      "id": 12
      "nome": "Santarém Alimentos LTDS",
      "nome_fantasia": "Santarém",
      "ativo": true,
      "endereco": {
         "pais": "Brasil",
         "estado": "SP",
         "cidade": "São Paulo",
         "bairro": "Centro Histórico de São Paulo"
      }
    },
    {
      "id": 16
      "nome": "Armazém São José Co.",
      "nome_fantasia": "Armazém São José",
      "ativo": true,
      "endereco": {
         "pais": "Brasil",
         "estado": "SP",
         "cidade": "São Paulo",
         "bairro": "Paraíso"
      }
    }
]
```

**Códigos de Respostas**

| código | descrição                                            |
|--------|------------------------------------------------------|
| 200    | dados dos centros de distribuição retornados         |

---

### Apagar Centros de Distribuição

`DELETE` /hungry/api/centros/{id}

> Endpoint para o centro de distribuição apagar o seu perfil no nosso sistema

**Códigos de Respostas**

| código | descrição                                            |
|--------|------------------------------------------------------|
| 204    | centro de distribuição apagado com sucesso           |
| 404    | não existe centro de distribuição com o ID informado |

---

### Desativar/ ativar Centros de Distribuição

`PATCH` /hungry/api/centros/{id}

> Endpoint para o centro de distribuição atualizar o seu status como ativo ou inativo

**Campos da requisição**

| campo | tipo    | obrigatório | descrição                                            |
|-------|---------|:-----------:|------------------------------------------------------|
| ativo | boolean |     sim     | mostra se o centro de distribuição está ativo ou não |

**Exemplo de corpo de requisição**

```js
{
    "ativo": false
}
```

**Códigos de Respostas**

| código | descrição                                            |
|--------|------------------------------------------------------|
| 200    | centro de distribuição atualizado como ativo/inativo |
| 404    | não existe centro de distribuição com o ID informado |

