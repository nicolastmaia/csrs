package br.ufrrj.labweb.campussocial;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ApplicationSettingsDBData implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ApplicationSettingsDBData() {
    }

    public static void main(String[] args) {
        SpringApplication.run(ApplicationSettingsDBData.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        String interestSql = "SELECT * FROM interestpost WHERE interest_id IN ("
                + String.join(",", "62", "53", "52", "60")
                + ")";
        List<Map<String, Object>> interestRows = jdbcTemplate.queryForList(interestSql);

    }

}