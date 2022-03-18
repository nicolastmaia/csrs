package br.ufrrj.labweb.campussocial.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufrrj.labweb.campussocial.model.TopicRequestData;
import br.ufrrj.labweb.campussocial.model.TopicResultData;
import br.ufrrj.labweb.campussocial.services.TopicService;

@RestController
@RequestMapping("/topicpois")
public class TopicController {

    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @PostMapping("/within/circle")
    List<TopicResultData> getWithinDistance(@RequestBody TopicRequestData requestData) {
        return topicService.getWithinDistance(requestData.getCenterLat(), requestData.getCenterLon(),
                requestData.getDistance(), requestData.getUnit());
    }

    @PostMapping("/within/square")
    List<TopicResultData> getWithinSquare(@RequestBody TopicRequestData requestData) {
        return topicService.getWithinSquare(requestData.getTopLeftLat(), requestData.getTopLeftLon(),
                requestData.getBottomRightLat(), requestData.getBottomRightLon(), requestData.getCenterLat(),
                requestData.getCenterLon(), requestData.getUnit());
    }

    @PostMapping("/bytitle")
    List<TopicResultData> getByTitle(@RequestBody TopicRequestData requestData) {
        return topicService.getByTitle(requestData.getTitle());
    }

}
