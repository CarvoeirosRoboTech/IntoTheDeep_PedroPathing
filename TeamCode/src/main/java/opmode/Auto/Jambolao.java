package opmode.Auto;

import static hardware.Globals.opModeType;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import hardware.Globals;
import hardware.Robot;

@Autonomous
public class Jambolao extends OpMode {
    hardware.Robot Robot = new Robot(this);
    @Override
    public void init() {
        Robot.opMODE = Globals.OpModeType.AUTO;

        telemetry.addData("OpModeGlobal:", opModeType);
        telemetry.addData("OpModeHardwarePassado:", Robot.opMODE);

        Robot.init();
    }

    @Override
    public void loop() {

    }
}
