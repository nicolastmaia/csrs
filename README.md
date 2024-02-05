# CSRS
This was my final graduation project for my university course in Information Systems. It's a Recommender System for georreferenced content for another project called CidadeSocial, developed by professors and students from UFRRJ and UFRJ.  
  
In this project, georreferenced items are taken from a PostgreSQL DB and indexed to Elasticsearch to be later recovered by the CSRS application.  
  
The actual CSRS application is written in SpringBoot and combines georreferenced items with users' interest tags to create a list with items recommended for a specific user.

Below you'll find two diagrams that explain the working of CSRS:
1. This first iamge is an activity diagram that shows how CSRS integrates with the CidadeSocial mobile app and the CidadeSocial server.

    <img src="https://github.com/nicolastmaia/csrs/assets/45211638/15bc14de-0c85-499c-8a09-f860ee9eece6" alt="csrs-flow" width="600"/>

&nbsp;  
  
2. This second diagram shows how CSRS is structered internally and how it interacts with its data sources.
    <img src="https://github.com/nicolastmaia/csrs/assets/45211638/bdd54ef2-536d-460c-997a-aebc3ad0577f" alt="csrs-internal" width="600"/>  
  
In this [link](https://github.com/nicolastmaia/csrs-map-demo) you can also find a frontend demo application that uses CSRS without the need of an intermediary CidadeSocial server running.
