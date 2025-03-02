package org.example.pesadillamago.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.example.pesadillamago.common.adapters.ContainerAdapter;
import org.example.pesadillamago.common.adapters.DoorAdapter;
import org.example.pesadillamago.common.adapters.ItemAdapter;
import org.example.pesadillamago.common.adapters.SiteAdapter;
import org.example.pesadillamago.game.demiurge.Demiurge;
import org.example.pesadillamago.game.dungeon.Door;
import org.example.pesadillamago.game.dungeon.Site;
import org.example.pesadillamago.game.object.Item;
import org.example.pesadillamago.game.objectContainer.Container;


import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;

public class FilesDao {

    private final Gson gson;

    public FilesDao() {
        gson = new GsonBuilder()
                .registerTypeAdapter(Door.class, new DoorAdapter())
                .registerTypeAdapter(Site.class, new SiteAdapter())
                .registerTypeAdapter(Item.class,new ItemAdapter())
                .registerTypeAdapter(Container.class,new ContainerAdapter())
                .create();
    }

    public boolean saveDemiurge(Demiurge demiurge) {
        try (FileWriter fileWriter = new FileWriter("data/demiurge.json")) {
            gson.toJson(demiurge, fileWriter);
            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public Demiurge loadDemiurge() {
        Type demiurge = new TypeToken<Demiurge>() {
        }.getType();
        try (FileReader fileReader = new FileReader("data/demiurge.json")) {
            return gson.fromJson(fileReader, demiurge);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
