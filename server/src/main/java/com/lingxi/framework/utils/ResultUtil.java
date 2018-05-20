package com.lingxi.framework.utils;

import com.lingxi.framework.domain.Page;
import com.lingxi.framework.domain.ResultMsg;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class ResultUtil {

    public static <T> ResultMsg<T> success(T data) {
        ResultMsg result = new ResultMsg();
        result.setData(data);
        result.setStatus(ResultMsg.SUCCESS);
        return result;
    }
    public static <T> ResultMsg<Page<T>> success(org.springframework.data.domain.Page<T> page) {
        return success(new Page(page));
    }

    public static ResultMsg failure(String msg) {
        ResultMsg result = new ResultMsg();
        result.setData(msg);
        result.setStatus(ResultMsg.FAILURE);
        return result;
    }

    public static ResultMsg failure(String msg, String status) {
        ResultMsg result = failure(msg);
        if(status != ResultMsg.SUCCESS){
            result.setStatus(status);
        }else{
            log.warn("code 不能为：" + ResultMsg.SUCCESS);
        }
        return result;
    }

    public static ResultMsg exception(String msg) {
        ResultMsg result = new ResultMsg();
        result.setData(msg);
        result.setStatus(ResultMsg.EXCEPTION);
        return result;
    }

    public static void responsePrint(HttpServletResponse response, ResultMsg msg) throws IOException {
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        writer.print(JsonUtils.toJson(msg));
        writer.flush();
        writer.close();
    }
}
