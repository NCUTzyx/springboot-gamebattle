package com.zyx.controller.peak;

import com.alibaba.fastjson2.JSONObject;
import com.zyx.service.peak.GetPeakListService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author 张宇森
 * @version 1.0
 */
@RestController
public class GetPeakListController {

    @Resource
    private GetPeakListService getPeakListService;

    @GetMapping("/api/peak/getlist")
    public JSONObject getList(@RequestParam Map<String, String> data) {
        Integer page = Integer.valueOf(data.get("page"));
        return getPeakListService.getList(page);
    }
}
