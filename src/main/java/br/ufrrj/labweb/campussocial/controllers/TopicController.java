package br.ufrrj.labweb.campussocial.controllers;

import java.io.IOException;
import java.util.List;

import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufrrj.labweb.campussocial.model.Topic;
import br.ufrrj.labweb.campussocial.model.TopicRequestData;
import br.ufrrj.labweb.campussocial.model.TopicResultData;
import br.ufrrj.labweb.campussocial.services.TopicService;

@RestController
@RequestMapping("/topics")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @PostMapping("/square")
    List<TopicResultData> getWithinSquare(@RequestBody TopicRequestData requestData) throws IOException {

        SearchHits searchHits = topicService.getWithinSquare(requestData.getTopLeftLat(),
                requestData.getTopLeftLon(),
                requestData.getBottomRightLat(), requestData.getBottomRightLon(), requestData.getCenterLat(),
                requestData.getCenterLon(), requestData.getUnit(), requestData.getTimestampLowerBound(),
                requestData.getTimestampUpperBound(), requestData.getPageOffset(),
                requestData.getSearchAfter());

        return topicService.toResultData(searchHits);

    }
}
