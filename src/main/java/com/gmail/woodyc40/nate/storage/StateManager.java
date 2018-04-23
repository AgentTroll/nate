package com.gmail.woodyc40.nate.storage;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.gmail.woodyc40.nate.Util.UTF_8;
import static com.gmail.woodyc40.nate.Util.read;

public class StateManager {
    private JsonArray array;

    public StateManager(Path path) throws IOException {
        JsonObject obj = read(path);
        if (obj == null) {
            this.array = new JsonArray();
        } else {
            this.array = obj.getAsJsonArray("collection");
        }
    }

    public JsonArray getArray() {
        return this.array;
    }

    public void setArray(JsonArray array) {
        this.array = array;
    }

    public void write(Path path) throws IOException {
        JsonObject obj = new JsonObject();
        obj.add("collection", this.array);

        Files.write(path, obj.toString().getBytes(UTF_8));
    }

    public void reset() {
        this.array = new JsonArray();
    }
}