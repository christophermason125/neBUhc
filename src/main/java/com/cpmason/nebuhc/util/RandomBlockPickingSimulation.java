package com.cpmason.nebuhc.util;

import org.bukkit.Material;

public class RandomBlockPickingSimulation {
    private static final int Y_RANGE = 128;
    /**
     * Simulates a routine that picks a certain number of blocks within the world border every tick up until the border
     * has finished shrinking, then calculates the density of blocks that have been picked within the final border.
     *
     * @param frequency the number of block picks done per tick.
     * @param borderFinal final corner size of border in blocks.
     * @param borderInitial initial corner size of border in blocks.
     * @param ticksUntilShrinkStart the number of ticks before the border starts to shrink.
     * @param ticksUntilShrinkEnd the number of ticks it takes for the border to finish shrinking
     *                            (once it has already begun to shrink)
     * @return the density of blocks picked in the final border once it is done shrinking.
     */
    public static double simulate(int frequency, int borderFinal, int borderInitial, int ticksUntilShrinkStart,
                                  int ticksUntilShrinkEnd) {
        double period = 1 / (double) frequency;
        double pickedBlocksInFinal = 0;
        for (double t = 0; t < ticksUntilShrinkStart + ticksUntilShrinkEnd; t += period) {
            pickedBlocksInFinal += (4 * Y_RANGE * Math.pow(borderFinal, 2) - pickedBlocksInFinal) /
                    (4 * Y_RANGE * Math.pow(borderInitial + t *
                            ((double) (borderFinal - borderInitial) / ticksUntilShrinkEnd) *
                            ((t > ticksUntilShrinkStart) ? 1 : 0), 2));
        }

        return pickedBlocksInFinal / (1024 * Math.pow(borderFinal, 2));
    }

    public static void main(String[] args) {
        System.out.println(simulate(200, 50, 1500, 0, 6000*20));
    }
}
