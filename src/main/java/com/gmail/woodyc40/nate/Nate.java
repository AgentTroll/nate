package com.gmail.woodyc40.nate;

import com.gmail.woodyc40.nate.storage.Config;
import com.gmail.woodyc40.nate.storage.NodeCollection;
import com.gmail.woodyc40.nate.storage.StateManager;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import javax.inject.Inject;

public class Nate {
    private final Config config;
    private final StateManager stateManager;

    private NodeCollection collection;

    @Inject
    public Nate(Config config, StateManager stateManager) {
        this.config = config;
        this.stateManager = stateManager;
    }

    public void init() {
        this.collection = new NodeCollection(this.config.getBrainSize());
        JsonArray array = this.stateManager.getCollection();
        for (JsonElement jsonElement : array) {
            this.collection.add(new Node(jsonElement));
        }
    }

    public void compute(int[] data) {
    }

    public void close() {
    }
}