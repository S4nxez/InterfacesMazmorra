package org.example.pesadillamago.loaderManual;

import java.io.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;

class GameState {
    private String playerName;
    private int level;
    private int score;

    public GameState(String playerName, int level, int score) {
        this.playerName = playerName;
        this.level = level;
        this.score = score;
    }

    public String getPlayerName() { return playerName; }
    public int getLevel() { return level; }
    public int getScore() { return score; }

    public static GameState loadFromTextFile(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String playerName = reader.readLine();
            int level = Integer.parseInt(reader.readLine());
            int score = Integer.parseInt(reader.readLine());
            return new GameState(playerName, level, score);
        }
    }

    public void saveToTextFile(String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(playerName + "\n");
            writer.write(level + "\n");
            writer.write(score + "\n");
        }
    }

    public static GameState loadFromXML(String filename) throws Exception {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = builder.parse(new File(filename));
        Element root = doc.getDocumentElement();

        String playerName = root.getElementsByTagName("playerName").item(0).getTextContent();
        int level = Integer.parseInt(root.getElementsByTagName("level").item(0).getTextContent());
        int score = Integer.parseInt(root.getElementsByTagName("score").item(0).getTextContent());
        
        return new GameState(playerName, level, score);
    }

    public void saveToXML(String filename) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();
        
        Element root = doc.createElement("GameState");
        doc.appendChild(root);
        
        Element nameElement = doc.createElement("playerName");
        nameElement.appendChild(doc.createTextNode(playerName));
        root.appendChild(nameElement);
        
        Element levelElement = doc.createElement("level");
        levelElement.appendChild(doc.createTextNode(String.valueOf(level)));
        root.appendChild(levelElement);
        
        Element scoreElement = doc.createElement("score");
        scoreElement.appendChild(doc.createTextNode(String.valueOf(score)));
        root.appendChild(scoreElement);
        
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(new DOMSource(doc), new StreamResult(new File(filename)));
    }
}
