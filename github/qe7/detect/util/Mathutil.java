package github.qe7.detect.util;

import net.minecraft.util.MathHelper;

import java.util.Random;

public class Mathutil {

    public int getRandomIntBetween(int min, int max) {
        Random random = new Random();
        return MathHelper.getRandomIntegerInRange(random, min, max);
    }
    public double getRandomDoubleBetween(double min, double max) {
        Random random = new Random();
        return MathHelper.getRandomDoubleInRange(random, min, max);
    }
    public float getRandomFloatBetween(float min, float max) {
        Random random = new Random();
        return (float) MathHelper.getRandomDoubleInRange(random, min, max);
    }


}
