package opmode.TeleOp;

import static Globals.GlobalPositions.opModeType;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import Globals.GlobalPositions.*;
import controller.Controller;
import hardware.Robot;
import hardware.RobotHardware;

@TeleOp(name = "TeleOp Full", group = "Iterative OpMode")
public class TeleOpFull extends OpMode {


    Controller controle;
    public Controller getControle() {
        return controle;
    }

    @Override
    public void init() {
        opModeType = OpModeType.TELEOP;
        telemetry.addData("OPMODE:", opModeType);
        telemetry.update();

//        robot.init(hardwareMap);
    }

    @Override
    public void start() {

    }

    @Override
    public void loop() {

    }

    @Override
    public void stop() {

    }
}
