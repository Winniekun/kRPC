package com.wkk.rpc.core.codec;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 类描述: RPC 请求 body
 *
 * @author kongweikun@163.com
 * @date 2023/6/28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RPCRequestBody implements Serializable {

    /**
     * 接口名
     */
    private String interfaceName;

    /**
     * 方法名
     */
    private String methodName;

    /**
     * 参数
     */
    private Object[] args;

    /**
     * 参数类型
     */
    private Class<?>[] argTypes;
}
