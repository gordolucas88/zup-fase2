# Orange Talents
## Caso de uso
1. API Rest de cadastro de dados pessoais.
2. Campos necessários para cadastros são: Nome, E-mail, CPF, Data de Nascimento.
3. Caso os dados fornecidos forem validados com sucesso, efetuar o devido cadastro na base de dados. 
4. Caso os dados fornecidos não passarem na validação devolver a resposta para o solicitante.

## Procedimentos
O primeiro passo para começar a construir é acessar o site: https://start.spring.io/
e escolher os paramêtros do projeto.
* Definimos o nome do projeto.
* Utilizamos nesse projeto o gerenciador "**maven**" a linguagem "**java**" a versão do spring "**2.4.1**" o packaging utilizado é o "**jar**" e a versão do java utilizado é a "**15**".

Usamos as seguintes dependências de projetos:
  * Spring Web: Essa dependência já vem pré configurada com um servidor web, funcionalidades como tomcat e routers.* 
  * Data-jpa: Essa dependência cuida de toda parte de persistência de dados, ORM entre outros.
* Validation: Essa dependência tem usa série de validações do Hibernate(Ferramenta utilizada também no jpa, também tem a funcionalidade de ORM)
* mysql: Essa dependência provê os componentes necessários para o JPA, acessar o banco de dados mysql (Banco de dados escolhido para persistir os dados).
* Lombok: Essa dependência é de caratér opcional. Ela provê uma série de agilidades no momento do desenvolvimento, pois já cria getter e setter automaticamente, em tempo de compilação, deixa o nosso código mais limpo.

Após o término da criação do projeto, vamos colocar a mão na massa de verdade.

###Arquitetura do Projeto
De início vamos criar 6 pacotes são eles:
* entity: Nele vai conter nosso "Model", ele é a representação da nossa estrutura no banco de dados.
* repository: Nele vai conter a "Interface" com o nossos metódos e comportamentos
* service: Nele vai conter todas as nossas regras de inserção de dados, busca, atualização e exclusão.
* response: Nele vai conter nosso padrão de respostas, vamos inserir a mensagem padrão para cada situação.
* dto: Nele vai conter nossas validações antes de inserir na nossa base de dados.
* controller: Nele que vamos definir nossas rotas e como cada uma deve operar.

##Classes

###Pessoa
A primeira classe que vamos criar é a classe "Pessoa", ela vai ser nossa entidade, e vai ficar no pacote "entity".

```
package com.zup.orangeTalents.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pessoa")

public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(unique = true, length = 11, updatable = false)
    private String cpf;
    @Column(nullable = false)
    private LocalDate dtNascimento;


}
```
* @Data: Anotação do framework Lombok, Responsavél por criar todos os getters, setter, ToString e outros boilerplates do Java.
* @NoArgsConstructor: Anotação do framework Lombok, Responsavél por gerar um construtor sem paramêtros.
* @AllArgsConstructor:Anotação do framework Lombok, Responsavél por gerar um construtor com 1 paramêtro.
* @Entity: Anotação da JPA, Ela informa que a classe é também uma entidade do banco de dados relacional(Tabela do banco de dados), e que cada nova instância será um registro dessa tabela.
* @Table: Anotação da JPA, Utilizamos ela para definir o nome da tabela que será criada no banco de dados.

Definimos então a classe Pessoa e os atributos serão:
* id: Atribuímos a Anotação @Id(Informando que essa será a chave primária da nossa tabela), @GeneratedValue(Informa o tipo de geração de incremento que vamos utilizar, no nosso caso utilizei a geração tipo Identity, aonde o banco de dados trata de gerar o Id sequencialmente) definimos também como tipo Long, pois nossa tabela vai caber muitos registros.
* nome: Atribuímos a Anotação @Column(Utilizando essa anotação definimos os parâmetros da nossa coluna, nesse caso definimos que não pode ser nula) e definimos ela como tipo String(Texto). 
* email: Atribuímos a Anotação @Column(Utilizando essa anotação definimos os parâmetros da nossa coluna, nesse caso definimos que não pode ser nula, também a coluna é unique, ou seja, não permite valores repetidos) e definimos ela como tipo String(Texto).
* cpf: Atribuímos a Anotação @Column(Utilizando essa anotação definimos os parâmetros da nossa coluna, que o tamanho deve ser de 11 caracteres, também a coluna é unique, ou seja, não permite valores repetidos, e que a coluna não pode ser atualizada) e definimos ela como tipo String(Texto).
* dtNascimento: Atribuímos a Anotação @Column(Utilizando essa anotação definimos os parâmetros da nossa coluna, nesse caso definimos que não pode ser nula) e definimos ela como tipo LocalDate(Data).

###PessoaDto
A classe PessoaDto, será o nosso DTO(Data Transfer Object), ela será responsável por fazer as validações, e passar os dados para a nossa classe Pessoa.

```
package com.zup.orangeTalents.dto;

import com.zup.orangeTalents.entity.Pessoa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PessoaDto {

    private Long id;
    @NotBlank(message = "Nome nao pode ser Branco")
    @NotNull(message = "Nome nao pode ser Nulo")
    @Size(min = 5, max = 250)
    private String nome;

    @NotEmpty(message = "E-mail nao pode ser vazio")
    @Email(message = "E-mail Invalido")
    private String email;

    @NotEmpty(message = "CPF nao pode ser em Vazio")
    @CPF(message = "CPF Invalido")
    @Size(min = 11, max = 11, message = "Tamanho Invalido")
    private String cpf;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dtNascimento;

    public Pessoa toPessoa() {
        Pessoa pessoa = new Pessoa();

        pessoa.getId();
        pessoa.setNome(nome);
        pessoa.setEmail(email);
        pessoa.setCpf(cpf);
        pessoa.setDtNascimento(dtNascimento);

        return pessoa;
    }
}
```
* @Data: Anotação do framework Lombok, Responsavél por criar todos os getters, setter, ToString e outros boilerplates do Java.
* @NoArgsConstructor: Anotação do framework Lombok, Responsavél por gerar um construtor sem paramêtros.
* @AllArgsConstructor:Anotação do framework Lombok, Responsavél por gerar um construtor com 1 paramêtro.
 
Definimos então a classe Pessoa e os atributos serão: