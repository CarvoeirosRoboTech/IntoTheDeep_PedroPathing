package pedroPathing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import controller.Controller;

@TeleOp (name = "Test TeleOp Motores", group = "Iterative OpMode")
public class TestTeleOpMotores extends LinearOpMode {
    TeleOpcomMaquinasdeEstado teleOpcomMaquinasdeEstado = new TeleOpcomMaquinasdeEstado();
    Controller controle;

    @Override
    public void runOpMode() {
        teleOpcomMaquinasdeEstado.init();
        teleOpcomMaquinasdeEstado.start();

        DcMotor dcMotor = hardwareMap.get(DcMotor.class, "leftElevatorDrive");


        telemetry.addData("Status", "PLAY");
        telemetry.update();

        waitForStart();

        double postion = 0;
        boolean lastCircle = false;
        boolean lastSquare = false;
        boolean lastTriangle = false;
        boolean lastCross = false;
        while (opModeIsActive()) {

            //Teste dos elevadores e sliders
            if (controle.circle.wasJustReleased() && !lastCircle)
            {
                postion -= 0.5;
                lastCircle = true;
                teleOpcomMaquinasdeEstado.leftElevatorDrive.setPower(postion);
            }
            if (!controle.circle.wasJustReleased())
            {
                lastCircle = false;
            }

            if (controle.square.wasJustReleased() && !lastSquare)
            {
                postion += 0.5;
                lastSquare = true;
                teleOpcomMaquinasdeEstado.rightElevatorDrive.setPower(postion);
            }
            if (!controle.square.wasJustReleased())
            {
                lastSquare = false;
            }

            if (controle.triangle.wasJustReleased() && !lastTriangle)
            {
                postion -= 0.5;
                lastTriangle = true;
                teleOpcomMaquinasdeEstado.intakeSliderDrive.setPower(postion);
            }
            if (!controle.triangle.wasJustPressed())
            {
                lastTriangle = false;
            }

            if  (controle.cross.wasJustReleased() && !lastCross)
            {
                postion += 0.5;
                lastCross = true;
                teleOpcomMaquinasdeEstado.intakeDrive.setPower(postion);
            }
            if (!controle.cross.wasJustPressed())
            {
                lastCross = false;
            }

            telemetry.addData("leftElevatorDrive", teleOpcomMaquinasdeEstado.leftElevatorDrive.getCurrentPosition());
            telemetry.addData("rightElevatorDrive", teleOpcomMaquinasdeEstado.rightElevatorDrive.getCurrentPosition());
            telemetry.addData("intakeDrive", teleOpcomMaquinasdeEstado.intakeDrive.getCurrentPosition());
            telemetry.addData("intakeSliderDrive", teleOpcomMaquinasdeEstado.intakeSliderDrive.getCurrentPosition());
            telemetry.update();
        }
    }
}
