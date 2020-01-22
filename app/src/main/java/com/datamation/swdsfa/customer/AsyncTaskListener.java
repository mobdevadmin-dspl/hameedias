package com.datamation.swdsfa.customer;

import com.datamation.swdsfa.settings.TaskType;

/**
 * Created by Sathiyaraja on 7/3/2018.
 */

public interface AsyncTaskListener {
    void onTaskCompleted(TaskType taskType);
}
