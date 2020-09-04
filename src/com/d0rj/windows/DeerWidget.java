package com.d0rj.windows;

import com.d0rj.AnimationFrames;
import com.d0rj.DeerState;

import javax.swing.*;
import java.awt.*;
import java.net.URL;


public class DeerWidget extends JLabel {

    private final int DEFAULT_SIZE;
    private DeerState state = DeerState.Stand;
    private AnimationFrames walkAnimation;
    private AnimationFrames runAnimation;


    private URL loadResource(String path) {
        return this.getClass().getResource("/resources/" + path);
    }


    private ImageIcon rescaleIcon(ImageIcon original) {
        return new ImageIcon(
                original.getImage().getScaledInstance(DEFAULT_SIZE, DEFAULT_SIZE, Image.SCALE_FAST)
        );
    }


    private void initAnimations() {
        walkAnimation = new AnimationFrames(
                new ImageIcon[]{
                        rescaleIcon(new ImageIcon(loadResource("images/deer_walk_1.png"))),
                        rescaleIcon(new ImageIcon(loadResource("images/deer_walk_2.png"))),
                        rescaleIcon(new ImageIcon(loadResource("images/deer_walk_3.png"))),
                        rescaleIcon(new ImageIcon(loadResource("images/deer_walk_4.png"))),
                        rescaleIcon(new ImageIcon(loadResource("images/deer_walk_5.png"))),
                        rescaleIcon(new ImageIcon(loadResource("images/deer_walk_6.png"))),
                        rescaleIcon(new ImageIcon(loadResource("images/deer_walk_7.png"))),
                        rescaleIcon(new ImageIcon(loadResource("images/deer_walk_8.png"))),
                        rescaleIcon(new ImageIcon(loadResource("images/deer_walk_9.png"))),
                        rescaleIcon(new ImageIcon(loadResource("images/deer_walk_10.png"))),
                        rescaleIcon(new ImageIcon(loadResource("images/deer_walk_11.png"))),
                        rescaleIcon(new ImageIcon(loadResource("images/deer_walk_12.png"))),
                }
        );
        runAnimation = new AnimationFrames(
                new ImageIcon[]{
                        rescaleIcon(new ImageIcon(loadResource("images/deer_run_1.png"))),
                        rescaleIcon(new ImageIcon(loadResource("images/deer_run_2.png"))),
                        rescaleIcon(new ImageIcon(loadResource("images/deer_run_3.png"))),
                        rescaleIcon(new ImageIcon(loadResource("images/deer_run_4.png"))),
                        rescaleIcon(new ImageIcon(loadResource("images/deer_run_5.png"))),
                        rescaleIcon(new ImageIcon(loadResource("images/deer_run_6.png"))),
                        rescaleIcon(new ImageIcon(loadResource("images/deer_run_7.png"))),
                        rescaleIcon(new ImageIcon(loadResource("images/deer_run_8.png"))),
                        rescaleIcon(new ImageIcon(loadResource("images/deer_run_9.png"))),
                        rescaleIcon(new ImageIcon(loadResource("images/deer_run_10.png"))),
                        rescaleIcon(new ImageIcon(loadResource("images/deer_run_1.png"))),
                }
        );
    }


    public DeerWidget(int size) {
        DEFAULT_SIZE = size;

        initAnimations();

        setIcon(walkAnimation.getNextFrame());
    }


    public DeerState getState() {
        return state;
    }


    public void setState(DeerState state) {
        this.state = state;
    }


    public void nextFrame() {
        setIcon(switch (state) {
            case Walk -> walkAnimation.getNextFrame();
            case Run -> runAnimation.getNextFrame();
            case Stand -> getIcon();
        });
    }
}
