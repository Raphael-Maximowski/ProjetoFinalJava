# Sistema de Gestão de Pedidos

Um sistema completo de gestão de pedidos desenvolvido em **Java** como projeto final da disciplina de POO. O sistema permite gerenciar clientes, produtos e pedidos com processamento assíncrono (Utilizando Threads).

## 📋 Funcionalidades

- ✅ **Gestão de Clientes**: Cadastrar e listar clientes com validação de dados
- ✅ **Gestão de Produtos**: Cadastrar e listar produtos com categorias
- ✅ **Gestão de Pedidos**: Criar e listar pedidos com filtro por status
- ✅ **Processamento Assíncrono**: Processa pedidos em segundo plano
- ✅ **Validação de Dados**: Tratamento robusto de exceções
- ✅ **Repositórios em Memória**: Armazenamento de dados em RAM

## 🏗️ Arquitetura

O projeto segue o padrão **MVC** com separação de responsabilidades:

```
ProjetoFinalJava/
├── Main.java                          # Ponto de entrada da aplicação
├── MenuPrincipal.java                 # Interface(Console) de usuário
│
├── models/                            # Camada de Modelos
│   ├── Cliente.java                   # Classe de Cliente
│   ├── Produto.java                   # Classe de Produto
│   ├── Pedido.java                    # Classe de Pedido
│   └── ItemPedido.java                # Classe de Item do Pedido
│
├── services/                          # Camada de Serviços (Lógica de Negócio)
│   ├── ClienteService.java            # Serviços de Cliente
│   ├── ProdutoService.java            # Serviços de Produto
│   └── PedidoService.java             # Serviços de Pedido
│
├── repositories/                      # Camada de Dados (Persistência)
│   ├── Repositorio.java               # Interface base
│   ├── RepositorioCliente.java        # Repositório de Cliente
│   ├── RepositorioProduto.java        # Repositório de Produto
│   └── RepositorioPedido.java         # Repositório de Pedido
│
├── processors/                        # Processamento Assíncrono
│   ├── FilaProcessamento.java         # Fila de processamento
│   ├── ProcessadorPedidos.java        # Interface do processador
│   └── ProcessadorPedidosAssincrono.java  # Implementação assíncrona
│
├── enums/                             # Enumerações
│   ├── CategoriaProduto.java          # Categorias de produtos
│   └── StatusPedido.java              # Status dos pedidos
│
└── exceptions/                        # Exceções Customizadas
    ├── EmailInvalidoException.java
    ├── NomeInvalidoException.java
    ├── PedidoInvalidoException.java
    ├── PrecoInvalidoException.java
    └── ValidacaoException.java
```

## 🚀 Como Executar

### Pré-requisitos

- **Java JDK 8+** instalado
- **Compilador Java** (javac)

### Passos para Executar

1. **Navegue até o diretório do projeto:**
   ```powershell
   cd c:\Users\Samsung\Documents\Code\Faculdade\ProjetoFinalJava
   ```

2. **Compile o projeto:**
   ```powershell
   javac -d bin *.java models/*.java services/*.java repositories/*.java processors/*.java enums/*.java exceptions/*.java
   ```

3. **Execute a aplicação:**
   ```powershell
   java -cp bin Main
   ```

### Menu de Opções

Ao executar, você terá acesso ao seguinte menu:

```
==================================================
       SISTEMA DE GESTAO DE PEDIDOS
==================================================
1. Cadastrar Cliente
2. Cadastrar Produto
3. Criar Pedido
4. Listar Clientes
5. Listar Produtos
6. Listar Pedidos
7. Listar Pedidos por Status
8. Sair
==================================================
```

## 📖 Exemplos de Uso

### Cadastrar um Cliente

1. Selecione a opção **1** no menu
2. Digite o **nome** do cliente
3. Digite o **email** do cliente
4. O sistema validará os dados e exibirá uma mensagem de sucesso ou erro

### Cadastrar um Produto

1. Selecione a opção **2** no menu
2. Digite o **nome** do produto
3. Digite o **preço** do produto
4. Selecione a **categoria** do produto
5. O sistema confirmará o cadastro

### Criar um Pedido

1. Selecione a opção **3** no menu
2. Escolha um **cliente** da lista disponível
3. Adicione **produtos** ao pedido informando a quantidade
4. Finalize com **0** para confirmar o pedido
5. O pedido será enviado para processamento em segundo plano

## 🔍 Componentes Principais

### Modelos (Models)
- **Cliente**: Representa um cliente com ID, nome e email
- **Produto**: Representa um produto com ID, nome, preço e categoria
- **Pedido**: Representa um pedido com cliente, itens e status
- **ItemPedido**: Representa um item dentro de um pedido

### Serviços (Services)
- **ClienteService**: Gerencia operações de clientes
- **ProdutoService**: Gerencia operações de produtos
- **PedidoService**: Gerencia operações de pedidos

### Repositórios (Repositories)
- Implementam padrão de acesso a dados
- Funcionam com armazenamento em memória
- Gerenciam listas de objetos

### Processamento Assíncrono (Processors)
- **FilaProcessamento**: Fila de processamento thread-safe
- **ProcessadorPedidosAssincrono**: Processa pedidos em thread separada

## 🛡️ Validações

O sistema implementa validações robustas:

- ✅ **Email**: Não pode ser vazio
- ✅ **Nome**: Não pode ser vazio
- ✅ **Preço**: Deve ser maior que 0
- ✅ **Pedido**: Deve ter pelo menos um item

## ⚠️ Tratamento de Exceções

O projeto utiliza exceções customizadas para tratamento de erros:

- `EmailInvalidoException`
- `NomeInvalidoException`
- `PedidoInvalidoException`
- `PrecoInvalidoException`
- `ValidacaoException`

## 👨‍💻 Tecnologias Utilizadas

- **Linguagem**: Java
- **Padrão de Design**: MVC, Repository, Dependency Injection
- **Processamento**: Thread para operações assíncronas
- **Armazenamento**: In-Memory (HashMap, ArrayList)

## 📝 Notas Importantes

- Os dados são armazenados **em memória** e serão perdidos ao encerrar a aplicação
- O processamento de pedidos ocorre em uma **thread separada**
- O sistema é **thread-safe** para operações simultâneas

## 🎓 Propósito Acadêmico

Este projeto foi desenvolvido como trabalho final de faculdade para demonstrar conhecimentos em:

- Orientação a Objetos
- Padrões de Design
- Manipulação de Exceções
- Programação Concorrente
- Estruturas de Dados
- Boas Práticas de Código

## 📧 Informações da Equipe

- **Nome do Repositório**: ProjetoFinalJava
- **Integrantes**: Alan Pereria e Raphael-Maximowski
