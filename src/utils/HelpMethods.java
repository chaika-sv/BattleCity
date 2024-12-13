package utils;

import levels.LevelBlock;
import levels.LevelBlockType;
import main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.LinkedList;

import static utils.Constants.DEBUG_MODE;
import static utils.LoadSave.NUMBER_IMAGES;

public class HelpMethods {


    /**
     * Draw int number as sprite images
     * @param g Graphics
     * @param number the number to draw
     * @param x top left X to start drawing
     * @param y top left Y to start drawing
     */
    public static void DrawNumber(Graphics g, int number, int x, int y) {

        int digitSize;

        if (number == 0) {
            g.drawImage(NUMBER_IMAGES[0], x, y, 32, 32, null);
        }
        else {

            // Push digits to stack in reverse order
            LinkedList<Integer> stack = new LinkedList<Integer>();
            while (number > 0) {
                stack.push( number % 10 );
                number = number / 10;
            }

            if (stack.size() < 4)
                digitSize = 32;
            else if (stack.size() == 4)
                digitSize = 26;
            else
                digitSize = 18;


            // Pop digits from stack to draw them
            int i = 0;
            while (!stack.isEmpty()) {
                g.drawImage(NUMBER_IMAGES[stack.pop() % 10], x + i * digitSize, y, digitSize, digitSize, null);
                i++;
            }

        }

    }
}
