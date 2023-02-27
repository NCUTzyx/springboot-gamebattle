package com.zyx.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 张宇森
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Player {

    private Integer userId;
    private Integer rating;
    private Integer robotId;
    private Integer waitingTime; //等待时间


}
