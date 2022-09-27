package org.bougainvilleas.java.encrypt

import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property

interface JClassEncryptPluginExtension {
    ListProperty<String> getSkip()
    Property<String> getPackages()
    Property<String> getPassword()
}