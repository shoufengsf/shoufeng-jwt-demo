package com.shoufeng.shoufengjwtdemo.common;

import com.alibaba.fastjson.JSON;
import lombok.Builder;

/**
 * 统一API响应结果封装
 *
 * @author zhihao.mao
 */
@Builder
public class Result<T> {

  /**
   * 响应状态码，具体含义查看ResultCode
   */
  private int code;

  /**
   * 响应消息
   */
  private String message;

  /**
   * 响应数据
   */
  private T data;

  public int getCode() {
    return code;
  }

  public Result setCode(ResultCode resultCode) {
    this.code = resultCode.code();
    return this;
  }

  public Result setCode(int code) {
    this.code = code;
    return this;
  }

  public String getMessage() {
    return message;
  }

  public Result setMessage(String message) {
    this.message = message;
    return this;
  }

  public T getData() {
    return data;
  }

  public Result setData(T data) {
    this.data = data;
    return this;
  }

  @Override
  public String toString() {
    return JSON.toJSONString(this);
  }
}
