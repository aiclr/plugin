package org.bougainvilleas.java.pluggable;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * 解析 xml插件配置文件
 *
 * @author renqiankun
 * 2022-02-18 09:37:10 星期五
 */
public class XMLParser {
    public static Set<Plugin> getPlugins() {
        Set<Plugin> plugins = new HashSet<>();
        try {
            final URL resource = XMLParser.class.getClassLoader().getResource("plugins.xml");
            assert resource != null;
            plugins.addAll(((PluginRoot) JAXBContext.newInstance(PluginRoot.class).createUnmarshaller().unmarshal(new File(resource.getFile()))).getPluginSet());
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return plugins;
    }
}
