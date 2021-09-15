package com.example.springbootdelaytask.entity;

import lombok.Data;

/**
 * @author shs-cyhlwzytxf
 */
@Data
public class Order {

    private Long id;

    private Long orderId;

    private String mobile;

    private String email;

    private Integer status;

    private String createTime;

    private String createUser;

    private String paymentTime;

    private String modifiedTime;

    private String modifiedUser;

    private Integer isDeleted;
}
