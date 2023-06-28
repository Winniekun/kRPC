package com.wkk.rpc.core.codec;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 类描述:  RPC 响应 body
 *
 * @author kongweikun@163.com
 * @date 2023/6/28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RPCResponseBody implements Serializable {

    /**
     * 序列化后的内容
     */
    private Object object;
}
