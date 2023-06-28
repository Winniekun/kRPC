package com.wkk.rpc.idl.hello;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 类描述: TODO
 *
 * @author kongweikun@163.com
 * @date 2023/6/28
 */
@Data
@AllArgsConstructor
public class HelloResponse implements Serializable {

    private String msg;
}
