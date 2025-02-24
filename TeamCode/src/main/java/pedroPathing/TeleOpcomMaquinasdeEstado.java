package pedroPathing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOp com Maquinas de Estado", group = "Iterative OpMode")
public class TeleOpcomMaquinasdeEstado extends OpMode {

    DcMotor claw = null;
    Gamepad controle;
    private enum EstadoClaw{
        CLAW_OPEN,
        CLAW_CLOSED,
        DEPOSIT_CLAW_OPEN_POS,
        DEPOSIT_CLAW_CLOSE_POS
    }

    private enum EstadoGyro{
        GYRO_MIDDLE,
        GYRO_TRANSFER,
        GYRO_GET_SPECIMEN,
        INTAKE_LEFT_GYRO_TRANSFER_POS,
        INTAKE_RIGHT_GYRO_TRASNFER_POS,
        INTAKE_LEFT_GYRO_INTAKING_POS,
        INTAKE_RIGHT_GYRO_INTAKING_POS
    }

    private enum EstadoSlides{
        MAX_SLIDES_EXTENSION,
        SLIDES_PIVOT_READY_EXTENSION,
        LOW_BUCKET_HEIGHT,
        HIGH_BUCKET_HEIGHT,
        FRONT_HIGH_SPECIMEN_HEIGHT,
        BACK_HIGH_SPECIMEN_HEIGHT,
        BACK_HIGH_SPECIMEN_ATTACH_HEIGHT,
        AUTO_ASCENT_HEIGHT,
        ENDGAME_ASCENT_HEIGHT
    }

    private enum EstadoIntake{
        INTAKE_EXTENSION_OUT,
        INTAKE_EXTENSION_IN
    }

    EstadoClaw estadoClaw = EstadoClaw.CLAW_OPEN;

    @Override
    public void init() {
        claw = hardwareMap.get(DcMotor.class, "Garra");

    }
    @Override
    public void loop() {
        switch (estadoClaw) {
            case CLAW_OPEN:
                if (controle.cross)
                {
                    claw.setPower(0.4);
                    estadoClaw = EstadoClaw.CLAW_OPEN;
                }
                break;
            case CLAW_CLOSED:
            {
                if (controle.cross)
                {
                    claw.setPower(0.26);
                }

            }
        }
    }
}
