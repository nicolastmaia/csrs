package br.ufrrj.labweb.campussocial.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufrrj.labweb.campussocial.repositories.InterestPOIRepository;

@RestController
@RequestMapping("/interestpois")
public class InterestPOIController {

    private final InterestPOIRepository repository;

    public InterestPOIController(InterestPOIRepository repository) {
        this.repository = repository;
    }

}
