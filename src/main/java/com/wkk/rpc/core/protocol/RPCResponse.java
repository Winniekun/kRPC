package com.wkk.rpc.core.protocol;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 类描述: RPC响应内容
 *
 * @author kongweikun@163.com
 * @date 2023/6/28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RPCResponse implements Serializable {

    /**
     * 响应头
     */
    private String header;

    /**
     * 响应体
     */
    private byte[] body;
}
