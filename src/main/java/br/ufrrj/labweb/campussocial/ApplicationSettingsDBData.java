package br.ufrrj.labweb.campussocial;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import org.springframework.beans.factory.annotation.Autowired;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ApplicationSettingsDBData implements CommandLineRunner {

    @Autowired

    public ApplicationSettingsDBData() {
    }

    public static void main(String[] args) {
        SpringApplication.run(ApplicationSettingsDBData.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    }

}