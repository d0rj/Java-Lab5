package com.d0rj;

import javax.swing.*;


/**
 * Набор кадров для воспроизведения циклической анимации
 */
public class AnimationFrames {

    private final ImageIcon[] frames;
    private int currentFrame = 0;


    public AnimationFrames(ImageIcon[] frames) {
        this.frames = frames;
    }


    public void reset() {
        currentFrame = 0;
    }


    public ImageIcon getNextFrame() {
        ImageIcon result = frames[currentFrame];

        if (++currentFrame == frames.length)
            reset();

        return result;
    }
}
