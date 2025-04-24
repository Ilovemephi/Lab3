    
package mephi.b22901.ae.lab3;

import javax.xml.stream.*;
import javax.xml.stream.events.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class XmlHandler implements Handler {
    private Handler nextHandler;
    private MonsterStorage monsterStorage = new MonsterStorage();

    @Override
    public void setNext(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public List<Monster> importData(String filePath) {
        
        List<Monster> monsters = new ArrayList<>();

        if (!filePath.toLowerCase().endsWith(".xml")) {
            if (nextHandler != null) {
                return nextHandler.importData(filePath);
            } else {
                throw new IllegalArgumentException("Формат файла не поддерживается");
            }
        }

        XMLEventReader reader = null;
        try {

            XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
            reader = xmlInputFactory.createXMLEventReader(new FileInputStream(filePath));

            Monster monster = null;

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
                                monster.setHeight(reader.getElementText());
                                break;
                            case "ВесКг":
                                monster.setWeight(reader.getElementText());
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

                if (xmlEvent.isEndElement()) {
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
        } finally {

            if (reader != null) {
                try {
                    reader.close();
                } catch (XMLStreamException e) {
                    System.err.println("Ошибка при закрытии XMLEventReader: " + e.getMessage());
                }
            }
        }

        if (monsters.isEmpty()) {
            System.out.println("Файл не содержит данных о монстрах.");
        } else {
            monsterStorage.addMonsters(monsters);
            
        }
        return monsters;
    }

    @Override
    public void exportData(String filePath, List<Monster> monsters) {
        
        if (!filePath.toLowerCase().endsWith(".xml")) {
            if (nextHandler != null) {
                nextHandler.exportData(filePath, monsters); 
                return;
            } else {
                throw new IllegalArgumentException("Формат файла не поддерживается: " + filePath);
            }
        }
        
        XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
            XMLStreamWriter writer = xmlOutputFactory.createXMLStreamWriter(fileOutputStream, "UTF-8");

            writer.writeStartDocument("UTF-8", "1.0");
            writer.writeCharacters("\n"); 
            
            writer.writeStartElement("Монстры");
            writer.writeCharacters("\n");

            for (Monster monster : monsters) {
                writer.writeStartElement("Монстр");
                writer.writeCharacters("\n");

                writeXmlElement(writer, "Имя", monster.getName());
                writeXmlElement(writer, "Описание", monster.getInfo());
                writeXmlElement(writer, "Опасность", String.valueOf(monster.getDanger()));
                writeXmlElement(writer, "Обитание", monster.getResidence());
                writeXmlElement(writer, "ПервоеУпоминание", monster.getFirstMention());
                writeXmlElement(writer, "УязвимостькМагии", monster.getVulnerability());
                writeXmlElement(writer, "РостМ", monster.getHeight());
                writeXmlElement(writer, "ВесКг", monster.getWeight());
                writeXmlElement(writer, "Иммунитеты", monster.getImmunity());
                writeXmlElement(writer, "ВремяАктивности", monster.getActivityTime());
                writeXmlElement(writer, "РецептЯда", monster.getRecipe());
                writeXmlElement(writer, "ВремяПриготовленияМин", String.valueOf(monster.getTime()));
                writeXmlElement(writer, "Эффективность", monster.getEfficiency());

                writer.writeEndElement(); 
                writer.writeCharacters("\n");
            }

            writer.writeEndElement(); 
            writer.writeEndDocument();
            System.out.println("Данные успешно экспортированы в файл: " + filePath);

        } catch (Exception e) {
            System.err.println("Ошибка при экспорте данных в XML: " + e.getMessage());
        }
    }

    private void writeXmlElement(XMLStreamWriter writer, String tagName, String value) throws XMLStreamException {
        writer.writeStartElement(tagName);
        writer.writeCharacters(value != null ? value : "");
        writer.writeEndElement();
        writer.writeCharacters("\n");
    }
    
}