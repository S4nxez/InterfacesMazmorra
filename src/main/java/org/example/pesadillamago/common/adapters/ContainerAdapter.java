package org.example.pesadillamago.common.adapters;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.example.pesadillamago.game.Domain;
import org.example.pesadillamago.game.object.Item;
import org.example.pesadillamago.game.objectContainer.Container;
import org.example.pesadillamago.game.objectContainer.RoomSet;


import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class ContainerAdapter extends TypeAdapter<Container> {

    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Item.class, new ItemAdapter())
            .create();

    @Override
    public void write(JsonWriter out, Container container) throws IOException {
        JsonObject jsonObject = new JsonObject();

        // Serializar tipo de contenedor
        jsonObject.addProperty("type", container.getClass().getSimpleName());

        // Serializar los atributos comunes
        jsonObject.addProperty("domain", container.getDomain().name());
        jsonObject.addProperty("value", container.getValue());
        jsonObject.add("items", gson.toJsonTree(container.getItems()));

        // Escribir el objeto JSON
        gson.toJson(jsonObject, out);
    }

    @Override
    public Container read(JsonReader in) throws IOException {
        // Leemos el objeto JSON completo
        JsonObject jsonObject = gson.fromJson(in, JsonObject.class);

        // Obtenemos el campo "type" para saber qué subclase instanciar
        String type = jsonObject.get("type").getAsString();
        jsonObject.remove("type"); // Eliminamos el campo "type" para evitar conflictos al deserializar

        // Deserializar el campo 'domain' y 'value'
        Domain domain = Domain.valueOf(jsonObject.get("domain").getAsString());
        int value = jsonObject.get("value").getAsInt();
        JsonElement itemsElement = jsonObject.get("items");

        // Deserializamos los 'items' utilizando el ItemAdapter
        List<Item> items = new ArrayList<>();
        if (itemsElement != null && itemsElement.isJsonArray()) {
            // Obtén el TypeAdapter para Item utilizando el ItemAdapter
            TypeAdapter<Item> itemAdapter = gson.getAdapter(Item.class);

            // Creamos un JsonReader para cada elemento de la lista
            for (JsonElement itemElement : itemsElement.getAsJsonArray()) {
                // Usamos el JsonReader para deserializar el item
                JsonReader itemReader = new JsonReader(new StringReader(itemElement.toString()));
                Item item = itemAdapter.read(itemReader);  // Leer el item usando el adaptador
                items.add(item);
            }
        }

        // Crear la instancia correcta según el "type"
        Container container;
        System.out.println(type);
        switch (type) {

            case "RoomSet":
                container = new RoomSet(value);  // Crear RoomSet con el valor 'value'
                break;
            default:
                throw new IOException("Unknown container type: " + type);
        }

        // Asignamos los valores deserializados a la instancia de Container
        container.setItems(items);
        return container;
    }
}
