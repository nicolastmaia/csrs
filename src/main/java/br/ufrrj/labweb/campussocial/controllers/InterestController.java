package br.ufrrj.labweb.campussocial.controllers;

import java.util.List;

import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufrrj.labweb.campussocial.model.Interest;
import br.ufrrj.labweb.campussocial.model.InterestRequestData;
import br.ufrrj.labweb.campussocial.model.InterestResultData;
import br.ufrrj.labweb.campussocial.services.InterestService;

@RestController
@RequestMapping("/interestpois")
public class InterestController {

    private final InterestService interestService;

    public InterestController(InterestService interestService) {
        this.interestService = interestService;
    }

    @PostMapping("/bynamelist")
    List<InterestResultData> getByNamelist(@RequestBody InterestRequestData requestData) {
        List<SearchHit<Interest>> searchHits = interestService.getByNameList(requestData.getNameList());

        return interestService.toResultData(searchHits);

    }

}
