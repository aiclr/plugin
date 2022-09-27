package org.bougainvilleas.java.encrypt

import org.gradle.api.Plugin
import org.gradle.api.Project

class JClassEncryptPlugin implements Plugin<Project> {

    void apply(Project project) {

        def extension = project.extensions.create('classEncrypt',JClassEncryptPluginExtension)

        project.getTasks().register('encrypt', JClassEncryptTask.class, task -> {
            skip=extension.skip.get()
            packages = "${extension.packages.get()}"
            password = "${extension.password.get()}"
            task.group='build'
        })
    }
}
