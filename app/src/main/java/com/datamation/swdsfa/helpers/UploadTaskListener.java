package com.datamation.swdsfa.helpers;


import java.util.List;

public interface UploadTaskListener {
    void onTaskCompleted(List<String> list);
}