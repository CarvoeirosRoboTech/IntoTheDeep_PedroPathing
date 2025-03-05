package opmode.TeleOp;
//
import static hardware.Globals.DEPOSIT_PIVOT_SPECIMEN_BACK_SCORING_POS;
import static hardware.Globals.DEPOSIT_PIVOT_SPECIMEN_FRONT_SCORING_POS;
import static hardware.Globals.FRONT_HIGH_SPECIMEN_HEIGHT;
import static hardware.Globals.HIGH_BUCKET_HEIGHT;
import static hardware.Globals.LOW_BUCKET_HEIGHT;
import static hardware.Globals.opModeType;
import static hardware.Robot.deliveryGyroLeft;
import static hardware.Robot.deliveryGyroRight;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import controller.Controller;
import hardware.Globals.*;
import hardware.Robot;

@TeleOp(name = "TeleOp Full", group = "Iterative OpMode")
public class TeleOpFull extends OpMode {
    hardware.Robot Robot = new Robot(this);
    public Controller driver;
    public Controller operator;

    @Override
    public void init() {
        Robot.opMODE = OpModeType.TELEOP;

        driver = new Controller(gamepad1);
        operator = new Controller(gamepad2);


        telemetry.addData("OpModeGlobal:", opModeType);
        telemetry.addData("OpModeHardwarePassado:", Robot.opMODE);

        Robot.init();


    }

    @Override
    public void start() {
//        Robot.initHasMovement();
        driver = new Controller(gamepad1);
    }

    @Override
    public void loop() {
        operator = new Controller(gamepad2);

        if(operator.circle.wasJustPressed()) {
            telemetry.addData("CONTROLE", "OPERADOR PRECIONADO");
        }

        Robot.follower.setTeleOpMovementVectors(-gamepad1.left_stick_y, -gamepad1.left_stick_x, -gamepad1.right_stick_x, true);
        Robot.follower.update();

        if (driver.circle.wasJustPressed()) {
            Robot.setSliderTarget(FRONT_HIGH_SPECIMEN_HEIGHT);
            telemetry.addData("DRIVER", "PRECIONADO");
        }

        if (gamepad1.cross) {
            switch (Robot.scoring) {
                case HIGH_BUCKET: {
                    Robot.setSliderTarget(HIGH_BUCKET_HEIGHT);
                    break;
                }
                case LOW_BUCKET: {
                    Robot.setSliderTarget(LOW_BUCKET_HEIGHT);
                    break;
                }
                case HIGH_SPECIMEN: {
                    Robot.setSliderTarget(FRONT_HIGH_SPECIMEN_HEIGHT);
                    break;
                }
            }
        }

        if(gamepad1.dpad_up) {
            Robot.scoring = SCORING.HIGH_BUCKET;
        }

        if(gamepad1.dpad_down) {
            Robot.scoring = SCORING.LOW_BUCKET;
        }

        if(gamepad1.dpad_right) {
            Robot.scoring = SCORING.HIGH_SPECIMEN;
        }

        if (gamepad1.triangle) {
            Robot.setDeliveryClaw(Robot.isClawOpen);
        }

        if (gamepad1.square) {
            Robot.setClawOpen(true);
        }
        if (gamepad1.circle) {
            Robot.setClawOpen(false);
        }

        if (gamepad1.left_bumper) {
            deliveryGyroRight.setPosition(gamepad2.left_stick_x);
            deliveryGyroLeft.setPosition(gamepad2.left_stick_x);
//            Robot.minusShoulder();
//            Robot.setShoulderPos(DEPOSIT_PIVOT_SPECIMEN_FRONT_SCORING_POS);
        }

        if (gamepad1.right_bumper) {
            deliveryGyroRight.setPosition(gamepad2.left_stick_y);
            deliveryGyroLeft.setPosition(gamepad2.left_stick_y);
//            Robot.plusShoulder();
//            Robot.setShoulderPos(DEPOSIT_PIVOT_SPECIMEN_BACK_SCORING_POS);
        }

        deliveryGyroRight.setPosition(gamepad2.left_stick_x);
        deliveryGyroLeft.setPosition(gamepad2.left_stick_x);

        deliveryGyroRight.setPosition(gamepad2.left_stick_y);
        deliveryGyroLeft.setPosition(gamepad2.left_stick_y);
    }

    @Override
    public void stop() {

    }
}
