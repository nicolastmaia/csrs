package br.ufrrj.labweb.campussocial.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufrrj.labweb.campussocial.repositories.InterestRepository;

@RestController
@RequestMapping("/interestpois")
public class InterestController {

    public InterestController(InterestRepository repository) {
    }

}
