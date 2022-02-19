package org.bougainvilleas.java.pluggable;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import static javax.xml.stream.XMLStreamConstants.*;

/**
 * 解析 xml插件配置文件
 * @author renqiankun
 * 2022-02-18 09:37:10 星期五
 */
public class XMLParser
{
    public static Set<Plugin> getPlugins()
    {
        Set<Plugin> plugins = new HashSet<>();
        try
        {
            final URL resource = XMLParser.class.getClassLoader().getResource("plugins.xml");
            assert resource != null;
            final XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
            final XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(new FileReader(resource.getFile()));

            boolean bName = false;
            boolean bClassName = false;
            boolean bJar = false;
            Plugin plugin = null;
            while (xmlEventReader.hasNext())
            {
                final XMLEvent xmlEvent = xmlEventReader.nextEvent();
                switch (xmlEvent.getEventType())
                {
                    case START_ELEMENT:
                        final StartElement startElement = xmlEvent.asStartElement();
                        final String name = startElement.getName().getLocalPart();
                        if (name.equalsIgnoreCase("plugin"))
                        {
                            System.err.println("Start Element");
                            plugin = new Plugin();
                        }
                        else if (name.equalsIgnoreCase("name"))
                        {
                            bName = true;
                        }
                        else if (name.equalsIgnoreCase("className"))
                        {
                            bClassName = true;
                        }
                        else if (name.equalsIgnoreCase("jar"))
                        {
                            bJar = true;
                        }
                        break;
                    case CHARACTERS:
                        final Characters characters = xmlEvent.asCharacters();
                        final String data = characters.getData();
                        if (bName && plugin != null)
                        {
                            plugin.setName(data);
                            bName = false;
                        }
                        else if (bClassName && plugin != null)
                        {
                            plugin.setClassName(data);
                            bClassName = false;
                        }
                        else if (bJar && plugin != null)
                        {
                            plugin.setJar(data);
                            bJar = false;
                        }
                        break;
                    case END_ELEMENT:
                        final EndElement endElement = xmlEvent.asEndElement();
                        if (endElement.getName().getLocalPart().equalsIgnoreCase("plugin"))
                        {
                            System.err.println("End Element");
                            plugins.add(plugin);
                        }
                        break;
                }
            }

        }
        catch (XMLStreamException | FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return plugins;
    }
}
