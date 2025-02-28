package controller;

import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * A Controller
 */
public class Controller {
    private final Gamepad gamepad;
    public final Button leftBumper;
    public final Button rightBumper;
    public final Button dpadUp;
    public final Button dpadDown;
    public final Button dpadLeft;
    public final Button dpadRight;
    public final Button cross;
    public final Button circle;
    public final Button square;
    public final Button triangle;
    public final Button share;
    public final Button options;
    public final Button leftStickButton;
    public final Button rightStickButton;

    /**
     * Instantiates the Controller
     *
     * @param gamepad the gamepad to use
     */
    public Controller(Gamepad gamepad) {
        this.gamepad = gamepad;

        leftBumper = new Button();
        rightBumper = new Button();
        dpadUp = new Button();
        dpadDown = new Button();
        dpadLeft = new Button();
        dpadRight = new Button();
        square = new Button();
        circle = new Button();
        cross = new Button();
        triangle = new Button();
        share = new Button();
        options = new Button();
        leftStickButton = new Button();
        rightStickButton = new Button();
    }

    /**
     * Updates all Buttons, Axes, and Joysticks
     */
    public void update() {
        leftBumper.update(gamepad.left_bumper);
        rightBumper.update(gamepad.right_bumper);
        dpadUp.update(gamepad.dpad_up);
        dpadDown.update(gamepad.dpad_down);
        dpadLeft.update(gamepad.dpad_left);
        dpadRight.update(gamepad.dpad_right);
        cross.update(gamepad.cross);
        circle.update(gamepad.circle);
        square.update(gamepad.square);
        triangle.update(gamepad.triangle);
        share.update(gamepad.back);
        options.update(gamepad.start);
        leftStickButton.update(gamepad.left_stick_button);
        rightStickButton.update(gamepad.right_stick_button);
    }
}