# Geolocalização no Cidade Social com ElasticSearch
Como projeto da displina de web3, temos a implementação da aplicação CidadeSocial com um Client do ElasticSearch para que a consulta a ele, feita por um cliente mobile em React-Native, seja feita por meio da aplicação CidadeSocial no servidor e não diretamente.

## Elastic - Logstash - Postgres
Há um arquivo **docker-compose.yml** para acelerar o início dos servidores postgres e do ELK Stack, só sendo necessário executar o comando `docker-compose up`, na pasta `./docker`.

## Spring Boot

Para iniciar o servidor, basta apenas que os containers estejam rodando.
Rode os seguintes comandos na pasta raíz do projeto: 
1. `mvn -N wrapper:wrapper` para instalar o wrapper do maven. 
2. `./mvnw spring-boot:run`para exeutar o servidor.

## Postman

Na pasta `aux_files`, um arquivo com a *collection* de consultas do Postman. Siga as instruções do [site oficial](https://learning.postman.com/docs/getting-started/importing-and-exporting-data/#importing-postman-data) para importar o arquivo  no Postman e fazer os três testes: teste de busca por centróide, busca pelo retângulo da tela do celular, e teste de busca por palavra-chave.