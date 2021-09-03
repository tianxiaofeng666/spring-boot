package com.example.springbootswagger.exception;

import com.example.springbootswagger.bean.RestfulResponse;
import com.example.springbootswagger.bean.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

/**
 * 全局异常处理类
 * @author shs-cyhlwzytxf
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 处理方法参数无效异常
     * @param ex
     * @return
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public RestfulResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<FieldError> list = bindingResult.getFieldErrors();
        StringBuilder sb = new StringBuilder();
        sb.append(ResultCode.VALIDATE_FAILED.getMessage() + ":");
        int i = 0;
        for (FieldError fieldError : list) {
            ++i;
            sb.append(i);
            sb.append(fieldError.getDefaultMessage());
        }
        log.info("入参异常:{}",sb.toString());
        //return RestfulResponse.failed(bindingResult.getFieldErrors().get(0).getDefaultMessage());
        return RestfulResponse.failed(ResultCode.VALIDATE_FAILED.getCode(),sb.toString());
    }

    /**
     * 自定义业务异常捕获
     * @param e
     * @return
     */
    @ExceptionHandler(value = BusinessException.class)
    public RestfulResponse businessException(BusinessException e){
        log.error("发生业务异常，异常码为:{}，异常信息为：{}", e.getCode(), e.getMsg());
        return RestfulResponse.failed(e.getCode(),e.getMsg());
    }

    /**
     * 运行时异常捕获
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public RestfulResponse exceptionHandler(Exception e){
        log.error("unknown Exception happened, the reason is : {}",e.getMessage());
        return RestfulResponse.failed();
    }

}
