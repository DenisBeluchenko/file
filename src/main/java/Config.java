import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class Config {
    private boolean load;
    private boolean save;
    private boolean log;
    private String loadFileName;
    private String saveFileName;
    private String logFileName;
    private String saveType;
    private String loadType;

    public boolean isLoad() {
        return load;
    }

    public boolean isSave() {
        return save;
    }

    public boolean isLog() {
        return log;
    }

    public String getLoadFileName() {
        return loadFileName;
    }

    public String getSaveFileName() {
        return saveFileName;
    }

    public String getLogFileName() {
        return logFileName;
    }

    public String getSaveType() {
        return saveType;
    }

    public String getLoadType() {
        return loadType;
    }

    public Config() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File("shop.xml"));
        this.loadFileName = par(doc, "load", "fileName");
        this.loadType = par(doc, "load", "format");
        if (par(doc, "load", "enabled").equals("true")) {
            this.load = true;
        } else {
            this.load = false;
        }
        this.saveType = par(doc, "save", "format");
        this.saveFileName = par(doc, "load", "fileName");
        if (par(doc, "save", "enabled").equals("true")) {
            this.save = true;
        } else {
            this.save = false;
        }
        this.logFileName = par(doc, "log", "fileName");
        if (par(doc, "log", "enabled").equals("true")) {
            this.log = true;
        } else {
            this.log = false;
        }
    }

    public String par(Document nod, String teg, String txt) {
        NodeList node = nod.getElementsByTagName(teg);
        for (int i = 0; i < node.getLength(); i++) {
            Node book = node.item(i);
            if (book.getNodeType() != Node.TEXT_NODE) {
                NodeList bookProps = book.getChildNodes();
                for (int j = 0; j < bookProps.getLength(); j++) {
                    Node bookProp = bookProps.item(j);
                    if (bookProp.getNodeType() != Node.TEXT_NODE) {
                        if (txt.equals(bookProp.getNodeName())) {
                            return bookProp.getChildNodes().item(0).getTextContent();
                        }
                    }
                }
            }
        }
        return "";
    }
}