package com.salimov.yurii.algoritm;

import com.salimov.yurii.train.ITrain;
import com.salimov.yurii.train.IWagon;

/**
 * The class describes a method for
 * calculating the length of a train.
 *
 * @author Yurii Salimov (yurii.alex.salimov@gmail.com)
 * @version 1.0
 * @see ITrain
 */
public final class CalculateLength implements ICalculateLength {

    /**
     * The train.
     *
     * @see ITrain
     */
    private final ITrain train;

    /**
     * The calculated train length.
     */
    private int length;

    /**
     * Direction of user travel in the train.
     * {@code true} if forward movement,
     * {@code false} otherwise.
     */
    private boolean direction;

    /**
     * Constructor.
     *
     * @param train a train.
     * @see ITrain
     */
    public CalculateLength(final ITrain train) {
        this.train = train;
    }

    /**
     * Returns a train.
     *
     * @return The train.
     * @see ITrain
     */
    @Override
    public ITrain getTrain() {
        return this.train;
    }

    /**
     * Returns a calculated train length.
     *
     * @return The train length.
     */
    @Override
    public int getLength() {
        if ((this.train != null) && (this.length == 0)) {
            calculate();
        }
        return this.length;
    }

    /**
     * Calculates the length of the train.
     */
    private void calculate() {
        IWagon startWagon = this.train.getWagon(0);
        startWagon.onLamp();
        while (true) {
            getInDirection();
            this.train.getCurrentWagon().offLamp();
            startWagon = moveToStartWagon();
            if (!startWagon.getLampState()) {
                break;
            }
            this.direction = !this.direction;
        }
    }

    /**
     * Calculates the length of the train in some direction.
     *
     * @return The length in some direction.
     */
    private int getInDirection() {
        this.length = 1;
        if (this.direction) {
            while (!this.train.getNextWagon().getLampState()) {
                this.length++;
            }
        } else {
            while (!this.train.getPreviousWagon().getLampState()) {
                this.length++;
            }
        }
        return this.length;
    }

    /**
     * Moves to start wagon.
     *
     * @return The start wagon.
     * @see IWagon
     */
    private IWagon moveToStartWagon() {
        IWagon wagon = null;
        if (this.length > 0) {
            if (this.direction) {
                for (int i = 0; i < this.length; i++) {
                    wagon = this.train.getPreviousWagon();
                }
            } else {
                for (int i = 0; i < this.length; i++) {
                    wagon = this.train.getNextWagon();
                }
            }
        } else {
            wagon = this.train.getCurrentWagon();
        }
        return wagon;
    }
}