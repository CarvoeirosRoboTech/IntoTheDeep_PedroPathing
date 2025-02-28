package pedroPathing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import controller.Controller;

@TeleOp (name = "Test TeleOp Servos", group = "Iterative OpMode")
public class TestTeleOpServos extends LinearOpMode{

    TeleOpcomMaquinasdeEstado teleOpcomMaquinasdeEstado = new TeleOpcomMaquinasdeEstado();
    Controller controle;

    @Override
    public void runOpMode() {
        teleOpcomMaquinasdeEstado.init();
        teleOpcomMaquinasdeEstado.start();

        telemetry.addData("Status", "PLAY");

        waitForStart();

        double position = 0;
        boolean lastCircle = false;
        boolean lastSquare = false;
        boolean lastTriangle = false;
        boolean lastCross = false;
        boolean lastDpadUp = false;
        boolean lastDpadDown = false;
        while (opModeIsActive()) {
            if (!controle.circle.wasJustPressed() || lastCircle) {
            } else {
                position -= 0.5;
                lastCircle = true;
                teleOpcomMaquinasdeEstado.deliveryClaw.setPosition(position);
            }
            if (!controle.circle.wasJustPressed())
            {
                lastCircle = false;
            }

            if (controle.cross.wasJustPressed() && !lastCross)
            {
                position += 0.5;
                lastCross = true;
                teleOpcomMaquinasdeEstado.deliveryGyroLeft.setPosition(position);
            }
            if (!controle.cross.wasJustPressed())
            {
                lastCross = false;

            }

            if (controle.square.wasJustPressed() && !lastSquare)
            {
                position -= 0.5;
                lastSquare = true;
                teleOpcomMaquinasdeEstado.deliveryGyroRight.setPosition(position);
            }
            if (!controle.square.wasJustPressed())
            {
                lastSquare = false;
            }

            if (controle.triangle.wasJustPressed() && !lastTriangle)
            {
                position += 0.5;
                lastTriangle = true;
                teleOpcomMaquinasdeEstado.deliveryGyro.setPosition(position);
            }
            if (!controle.triangle.wasJustPressed())
            {
                lastTriangle = false;
            }

            if (controle.dpadUp.wasJustReleased() && !lastDpadUp)
            {
                position -= 0.5;
                lastDpadUp = true;
                teleOpcomMaquinasdeEstado.intakeLeftGyro.setPosition(position);
            }
            if (!controle.dpadUp.wasJustPressed())
            {
                lastDpadUp = false;
            }

            if (controle.dpadDown.wasJustPressed() && !lastDpadDown)
            {
                position += 0.5;
                lastDpadDown = true;
                teleOpcomMaquinasdeEstado.intakeRightGyro.setPosition(position);
            }
            if (!controle.dpadDown.wasJustPressed())
            {
                lastDpadDown = false;
            }

            telemetry.addData("deliveryClaw", teleOpcomMaquinasdeEstado.deliveryClaw.getPosition());
            telemetry.addData("deliveryGyroLeft", teleOpcomMaquinasdeEstado.deliveryGyroLeft.getPosition());
            telemetry.addData("deliveryGyroRight", teleOpcomMaquinasdeEstado.deliveryGyroRight.getPosition());
            telemetry.addData("deliveryGyro", teleOpcomMaquinasdeEstado.deliveryGyro.getPosition());
            telemetry.addData("intakeLeftGyro", teleOpcomMaquinasdeEstado.intakeLeftGyro.getPosition());
            telemetry.addData("intakeRightGyro", teleOpcomMaquinasdeEstado.intakeRightGyro.getPosition());
            telemetry.update();
        }
    }
}
