package org.bougainvilleas.java.encrypt

import org.gradle.api.provider.Property

interface JClassEncryptPluginExtension {
    Property<String> getPackages()
    Property<String> getPassword()
}