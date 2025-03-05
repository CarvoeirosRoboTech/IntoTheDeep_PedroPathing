package opmode.TeleOp;

import static hardware.Globals.MID_SERVO;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.util.Constants;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.hardware.rev.RevColorSensorV3;

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

    public Servo handRight = null;
    public Servo handLeft = null;
    public RevColorSensorV3 colorSensor = null;
    Controller controle;

    public Controller getControle() {
        return controle;
    }

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

    private enum Chamber {
        HIGH,
        LOW
    }

    SetDeliveryStatus setDeliveryStatus = SetDeliveryStatus.TRANSFER.MIDDLE.SPECIMEN;
    IntakeStatus intakeStatus = IntakeStatus.TRANSFER.INTAKING;
    Basket basket = Basket.HIGH.LOW;
    Chamber chamber = Chamber.LOW.HIGH;
    private final Pose startPose = new Pose(0, 0, 0);


    @Override
    public void init() {
        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap);
        follower.setStartingPose(startPose);

        //Motores
//        rightElevatorDrive = hardwareMap.get(DcMotor.class, "elevadorDireito"); //EH 0
//        leftElevatorDrive = hardwareMap.get(DcMotor.class, "elevadorEsquerdo"); //EH 1
//        intakeSliderDrive = hardwareMap.get(DcMotor.class, "sliderDrive");      //EH 3
//        intakeDrive = hardwareMap.get(DcMotor.class, "intakeDrive");            //EH 2
//        leftFront = hardwareMap.get(DcMotor.class, "leftFront");                //CH 3
//        leftRear = hardwareMap.get(DcMotor.class, "leftRear");                  //CH 2
//        rightFront = hardwareMap.get(DcMotor.class, "rightFront");              //CH 1
//        rightRear = hardwareMap.get(DcMotor.class, "rightRear");                //CH 0
//
//
//        rightElevatorDrive.setDirection(DcMotorSimple.Direction.FORWARD);
//        leftElevatorDrive.setDirection(DcMotorSimple.Direction.REVERSE);
//
//        leftFront.setDirection(DcMotorSimple.Direction.FORWARD);
//        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//
//        leftRear.setDirection(DcMotorSimple.Direction.REVERSE);
//        leftRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//
//        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
//        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//
//        rightRear.setDirection(DcMotorSimple.Direction.FORWARD);
//        rightRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//
//        intakeDrive.setDirection(DcMotorSimple.Direction.REVERSE);
//        intakeDrive.setDirection(DcMotorSimple.Direction.REVERSE);
//
//        leftElevatorDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        rightElevatorDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        intakeSliderDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//
//        leftElevatorDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        rightElevatorDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//
//        //Servos
//        deliveryClaw = hardwareMap.get(Servo.class, "deliveryGarra");             //CH Servo 3
//        deliveryGyro = hardwareMap.get(Servo.class, "deliveryGiro");              //CH Servo 2
//        deliveryGyroLeft = hardwareMap.get(Servo.class, "deliveryOmbroEsquerdo"); //CH Servo 0
//        deliveryGyroRight = hardwareMap.get(Servo.class, "deliveryOmbroDireito"); //CH Servo 1
//
//        intakeLeftGyro = hardwareMap.get(Servo.class, "intakeEsquerdo");          //EH Servo 1
//        intakeRightGyro = hardwareMap.get(Servo.class, "intakeDireito");          //EH Servo 0
//
//        handRight = hardwareMap.get(Servo.class, "hangDireito");                  //CH Servo 5
//        handLeft = hardwareMap.get(Servo.class, "hangEsquerdo");                  //CH Servo 2

        deliveryClaw.setPosition(MID_SERVO);
//        deliveryGyro.setPosition(MID_SERVO);
//        deliveryGyroRight.setPosition(MID_SERVO);
//        deliveryGyroLeft.setPosition(MID_SERVO);
//
//        intakeRightGyro.setPosition(MID_SERVO);
//        intakeLeftGyro.setPosition(MID_SERVO);

        //Sensor de Cor
        colorSensor = hardwareMap.get(RevColorSensorV3.class, "colorSensor");
        colorSensor.enableLed(true);

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
                    case HIGH: {
                        leftElevatorDrive.setTargetPosition(1900);
                        rightElevatorDrive.setTargetPosition(1900);
                        leftElevatorDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        rightElevatorDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        intakeLeftGyro.setPosition(1);
                        intakeRightGyro.setPosition(1);
                        deliveryGyro.setPosition(0.3);
                        deliveryGyroRight.setPosition(0.3);
                        deliveryGyroLeft.setPosition(0.3);

                        basket = Basket.HIGH;
                    }
                    case LOW: {
                        leftElevatorDrive.setTargetPosition(450);
                        rightElevatorDrive.setTargetPosition(450);
                        leftElevatorDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        rightElevatorDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        deliveryGyroRight.setPosition(0);
                        deliveryGyroLeft.setPosition(0);

                        basket = Basket.LOW;
                    }
                }

                if (controle.dpadUp.wasJustPressed()) {
                    basket = Basket.HIGH;
                }

                if (controle.dpadDown.wasJustPressed()) {
                    basket = Basket.LOW;
                }
            }

            follower.setTeleOpMovementVectors(-gamepad1.left_stick_y, -gamepad1.left_stick_x, -gamepad1.right_stick_x, true);
            follower.update();

            /* Telemetry Outputs of our Follower */
            telemetry.addData("X", follower.getPose().getX());
            telemetry.addData("Y", follower.getPose().getY());
            telemetry.addData("Heading in Degrees", Math.toDegrees(follower.getPose().getHeading()));

            telemetry.addData("", "------------");
            telemetry.addData("Current Basket", basket);

            /* Update Telemetry to the Driver Hub */
            telemetry.update();
        }

        if (controle.circle.wasJustPressed()) {
            double pos = deliveryClaw.getPosition();
            deliveryClaw.setPosition(pos - 0.05);
            pos = deliveryClaw.getPosition();

            telemetry.addData("POS GARRA:", pos);
            telemetry.update();

        }

        if (controle.triangle.wasJustPressed()) {
            double pos = deliveryClaw.getPosition();
            deliveryClaw.setPosition(pos + 0.05);
            pos = deliveryClaw.getPosition();

            telemetry.addData("POS GARRA:", pos);
            telemetry.update();

        }

        if (controle.square.wasJustPressed()) {
            deliveryClaw.setPosition(MID_SERVO);
        }
    }
        @Override
        public void start () {
            follower.startTeleopDrive();
            controle = new Controller(gamepad1);
            telemetry.addData("Status", "START");
        }
    }