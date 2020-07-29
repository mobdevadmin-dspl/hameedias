package com.datamation.hmdsfa.helpers;


import com.datamation.hmdsfa.api.TaskTypeUpload;

import java.util.List;

public interface UploadTaskListener {
    void onTaskCompleted(TaskTypeUpload taskType, List<String> list);
    void onTaskCompleted(List<String> list);
}