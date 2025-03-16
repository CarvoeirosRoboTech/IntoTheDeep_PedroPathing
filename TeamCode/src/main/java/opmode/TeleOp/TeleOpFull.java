package opmode.TeleOp;
//
import static hardware.Globals.DEPOSIT_GYRO_HIGH_BASKET_POS;
import static hardware.Globals.DEPOSIT_GYRO_HIGH_SPECIMEN_POS;
import static hardware.Globals.DEPOSIT_GYRO_HUMAN_POS;
import static hardware.Globals.DEPOSIT_GYRO_LOW_BASKET_POS;
import static hardware.Globals.DEPOSIT_GYRO_TRANSFER_POS;
import static hardware.Globals.DEPOSIT_SHOULDER_HIGH_BASKET_POS;
import static hardware.Globals.DEPOSIT_SHOULDER_HIGH_SPECIMEN_POS;
import static hardware.Globals.DEPOSIT_SHOULDER_HUMAN_POS;
import static hardware.Globals.DEPOSIT_SHOULDER_LOW_BASKET_POS;
import static hardware.Globals.DEPOSIT_SHOULDER_TRANSFER_POS;
import static hardware.Globals.FRONT_HIGH_SPECIMEN_HEIGHT;
import static hardware.Globals.HIGH_BUCKET_HEIGHT;
import static hardware.Globals.INTAKE_GET_SPEED;
import static hardware.Globals.INTAKE_GYRO_GET_POS;
import static hardware.Globals.INTAKE_GYRO_TRANSFER_POS;
import static hardware.Globals.INTAKE_HOLD_SPEED;
import static hardware.Globals.INTAKE_OUT_SPEED;
import static hardware.Globals.INTAKE_PIVOT_TAKING;
import static hardware.Globals.INTAKE_TAKING_SAMPLE_POWER;
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
import static hardware.Robot.leftElevatorDrive;
import static hardware.Robot.rightElevatorDrive;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import controller.Controller;
import hardware.Globals.*;
import hardware.Robot;

@TeleOp(name = "TeleOp Full", group = "Iterative OpMode")
public class TeleOpFull extends OpMode {
    hardware.Robot Robot = new Robot(this);
    public Controller driver;
    public Controller operator;

    public Controller getDriver() {
        return driver;
    }

    public Controller getOperator() {
        return operator;
    }


    private Telemetry data;

    @Override
    public void init() {
        Robot.opMODE = OpModeType.TELEOP;

        data = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());

        data.addLine("OpModeGlobal:" + opModeType);
        data.addLine("OpModeHardwarePassado:" + Robot.opMODE);

        Robot.init();


    }

    @Override
    public void start() {
        driver = new Controller(gamepad1);
        operator = new Controller(gamepad2);
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
                    deliveryGyro.setPosition(WRIST_SCORING);

                    data.addLine("HIGH BASKET");
                    data.update();
                    break;
                }
                case LOW_BUCKET: {
                    Robot.setElevatorTarget(LOW_BUCKET_HEIGHT);
                    Robot.setShoulderPos(DEPOSIT_SHOULDER_LOW_BASKET_POS);
                    Robot.setClawPos(DEPOSIT_GYRO_LOW_BASKET_POS);
                    deliveryGyro.setPosition(WRIST_SCORING);

                    data.addLine("LOW BASKET");
                    data.update();
                    break;
                }
                case HIGH_SPECIMEN: {
                    Robot.setElevatorTarget(FRONT_HIGH_SPECIMEN_HEIGHT);
                    Robot.setShoulderPos(DEPOSIT_SHOULDER_HIGH_SPECIMEN_POS);
                    Robot.setClawPos(DEPOSIT_GYRO_HIGH_SPECIMEN_POS);
                    deliveryGyro.setPosition(WRIST_FRONT_SPECIMEN_SCORING);

                    data.addLine("HIGH SPECIMEN");
                    data.update();
                    break;
                }
            }
        }


        if (operator.dpadLeft.wasJustPressed()) {
            switch (Robot.intake) {
                case TAKING: {
                    intakeLeftGyro.setPosition(INTAKE_GYRO_GET_POS);
                    Robot.setIntakeSliderTarget(MAX_SLIDER_EXTENSION);

                    intakeDrive.setPower(INTAKE_GET_SPEED);

                    Robot.intake = INTAKE.EJECTING;
                    break;
                }
                case EJECTING: {
                    intakeLeftGyro.setPosition(INTAKE_GYRO_TRANSFER_POS);
                    Robot.setIntakeSliderTarget(MIN_SLIDER_EXTENSION);
                    intakeDrive.setPower(INTAKE_HOLD_SPEED);

                    Robot.intake = INTAKE.TAKING;
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

        if (operator.square.wasJustPressed()) {
            Robot.setClawOpen(true);
        }
        if (operator.circle.wasJustReleased()) {
            Robot.setClawOpen(false);
        }

        if (operator.cross.wasJustPressed()) {
            if (Robot.scoring == SCORING.HIGH_SPECIMEN) {
                intakeLeftGyro.setPosition(0);
                Robot.setElevatorTarget(0);
                Robot.setClawPos(DEPOSIT_GYRO_HUMAN_POS);
                Robot.setShoulderPos(DEPOSIT_SHOULDER_HUMAN_POS);
            } else {
                intakeLeftGyro.setPosition(INTAKE_GYRO_TRANSFER_POS);
                Robot.setElevatorTarget(0);
                Robot.setClawPos(DEPOSIT_GYRO_TRANSFER_POS);
                Robot.setShoulderPos(DEPOSIT_SHOULDER_TRANSFER_POS);
            }

        }

        if (driver.rightBumper.wasJustPressed()) {
            data.addLine("Entrou Controle Driver");
            Robot.setShoulderPos(deliveryGyroLeft.getPosition() + 0.01);
        }

        if (driver.leftBumper.wasJustPressed()) {
            Robot.setShoulderPos(deliveryGyroLeft.getPosition() - 0.01);
        }


        if (operator.rightBumper.wasJustPressed()) {
            Robot.setClawPos(deliveryGyro.getPosition() + 0.01);
//            intakeLeftGyro.setPosition(intakeLeftGyro.getPosition() + 0.01);
        }

        if (operator.leftBumper.wasJustPressed()) {
            Robot.setClawPos(deliveryGyro.getPosition() - 0.01);
//            intakeLeftGyro.setPosition(intakeLeftGyro.getPosition() - 0.01);
        }

        if (operator.triangle.wasJustPressed()){
            int atual = leftElevatorDrive.getCurrentPosition();
            Robot.setElevatorTarget(atual + 100);
        }

        if (operator.dpadRight.wasJustPressed()) {
            intakeLeftGyro.setPosition(INTAKE_GYRO_GET_POS);
            Robot.setIntakeSliderTarget(MAX_SLIDER_EXTENSION);
            intakeDrive.setPower(INTAKE_OUT_SPEED);
        }

//
//        if (gamepad2.left_bumper) {
//            Robot.setClawPos(DEPOSIT_GYRO_HUMAN_POS);
//        }

        //deliveryGyroLeft.setPosition(gamepad2.left_stick_x);

//        Robot.setShoulderPos(gamepad2.left_stick_x);
//        deliveryGyro.setPosition(gamepad2.right_stick_x);
//
        data.addLine("Gyro POS" + deliveryGyro.getPosition());
        data.addLine("Intake Gyro POS" + intakeLeftGyro.getPosition());
        data.addLine("Claw POS" + deliveryClaw.getPosition());
        data.addLine("Shoulder POS" + deliveryGyroRight.getPosition());
        data.addLine("EL. E. POS" + leftElevatorDrive.getCurrentPosition());
        data.addLine("EL. D. POS" + rightElevatorDrive.getCurrentPosition());

        data.update();

        driver.update();
        operator.update();
    }

    @Override
    public void stop() {

    }
}
