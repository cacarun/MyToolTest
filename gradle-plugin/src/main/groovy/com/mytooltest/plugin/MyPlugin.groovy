package com.mytooltest.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class MyPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        println("apply cjw plugin")
        project.tasks.create("mytask", MyTask.class)
    }
}