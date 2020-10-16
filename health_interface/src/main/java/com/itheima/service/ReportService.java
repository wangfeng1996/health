package com.itheima.service;

import java.util.Map;

/**
 * @author wangfeng
 */
public interface ReportService {
    /**
     * 获取前端中所需要的数据，并且将数据存储到map集合中
     *
     * @return
     */
    public Map<String, Object> getBusinessReport();
}
