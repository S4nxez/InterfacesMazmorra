package org.example.pesadillamago.common.adapters;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.example.pesadillamago.game.object.Item;
import org.example.pesadillamago.game.object.Necklace;
import org.example.pesadillamago.game.object.SingaCrystal;
import org.example.pesadillamago.game.object.Weapon;
import org.example.pesadillamago.game.objectContainer.Chest;
import org.example.pesadillamago.game.objectContainer.JewelryBag;
import org.example.pesadillamago.game.objectContainer.RoomSet;


import java.io.IOException;

public class ItemAdapter extends TypeAdapter<Item> {

    private final Gson gson = new Gson();

    @Override
    public void write(JsonWriter out, Item item) throws IOException {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("type", item.getClass().getSimpleName());

        jsonObject.addProperty("domain", item.getDomain().name());
        jsonObject.add("value", gson.toJsonTree(item.getValue()));
        jsonObject.addProperty("viewed", item.isViewed());

        gson.toJson(jsonObject, out);
    }

    @Override
    public Item read(JsonReader in) throws IOException {
        JsonObject jsonObject = gson.fromJson(in, JsonObject.class);

        JsonElement typeElement = jsonObject.get("type");
        String type = typeElement != null ? typeElement.getAsString() : null;

        if (type != null) {
            System.out.println(type);
            switch (type) {
                case "Weapon":
                    return gson.fromJson(jsonObject, Weapon.class);
                case "Necklace":
                    return gson.fromJson(jsonObject, Necklace.class);
                case "SingaCrystal":
                    int value = jsonObject.get("value").getAsInt();
                    return SingaCrystal.createCrystal(value);
                case "RoomSet":
                    return gson.fromJson(jsonObject, RoomSet.class);
                case "Chest":
                    return gson.fromJson(jsonObject, Chest.class);
                case "JewelryBag":
                    return gson.fromJson(jsonObject, JewelryBag.class);
                default:
                    throw new IOException("Unknown item type: " + type);
            }
        }

        return null;
    }
}
