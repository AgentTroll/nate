package com.gmail.woodyc40.nate;

import com.gmail.woodyc40.nate.storage.Config;
import com.gmail.woodyc40.nate.storage.NodeCollection;
import com.gmail.woodyc40.nate.storage.StateManager;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import javax.inject.Inject;
import java.util.Iterator;

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
            this.collection.add(new Node(this, jsonElement));
        }

        int diff = this.collection.size() - this.config.getBrainSize();
        int counter = 0;
        if (diff > 0) {
            for (Iterator<Node> it = this.collection.iterator(); it.hasNext(); ) {
                if (counter != diff) {
                    counter++;
                    it.remove();
                } else {
                    break;
                }
            }
        } else {
            for (int i = 0; i < -diff; i++) {
                this.collection.add(new Node(this));
            }
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

    public void close() {
        JsonArray array = new JsonArray();
        for (Node node : this.collection) {
            array.add(node.serialize());
        }
        this.stateManager.setCollection(array);
        this.collection.clear();
    }
}