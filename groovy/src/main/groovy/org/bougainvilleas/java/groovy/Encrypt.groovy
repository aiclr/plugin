package org.bougainvilleas.java.groovy

import org.gradle.api.Plugin
import org.gradle.api.Project

class Encrypt implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.getTasks().create("encrypt",EncryptTask.class,(task)->
        {
            task.key=(byte)1
            task.group='build'
        })
    }
}
