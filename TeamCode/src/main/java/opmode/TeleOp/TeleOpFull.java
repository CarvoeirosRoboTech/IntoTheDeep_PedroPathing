package opmode.TeleOp;
//
import static hardware.Globals.DEPOSIT_GYRO_HIGH_BASKET_POS;
import static hardware.Globals.DEPOSIT_GYRO_HIGH_SPECIMEN_POS;
import static hardware.Globals.DEPOSIT_GYRO_HUMAN_POS;
import static hardware.Globals.DEPOSIT_GYRO_LOW_BASKET_POS;
import static hardware.Globals.DEPOSIT_GYRO_TRANSFER_POS;
import static hardware.Globals.DEPOSIT_PIVOT_READY_TRANSFER_POS;
import static hardware.Globals.DEPOSIT_PIVOT_SCORING_POS;
import static hardware.Globals.DEPOSIT_PIVOT_SPECIMEN_FRONT_SCORING_POS;
import static hardware.Globals.DEPOSIT_PIVOT_TRANSFER_POS;
import static hardware.Globals.DEPOSIT_SHOULDER_HIGH_BASKET_POS;
import static hardware.Globals.DEPOSIT_SHOULDER_HIGH_SPECIMEN_POS;
import static hardware.Globals.DEPOSIT_SHOULDER_LOW_BASKET_POS;
import static hardware.Globals.DEPOSIT_SHOULDER_TRANSFER_POS;
import static hardware.Globals.FRONT_HIGH_SPECIMEN_HEIGHT;
import static hardware.Globals.HIGH_BUCKET_HEIGHT;
import static hardware.Globals.INTAKE_GYRO_GET_POS;
import static hardware.Globals.INTAKE_GYRO_TRANSFER_POS;
import static hardware.Globals.INTAKE_PIVOT_EJECTING;
import static hardware.Globals.INTAKE_PIVOT_READY_TRANSFER_POS;
import static hardware.Globals.INTAKE_PIVOT_TAKING;
import static hardware.Globals.LOW_BUCKET_HEIGHT;
import static hardware.Globals.MAX_SLIDER_EXTENSION;
import static hardware.Globals.MIN_SLIDER_EXTENSION;
import static hardware.Globals.WRIST_FRONT_SPECIMEN_SCORING;
import static hardware.Globals.WRIST_SCORING;
import static hardware.Globals.opModeType;
import static hardware.Robot.deliveryClaw;
import static hardware.Robot.deliveryGyro;
import static hardware.Robot.deliveryGyroLeft;
import static hardware.Robot.deliveryGyroRight;
import static hardware.Robot.intakeDrive;
import static hardware.Robot.intakeLeftGyro;
import static hardware.Robot.intakeSliderDrive;
import static hardware.Robot.leftElevatorDrive;
import static hardware.Robot.rightElevatorDrive;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

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
//        Robot.opMODE = OpModeType.TELEOP;

//        driver = new Controller(gamepad1);
//        if (driver.leftStickButton.wasJustPressed()) {
//            telemetry.addData("DRIVER PESSIONADO", "");
//            telemetry.update();
//        }
//
//        operator = new Controller(gamepad2);
//        if (operator.leftStickButton.wasJustPressed()) {
//            telemetry.addData("OPERATOR PESSIONADO", "");
//            telemetry.update();
//        }


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
                    Robot.setElevatorTarget(HIGH_BUCKET_HEIGHT);
                    Robot.setShoulderPos(DEPOSIT_SHOULDER_HIGH_BASKET_POS);
                    Robot.setClawPos(DEPOSIT_GYRO_HIGH_BASKET_POS);
//                    deliveryGyro.setPosition(WRIST_SCORING);
                    break;
                }
                case LOW_BUCKET: {
                    Robot.setElevatorTarget(LOW_BUCKET_HEIGHT);
                    Robot.setShoulderPos(DEPOSIT_SHOULDER_LOW_BASKET_POS);
                    Robot.setClawPos(DEPOSIT_GYRO_LOW_BASKET_POS);
//                    deliveryGyro.setPosition(WRIST_SCORING);
                    break;
                }
                case HIGH_SPECIMEN: {
                    Robot.setElevatorTarget(FRONT_HIGH_SPECIMEN_HEIGHT);
                    Robot.setShoulderPos(DEPOSIT_SHOULDER_HIGH_SPECIMEN_POS);
                    Robot.setClawPos(DEPOSIT_GYRO_HIGH_SPECIMEN_POS);
//                    deliveryGyro.setPosition(WRIST_FRONT_SPECIMEN_SCORING);
                    break;
                }
            }
        }


        if (gamepad2.dpad_left) {
            switch (Robot.intake) {
                case TAKING: {
                    intakeLeftGyro.setPosition(INTAKE_GYRO_GET_POS);
                    Robot.setIntakeSliderTarget(MAX_SLIDER_EXTENSION);

                    intakeDrive.setPower(0.8);

                    Robot.intake = INTAKE.EJECTING;

                    telemetry.addData("INDO", "");
                    telemetry.update();
                    break;
                }
                case EJECTING: {
                    intakeLeftGyro.setPosition(INTAKE_GYRO_TRANSFER_POS);
                    Robot.setIntakeSliderTarget(MIN_SLIDER_EXTENSION);
//
                    intakeDrive.setPower(0.1);

                    Robot.intake = INTAKE.TAKING;


                    telemetry.addData("VOLTANDO", "");
                    telemetry.update();
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

//        if (gamepad1.triangle) {
//            Robot.setDeliveryClaw();
//        }

        if (gamepad2.square) {
            Robot.setClawOpen(true);
        }
        if (gamepad2.circle) {
            Robot.setClawOpen(false);
        }

        if (gamepad2.cross) {
            intakeLeftGyro.setPosition(INTAKE_GYRO_TRANSFER_POS);
            Robot.setClawPos(DEPOSIT_GYRO_TRANSFER_POS);
            Robot.setShoulderPos(DEPOSIT_SHOULDER_TRANSFER_POS);
        }

        if (gamepad2.triangle) {
            Robot.setClawPos(DEPOSIT_GYRO_HUMAN_POS);
        }

//        leftElevatorDrive.setTargetPositio (gamepad2.left_stick_x);
//        deliveryGyroLeft.setPosition(gamepad2.left_stick_x);

        if (gamepad2.right_bumper) {
            rightElevatorDrive.setTargetPosition(rightElevatorDrive.getCurrentPosition()+100);
            leftElevatorDrive.setTargetPosition(rightElevatorDrive.getCurrentPosition()+100);

            rightElevatorDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftElevatorDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            leftElevatorDrive.setPower(0.8);
            rightElevatorDrive.setPower(0.8);
        }

        if (gamepad2.left_bumper) {
            rightElevatorDrive.setTargetPosition(rightElevatorDrive.getCurrentPosition()-100);
            leftElevatorDrive.setTargetPosition(rightElevatorDrive.getCurrentPosition()-100);

            rightElevatorDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftElevatorDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            leftElevatorDrive.setPower(-0.4);
            rightElevatorDrive.setPower(-0.4);
        }

        Robot.setShoulderPos(gamepad2.left_stick_x);
        deliveryGyro.setPosition(gamepad2.right_stick_x);
//
        telemetry.addData("DELIVERY GYRO POS", deliveryGyro.getPosition());
//        telemetry.addData("TRANSFER POS", intakeLeftGyro.getPosition());
        telemetry.addData("SHOULDER POS", deliveryGyroLeft.getPosition());
        telemetry.addData("EL. E. POS:", leftElevatorDrive.getCurrentPosition());
        telemetry.addData("EL. D. POS:", rightElevatorDrive.getCurrentPosition());
//        telemetry.addData("INTAKE SLIDER POS", intakeSliderDrive.getCurrentPosition());
        telemetry.update();
    }

    @Override
    public void stop() {

    }
}
