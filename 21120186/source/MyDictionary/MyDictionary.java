/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MyDictionary;

import MyDictionaryGUI.MyDictionaryGUI;
import java.io.File;
import java.text.Collator;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author lehuutri
 */
public class MyDictionary {
    private static volatile MyDictionary INSTANCE = null;
    
    private final String VIE_ENG_PATH = "./data/Viet_Anh.xml";
    private final String ENG_VIE_PATH = "./data/Anh_Viet.xml";
    private final String VIE_FAVORITES = "./data/Viet_Favs.xml";
    private final String ENG_FAVORITES = "./data/Anh_Favs.xml";
    private final String HISTORY_PATH = "./data/history.xml";
    private final String RECORD_TAG = "record";
    private final String WORD_TAG = "word";
    private final String MEANING_TAG = "meaning";
    
    //set comparator to sort vietnamese
    private Locale vieL = new Locale("vi", "VN");
    public Collator vieCollator = Collator.getInstance(vieL);
    {vieCollator.setStrength(Collator.PRIMARY);}
    
    private HashMap<String, String> VieEngWords = new HashMap<>();
    private HashMap<String, String> EngVieWords = new HashMap<>();
    private TreeSet<String> favoritesVie = new TreeSet<>(vieCollator);
    private TreeSet<String> favoritesEng = new TreeSet<>();
    private List<SearchHistory> history = new ArrayList<>();
    
    private MyDictionary() {

        this.loadEngVieWords();
        this.loadVieEngWords();
        this.loadEngFavsWords();
        this.loadVieFavsWords();
        this.loadHistory();
    }
    
    public static synchronized MyDictionary getInstance() {
        if (MyDictionary.INSTANCE == null) {
            MyDictionary.INSTANCE = new MyDictionary();
        }
        
        return MyDictionary.INSTANCE;
    }
    
    public boolean addVietWord(String word, String meaning) {
        if (this.findVieWord(word) == null)
        {
            this.VieEngWords.put(word, meaning);
            
            return true;
        }
        
        return false;
    }
    
    public boolean deleteVietWord(String word) {
        if (this.findVieWord(word) != null)
        {
            this.VieEngWords.remove(word);
            
            return true;
        }
        
        return false;
    }
    
    public boolean addEngWord(String word, String meaning) {
        if (this.findEngWord(word) == null)
        {
            this.EngVieWords.put(word, meaning);
            
            return true;
        }
        
        return false;
    }
    
    public boolean deleteEngWord(String word) {
        if (this.findEngWord(word) != null)
        {
            this.EngVieWords.remove(word);
            
            return true;
        }
        
        return false;
    }
    
    public void displayEngVieWords() {
        this.EngVieWords.forEach((key, value) -> System.out.println(key + " :: " + value));
    }
    
    public void displayVieEngWords() {
        this.VieEngWords.forEach((key, value) -> System.out.println(key + " :: " + value));
    }
    
    public String[] findVieWordsStartWith(String word) {
        int size = VieEngWords.keySet().size();
        String[] keySet = VieEngWords.keySet().toArray(new String[size]);
        List<String> result = new ArrayList<>();
        
        for (String k: keySet)
        {
            if (k.startsWith(word))
                result.add(k);
        }
        
        return result.toArray(new String[size]);
    }
    
    public String findVieWord(String word) {
        String result = VieEngWords.get(word);
        
        return result;
    }
    
    public String findEngWord(String word) {
        String result = EngVieWords.get(word);
        
        return result;
    }
    
    public String[] findEngWordsStartWith(String word) {
        int size = EngVieWords.keySet().size();
        String[] keySet = EngVieWords.keySet().toArray(new String[size]);
        List<String> result = new ArrayList<>();
        
        for (String k: keySet)
        {
            if (k.startsWith(word))
                result.add(k);
        }
        
        return result.toArray(new String[size]);
    }
    
    // Load Viet - Eng Words into MyDictionary
    private void loadVieEngWords() {
        File inDataFile = new File(this.VIE_ENG_PATH);
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        
        try {
           DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
           Document doc = docBuilder.parse(inDataFile);
           
           doc.getDocumentElement().normalize();
           
           NodeList wordNodeList = doc.getElementsByTagName(this.RECORD_TAG);
           
           for (int i = 0; i < wordNodeList.getLength(); i++) {
               Node wordNode = wordNodeList.item(i);
               
               if (wordNode.getNodeType() == Node.ELEMENT_NODE) {
                   Element element = (Element) wordNode;
                   
                   String word = element.getElementsByTagName(this.WORD_TAG).item(0).getTextContent();
                   String meaning = element.getElementsByTagName(this.MEANING_TAG).item(0).getTextContent();
                   
                   this.VieEngWords.put(word, meaning);
               }
           }
           
        } catch (Exception e) {
            System.err.append(e.toString());
        }
    }
    
    public void exportEngVieWords() {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        
        try {            
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();
            // add elements to Document
            Element rootElement = doc.createElement("dictionary");
            // append root element to document
            doc.appendChild(rootElement);
           
            this.EngVieWords.forEach((key, value) -> {
                Element record = doc.createElement("record");
                Element word = doc.createElement(WORD_TAG);
                Element meaning = doc.createElement(MEANING_TAG);
                
                word.appendChild(doc.createTextNode(key));
                meaning.appendChild(doc.createTextNode(value));
                
                
                record.appendChild(word);
                record.appendChild(meaning);
                
                rootElement.appendChild(record);
            }); 
            
                        // for output to file, console
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            // for pretty print
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);

            // write to console or fil
            StreamResult file = new StreamResult(new File(ENG_VIE_PATH));

            // write data
            transformer.transform(source, file);
        } catch (ParserConfigurationException ex) {
            System.err.append(ex.toString());
        } catch (TransformerException ex) {
             System.err.append(ex.toString());
        }
    }
    
    // load Eng - Vie words into MyDictionary
    private void loadEngVieWords()
    {
        File inDataFile = new File(this.ENG_VIE_PATH);
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        
        try {
           DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
           Document doc = docBuilder.parse(inDataFile);
           
           doc.getDocumentElement().normalize();
           
           NodeList wordNodeList = doc.getElementsByTagName(this.RECORD_TAG);
           
           for (int i = 0; i < wordNodeList.getLength(); i++) {
               Node wordNode = wordNodeList.item(i);
               
               if (wordNode.getNodeType() == Node.ELEMENT_NODE) {
                   Element element = (Element) wordNode;
                   
                   String word = element.getElementsByTagName(this.WORD_TAG).item(0).getTextContent();
                   String meaning = element.getElementsByTagName(this.MEANING_TAG).item(0).getTextContent();
                   
                   this.EngVieWords.put(word, meaning);
               }
           }
           
        } catch (Exception e) {
            System.err.append(e.toString());
        }
    }
    
    public void exportVieEngWords() {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        
        try {            
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            Element rootElement = doc.createElement("dictionary");

            doc.appendChild(rootElement);
           
            this.VieEngWords.forEach((key, value) -> {
                Element record = doc.createElement(RECORD_TAG);
                Element word = doc.createElement(WORD_TAG);
                Element meaning = doc.createElement(MEANING_TAG);
                
                word.appendChild(doc.createTextNode(key));
                meaning.appendChild(doc.createTextNode(value));
                
                
                record.appendChild(word);
                record.appendChild(meaning);
                
                rootElement.appendChild(record);
            }); 
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);


            StreamResult file = new StreamResult(new File(VIE_ENG_PATH));

            transformer.transform(source, file);
        } catch (ParserConfigurationException ex) {
            System.err.append(ex.toString());
        } catch (TransformerException ex) {
             System.err.append(ex.toString());
        }
    }
    
    public void exportVieFavWords() {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        
        try {            
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            Element rootElement = doc.createElement("favorites");

            doc.appendChild(rootElement);
           
            this.favoritesVie.forEach((key) -> {
                Element record = doc.createElement(RECORD_TAG);
                Element word = doc.createElement(WORD_TAG);
                
                word.appendChild(doc.createTextNode(key));                
                
                record.appendChild(word);
                
                rootElement.appendChild(record);
            }); 
            

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);

            StreamResult file = new StreamResult(new File(VIE_FAVORITES));

            transformer.transform(source, file);
        } catch (ParserConfigurationException ex) {
            System.err.append(ex.toString());
        } catch (TransformerException ex) {
             System.err.append(ex.toString());
        }
    }
    
    public void exportHistory() {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        
        try {            
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            Element rootElement = doc.createElement("history");

            doc.appendChild(rootElement);
           
            this.history.forEach((key) -> {
                Element record = doc.createElement(RECORD_TAG);
                Element word = doc.createElement(WORD_TAG);
                Element date = doc.createElement("date");
                
                word.appendChild(doc.createTextNode(key.getWord()));
                date.appendChild(doc.createTextNode(key.getSearchDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
                
                record.appendChild(word);
                record.appendChild(date);
                
                rootElement.appendChild(record);
            }); 
            

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);

            StreamResult file = new StreamResult(new File(HISTORY_PATH));

            transformer.transform(source, file);
        } catch (ParserConfigurationException ex) {
            System.err.append(ex.toString());
        } catch (TransformerException ex) {
             System.err.append(ex.toString());
        }
    }
    
    public void exportEngFavWords() {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        
        try {            
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            Element rootElement = doc.createElement("favorites");

            doc.appendChild(rootElement);
           
            this.favoritesEng.forEach((key) -> {
                Element record = doc.createElement(RECORD_TAG);
                Element word = doc.createElement(WORD_TAG);
                
                word.appendChild(doc.createTextNode(key));                
                
                record.appendChild(word);
                
                rootElement.appendChild(record);
            }); 
            

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);


            StreamResult file = new StreamResult(new File( ENG_FAVORITES));


            transformer.transform(source, file);
        } catch (ParserConfigurationException ex) {
            System.err.append(ex.toString());
        } catch (TransformerException ex) {
             System.err.append(ex.toString());
        }
    }
    
    private void loadEngFavsWords()
    {
        File inDataFile = new File( this.ENG_FAVORITES);
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        
        try {
           DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
           Document doc = docBuilder.parse(inDataFile);
           
           doc.getDocumentElement().normalize();
           
           NodeList wordNodeList = doc.getElementsByTagName(this.RECORD_TAG);
           
           for (int i = 0; i < wordNodeList.getLength(); i++) {
               Node wordNode = wordNodeList.item(i);
               
               if (wordNode.getNodeType() == Node.ELEMENT_NODE) {
                   Element element = (Element) wordNode;
                   
                   String word = element.getElementsByTagName(this.WORD_TAG).item(0).getTextContent();
                   
                   this.favoritesEng.add(word);
               }
           }
           
        } catch (Exception e) {
            System.err.append(e.toString());
        }
    }
    
    public void addToHistory(SearchHistory sh) {
        this.history.add(sh);
    }
    
    public List<SearchHistory> getHistory() {
        return this.history;
    }
    
    public List<SearchHistory> getSearchHistory(LocalDate startDate, LocalDate endDate) {
        List<SearchHistory> result = new ArrayList<>();
        
        this.history.forEach(e -> {
            if (e.getSearchDate().isEqual(endDate) || e.getSearchDate().isEqual(startDate)) {
                result.add(e);
                
                return;
            }
            if (e.getSearchDate().isAfter(startDate ) && e.getSearchDate().isBefore(endDate)) {
                result.add(e);
                
                return;
            }
        });
            
        return result;
    }
    
    private void loadHistory() {
        File inDataFile = new File( this.HISTORY_PATH);
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        
        try {
           DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
           Document doc = docBuilder.parse(inDataFile);
           
           doc.getDocumentElement().normalize();
           
           NodeList wordNodeList = doc.getElementsByTagName(this.RECORD_TAG);
           
           for (int i = 0; i < wordNodeList.getLength(); i++) {
               Node wordNode = wordNodeList.item(i);
               
               if (wordNode.getNodeType() == Node.ELEMENT_NODE) {
                   Element element = (Element) wordNode;
                   
                   String word = element.getElementsByTagName(this.WORD_TAG).item(0).getTextContent();
                   String date = element.getElementsByTagName("date").item(0).getTextContent();
                   
                   this.history.add(new SearchHistory(word, date));
               }
           }
           
        } catch (Exception e) {
            System.err.append(e.toString());
        }
    }
    
    private void loadVieFavsWords()
    {
        File inDataFile = new File( this.VIE_FAVORITES);
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        
        try {
           DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
           Document doc = docBuilder.parse(inDataFile);
           
           doc.getDocumentElement().normalize();
           
           NodeList wordNodeList = doc.getElementsByTagName(this.RECORD_TAG);
           
           for (int i = 0; i < wordNodeList.getLength(); i++) {
               Node wordNode = wordNodeList.item(i);
               
               if (wordNode.getNodeType() == Node.ELEMENT_NODE) {
                   Element element = (Element) wordNode;
                   
                   String word = element.getElementsByTagName(this.WORD_TAG).item(0).getTextContent();
                   
                   this.favoritesVie.add(word);
               }
           }
           
        } catch (Exception e) {
            System.err.append(e.toString());
        }
    }
    
    public boolean addFavEng(String word) {
        if (this.favoritesEng.contains(word)) {
            return false;
        }
        
        this.favoritesEng.add(word);
        
        return true;
    }
    
    public boolean addFavVie(String word) {
        if (this.favoritesVie.contains(word)) {
            return false;
        }
        
        this.favoritesVie.add(word);
        
        return true;
    }
    
    public boolean deleteFavEng(String word) {
        if (this.favoritesEng.contains(word)) {
            this.favoritesEng.remove(word);
            
            return true;
        }
        
        return false;
    }
    
    public boolean deleteFavVie(String word) {
        if (this.favoritesVie.contains(word)) {
            this.favoritesVie.remove(word);
            
            return true;
        }
        
        return false;
    }
    
    public TreeSet<String> getEngFavs() {
        return this.favoritesEng;
    }
    
    public TreeSet<String> getVieFavs() {
        return this.favoritesVie;
    }
}
