package com.marvin.service;

/**
 * @author Marvin
 * @Description com.marvin.service
 * @create 2022-03-07 11:23
 */


public interface MsgService {
    boolean send(String phone, String code);
}
