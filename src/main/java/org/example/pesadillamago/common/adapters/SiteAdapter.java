package org.example.pesadillamago.common.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.example.pesadillamago.game.dungeon.Door;
import org.example.pesadillamago.game.dungeon.Site;


import java.io.IOException;
import java.util.ArrayList;

public class SiteAdapter extends TypeAdapter<Site> {

    @Override
    public void write(JsonWriter out, Site site) throws IOException {
        out.beginObject();
        out.name("id").value(site.getId());
        out.name("description").value(site.getDescription());
        out.name("visited").value(site.isVisited());
        out.name("exit").value(site.isExit());
        out.name("doors");

        out.beginArray();
        for (Door door : site.getDoors()) {
            out.beginObject();
            out.name("doorId").value(door.toString());
            out.endObject();
        }
        out.endArray();

        out.endObject();
    }

    @Override
    public Site read(JsonReader in) throws IOException {
        in.beginObject();
        int id = -1;
        String description = null;
        boolean visited = false;
        boolean exit = false;
        ArrayList<Door> doors = new ArrayList<>();

        while (in.hasNext()) {
            String name = in.nextName();
            switch (name) {
                case "id":
                    id = in.nextInt();
                    break;
                case "description":
                    description = in.nextString();
                    break;
                case "visited":
                    visited = in.nextBoolean();
                    break;
                case "exit":
                    exit = in.nextBoolean();
                    break;
                case "doors":
                    in.beginArray();
                    while (in.hasNext()) {
                        in.beginObject();
                        while (in.hasNext()) {
                            String doorProperty = in.nextName();
                            if (doorProperty.equals("doorId")) {
                                String doorId = in.nextString();
                                Door door = findDoorById(doorId);
                                doors.add(door);
                            }
                        }
                        in.endObject();
                    }
                    in.endArray();
                    break;
            }
        }
        in.endObject();
        Site site = new Site(id, description, null);
        site.setVisited(visited);
        site.setExit(exit);
        site.setDoors(doors);
        return site;
    }

    private Door findDoorById(String doorId) {
        return new Door(new Site(0, "default", null), new Site(1, "default", null));
    }
}
