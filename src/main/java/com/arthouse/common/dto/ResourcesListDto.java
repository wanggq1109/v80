package com.arthouse.common.dto;

import java.math.BigInteger;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 11-7-6
 * Time: 下午3:21
 * To change this template use File | Settings | File Templates.
 */
public class ResourcesListDto {
    private BigInteger id;
    private String resourceDesc;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getResourceDesc() {
        return resourceDesc;
    }

    public void setResourceDesc(String resourceDesc) {
        this.resourceDesc = resourceDesc;
    }
}
