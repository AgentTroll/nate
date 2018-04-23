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
        JsonArray array = this.stateManager.getArray();
        for (JsonElement jsonElement : array) {
            if (this.collection.size() == this.config.getBrainSize()) {
                break;
            }
            this.collection.add(new Node(this, jsonElement));
        }

        while (this.collection.size() < this.config.getBrainSize()) {
            this.collection.add(new Node(this));
        }
    }

    public int compute(int[] data) {
        int compute = 0;
        for (int datum : data) {
            for (Node node : this.collection) {
                compute = node.compute(compute, datum);
            }
        }

        return compute;
    }

    public int computeWithEvolution(int[] data) {
        int compute = 0;
        for (int datum : data) {
            for (Node node : this.collection) {
                compute = node.computeWithEvolution(compute, datum);
            }
        }

        return compute;
    }

    public void learn(int expect, int[] data) {
        for (Node node : this.collection) {
            node.expect(expect, data);
        }
    }

    public Config getConfig() {
        return this.config;
    }

    public void saveState() {
        JsonArray array = new JsonArray(this.config.getBrainSize());
        for (Node node : this.collection) {
            array.add(node.serialize());
        }
        this.stateManager.setArray(array);
    }

    public void close() {
        this.saveState();
        this.collection.clear();
    }
}