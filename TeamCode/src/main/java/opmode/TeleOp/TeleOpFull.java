package opmode.TeleOp;

import static hardware.Globals.FRONT_HIGH_SPECIMEN_HEIGHT;
import static hardware.Globals.HIGH_BUCKET_HEIGHT;
import static hardware.Globals.opModeType;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import controller.Controller;
import hardware.Globals;
import hardware.Robot;

@TeleOp(name = "TeleOp Full", group = "Iterative OpMode")
public class TeleOpFull extends OpMode {
    hardware.Robot Robot = new Robot(this);
    public Controller driver;
    public Controller operator;

    @Override
    public void init() {
        Robot.opMODE = Globals.OpModeType.TELEOP;

        driver = new Controller(gamepad1);
        operator = new Controller(gamepad2);


        telemetry.addData("OpModeGlobal:", opModeType);
        telemetry.addData("OpModeHardwarePassado:", Robot.opMODE);

        Robot.init();


    }

    @Override
    public void start() {
//        Robot.initHasMovement();
    }

    @Override
    public void loop() {
        Robot.follower.setTeleOpMovementVectors(-gamepad1.left_stick_y, -gamepad1.left_stick_x, -gamepad1.right_stick_x, true);
        Robot.follower.update();

        if (driver.circle.wasJustPressed()) {
            Robot.setSliderTarget(FRONT_HIGH_SPECIMEN_HEIGHT);
        }

        if (gamepad1.cross) {
            Robot.setSliderTarget(FRONT_HIGH_SPECIMEN_HEIGHT);
        }
    }

    @Override
    public void stop() {

    }
}
