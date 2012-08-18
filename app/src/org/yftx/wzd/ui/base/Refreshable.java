package org.yftx.wzd.ui.base;

import org.yftx.common.task.TaskAdapter;

/**
 * User: yftx
 * Mail: yftx.net@gmail.com
 * Date: 12-8-12
 */
public interface Refreshable {
    /**
     * 请求数据
     * @param listener 任务处理过程的回调函数
     */
    void doRetrieve(TaskAdapter listener);
}
