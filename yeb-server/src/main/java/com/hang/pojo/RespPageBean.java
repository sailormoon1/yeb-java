package com.hang.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @USER: zzh-
 * @DATE: 2022/4/21
 * @TIME: 13:42
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespPageBean {

    /**
     * 总条数
     */
    private Long total;

    /**
     * 数据list
     */
    private List<?> data;



}
