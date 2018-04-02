package com.gmail.woodyc40.nate.storage;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.nio.file.Path;

import static com.gmail.woodyc40.nate.Util.read;

public class Config {
    private final int brainSize;

    public Config(Path path) throws IOException {
        JsonObject obj = read(path);
        if (obj == null) {
            this.brainSize = 300_000;
        } else {
            this.brainSize = obj.getAsJsonPrimitive("brain-size").getAsInt();
        }
    }

    public int getBrainSize() {
        return this.brainSize;
    }
}