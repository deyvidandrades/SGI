

# SGI - Sistema Gerenciador de Imobiária
#### [Projeto prático](https://github.com/deyvidandrades/SGI) desenvoldido na disciplina de Engenharia de Software.

A proposta inicial é de apresentar um sistema de gestão para imobiliárias, onde os gerentes e possam acompanhar andamento dos contratos, cadastrar novos imóveis e clientes. Onde os vendedores possam consultar os contratos em vigência, cadastrar clientes e realizar a venda/locação de um imóvel à um cliente. O foco principal tornar simples os processos de venda e locação de imóveis para diferentes imobiliárias.

# Primeiros passos

Essas instruções farão com que você tenha uma cópia do projeto em execução na sua máquina local para fins de desenvolvimento e teste. Veja a implantação de notas sobre como implantar o projeto em um sistema ativo.

**Importante:** Toda a GUI do projeto foi desenvolvida com o *Intellij Idea GUI Designer*, limitando o uso de form designers de outras ide's.

## Pré-requisitos

O que você precisa instalar para desenvolver e rodar o projeto:
* [Java Jdk - Java 17](https://www.oracle.com/technetwork/pt/java/javase/downloads/jdk8-downloads-2133151.html) - Ambiente de desenvolvimento Java
* [Intellij Idea](https://www.jetbrains.com/idea/download/) - IDE usada no projeto


## Baixe ou clone o repositório
```
$ git clone https://github.com/deyvidandrades/SGC.git
```
## Rodando o SGI:
```
$ java -jar SGI.jar
```

## Configurações para a Intellij Idea

Importe as bibliotecas utilizadas no projeto, localizadas em: (**src/bibliotecas**):
```
file > Project Estructure > Libraries > + > Java > (biblioteca) > OK
```

Configure os artefatos e marque a opção `include in project build`:
```
file > Project Estructure > Artifacts > + > JAR > from modules with dependencies
```
Construa o projeto (**ctrl+f9**) e crie uma configuração de executável `Jar Application`:
```
run > Edit Configurations > + > Jar Application
```
Selecione o caminho onde foi criado o .jar: (**/out/artifacts/SGI/SGI.jar**)

Adicione o artefato à nova configuração:
```
Before Launch > + > Build Artifacts > SGI.jar
```
Agora você pode rodar o programa diretamente do Intellij Idea.

## Como usar o SGI

### Executando o SGI
Se você optar por não abrir o projeto na *Intellij Idea*  e configurar manualmente seu ambiente de execução, você pode simplesmente rodar o executável java localizado no diretório `dist/` na raiz do projeto.
```
$ java -jar SGI.jar
```

Entre com o login e a senha padrão e crie seu usuário e dê as devidas permissões.
```
admin admin
```
Simule a venda e locação de imóveis, cadastre clientes, novos imóveis, funcionários e crie contratos de venda/locação.

## Ferramentas e Bibliotecas

* [Java Jdk 17+](https://www.oracle.com/technetwork/pt/java/javase/downloads/jdk8-downloads-2133151.html) - Ambiente de desenvolvimento Java
* [Intellij Idea](https://www.jetbrains.com/idea/download/) - IDE usada no projeto
* [Commons-io](https://mvnrepository.com/artifact/commons-io/commons-io) para leitura de arquivos.
* [JSON](https://mvnrepository.com/artifact/org.json/json) - Json-java utilizado para persistência de dados. 
* [FlatLaf](https://mvnrepository.com/artifact/com.formdev/flatlaf/3.1.1) - Tema moderno para o Java Swing

## Changelog
- 

## Autores

* **Deyvid Andrade** - [GIT](https://github.com/deyvidandrades)
* **Kleber Salllum** - [GIT](https://github.com/klebersalllum/SGI)
* 
## Licença
Este projeto está licenciado sob a licença MIT - consulte o arquivo [LICENSE.md](LICENSE.md) para obter detalhes
