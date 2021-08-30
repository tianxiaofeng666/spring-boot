/** 以下代码由代码生成器自动生成，如无必要，请勿修改！2021-6-7 9:32:59 **/
package com.example.springbootswagger.bean;

/**
 * @author code-generator
 * @date 2021-6-7 9:32:59
 */
 public enum ResultCode {
     /*
      * 接口调用成功
      * */
     SUCCESS(0, "接口调用成功"),
     /*
      * 接口调用失败
      * */
     FAILED(500, "接口调用失败"),
     /*
      * 参数检验失败
      * */
     VALIDATE_FAILED(1000, "参数检验失败"),
     /*
      * 暂未登录或token已经过期
      * */
     UNAUTHORIZED(1001, "暂未登录或token已经过期"),
     /*
      * 没有相关权限
      * */
     FORBIDDEN(1002, "没有相关权限");
     private int code;
     private String message;

     private ResultCode(int code, String message) {
         this.code = code;
         this.message = message;
     }

     public int getCode() {
         return code;
     }

     public String getMessage() {
         return message;
     }
 }
