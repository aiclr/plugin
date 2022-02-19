package org.bougainvilleas.java.pluggable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

@XmlRootElement(name = "plugins")
@XmlAccessorType(XmlAccessType.FIELD)
public class PluginRoot {
    @XmlElement(name = "plugin")
    private Set<Plugin> pluginSet;

    public Set<Plugin> getPluginSet() {
        return pluginSet;
    }

    public void setPluginSet(Set<Plugin> pluginSet) {
        this.pluginSet = pluginSet;
    }
}