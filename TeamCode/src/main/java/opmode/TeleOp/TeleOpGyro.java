package opmode.TeleOp;

import static hardware.Globals.DEPOSIT_GYRO_HIGH_BASKET_POS;
import static hardware.Globals.DEPOSIT_GYRO_HIGH_SPECIMEN_POS;
import static hardware.Globals.DEPOSIT_GYRO_LOW_BASKET_POS;
import static hardware.Globals.FRONT_HIGH_SPECIMEN_HEIGHT;
import static hardware.Globals.HIGH_BUCKET_HEIGHT;
import static hardware.Globals.LOW_BUCKET_HEIGHT;
import static hardware.Globals.MID_SERVO;
import static hardware.Robot.deliveryGyroLeft;
import static hardware.Robot.rightElevatorDrive;

import android.net.wifi.aware.IdentityChangedListener;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.util.Constants;
import  com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import controller.Controller;
import hardware.Globals;
import hardware.Robot;
import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;


@TeleOp(name = "TeleOp TESTE CONTROLE", group = "Iterative OpMode")
public class TeleOpGyro extends OpMode {
    hardware.Robot Robot = new Robot(this);
    public Controller driver;
    public Controller operator;

    @Override
    public void init() {
        Robot.opMODE = Globals.OpModeType.TELEOP;

        telemetry.addData("INIT", "");

        Robot.init();
    }

    @Override
    public void start() {
        driver = new Controller(gamepad1);
        operator = new Controller(gamepad2);

    }

    @Override
    public void loop() {
        if (driver.cross.wasJustPressed()) {
            telemetry.addLine("X APERTADO DRIVER");
        }

        if (operator.cross.wasJustPressed()) {
            telemetry.addLine("X APERTADO OPERADOR");
        }


        driver.update();
        operator.update();
    }
}