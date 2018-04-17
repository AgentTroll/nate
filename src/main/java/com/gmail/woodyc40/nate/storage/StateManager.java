package com.gmail.woodyc40.nate.storage;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.gmail.woodyc40.nate.Util.UTF_8;
import static com.gmail.woodyc40.nate.Util.read;

public class StateManager {
    private JsonArray collection;

    public StateManager(Path path) throws IOException {
        JsonObject obj = read(path);
        if (obj == null) {
            this.collection = new JsonArray();
        } else {
            this.collection = obj.getAsJsonArray("collection");
        }
    }

    public JsonArray getCollection() {
        return this.collection;
    }

    public void setCollection(JsonArray collection) {
        this.collection = collection;
    }

    public void write(Path path) throws IOException {
        JsonObject obj = new JsonObject();
        obj.add("collection", this.collection);

        Files.write(path, obj.toString().getBytes(UTF_8));
    }

    public void reset() {
        this.collection = new JsonArray();
    }
}