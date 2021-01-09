# Orange Talents

*Todo projeto descrito nesse "Guia" se encontra no github e você pode ler, e testar o projeto pelo link: https://github.com/gordolucas88/zup-fase2*

## Caso de uso

1. API Rest de cadastro de dados pessoais.
2. Campos necessários para cadastros são: Nome, E-mail, CPF, Data de Nascimento.
3. Caso os dados fornecidos forem validados com sucesso, efetuar o devido cadastro na base de dados. 
4. Caso os dados fornecidos não passarem na validação devolver a resposta para o solicitante.

## Decisões

* Nesse Projeto vamos utilizar o padrão de Projeto MVC(Model-View-Controller). Apesar de ser um dos padrões de projetos mais utilizados, ele vai suprir bastante as nossas necessidades.
* Para todas as nossa validações de dados usaremos depêndendencias do Spring.

#### Benefícios:

* Ajuda consideravelmente a manutenção do nosso código. Pois temos as responsibildades claramente separadas.
* Amplamente utilizado, então outras pessoas irão conseguir entender o padrão do nosso projeto.

### Pontos negativos:

* Apesar de pesquisar muito, realmente não encontrei um maléficio no uso desse design pattern.

## Procedimentos

O primeiro passo para começar a construir é acessar o site: https://start.spring.io/
e escolher os paramêtros do projeto.

* Definimos o nome do projeto.
* Utilizamos nesse projeto o gerenciador "**maven**" a linguagem "**java**" a versão do spring "**2.4.1**" o packaging utilizado é o "**jar**" e a versão do java utilizado é a "**15**".

Usamos as seguintes dependências de projetos:

* **Spring Web:** Essa dependência já vem pré configurada com um servidor web, funcionalidades como tomcat e routers.* 
* **Data-jpa:** Essa dependência cuida de toda parte de persistência de dados, ORM entre outros.
* **Validation:** Essa dependência tem usa série de validações do Hibernate(Ferramenta utilizada também no jpa, também tem a funcionalidade de ORM)
* **mysql:** Essa dependência provê os componentes necessários para o JPA, acessar o banco de dados mysql (Banco de dados escolhido para persistir os dados).
* **Lombok:** Essa dependência é de caratér opcional. Ela provê uma série de agilidades no momento do desenvolvimento, pois já cria getter e setter automaticamente, em tempo de compilação, deixa o nosso código mais limpo.

Após o término da criação do projeto, vamos colocar a mão na massa de verdade.

### Arquitetura do Projeto

De início vamos criar 5 pacotes, são eles:

* **entity:** Nele vai conter nosso "Model", ele é a representação da nossa estrutura no banco de dados.
* **repository:** Nele vai conter a "Interface" com o nossos metódos e comportamentos
* **service:** Nele vai conter todas as nossas regras de inserção de dados, busca, atualização e exclusão.
* **dto:** Nele vai conter nossas validações antes de inserir na nossa base de dados.
* **controller:** Nele que vamos definir nossas rotas e como cada uma deve operar.

### Propriedades da aplicação

Será necessário configurar alguns parâmetros no nosso arquivo "application.properties", é nele que fica cadastrado as propriedades do Spring. Vamos adicionar os seguintes parâmetros:

* **spring.datasource.driver-class-name=** Como vamos utilizar mysql no nosso projeto, iformamos o driver  de conexão ao banco que vamos utilizar, no nosso caso será: `com.mysql.cj.jdbc.Driver`.
* **spring.datasource.url=** Aqui vamos colocar o nosso endereço de conexão `jdbc:mysql://localhost:3306/zup?useTimezone=true&serverTimezone=America/Sao_Paulo`, os dados que vem após o sinal de ?, informa que são parâmetros opcionais, nesse caso informo que o TimeZone será America/Sao_Paulo.
* **spring.datasource.username=** Aqui colocamos o usuário do mysql.
* **spring.datasource.password=** Aqui colocamos a senha para acessar o mysql.
* **spring.jpa.show-sql=true** Esse parâmetro é opcional, como ele está definido como true, o spring irá mostrar as queries do sql no nosso terminal.
* **spring.jpa.hibernate.ddl-auto = create-drop** Esse parâmetro é aconselhado utilizar só em desenvolvimento, com ele definido como `create-drop` o Spring vai excluir e criar novamente o nosso banco de dados, toda vez que iniciarmos a aplicação.
* **spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL5Dialect** Essa é o dialeto que vamos utilizar na nossa conexão ao banco de dados.
* **server.port=3000** Aqui definimos qual a porta que vai rodar a nossa aplicação, nesse caso irá executar na porta 3000

## Classes

### Pessoa

A primeira classe que vamos criar é a classe "Pessoa", ela vai ser nossa entidade, e vai ficar no pacote "entity".

```
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
* **@Data:** Anotação do framework Lombok, Responsável por criar todos os getters, setter, ToString e outros boilerplates do Java.
* **@NoArgsConstructor:** Anotação do framework Lombok, Responsável por gerar um construtor sem paramêtros.
* **@AllArgsConstructor:** Anotação do framework Lombok, Responsável por gerar um construtor com 1 paramêtro.
* **@Entity:** Anotação da JPA, Ela informa que a classe é também uma entidade do banco de dados relacional(Tabela do banco de dados), e que cada nova instância será um registro dessa tabela.
* **@Table:** Anotação da JPA, Utilizamos ela para definir o nome da tabela que será criada no banco de dados.

Definimos então a classe Pessoa e os atributos serão:
* **id:** Atribuímos a Anotação @Id(Informando que essa será a chave primária da nossa tabela), @GeneratedValue(Informa o tipo de geração de incremento que vamos utilizar, no nosso caso utilizei a geração tipo Identity, aonde o banco de dados trata de gerar o Id sequencialmente) definimos também como tipo Long, pois nossa tabela vai caber muitos registros.
* **nome:** Atribuímos a Anotação @Column(Utilizando essa anotação definimos os parâmetros da nossa coluna, nesse caso definimos que não pode ser nula) e definimos ela como tipo String(Texto). 
* **email:** Atribuímos a Anotação @Column(Utilizando essa anotação definimos os parâmetros da nossa coluna, nesse caso definimos que não pode ser nula, também a coluna é unique, ou seja, não permite valores repetidos) e definimos ela como tipo String(Texto).
* **cpf:** Atribuímos a Anotação @Column(Utilizando essa anotação definimos os parâmetros da nossa coluna, que o tamanho deve ser de 11 caracteres, também a coluna é unique, ou seja, não permite valores repetidos, e que a coluna não pode ser atualizada) e definimos ela como tipo String(Texto).
* **dtNascimento:** Atribuímos a Anotação @Column(Utilizando essa anotação definimos os parâmetros da nossa coluna, nesse caso definimos que não pode ser nula) e definimos ela como tipo LocalDate(Data).

### PessoaDto

A classe PessoaDto, será o nosso DTO(Data Transfer Object), ela será responsável por fazer as validações, e passar os dados para a nossa classe Pessoa.

```
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

* **@Data:** Anotação do framework Lombok, Responsavél por criar todos os getters, setter, ToString e outros boilerplates do Java.
* **@NoArgsConstructor:** Anotação do framework Lombok, Responsavél por gerar um construtor sem paramêtros.
* **@AllArgsConstructor:** Anotação do framework Lombok, Responsavél por gerar um construtor com 1 paramêtro.
 
Definimos então a classe PessoaDto e os atributos serão:

* **id:** Não adicionamos nenhuma validação, pois de forma alguma vamos receber esse dado, só vamos mostrar o retorno dele na api.
* **nome:** Atribuímos a Anotação @NotBlank(Validação do framework validator, não permite inserir dados em branco), @NotNull(Validação do framework validator, não permite inserir dados nulos), @Size(Define que o tamanho máximo e mínimo do campo).
* **email:** Atribuímos a Anotação @NotEmpty(Validação do framework validator, não permite que o campo seja vazio), @Email(Validação do framework validator, analisa se o texto digitado é um e-mail válido).
* **cpf:** Atribuímos a Anotação @NotEmpty(Validação do framework validator, não permite que o campo seja vazio), @CPF(Validação do framework validator, analisa se o texto digitado é um CPF válido, inclusive o digíto verificador), @Size(Define que o tamanho máximo e mínimo do campo).
* **dtNascimento:** Atribuímos a Anotação @DateTimeFormat(Validação do framework validator, não permite inserir dados que não sejam datas, e adicionamos que o formato deve ser "yyyy-mm-dd").

Por último definimos um metódo que pega os dados validados, criamos uma nova instância da classe Pessoa, e inserimos os dados que passaram pela validação.

### PessoaService

A clase PessoaService, vai ser a responsável por cadastrar, excluir, consultar e atualizar os nossos dados.


```

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository repository;

    public Pessoa cadastrarPessoa(Pessoa pessoa){
       return repository.save(pessoa);
    }


    public List<Pessoa> listarPessoas(){
        return repository.findAll();
    }

    public Pessoa listarPessoaId(Long id) throws Exception {

        return repository.findById(id).orElseThrow(Exception::new);
    }

    public Pessoa listarPessoaCPF(String cpf) throws Exception {
        return repository.findByCpf(cpf).orElseThrow(Exception::new);
    }

    public String excluirPessoa(Long id){
        repository.deleteById(id);
        return "Cadastro Excluido: " + id;
    }

    public Pessoa atualizarPessoa(Pessoa pessoa){
        Pessoa updatePessoa=repository.findById(pessoa.getId()).orElse(null);
        updatePessoa.setNome(pessoa.getNome());
        updatePessoa.setDtNascimento(pessoa.getDtNascimento());
        return repository.save(updatePessoa);
    }

}

```

* **@Service:** Marcamos a classe com essa anotação para o Spring saber que dentro dela irá conter nossa lógica de negócio.
* **@AutoWired:** Marcamos isso no nosso construtor para ele se tornar um ponto de injeção de dependência aonde "PessoaService" Necessita que "PessoaRepository" esteja instânciada.

Apartir desse momento começamos a realmente implementar as regras de negócio.

1. O primeiro metódo "cadastrarPessoa", recebe uma "Pessoa" e então utilizamos o metódo "save" do nosso repositório, que basicamente cria um insert do registro no banco de dados. ,
1. O segundo metódo "listarPessoas", retorna o resultado do metódo "findAll", basicamente um array com todas as Pessoas cadastradas em nossa tabela.
1. O terceiro metódo "listarPessoaId", recebe um "id" como parâmetro e então retorna a "Pessoa" que tem esse "id" através do metódo "findById". Caso o Id não exista então retornamos uma exceção.
1. O quarto metódo "listarPessoaCPF", recebe uma string (que seria o cpf), e retorna a "Pessoa" que tem esse cpf através do metódo "findByCpf". Caso o CPF não exista então retornamos uma exceção.
1. O quinto metódo "excluirPessoa", recebe um id, e exclui a "Pessoa" que tem esse esse Id através do metódo "deleteById" e retorna a mensagem de sucesso e o id excluído.
1. O sexto e ultimo metódo "atualizarPessoa", recebe uma "Pessoa", e então atualiza os campos: nome e data de nascimento, na nossa regra não é possível alterar o e-mail ou cpf.

### PessoaRepository

Essa é a única interface da nossa aplicação, a função dela é prover os metódos e funcionalidades para o nosso serviço.

```
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    Optional<Pessoa> findByCpf(String cpf);
}
```

* Como mostrado acima, ela é bem simples, a nossa interface PessoaRepository, herda as funcionalidades do JpaRepository, dessa maneira já temos todas as funcionalidades básicas de CRUD(Criar, Ler, Atualizar e Deletar ) da nossa aplicação.
* Informamos que "JpaRepository" é um "Generics" de Pessoa.
* Criamos o metódo de busca por nome e cpf, visto que não são campos identificadores, e como queremos realizar buscas através deles, precisamos criar os seus devidos metódos.
* Temos que adicionar a "Flag" Optional, porquê a consulta pode não retornar nenhum CPF(Caso o cpf solicitado não esteja cadastrado).

### PessoaController

A classe "PessoaController", será a responsável por receber as requisições e encaminhar elas para nosso serviço, para o usuário ela é o ponto de partida. Será ela que irá usar nossas validações e irá retornar os dados para o usuário.

```

@RestController
public class PessoaController {


    @Autowired
    private PessoaService service;


    @PostMapping("/pessoa")
    public ResponseEntity<Pessoa> adicionarPessoa(@Valid @RequestBody PessoaDto pessoaDto, BindingResult result) {
        Response<PessoaDto> response = new Response<PessoaDto>();

        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            List<String> errors = response.getErrors();
            System.out.println(errors);
            return ResponseEntity.badRequest().build();

        }
        Pessoa pessoa = service.cadastrarPessoa(pessoaDto.toPessoa());


        return ResponseEntity.created(URI.create("htttp://localhost:3000/pessoa/" + pessoa.getId())).body(pessoa);

    }

    @GetMapping("/pessoas")
    public ResponseEntity<List<Pessoa>> listarPessoas(){

        return ResponseEntity.ok().body(service.listarPessoas());
    }

    @GetMapping("/pessoaId/{id}")
    public ResponseEntity<Pessoa> listarPessoaId(@PathVariable("id") Long id){
        try {
            return ResponseEntity.ok().body(service.listarPessoaId(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }
    @GetMapping("/pessoaCPF/{cpf}")
    public ResponseEntity<Pessoa> listarPessoaCpf(@PathVariable("cpf") String cpf) {

        try {
           return ResponseEntity.ok().body(service.listarPessoaCPF(cpf));

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }

    @PutMapping("/atualizar")
    public ResponseEntity<Pessoa> atualizarPessoa(@RequestBody Pessoa pessoa) {
       try {
           service.atualizarPessoa(pessoa);
           return  ResponseEntity.ok().build();

       } catch (NullPointerException error){
           return ResponseEntity.notFound().build();
       }

    }

    @DeleteMapping("/excluir/{id}")
    public  ResponseEntity<String> excluirPessoa(@PathVariable("id") Long id) {

        try {
            service.excluirPessoa(id);
            return ResponseEntity.ok().body("Excluido id:" + id) ;
        } catch (EmptyResultDataAccessException error) {
            return ResponseEntity.notFound().build();
        }

    }
}


```

* **@RestController:** É uma anotação do framework Spring, ela marca que a classe é um controller, tipo "REST", com isso já implementa outros componentes como @ResponseBody e @RequestMapping, que utilizamos logo abaixo.
* **@AutoWired:** Marcamos isso no nosso construtor para ele se tornar um ponto de injeção de dependência, também é uma anotação do Spring, nesse caso dizemos que a nossa classe "PessoaCOntroller" precisa de "PessoaService" instânciada.

Abaixo agora vem as nossas Rotas:

1. **"/pessoa":** 
   
   * Criamos essa rota com a anotação @PostMapping(Isso indica que o metódo será executado ao utilizar o verbo http POST no endereço informado.).
   * Em seguida informamos que o metódo irá retornar uma "ResponseEntity" e que a mesma é um "Generic" da classe "Pessoa".
   * Nos parâmetros, colocamos a anotação @Valid(Isso informa que o parametro recebido irá passar pelo nosso processo validatório).
   * Usamos a anotação @RequestBody(quando usamos essa anotação o Spring "deserealiza" o body recebido, transformando ele em um objeto java).
   * Informamos que devemos receber uma "PessoaDto", e o segundo parâmetro "BindingResult", é simplesmente o conteúdo após passar pela validação invocada pela anotação @Valid.
   * Fazemos uma simples verificação, para saber se o retorno da nossa validação contém erros, caso ela contenha erros, retornamos o metódo com o a resposta 400 bad request e encarregamos a "ResponseEntity" de devolver esse retorno.
   * Caso não exista erros na validação, chamamos o nosso serviço que irá salvar o cadastro no nosso banco de dados e retornar o registro para a variavél "pessoa".
   * Por último retornamos o statuscode 201 created, com a URI do registro cadastrado(a URI, gerei hardcode mesmo, mas o ideal seria pegar os dados das variáveis de ambiente).
    
1. **"/pessoas":**

    * Criamos essa rota com a anotação @GetMapping(Isso indica que o metódo será executado ao utilizar o verbo http GET no endereço informado.).
    * Esse metódo é o mais simples do nosso controller, ele simplesmente recebe a chamada, e solicita ao serviço, retornar uma listagem com todos os cadastros contidos em nossa base de dados.
    
1. **"/pessoaId/id":**

    * Criamos essa rota com a anotação @GetMapping(Isso indica que o metódo será executado ao utilizar o verbo http GET no endereço informado.).
    * Pedimos para o nosso serviço buscar o cadastro no "Id" informado, e em seguida fazemos uma verificação.
    * Caso o serviço não tenha encontrado o registro, retornamos o statuscode 404 Not Found.
    * Caso o serviço tenha encontrado o registro com sucesso, retornamos o statuscode 200 OK, e retornamos o cadastro no body do Response.

1. **"/pessoaCPF/cpf":**

    * Criamos essa rota com a anotação @GetMapping(Isso indica que o metódo será executado ao utilizar o verbo http GET no endereço informado.).
    * Pedimos para o nosso serviço buscar o cadastro no "cpf" informado, e em seguida fazemos uma verificação.
    * Caso o serviço não tenha encontrado o registro, retornamos o statuscode 404 Not Found.
    * Caso o serviço tenha encontrado o registro com sucesso, retornamos o statuscode 200 OK, e retornamos o cadastro no body do Response.
    
1. **"/atualizar":**

    * Criamos essa rota com a anotação @PutMapping(Isso indica que o metódo será executado ao utilizar o verbo http PUT no endereço informado.).
    * Nesse metódo recebemos no corpo da requisição(JSON), um objeto "Pessoa".
    * Então entramos no Try do nosso metódo, aonde ele vai tentar atualizar a "Pessoa", caso a atualização seja feita com sucesso retornamos statuscode 200 OK.
    * Caso o bloco Try falhe(ou seja, não encontrar o registro, ou tentar atualizar um campo que não deve ser atualizado, no caso e-mail ou cpf), irá cair no nosso bloco Catch, que ira retornar statuscode 404 Not Found.
    
1. **"/excluir/id":**

    * Criamos essa rota com a anotação @DeleteMapping(Isso indica que o metódo será executado ao utilizar o verbo http DELETE no endereço informado.).
    * Nesse metódo recebemos como parâmetro na URL um id de "Pessoa".
    * Então entramos no Try do nosso metódo, aonde ele vai tentar excluir a "Pessoa" com o "id" informado, caso a exclusão seja feita com sucesso retornamos statuscode 200 OK.
    * Caso o bloco Try falhe(ou seja, não encontrar o registro), irá cair no nosso bloco Catch, que ira retornar statuscode 404 Not Found.
    
    

