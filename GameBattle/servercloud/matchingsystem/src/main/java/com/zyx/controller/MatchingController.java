package com.zyx.controller;

import com.zyx.service.MatchingService;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author 张宇森
 * @version 1.0
 */
@RestController
public class MatchingController {

    @Resource
    private MatchingService matchingService;

    @PostMapping("/player/add")
    public String addPlayer(@RequestParam MultiValueMap<String, String> data){
        Integer userId = Integer.parseInt(Objects.requireNonNull(data.getFirst("user_id")));
        Integer rating = Integer.parseInt(Objects.requireNonNull(data.getFirst("rating")));
        Integer robotId = Integer.valueOf(Objects.requireNonNull(data.getFirst("robot_id")));
        return matchingService.addPlayer(userId,rating,robotId);
    }

    @PostMapping("/player/delete")
    public String deletePlayer(@RequestParam MultiValueMap<String, String> data){
        Integer userId = Integer.parseInt(Objects.requireNonNull(data.getFirst("user_id")));
        return matchingService.deletePlayer(userId);
    }

}
