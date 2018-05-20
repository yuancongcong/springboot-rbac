package com.lingxi.framework.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * 封装json对象，所有返回结果都使用它
 */
@Getter
@Setter
@Slf4j
public class ResultMsg<T> {

    public static String SUCCESS = "success";
    public static String FAILURE = "failure";
    public static String EXCEPTION = "exception";

    private String status ;

    private T data;// 成功时返回的数据
}


