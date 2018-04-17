package com.gmail.woodyc40.nate.storage;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.gmail.woodyc40.nate.Util.UTF_8;
import static com.gmail.woodyc40.nate.Util.read;

public class Config {
    private final int brainSize;
    private final int nodeCycleLimit;
    private final boolean continuous;

    public Config(Path path) throws IOException {
        JsonObject obj = read(path);
        if (obj == null) {
            this.brainSize = 300_000;
            this.nodeCycleLimit = 60;
            this.continuous = false;
            this.write(path);
        } else {
            this.brainSize = obj.getAsJsonPrimitive("brain-size").getAsInt();
            this.nodeCycleLimit = obj.getAsJsonPrimitive("node-cycle-limit").getAsInt();
            this.continuous = obj.getAsJsonPrimitive("continuous").getAsBoolean();
        }
    }

    public int getBrainSize() {
        return this.brainSize;
    }

    public int getNodeCycleLimit() {
        return this.nodeCycleLimit;
    }

    public boolean isContinuous() {
        return this.continuous;
    }

    public void write(Path path) throws IOException {
        JsonObject obj = new JsonObject();
        obj.addProperty("brain-size", this.brainSize);
        obj.addProperty("node-cycle-limit", this.nodeCycleLimit);
        obj.addProperty("continuous", this.continuous);

        Files.write(path, obj.toString().getBytes(UTF_8));
    }
}