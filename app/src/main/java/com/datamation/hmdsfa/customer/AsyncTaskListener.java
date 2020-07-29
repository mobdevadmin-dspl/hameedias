package com.datamation.hmdsfa.customer;

import com.datamation.hmdsfa.settings.TaskTypeDownload;

/**
 * Created by Sathiyaraja on 7/3/2018.
 */

public interface AsyncTaskListener {
    void onTaskCompleted(TaskTypeDownload taskType);
}
