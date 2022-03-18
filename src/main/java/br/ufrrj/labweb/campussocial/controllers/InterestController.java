package br.ufrrj.labweb.campussocial.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufrrj.labweb.campussocial.repositories.InterestRepository;

@RestController
@RequestMapping("/interestpois")
public class InterestController {

    private final InterestRepository repository;

    public InterestController(InterestRepository repository) {
        this.repository = repository;
    }

}
