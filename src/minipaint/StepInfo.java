/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package minipaint;

import java.io.Serializable;

/**
 *
 * @author Yousuf
 */
public class StepInfo implements Serializable {

        private int stepType;
        private Coordinate stepCoordinate;

        public StepInfo(int inStepType, Coordinate inStepCoordinate) {
            stepType = inStepType;
            stepCoordinate = inStepCoordinate;
        }

        public int getStepType() {
            return stepType;
        }

        public Coordinate getStepCoordinate() {
            return stepCoordinate;
        }
    }
