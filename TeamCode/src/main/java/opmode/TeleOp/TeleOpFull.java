package opmode.TeleOp;
//
import static hardware.Globals.DEPOSIT_PIVOT_SCORING_POS;
import static hardware.Globals.DEPOSIT_PIVOT_SPECIMEN_BACK_INTAKE_POS;
import static hardware.Globals.DEPOSIT_PIVOT_SPECIMEN_BACK_SCORING_POS;
import static hardware.Globals.DEPOSIT_PIVOT_SPECIMEN_FRONT_SCORING_POS;
import static hardware.Globals.FRONT_HIGH_SPECIMEN_HEIGHT;
import static hardware.Globals.HIGH_BUCKET_HEIGHT;
import static hardware.Globals.INTAKE_FORWARD_SPEED;
import static hardware.Globals.INTAKE_PIVOT_EJECTING;
import static hardware.Globals.INTAKE_PIVOT_TAKING;
import static hardware.Globals.INTAKE_REVERSE_SPEED;
import static hardware.Globals.INTAKE_STOP;
import static hardware.Globals.LOW_BUCKET_HEIGHT;
import static hardware.Globals.WRIST_FRONT_SPECIMEN_SCORING;
import static hardware.Globals.WRIST_SCORING;
import static hardware.Globals.opModeType;
import static hardware.Robot.deliveryGyro;
import static hardware.Robot.deliveryGyroLeft;
import static hardware.Robot.deliveryGyroRight;
import static hardware.Robot.intakeDrive;
import static hardware.Robot.intakeLeftGyro;

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
        if (driver.leftStickButton.wasJustPressed()) {
            telemetry.addData("DRIVER PESSIONADO", "");
            telemetry.update();
        }

        operator = new Controller(gamepad2);
        if (operator.leftStickButton.wasJustPressed()) {
            telemetry.addData("OPERATOR PESSIONADO", "");
            telemetry.update();
        }


        telemetry.addData("OpModeGlobal:", opModeType);
        telemetry.addData("OpModeHardwarePassado:", Robot.opMODE);

        Robot.init();


    }

    @Override
    public void start() {
    }

    @Override
    public void loop() {

        Robot.follower.setTeleOpMovementVectors(-gamepad1.left_stick_y, -gamepad1.left_stick_x, -gamepad1.right_stick_x, true);
        Robot.follower.update();

        if (gamepad1.cross) {
            switch (Robot.scoring) {
                case HIGH_BUCKET: {
                    Robot.setSliderTarget(HIGH_BUCKET_HEIGHT);
                    Robot.setShoulderPos(DEPOSIT_PIVOT_SCORING_POS);
                    deliveryGyro.setPosition(WRIST_SCORING);
                    break;
                }
                case LOW_BUCKET: {
                    Robot.setSliderTarget(LOW_BUCKET_HEIGHT);
                    Robot.setShoulderPos(DEPOSIT_PIVOT_SCORING_POS);
                    deliveryGyro.setPosition(WRIST_SCORING);
                    break;
                }
                case HIGH_SPECIMEN: {
                    Robot.setSliderTarget(FRONT_HIGH_SPECIMEN_HEIGHT);
                    Robot.setShoulderPos(DEPOSIT_PIVOT_SPECIMEN_FRONT_SCORING_POS);
                    deliveryGyro.setPosition(WRIST_FRONT_SPECIMEN_SCORING);
                    break;
                }
            }
        }

        if (gamepad2.dpad_left) {
            switch (Robot.intake) {
                case TAKING: {
                    intakeLeftGyro.setPosition(INTAKE_PIVOT_TAKING);
                    intakeDrive.setPower(INTAKE_FORWARD_SPEED);
                    break;
                }
                case EJECTING: {
                    intakeLeftGyro.setPosition(INTAKE_PIVOT_EJECTING);
                    intakeDrive.setPower(INTAKE_STOP);
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
            Robot.setDeliveryClaw();
        }

        if (gamepad1.square) {
            Robot.setClawOpen(true);
        }
        if (gamepad1.circle) {
            Robot.setClawOpen(false);
        }

        if (gamepad1.left_bumper) {
            Robot.setShoulderPos(DEPOSIT_PIVOT_SPECIMEN_FRONT_SCORING_POS);
//            Robot.minusShoulder();
//            Robot.setShoulderPos(DEPOSIT_PIVOT_SPECIMEN_FRONT_SCORING_POS);
        }

        if (gamepad1.right_bumper) {
            Robot.setShoulderPos(DEPOSIT_PIVOT_SPECIMEN_BACK_INTAKE_POS);
//            Robot.plusShoulder();
//            Robot.setShoulderPos(DEPOSIT_PIVOT_SPECIMEN_BACK_SCORING_POS);
        }

        if (gamepad2.dpad_up) {
            Robot.intake = INTAKE.TAKING;
        }

        if (gamepad2.dpad_down) {
            Robot.intake = INTAKE.EJECTING;
        }
//        deliveryGyroRight.setPosition(gamepad2.left_stick_x);
//        deliveryGyroLeft.setPosition(gamepad2.left_stick_x);
//
//        deliveryGyro.setPosition(gamepad2.right_stick_x);
//
//        telemetry.addData("LEFT SHOULDER POS", deliveryGyroLeft.getPosition());
//        telemetry.addData("RIGHT SHOULDER POS", deliveryGyroRight.getPosition());
//
//        telemetry.addData("DELIVERY GYRO POS", deliveryGyro.getPosition());
//        telemetry.update();
    }

    @Override
    public void stop() {

    }
}
