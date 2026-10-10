# Raízes do Nordeste — API

API REST para gerenciamento de clientes, usuários, unidades, produtos, estoque, pedidos, pagamentos, fidelidade e auditoria.

## Tecnologias utilizadas

- Java 25
- Spring Boot 3.5.16
- Spring Web, Spring Data JPA, Bean Validation e Spring Security
- PostgreSQL 17
- Docker Compose
- JWT para autenticação
- Swagger/OpenAPI para documentação da API
- Gradle Wrapper

## Como testar a aplicação

A aplicação pode ser testada de duas maneiras: pela versão publicada, sem necessidade de instalação local, ou por meio da execução completa do projeto no ambiente local.

### 1. Testar a versão publicada

A aplicação está hospedada na plataforma Render, enquanto o banco de dados PostgreSQL está hospedado no Neon. Dessa forma, é possível acessar e testar a API pela URL pública, sem precisar instalar a aplicação ou configurar o banco de dados localmente.

- **URL base da API:** https://raizes-do-nordeste-backend-aayl.onrender.com
- **Documentação Swagger:** https://raizes-do-nordeste-backend-aayl.onrender.com/swagger-ui/index.html
- **Verificação de funcionamento:** https://raizes-do-nordeste-backend-aayl.onrender.com/smoke

A documentação Swagger permite consultar os endpoints disponíveis e visualizar os parâmetros e os formatos das requisições. Também é possível utilizar o Postman para executar os testes, configurando a URL base para o endereço publicado.

Os endpoints que exigem autenticação devem ser utilizados com um token JWT válido. Para obter o token, execute o login com um usuário cadastrado e utilize o token retornado nas requisições protegidas.

### Observação   
**A aplicação está hospedada no plano gratuito do Render. Após períodos de inatividade, a instância pode ser suspensa automaticamente. Nesse caso, a primeira requisição pode apresentar um tempo de resposta elevado enquanto a aplicação é inicializada novamente. Durante os testes realizados, esse tempo de inicialização foi superior a um minuto.**

### 2. Executar a aplicação localmente

Para executar a aplicação localmente, é necessário configurar o ambiente Java, iniciar o banco de dados PostgreSQL e executar a API com o perfil `dev` ativo.

#### 2.1. Pré-requisitos

Antes de iniciar, instale ou configure:

- JDK 25;
- Docker Desktop;
- Git, caso o projeto seja obtido pelo repositório;
- Postman, caso deseje executar a coleção de testes.

O projeto inclui o Gradle Wrapper, que permite executar tarefas do Gradle sem exigir uma instalação independente do Gradle.

#### 2.2. Iniciar o banco de dados PostgreSQL

O projeto possui o arquivo `docker-compose.yml`, que define o serviço do PostgreSQL 17. Esse serviço utiliza as seguintes configurações para o ambiente local:

- Banco de dados: `raizesdb`;
- Usuário: `postgres`;
- Senha: `postgres`;
- Porta: `5432`;
- Volume persistente: `postgres_data`.

Primeiro, inicie o Docker Desktop. Em seguida, abra um terminal na pasta `backend`, onde está localizado o arquivo `docker-compose.yml`, e execute:

```bash
docker compose up -d
```

Esse comando cria e inicia o contêiner do PostgreSQL em segundo plano.

Para verificar se o contêiner está em execução, utilize:

```bash
docker compose ps
```

A aplicação utiliza as configurações de conexão definidas para o perfil de desenvolvimento. Se as configurações do banco forem alteradas, os dados de conexão da aplicação também deverão ser ajustados.

#### 2.3. Iniciar a API com o perfil `dev`

Com o banco de dados iniciado, execute a aplicação com o perfil `dev` ativo. Isso pode ser feito por uma IDE compatível com Java e Spring Boot ou pelo terminal.

No IntelliJ IDEA, configure a execução da classe principal da aplicação para utilizar o perfil `dev` e inicie a execução.

No Windows, também é possível iniciar a aplicação pelo terminal. Na pasta `backend`, execute:

```powershell
.\gradlew.bat bootRun --args="--spring.profiles.active=dev"
```

Em sistemas Linux ou macOS compatíveis, o comando equivalente é:

```bash
./gradlew bootRun --args='--spring.profiles.active=dev'
```

O comando para Linux e macOS pressupõe que o arquivo `gradlew` tenha permissão de execução.

Durante a inicialização, o perfil `dev` configura o acesso ao banco de dados e executa o script de dados de desenvolvimento. O banco fica armazenado no volume persistente `postgres_data`; por isso, os dados e os usuários continuam existindo mesmo quando o contêiner é encerrado e iniciado novamente.

O script verifica os dados que já estão no banco antes de inserir o administrador padrão (`admin@raizes.com`). A inserção só ocorre se ainda não houver nenhum usuário com perfil `ADMIN` e se esse e-mail também não estiver cadastrado. Se já existir um administrador, o script não cria outro nem altera a conta existente. Assim, nas inicializações seguintes com o mesmo volume, o administrador já salvo é mantido. Se o volume for removido e o banco começar vazio, o script poderá inserir novamente o administrador padrão.

Após a inicialização, a API estará disponível em:

`http://localhost:8080`

#### 2.4. Acessar a documentação local

Com a aplicação em execução, a documentação pode ser acessada pelos seguintes endereços:

- **URL base da API:** http://localhost:8080
- **Documentação Swagger:** http://localhost:8080/swagger-ui/index.html
- **Verificação de funcionamento:** http://localhost:8080/smoke

#### 2.5. Encerrar a aplicação

Se a API tiver sido iniciada pelo terminal, pressione `Ctrl + C` no terminal em que ela está sendo executada.

Para encerrar o contêiner do PostgreSQL, abra outro terminal na pasta que contém o arquivo `docker-compose.yml` e execute:

```bash
docker compose down
```

Esse comando encerra e remove o contêiner, mas preserva os dados armazenados no volume `postgres_data`.

Para remover também os dados persistidos do banco, seria necessário excluir explicitamente esse volume. Essa operação não deve ser realizada se os dados precisarem ser mantidos.

## Configuração de produção

Na implantação de produção, as configurações de conexão com o PostgreSQL são fornecidas por variáveis de ambiente, definidas no ambiente de hospedagem. As credenciais de produção não são disponibilizadas neste repositório.

O perfil `prod` utiliza as configurações próprias desse ambiente e não executa o script de criação do administrador utilizado no desenvolvimento local.

## Autenticação

A API utiliza tokens JWT do tipo Bearer. O token é obtido por meio do endpoint `POST /auth/login` e deve ser enviado nas requisições que exigem autenticação, no cabeçalho:

```http
Authorization: Bearer <token>
```

Os perfis de acesso disponíveis são `CLIENTE`, `ATENDENTE` e `ADMIN`, com permissões específicas conforme as regras de autorização da aplicação.

O endpoint `POST /auth/cadastro` permite cadastrar clientes, exigindo os dados obrigatórios definidos pela API, incluindo o consentimento para o tratamento de dados pessoais.

## Endpoints

Os endpoints são acessados diretamente pela raiz da aplicação, sem um prefixo global `/api`.

| Método | Endpoint | Acesso |
|---|---|---|
| `POST` | `/auth/cadastro` | Público |
| `POST` | `/auth/login` | Público |
| `GET` | `/smoke` | Público |
| `GET` | `/produtos` | Público |
| `GET` | `/produtos/{id}` | Público |
| `POST` | `/produtos` | `ATENDENTE` ou `ADMIN` |
| `PATCH` | `/produtos/{id}` | `ATENDENTE` ou `ADMIN` |
| `DELETE` | `/produtos/{id}` | `ADMIN` |
| `GET` | `/unidades` | Público |
| `GET` | `/unidades/{id}` | Público |
| `POST` | `/unidades` | `ATENDENTE` ou `ADMIN` |
| `PATCH` | `/unidades/{id}` | `ATENDENTE` ou `ADMIN` |
| `DELETE` | `/unidades/{id}` | `ADMIN` |
| `GET` | `/clientes` | Autenticado; `CLIENTE` vê somente os próprios dados; `ATENDENTE` e `ADMIN` veem todos |
| `GET` | `/clientes/{id}` | Autenticado; `CLIENTE` acessa somente o próprio cadastro; `ATENDENTE` e `ADMIN` podem acessar qualquer cadastro |
| `PATCH` | `/clientes/{id}` | Autenticado; `CLIENTE` altera somente o próprio cadastro; `ATENDENTE` e `ADMIN` podem alterar qualquer cadastro |
| `DELETE` | `/clientes/{id}` | `ADMIN` |
| `GET` | `/usuarios` | `ADMIN` |
| `POST` | `/usuarios` | `ADMIN` |
| `GET` | `/estoques` | Autenticado |
| `GET` | `/estoques/{id}` | Autenticado |
| `POST` | `/estoques` | `ATENDENTE` ou `ADMIN` |
| `PATCH` | `/estoques/{id}` | `ATENDENTE` ou `ADMIN` |
| `DELETE` | `/estoques/{id}` | `ADMIN` |
| `POST` | `/pedidos` | Autenticado; `CLIENTE` cria pedido em seu próprio nome |
| `GET` | `/pedidos` | Autenticado; `CLIENTE` consulta somente os próprios pedidos |
| `GET` | `/pedidos/{id}` | Autenticado; `CLIENTE` consulta somente o próprio pedido |
| `PATCH` | `/pedidos/{id}` | `CLIENTE`, `ATENDENTE` ou `ADMIN`; `CLIENTE` altera somente o próprio pedido |
| `DELETE` | `/pedidos/{id}` | `ADMIN` |
| `POST` | `/pagamentos` | `CLIENTE`, `ATENDENTE` ou `ADMIN`; `CLIENTE` paga somente o próprio pedido |
| `GET` | `/pagamentos` | Autenticado; `CLIENTE` consulta pagamentos dos próprios pedidos |
| `GET` | `/pagamentos/{id}` | Autenticado; `CLIENTE` consulta somente pagamento do próprio pedido |
| `DELETE` | `/pagamentos/{id}` | `ADMIN` |
| `GET` | `/fidelidade/{id}` | Autenticado; `CLIENTE` consulta somente a própria fidelidade |
| `POST` | `/auditorias` | `ADMIN` |
| `GET` | `/auditorias` | `ADMIN` |
| `GET` | `/auditorias/{id}` | `ADMIN` |

A disponibilidade e as permissões de cada operação devem ser consultadas na documentação Swagger.

## Testes com Postman

A coleção de testes está localizada no diretório `postman`, no arquivo `Projeto Final.postman_collection.json`.

Para executar os testes:

1. Importe o arquivo JSON no Postman.
2. Para testar a versão publicada, mantenha a URL de produção configurada na coleção. Para testar localmente, substitua a URL de produção por `http://localhost:8080` nas requisições.
3. Execute o login correspondente ao perfil que será utilizado: `ADMIN`, `CLIENTE` ou `ATENDENTE`.
4. Execute as requisições desejadas.

A coleção utiliza a variável `token` para armazenar o JWT obtido no login. Antes de executar testes que dependem de permissões específicas, realize o login com o perfil adequado, pois o token armazenado corresponde ao último login realizado.

Para os testes de acesso sem autenticação, utilize as requisições configuradas sem envio de token.

## Pagamentos

O gateway de pagamento utilizado pela aplicação é simulado e não realiza integração com um provedor financeiro real.

Os pagamentos em dinheiro são aceitos somente para pedidos do canal `BALCAO`. As demais formas de pagamento podem resultar em aprovação ou recusa conforme o comportamento implementado no gateway simulado.

## Testes automatizados e build

No Windows, os testes automatizados podem ser executados pelo terminal, na pasta `backend`:

```powershell
.\gradlew.bat test
```

Para executar a compilação completa:

```powershell
.\gradlew.bat clean build
```

Em sistemas Linux ou macOS compatíveis, utilize:

```bash
./gradlew test
./gradlew clean build
```
