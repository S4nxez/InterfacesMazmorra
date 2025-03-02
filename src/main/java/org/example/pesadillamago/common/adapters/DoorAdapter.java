package org.example.pesadillamago.common.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.example.pesadillamago.game.Domain;
import org.example.pesadillamago.game.dungeon.Door;
import org.example.pesadillamago.game.dungeon.Site;
import org.example.pesadillamago.game.objectContainer.Container;


import java.io.IOException;

public class DoorAdapter extends TypeAdapter<Door> {

    @Override
    public void write(JsonWriter out, Door door) throws IOException {
        out.beginObject();
        // Se serializan los IDs de los sites con las claves "a" y "b"
        out.name("a").value(door.getA().getId());
        out.name("b").value(door.getB().getId());
        out.name("used").value(door.isUsed());
        out.endObject();
    }

    @Override
    public Door read(JsonReader in) throws IOException {
        in.beginObject();
        int siteAId = 0;
        int siteBId = 0;
        boolean used = false;

        // Se leen los campos del JSON
        while (in.hasNext()) {
            String name = in.nextName();
            if ("a".equals(name)) {
                siteAId = in.nextInt();
            } else if ("b".equals(name)) {
                siteBId = in.nextInt();
            } else if ("used".equals(name)) {
                used = in.nextBoolean();
            } else {
                in.skipValue();
            }
        }
        in.endObject();

        // Se crean las instancias de Site a partir de sus IDs.
        // Aquí usamos un método auxiliar que crea el Site con un contenedor concreto (Backpack)
        Site siteA = createSiteFromId(siteAId);
        Site siteB = createSiteFromId(siteBId);

        if (siteA == null || siteB == null) {
            throw new IOException("Invalid Site ID(s) provided in the JSON.");
        }

        // Se crea la puerta y se asigna el estado "used"
        Door door = new Door(siteA, siteB);
        door.setUsed(used);
        return door;
    }

    /**
     * Método auxiliar para crear un objeto Site a partir de su ID.
     * Aquí se usa una implementación de ejemplo: se crea un Site con un contenedor concreto (Backpack),
     * un dominio de ejemplo y una descripción por defecto. Ajusta estos valores según la lógica de tu aplicación.
     */
    private Site createSiteFromId(int siteId) {
        switch (siteId) {
            case -1:
                return new Site(-1, "Home", new Container(Domain.LIFE, 10) {});
            case 0:
                return new Site(0, "Common room", new Container(Domain.LIFE, 10) {});
            case 1:
                return new Site(1, "Exit room", new Container(Domain.LIFE, 10) {});
            default:
                return new Site(siteId, "Site " + siteId, new Container(Domain.LIFE, 10) {});
        }
    }
}
