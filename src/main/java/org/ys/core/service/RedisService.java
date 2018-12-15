package org.ys.core.service;

public interface RedisService {
    /**
     * 系统初始化
     */
    public void initSystemCach() throws Exception;

    public void refreshSystemCach() throws Exception;
}
