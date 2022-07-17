package com.example.demo.exception;

import com.example.demo.bean.BaseRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author shs-cyhlwzytxf
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 自定义业务异常捕获
     * @param e
     * @return
     */
    @ExceptionHandler(value = BusinessException.class)
    public BaseRes businessException(BusinessException e){
        log.error("发生业务异常，异常码为:{}，异常信息为：{}", e.getCode(), e.getMsg());
        return BaseRes.error(e.getCode(), e.getMsg());
    }

    /**
     * 运行时异常捕获
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public BaseRes exceptionHandler(Exception e){
        log.error("unknown Exception happened, the reason is : {}",e);
        return BaseRes.error(ErrorMessageEnum.SERVICE_EXCEPTION.getErrCode(),
                ErrorMessageEnum.SERVICE_EXCEPTION.getErrMsg());
    }

    /**
     * 请求参数异常，一般用于校验body参数
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public BaseRes handleValidationBodyException(MethodArgumentNotValidException e) {
        log.error("request param ValidException, the reason is : {}",e);
        if(!CollectionUtils.isEmpty(e.getBindingResult().getAllErrors())){
            return BaseRes.error(400, e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        }
        return BaseRes.error(400, "未知参数错误");
    }

}
