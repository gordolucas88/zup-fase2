# Orange Talents
## Caso de uso
1. API Rest de cadastro de abertura de uma conta.
2. Campos necessário são: Nome, E-mail, CPF, Data de Nascimento.
3. Necessário criar um ID (Campo identificador). 
4. O ID e o CPF são UNIQUE KEYS (Não se repetem).
5. Validar se o CPF digitado está correto (Necessário ter 11 digitos e conter somente números de 0 a 9).
6. Caso a requisição de cadastro em formato JSON, passe nos critérios de validação, inserir registro na base de dados e retornar status 201
7. Caso a requisição não seja feita com sucesso retornar status 400.
8. Criar retorno de consulta, que irá retornar 200 caso encontre o registro, ou 404, caso não encoontre.

## Passos realizados
1. Criado os pacotes. dentro de src/main/java (Model, controller, service)








