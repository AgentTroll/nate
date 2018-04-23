package com.gmail.woodyc40.nate;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.concurrent.ThreadLocalRandom;

public class Node {
    private final Nate nate;

    private int seed;

    public Node(Nate nate) {
        this.nate = nate;
        this.seed = ThreadLocalRandom.current().nextInt();
    }

    public Node(Nate nate, JsonElement element) {
        this.nate = nate;

        JsonObject obj = element.getAsJsonObject();
        this.seed = obj.getAsJsonPrimitive("seed").getAsInt();
    }

    public JsonElement serialize() {
        JsonObject element = new JsonObject();
        element.addProperty("seed", this.seed);

        return element;
    }

    public int compute(int lastResult, int data) {
       return lastResult + this.seed + this.mix(data);
    }

    public int computeWithEvolution(int lastResult, int data) {
        int mix = this.mix(data) + lastResult;
        this.seed = mix;

        return mix;
    }

    public void expect(int result, int[] data) {
        int mix = 0;

        int closestData = 0;
        int closestMixDiff = Integer.MAX_VALUE;
        for (int i = 0; i < this.nate.getConfig().getNodeCycleLimit(); i++) {
            int element = data[i % data.length];
            mix = this.compute(mix, element);

            int diff = mix - result;
            if (diff < closestMixDiff) {
                closestData = element;
                closestMixDiff = diff;
            }

            if (mix == result) {
                break;
            }
        }

        this.computeWithEvolution(result, closestData);
    }

    private int mix(int data) {
        data = (int) (System.currentTimeMillis() >>> data % 32);
        return data;
    }
}