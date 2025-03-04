package hardware;

import static hardware.Globals.*;

import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.localization.Pose;
import com.pedropathing.localization.PoseUpdater;
import com.pedropathing.util.Constants;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;

public class RobotHardware {

    /* Declare OpMode members. */
    private OpMode myOpMode = null;   // gain access to methods in the calling OpMode.
    public RobotHardware (OpMode opmode) {
        myOpMode = opmode;
    }

    //Drive Motors
    private DcMotor leftRear;
    private DcMotor leftFront;
    private DcMotor rightRear;
    private DcMotor rightFront;

    // Deposit Motors
    public DcMotor leftElevatorDrive;
    public DcMotor rightElevatorDrive;

    // Intake Motors
    public DcMotor intakeSliderDrive;
    public DcMotor intakeDrive;

    //Delivery Servos
    public Servo deliveryClaw;
    public Servo deliveryGyroLeft;
    public Servo deliveryGyroRight;
    public Servo deliveryGyro;

    //Intake Servos
    public Servo intakeLeftGyro;
    public Servo intakeRightGyro;

    //Hang Servos
    public Servo hangRight;
    public Servo hangLeft;

    //3rd Devices
    public RevColorSensorV3 colorSensor;
//    public Limelight3A limelight;

    //Follower
    private Follower follower;
    public PoseUpdater poseUpdater;
    private final Pose startPose = new Pose(0,0,0);

    public void init()    {
        rightElevatorDrive = myOpMode.hardwareMap.get(DcMotor.class, "elevadorDireito"); //EH 0
        leftElevatorDrive = myOpMode.hardwareMap.get(DcMotor.class, "elevadorEsquerdo"); //EH 1
        intakeSliderDrive = myOpMode.hardwareMap.get(DcMotor.class, "sliderDrive");      //EH 3
        intakeDrive = myOpMode.hardwareMap.get(DcMotor.class, "intakeDrive");            //EH 2
        leftFront = myOpMode.hardwareMap.get(DcMotor.class, "leftFront");                //CH 3
        leftRear = myOpMode.hardwareMap.get(DcMotor.class, "leftRear");                  //CH 2
        rightFront = myOpMode.hardwareMap.get(DcMotor.class, "rightFront");              //CH 1
        rightRear = myOpMode.hardwareMap.get(DcMotor.class, "rightRear");                //CH 0


        rightElevatorDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        leftElevatorDrive.setDirection(DcMotorSimple.Direction.REVERSE);

        leftFront.setDirection(DcMotorSimple.Direction.FORWARD);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        leftRear.setDirection(DcMotorSimple.Direction.REVERSE);
        leftRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
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
        deliveryClaw = myOpMode.hardwareMap.get(Servo.class, "deliveryGarra");             //CH Servo 3
        deliveryGyro = myOpMode.hardwareMap.get(Servo.class, "deliveryGiro");              //CH Servo 2
        deliveryGyroLeft = myOpMode.hardwareMap.get(Servo.class, "deliveryOmbroEsquerdo"); //CH Servo 0
        deliveryGyroRight = myOpMode.hardwareMap.get(Servo.class, "deliveryOmbroDireito"); //CH Servo 1

        intakeLeftGyro = myOpMode.hardwareMap.get(Servo.class, "intakeEsquerdo");          //EH Servo 1
        intakeRightGyro = myOpMode.hardwareMap.get(Servo.class, "intakeDireito");          //EH Servo 0

        hangRight = myOpMode.hardwareMap.get(Servo.class, "hangDireito");                  //CH Servo 5
        hangLeft = myOpMode.hardwareMap.get(Servo.class, "hangEsquerdo");                  //CH Servo 2

        deliveryClaw.setPosition(MID_SERVO);
        deliveryGyro.setPosition(MID_SERVO);
        deliveryGyroRight.setPosition(MID_SERVO);
        deliveryGyroLeft.setPosition(MID_SERVO);
//
//        intakeRightGyro.setPosition(MID_SERVO);
//        intakeLeftGyro.setPosition(MID_SERVO);

        //Sensor de Cor
        colorSensor = myOpMode.hardwareMap.get(RevColorSensorV3.class, "colorSensor");
        colorSensor.enableLed(true);

//        limelight = hardwareMap.get(Limelight3A.class, "limelight");


        //Drive Methods, choose between drive methods based on OpMode;
        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(myOpMode.hardwareMap);

        FollowerConstants.useBrakeModeInTeleOp = true;
        poseUpdater = new PoseUpdater(myOpMode.hardwareMap);

        if (opModeType.equals(opModeType.TELEOP)) {
            follower.startTeleopDrive();

            follower.setStartingPose(autoEndPose);

            myOpMode.telemetry.addData(">", "Drive Initialized TELEOP");
            myOpMode.telemetry.update();
        } else {
            follower.setStartingPose(new Pose(0,0,0));
            myOpMode.telemetry.addData(">", "Drive Initialized AUTO");
            myOpMode.telemetry.update();
        }

        myOpMode.telemetry.addData(">", "Hardware Initialized");
        myOpMode.telemetry.update();

    }

    public enum RobotState {
        MIDDLE_RESTING,
        TRANSFERRED,
        SCORING,
        INTAKING,
        EJECTING,
        SPECIMEN_INTAKING,
        SPECIMEN_SCORING
    }

    public static Robot.RobotState robotState;
}
