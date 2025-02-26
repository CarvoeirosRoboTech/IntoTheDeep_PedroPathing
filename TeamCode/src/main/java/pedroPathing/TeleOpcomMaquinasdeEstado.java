package pedroPathing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.util.Constants;

import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;
import android.provider.SyncStateContract;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOp com Maquinas de Estado", group = "Iterative OpMode")
public class TeleOpcomMaquinasdeEstado extends OpMode {

    private Follower follower;
    private DcMotor leftElevatorDrive = null;
    private DcMotor rightElevatorDrive = null;
    private DcMotor intakeSliderDrive = null;
    private DcMotor intakeDrive = null;
    private Servo deliveryClaw = null;
    private Servo deliveryGyroLeft = null;
    private Servo deliveryGyroRight = null;
    private Servo deliveryGyro = null;
    private Servo intakeLeftGyro = null;
    private Servo intakeRightGyro = null;
    private final Pose startPose = new Pose(0,0,0);

    Gamepad controle;

    //Trava a posição do braço + garra dependendo da ação desejada;
    private enum SetDeliveryStatus {
        TRANSFER,
        MIDDLE,
        SPECIMEN
    }

    private enum IntakeStatus {
        INTAKING,
        TRANSFER
    }

    private enum BasketStatus {
        LOW,
        HIGH
    }

    SetDeliveryStatus setDeliveryStatus = SetDeliveryStatus.TRANSFER.MIDDLE.SPECIMEN;
    IntakeStatus intakeStatus = IntakeStatus.TRANSFER.INTAKING;
    BasketStatus basketStatus = BasketStatus.LOW.HIGH;

    @Override
    public void init() {

        Constants.setConstants(FConstants.class,LConstants.class);
        follower = new Follower(hardwareMap);
        follower.setStartingPose(startPose);

        //Motores
        rightElevatorDrive = hardwareMap.get(DcMotor.class, "rightElevatorDrive"); // Nome na Driver Station Deverá ser o mesmo que o nome entre ""
        leftElevatorDrive = hardwareMap.get(DcMotor.class, "leftElevatorDrive");
        intakeSliderDrive = hardwareMap.get(DcMotor.class, "intakeSliderDrive");
        intakeDrive = hardwareMap.get(DcMotor.class, "intakeDrive");

        rightElevatorDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        leftElevatorDrive.setDirection(DcMotorSimple.Direction.FORWARD);

        intakeDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        intakeDrive.setDirection(DcMotorSimple.Direction.REVERSE);

        leftElevatorDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightElevatorDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        intakeSliderDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        leftElevatorDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightElevatorDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //Servos
        deliveryClaw = hardwareMap.get(Servo.class, "deliveryClaw");
        deliveryGyro = hardwareMap.get(Servo.class, "deliveryGyro");
        deliveryGyroLeft = hardwareMap.get(Servo.class, "deliveryGyroLeft");
        deliveryGyroRight = hardwareMap.get(Servo.class, "deliveryGyroRight");

        intakeLeftGyro = hardwareMap.get(Servo.class, "intakeGyroLeft");
        intakeRightGyro = hardwareMap.get(Servo.class, "intakeGyroRight");

        deliveryClaw.setPosition(0);
        deliveryGyro.setPosition(0);
        deliveryGyroRight.setPosition(0);
        deliveryGyroLeft.setPosition(0);

        intakeRightGyro.setPosition(0);
        intakeLeftGyro.setPosition(0);

        telemetry.addData(">", "Hardware Initialized");
        telemetry.update();
    }

    public void setIntakePower(double power) {
        intakeDrive.setPower(power);
    }

    public void setIntakeExtensionPower(double power) {
        intakeSliderDrive.setPower(power);
    }

    public void setDeliveryGyroPosition(double position) {
        deliveryGyro.setPosition(position);
    }

    @Override
    public void loop() {
        if (controle.cross) {
            switch (setDeliveryStatus) {
                case TRANSFER: {
                    deliveryClaw.setPosition(0.4);
                    deliveryGyro.setPosition(0.35);
                    deliveryGyroRight.setPosition(0);
                    deliveryGyroLeft.setPosition(0);
                    if (controle.cross)
                    {
                        setDeliveryStatus = SetDeliveryStatus.MIDDLE;
                        break;
                    }
                }
                break;

                case MIDDLE:
                    if (controle.cross) {
                        deliveryGyro.setPosition(0.3);
                        deliveryGyroRight.setPosition(0);
                        deliveryGyroLeft.setPosition(0);
                        if (controle.cross)
                        {
                            setDeliveryStatus = SetDeliveryStatus.SPECIMEN;
                            break;
                        }
                    }
                    break;

                case SPECIMEN:
                    if (controle.cross) {
                        deliveryClaw.setPosition(0.4);
                        deliveryGyro.setPosition(0.25);
                        deliveryGyroRight.setPosition(0);
                        deliveryGyroLeft.setPosition(0);
                    }

                    if (controle.cross) {
                        setDeliveryStatus = SetDeliveryStatus.TRANSFER;
                        break;
                    }
            }
        }

        if (controle.dpad_up) {
            switch (intakeStatus) {
                case INTAKING: {
                    intakeRightGyro.setPosition(-0.2);
                    intakeLeftGyro.setPosition(0.2);
                    if (controle.dpad_up) {
                        intakeStatus = IntakeStatus.TRANSFER;
                    }
                }
                break;

                case TRANSFER:
                    if (controle.dpad_up) {
                        intakeRightGyro.setPosition(-0.2);
                        intakeLeftGyro.setPosition(0.2);
                        if (controle.dpad_up)
                        {
                            intakeStatus = IntakeStatus.INTAKING;
                        }
                    }
                    break;
            }
        }

        if (controle.right_bumper)
        {
            switch (basketStatus)
            {
                case LOW:
                    rightElevatorDrive.setTargetPosition(450);
                    leftElevatorDrive.setTargetPosition(450);
                    rightElevatorDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    leftElevatorDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    if (controle.right_bumper)
                    {
                        basketStatus = BasketStatus.HIGH;
                    }
                    break;

                case HIGH:
                    rightElevatorDrive.setTargetPosition(1900);
                    leftElevatorDrive.setTargetPosition(1900);
                    rightElevatorDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    leftElevatorDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    if (controle.right_bumper)
                    {
                        basketStatus = BasketStatus.LOW;
                    }
                    break;
            }
        }
    }
}
