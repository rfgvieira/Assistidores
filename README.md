
# Assistidores

API para ser consumida para uma plataforma de séries


## Stack

**Linguagem:** Java (versions: 17)

**Bibliotecas/Frameworks:** Spring Boot (3.2.4), JPA/Hibernate

**Banco de Dados:** PostgreSQL (version: 16)

**IDE:** IntelliJ

## Estrutura do Projeto
Utilizando a arquitetura MVC o projeto ficou com  a seguinte estrutura:

- **`config`:** Contém configuração da aplicação.
- **`controller`:** Contem as rotas para serem consumidas
- **`dto`:** Contem as DTO
- **`model`:** Classes representando as tabelas do banco de dados
- **`principal`:** Contém a classe principal da aplicação
- **`repository`:** Contém a comunicação com o banco de dados
- **`service`:** Contém arquivos relacionados a rede

## Configuração

### IntelliJ
* Selecione `File` na barra de menu
* Depois Selecione `New` e em seguida `Project from Version Control...`
* No campo `URL` cole o seguinte trecho:
```bash
  https://github.com/rfgvieira/Assistidores.git
```
Após isso você terá que criar o banco de dados no PostgreSQL, depois de criado volte ao Intellij e abra o arquivo `application.properties` localizado na pasta `resources` e configure os seguintes campos com os dados do banco que você acabou de criar:
* `spring.datasource.url`
* `spring.datasource.username`
* `spring.datasource.password`

Obs.: O banco necessita ser populado após a migração

    
## Rodar

### IntelliJ
* Clique onde está escrito `Current File` e depois em `Edit Configurations...`
* Após isso clique em `Add new...` -> `Application` 
* No campo `Main Class` clique no ícone e selecione `AssistidoresApplication` e clique em `OK`
* Após isso para rodar a aplicação é só clicar no ícone de rodar ou debugar

## Rotas

### `/series`
* Contém dados de todas as séries

### `/series/top5`
* Obtém as 5 séries com melhor avaliação

### `/series/lancamentos`
* Obtém as 5 séries que teve episódios mais recentes

### `/series/{id}`
* Obtém uma séries especificada pelo id dela

 ### `/series/{id}/temporadas/todas`
* Obtém os episodios de todas as temporadas da série

### `/series/{id}/temporadas/{num}`
*  Obtém os episodios da temporada especificada de uma série

### `/series/categoria/{nome}`
*  Obtém todas as séries de uma determinada categorias


## Author

- Rodrigo Vieira ([@rfgvieira](https://github.com/rfgvieira))

