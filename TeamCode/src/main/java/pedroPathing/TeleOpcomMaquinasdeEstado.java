package pedroPathing;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.util.Constants;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;
import controller.Controller;

@TeleOp(name = "TeleOp com Maquinas de Estado", group = "Iterative OpMode")
public class TeleOpcomMaquinasdeEstado extends OpMode {

    public DcMotor leftElevatorDrive = null;
    public DcMotor rightElevatorDrive = null;
    public DcMotor intakeSliderDrive = null;
    public DcMotor intakeDrive = null;
    private DcMotor leftRear = null;
    private DcMotor leftFront = null;
    private DcMotor rightRear = null;
    private DcMotor rightFront = null;
    private Follower follower;
    public Servo deliveryClaw = null;
    public Servo deliveryGyroLeft = null;
    public Servo deliveryGyroRight = null;
    public Servo deliveryGyro = null;
    public Servo intakeLeftGyro = null;
    public Servo intakeRightGyro = null;
    Controller controle;

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

    private enum Basket {
        HIGH,
        LOW
    }

    SetDeliveryStatus setDeliveryStatus = SetDeliveryStatus.TRANSFER.MIDDLE.SPECIMEN;
    IntakeStatus intakeStatus = IntakeStatus.TRANSFER.INTAKING;
    Basket basket = Basket.HIGH.LOW;
    private final Pose startPose = new Pose(0,0,0);


    @Override
    public void init() {
            Constants.setConstants(FConstants.class, LConstants.class);
            follower = new Follower(hardwareMap);
            follower.setStartingPose(startPose);

            //Motores
        rightElevatorDrive = hardwareMap.get(DcMotor.class, "rightElevatorDrive"); // Nome na Driver Station Deverá ser o mesmo que o nome entre ""
        leftElevatorDrive = hardwareMap.get(DcMotor.class, "leftElevatorDrive");
        intakeSliderDrive = hardwareMap.get(DcMotor.class, "intakeSliderDrive");
        intakeDrive = hardwareMap.get(DcMotor.class, "intakeDrive");
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        leftRear = hardwareMap.get(DcMotor.class, "leftRear");
        rightFront = hardwareMap.get(DcMotor.class,"rightFront");
        rightRear = hardwareMap.get(DcMotor.class, "rightRear");

        rightElevatorDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        leftElevatorDrive.setDirection(DcMotorSimple.Direction.FORWARD);

        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        leftRear.setDirection(DcMotorSimple.Direction.REVERSE);
        leftRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        rightFront.setDirection(DcMotorSimple.Direction.FORWARD);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        rightRear.setDirection(DcMotorSimple.Direction.FORWARD);
        rightRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

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
        if (controle.cross.wasJustPressed()) {
            switch (setDeliveryStatus) {
                case TRANSFER: {
                    deliveryClaw.setPosition(0.4);
                    deliveryGyro.setPosition(0.35);
                    deliveryGyroRight.setPosition(0);
                    deliveryGyroLeft.setPosition(0);

                    setDeliveryStatus = SetDeliveryStatus.MIDDLE;

                }
                break;

                case MIDDLE:
                    if (controle.cross.wasJustPressed()) {
                        deliveryGyro.setPosition(0.3);
                        deliveryGyroRight.setPosition(0);
                        deliveryGyroLeft.setPosition(0);

                        setDeliveryStatus = SetDeliveryStatus.SPECIMEN;
                    }
                    break;

                case SPECIMEN:
                    if (controle.cross.wasJustPressed()) {
                        deliveryClaw.setPosition(0.4);
                        deliveryGyro.setPosition(0.25);
                        deliveryGyroRight.setPosition(0);
                        deliveryGyroLeft.setPosition(0);
                    }

                    setDeliveryStatus = SetDeliveryStatus.TRANSFER;
            }

            if (controle.dpadUp.wasJustPressed()) {
                switch (intakeStatus) {
                    case INTAKING: {
                        intakeRightGyro.setPosition(-0.2);
                        intakeLeftGyro.setPosition(0.2);

                        intakeStatus = IntakeStatus.TRANSFER;
                    }
                    break;

                    case TRANSFER:
                        if (controle.dpadUp.wasJustPressed()) {
                            intakeRightGyro.setPosition(-0.2);
                            intakeLeftGyro.setPosition(0.2);

                            intakeStatus = IntakeStatus.INTAKING;
                        }
                        break;
                }
            }

            if (controle.leftBumper.wasJustPressed()) {
                switch (basket) {
                    case HIGH:
                    {
                        leftElevatorDrive.setTargetPosition(1900);
                        rightElevatorDrive.setTargetPosition(1900);
                        leftElevatorDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        rightElevatorDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        intakeLeftGyro.setPosition(1);
                        intakeRightGyro.setPosition(1);
                        deliveryGyro.setPosition(0.3);
                        deliveryGyroRight.setPosition(0.3);
                        deliveryGyroLeft.setPosition(0.3);

                        basket = Basket.LOW;
                    }
                    break;

                    case LOW:
                    {
                        leftElevatorDrive.setTargetPosition(450);
                        rightElevatorDrive.setTargetPosition(450);
                        leftElevatorDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        rightElevatorDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        intakeLeftGyro.setPosition(0.5);
                        intakeRightGyro.setPosition(0.5);

                        basket = Basket.HIGH;
                    }
                    break;
                    }
            }
        }

        follower.setTeleOpMovementVectors(-gamepad1.left_stick_y, -gamepad1.left_stick_x, -gamepad1.right_stick_x, false);
        follower.update();

        /* Telemetry Outputs of our Follower */
        telemetry.addData("X", follower.getPose().getX());
        telemetry.addData("Y", follower.getPose().getY());
        telemetry.addData("Heading in Degrees", Math.toDegrees(follower.getPose().getHeading()));

        /* Update Telemetry to the Driver Hub */
        telemetry.update();
    }

    @Override
    public void start() {
        follower.startTeleopDrive();
    }
}
