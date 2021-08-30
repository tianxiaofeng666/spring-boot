package com.example.springbootswagger.config;

import com.example.springbootswagger.bean.RestfulResponse;
import com.example.springbootswagger.bean.ResultCode;
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
@RestControllerAdvice
public class ExceptionHandlerConfig {
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
        System.out.println("信息：" + sb.toString());
        //return RestfulResponse.failed(bindingResult.getFieldErrors().get(0).getDefaultMessage());
        return RestfulResponse.failed(ResultCode.VALIDATE_FAILED.getCode(),sb.toString());
    }

    /**
     * 处理约束条件例外异常
     * @param ex
     * @return
     */
    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public RestfulResponse handleConstraintViolationException(ConstraintViolationException ex) {
        Optional<ConstraintViolation<?>> msg = ex.getConstraintViolations().stream().findFirst();
        RestfulResponse response = RestfulResponse.failed();
        return msg.map(constraintViolation -> setMessage(response, constraintViolation.getMessage())).orElseGet(this::setCode);
    }


    private RestfulResponse setMessage(RestfulResponse response,String message) {
        response.setMessage(message);
        return response;
    }

    private RestfulResponse setCode() {
        return RestfulResponse.failed();
    }

}
