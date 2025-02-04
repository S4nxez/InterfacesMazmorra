package org.example.pesadillamago.loaderManual;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.example.pesadillamago.game.dungeon.Dungeon;
import org.example.pesadillamago.game.dungeon.Room;
import org.w3c.dom.*;

class GameState {
    private String playerName;
    private int level;
    private int score;
    private List<Dungeon> dungeons;

    public List<Dungeon> getDungeons() { return dungeons; }
    public void setDungeons(List<Dungeon> dungeons) { this.dungeons = dungeons; }

    public GameState(String playerName, int level, int score) {
        this.playerName = playerName;
        this.level = level;
        this.score = score;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getLevel() {
        return level;
    }

    public int getScore() {
        return score;
    }

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

    NodeList dungeonNodes = root.getElementsByTagName("dungeon");
    List<Dungeon> dungeons = new ArrayList<>();

    for (int i = 0; i < dungeonNodes.getLength(); i++) {
        Element dungeonElement = (Element) dungeonNodes.item(i);
        Dungeon dungeon = new Dungeon();

        NodeList roomNodes = dungeonElement.getElementsByTagName("room");
        for (int j = 0; j < roomNodes.getLength(); j++) {
            Element roomElement = (Element) roomNodes.item(j);
            String roomText = roomElement.getTextContent().trim();
            Room room = Room.fromString(roomText);
            dungeon.addRoom(room);
        }

        dungeons.add(dungeon);
    }

    GameState gameState = new GameState(playerName, level, score);
    gameState.setDungeons(dungeons);
    return gameState;
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

        Element dungeonsElement = doc.createElement("dungeons");
        root.appendChild(dungeonsElement);

        for (Dungeon dungeon : dungeons) {
            Element dungeonElement = doc.createElement("dungeon");
            dungeonsElement.appendChild(dungeonElement);

            Iterator<Room> roomIterator = dungeon.iterator();
            while (roomIterator.hasNext()) {
                Room room = roomIterator.next();
                Element roomElement = doc.createElement("room");
                roomElement.appendChild(doc.createTextNode(room.toString())); // Asumiendo que Room tiene un método toString adecuado
                dungeonElement.appendChild(roomElement);
            }
        }

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(new DOMSource(doc), new StreamResult(new File(filename)));
    }
}
