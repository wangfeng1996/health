package com.itheima.entity;

import com.itheima.pojo.CheckGroup;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wangfeng
 */
@Data
@AllArgsConstructor
public class CheckGroupAndCheckItemIds implements Serializable {
    private CheckGroup checkGroup;
    private Integer[] checkitemIds;
}
