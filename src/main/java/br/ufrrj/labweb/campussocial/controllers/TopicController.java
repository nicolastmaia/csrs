package br.ufrrj.labweb.campussocial.controllers;

import java.util.List;

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

    @PostMapping("/circle")
    List<TopicResultData> getWithinCircle(@RequestBody TopicRequestData requestData) {

        List<SearchHit<Topic>> searchHits = topicService.getWithinCircle(requestData.getCenterLat(),
                requestData.getCenterLon(),
                requestData.getDistance(), requestData.getUnit());

        return topicService.toResultData(searchHits);
    }

    @PostMapping("/square")
    List<TopicResultData> getWithinSquare(@RequestBody TopicRequestData requestData) {

        List<SearchHit<Topic>> searchHits = topicService.getWithinSquare(requestData.getTopLeftLat(),
                requestData.getTopLeftLon(),
                requestData.getBottomRightLat(), requestData.getBottomRightLon(), requestData.getCenterLat(),
                requestData.getCenterLon(), requestData.getUnit(), requestData.gettimestampLowerBound(),
                requestData.getTimestampUpperBound(), requestData.getPageStart(), requestData.getPageOffset());

        return topicService.toResultData(searchHits);
    }

    @PostMapping("/bytitle")
    List<TopicResultData> getByTitle(@RequestBody TopicRequestData requestData) {

        List<SearchHit<Topic>> searchHits = topicService.getByTitle(requestData.getTitle());

        return topicService.toResultData(searchHits);
    }

}
