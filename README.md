# Geolocalização no Cidade Social com ElasticSearch
Como projeto da displina de web3, temos a implementação da aplicação CidadeSocial com um Client do ElasticSearch para que a consulta a ele, feita por um cliente mobile em React-Native, seja feita por meio da aplicação CidadeSocial no servidor e não diretamente.

## Elastic - Logstash - Postgres
Há um arquivo **docker-compose.yml** para acelerar o início dos servidores postgres e do ELK Stack dentro da pasta 'docker'. Entre nessa pasta e siga uma das seguintes instruções dependendo da maneira que deseja levantar os containers.

#### Opção 1: Se quiser aproveitar as configurações de limite de memória e CPU do **docker-compose.yml**, você precisará:
  1. Iniciar um docker swarm com `docker swarm init`;
  2. Depois, execute o comando `docker stack deploy -c docker-compose.yml cs-stack`.
    Isso fará com que os containers rodem com os limites de memória e CPU definidos nas seções *deploy* de cada serviço no arquivo **docker-compose.yml**.
#### Opção 2: Caso queira apenas levantar os containers e não se importar com os limites de memória e CPU, execute: `docker-compose up`.
## Spring Boot

Para iniciar o servidor, basta apenas que os containers estejam rodando.
Rode o comando `./mvnw spring-boot:run`  na pasta raíz do projeto para exeutar o servidor.

## Postman

Na pasta `aux_files`, um arquivo com a *collection* de consultas do Postman. Siga as instruções do [site oficial](https://learning.postman.com/docs/getting-started/importing-and-exporting-data/#importing-postman-data) para importar o arquivo  no Postman e fazer os três testes: teste de busca por centróide, busca pelo retângulo da tela do celular, e teste de busca por palavra-chave.