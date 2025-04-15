
package mephi.b22901.ae.lab3;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class XmlHandler implements Handler {
    private Handler nextHandler;
    @Override
    public void setNext(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void handle(String filePath) {
        if (filePath.endsWith(".xml")){
            try {
                parseXmlFile(filePath);
            } catch (XMLStreamException ex) {
                System.err.println("Ошибка при парсинге XML-файла: " + ex.getMessage());
            }
        }
        else if (nextHandler != null){
            nextHandler.handle(filePath);
        }
        else {
            System.err.println("Файл не может быть обработан: " + filePath);
        }     
            
    }

    private void parseXmlFile(String filePath) throws XMLStreamException {
        List<Monster> monsters = new ArrayList<>();
        Monster monster = null;
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        try {
            XMLEventReader reader = xmlInputFactory.createXMLEventReader(new FileInputStream(filePath));
            
            while (reader.hasNext()) {
                XMLEvent xmlEvent = reader.nextEvent();
                if (xmlEvent.isStartElement()) {
                    StartElement startElement = xmlEvent.asStartElement();
                    String tagName = startElement.getName().getLocalPart();
                    
                    if (tagName.equals("Монстр")) {
                        monster = new Monster();
                        monster.setInfoType("XML");
                    } else if (monster != null) {
                        switch (tagName) {
                            case "Имя":
                                monster.setName(reader.getElementText());
                                break;
                            case "Описание":
                                monster.setInfo(reader.getElementText());
                                break;
                            case "Опасность":
                                monster.setDanger(Integer.parseInt(reader.getElementText()));
                                break;
                            case "Обитание":
                            monster.setResidence(reader.getElementText());
                            break;
                            case "ПервоеУпоминание":
                                monster.setFirstMention(reader.getElementText());
                                break;
                            case "УязвимостькМагии":
                                monster.setVulnerability(reader.getElementText());
                                break;
                            case "РостМ":
                                monster.setHeight(Integer.parseInt(reader.getElementText()));
                                break;
                            case "ВесКг":
                                monster.setWeight(Integer.parseInt(reader.getElementText()));
                                break;
                            case "Иммунитеты":
                                monster.setImmunity(reader.getElementText());
                                break;
                            case "ВремяАктивности":
                                monster.setActivityTime(reader.getElementText());
                                break;
                            case "РецептЯда":
                                monster.setRecipe(reader.getElementText());
                                break;
                            case "ВремяПриготовленияМин":
                                monster.setTime(Integer.parseInt(reader.getElementText()));
                                break;
                            case "Эффективность":
                                monster.setEfficiency(reader.getElementText());
                                break;
                        }
                    }
                }
                
                
                if (xmlEvent.isEndElement()){
                    EndElement endElement = xmlEvent.asEndElement();
                    if (endElement.getName().getLocalPart().equals("Монстр") && monster != null) {
                        monsters.add(monster);
                        monster = null; 
                    }
                }
                
            }
        } catch (FileNotFoundException e) {
            System.err.println("Файл не найден: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Ошибка при преобразовании числа: " + e.getMessage());
        } catch (XMLStreamException e) {
            System.err.println("Ошибка при чтении XML: " + e.getMessage());
        }
    }
    
}
