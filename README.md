# Geolocalização no Cidade Social com ElasticSearch
Como projeto da displina de web3, temos a implementação da aplicação CidadeSocial com um Client do ElasticSearch para que a consulta a ele, feita por um cliente mobile em React-Native, seja feita por meio da aplicação CidadeSocial no servidor e não diretamente.

## Elastic - Logstash - Postgres
Primeiramente, temos que configurar o *pipeline* para que o logstash possa pegar dados do postgres e preencher e atualizar o elastic conforme o banco seja utilizado.

Para isso, é necessário ter o logstash, que não está dockerizado, e precisa ser instalado por meio do [site oficial](https://www.elastic.co/guide/en/logstash/current/installing-logstash.html). As instruções também se encontram nele.

Após a instalação, é necessário alterar o arquivo .conf original, pelo modificado para acessar e requisitar dados do postgres. O arquivo modificado é o **logstash.conf**, dentro da pasta **aux_files**.

Há um arquivo docker-compose.yml para acelerar o início dos servidores postgres e do elasticSearch, só sendo necessário executar o comando `docker-compose up`, na pasta onde o docker-compose.yml se encontra.

Com o container iniciado, siga os passos da seção [Postman - Spring Boot](##Postman---Spring-Boot) para pegar a coleção de rotas do postman para testar o server e depois volte para o próximo passo.

Antes de iniciar o logstash, execute a única rota PUT que se encontra no arquivo do postman importado.

Após instalar e configurar o logstash, entre na pasta '/usr/share/logstash/bin' e execute o comando `./logstash -f ./logstash.conf`.

Para popular o banco rode os seguintes comandos após estar com os containers rodando :

1 - `docker exec -it db-new-cs bash`
2 - `cd /var/lib/postgres/data`
3 - `psql -U postgres -d campsocialdb < cs-populado`

Pronto. O banco agr já está populado com alguns dados mockados que inserimos através do postman.

Já temos também o elastic rodando num container e o logstash rodando na sua máquina.

Continue nos próximos passos para rodar o servidor spring-boot e depois testar as rotas com o postman.
## Spring Boot - Elastic

Para utilizar a aplicação Spring Boot, basta apenas que o elasticSearch esteja iniciado e que você possua o maven instalado na sua máquina. Rode o comando `mvn spring-boot:run` na pasta raíz da aplicação para subi-la ao tomcat.
Os arquivos de configuração necessários para o acesso ao elastic já estão inclusos.

## Postman - Spring Boot

A aplicação está funcional no client React-native, mas o start com o react-native para teste pode ser substituido por um teste no Postman, por questões de simplicidade. 

Também estará no arquivo zipado aux_files.zip, um arquivo com as consultas no Postman. Então basta apenas importar o arquivo e fazer os três testes: teste de busca por centróide, busca pelo retangulo da tela do celular, e teste de busca por palavra-chave.

Para importar basta seguir as instruções do [site oficial](https://learning.postman.com/docs/getting-started/importing-and-exporting-data/#importing-postman-data).