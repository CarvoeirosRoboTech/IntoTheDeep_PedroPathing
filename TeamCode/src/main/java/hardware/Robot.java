package hardware;

import static java.lang.Thread.sleep;
import static hardware.Globals.*;

import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.localization.Pose;
import com.pedropathing.localization.PoseUpdater;
import com.pedropathing.util.Constants;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;

public class Robot {
    /* Declare OpMode members. */
    private OpMode myOpMode = null;   // gain access to methods in the calling OpMode.
    public Robot(OpMode opmode) {
        myOpMode = opmode;
    }

//    public static Delivery delivery = new Delivery();

    //Drive Motors
    private DcMotor leftRear;
    private DcMotor leftFront;
    private DcMotor rightRear;
    private DcMotor rightFront;

    // Deposit Motors
    public static DcMotor leftElevatorDrive;
    public static DcMotor rightElevatorDrive;

    // Intake Motors
    public static DcMotor intakeSliderDrive;
    public static DcMotor intakeDrive;

    //Delivery Servos
    public static Servo deliveryClaw;
    public static Servo deliveryGyroLeft;
    public static Servo deliveryGyroRight;
    public static Servo deliveryGyro;

    //Intake Servos
    public static Servo intakeLeftGyro;
    public static Servo intakeRightGyro;

    //Hang Servos
    public static Servo hangRight;
    public static Servo hangLeft;

    //3rd Devices
    public RevColorSensorV3 colorSensor;
//    public Limelight3A limelight;

    //Follower
    public PoseUpdater poseUpdater;
    public Follower follower;
    private final Pose startPose = new Pose(0,0,0);

    public OpModeType opMODE = OpModeType.TELEOP;
    public SCORING scoring = SCORING.HIGH_BUCKET;
    public INTAKE intake = INTAKE.TAKING;

    public boolean isClawOpen = false;

    double position = MID_SERVO;


    public void init()    {
//        delivery.init();

        rightElevatorDrive = myOpMode.hardwareMap.get(DcMotor.class, "elevadorDireito"); //EH 0
        leftElevatorDrive = myOpMode.hardwareMap.get(DcMotor.class, "elevadorEsquerdo"); //EH 1
        intakeSliderDrive = myOpMode.hardwareMap.get(DcMotor.class, "sliderDrive");      //EH 3
        intakeDrive = myOpMode.hardwareMap.get(DcMotor.class, "intakeDrive");            //EH 2
        leftFront = myOpMode.hardwareMap.get(DcMotor.class, "leftFront");                //CH 3
        leftRear = myOpMode.hardwareMap.get(DcMotor.class, "leftRear");                  //CH 2
        rightFront = myOpMode.hardwareMap.get(DcMotor.class, "rightFront");              //CH 1
        rightRear = myOpMode.hardwareMap.get(DcMotor.class, "rightRear");                //CH 0


        rightElevatorDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        leftElevatorDrive.setDirection(DcMotorSimple.Direction.FORWARD);

        leftFront.setDirection(DcMotorSimple.Direction.FORWARD);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        leftRear.setDirection(DcMotorSimple.Direction.REVERSE);
        leftRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        rightRear.setDirection(DcMotorSimple.Direction.FORWARD);
        rightRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

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

        deliveryGyroRight.setDirection(Servo.Direction.REVERSE);

        intakeLeftGyro = myOpMode.hardwareMap.get(Servo.class, "intakeEsquerdo");          //EH Servo 1
        intakeRightGyro = myOpMode.hardwareMap.get(Servo.class, "intakeDireito");          //EH Servo 0

        hangRight = myOpMode.hardwareMap.get(Servo.class, "hangDireito");                  //CH Servo 5

        deliveryClaw.setPosition(MID_SERVO);
//        deliveryGyro.setPosition(MID_SERVO);
//        deliveryGyroRight.setPosition(MID_SERVO);
//        deliveryGyroLeft.setPosition(MID_SERVO);
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

        myOpMode.telemetry.addData("OPMODE_HARDWARE", opMODE);

        if (opMODE.equals(OpModeType.TELEOP)) {
            follower.startTeleopDrive();
            follower.setStartingPose(autoEndPose);

            setElevatorTarget(0);
            setIntakeSliderTarget(0);
            isClawOpen = true;

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

    public void setElevatorTarget(int target) {
        myOpMode.telemetry.addData("inside robot target", target);

        rightElevatorDrive.setTargetPosition(target);
        leftElevatorDrive.setTargetPosition(target);

        rightElevatorDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftElevatorDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        rightElevatorDrive.setPower(SLIDER_POWER);
        leftElevatorDrive.setPower(SLIDER_POWER);

        myOpMode.telemetry.addData("STATUS", "Moved to position");
        myOpMode.telemetry.update();
    }

    public void setIntakeSliderTarget(int target) {
        myOpMode.telemetry.addData("Given position", target);
        myOpMode.telemetry.update();

        intakeSliderDrive.setTargetPosition(target);

        intakeSliderDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        intakeSliderDrive.setPower(SLIDER_POWER);

        myOpMode.telemetry.addData("Done positioning at: ", target);
        myOpMode.telemetry.update();
    }

    public void setDeliveryClaw() {
        isClawOpen = !isClawOpen;

        if (isClawOpen) {
            deliveryClaw.setPosition(DEPOSIT_CLAW_CLOSE_POS);
            isClawOpen = false;
        } else {
            deliveryClaw.setPosition(DEPOSIT_CLAW_OPEN_POS);
            isClawOpen = true;
        }
    }

    public void setClawOpen(boolean open) {
        if (open) {
            deliveryClaw.setPosition(DEPOSIT_CLAW_OPEN_POS);
        } else {
            deliveryClaw.setPosition(DEPOSIT_CLAW_CLOSE_POS);
        }
    }

    public void setClawPos(double position) {
        deliveryGyro.setPosition(position);
    }

    public void setShoulderPos(double position) {
//        double newPos = deliveryGyroLeft.getPosition() + position;
//
        deliveryGyroRight.setPosition(position);
        deliveryGyroLeft.setPosition(position);
//        deliveryGyroLeft.setPosition(newPos);
//        deliveryGyroLeft.setPosition(newPos);


//        myOpMode.telemetry.addData("passed POS", position);
////        myOpMode.telemetry.addData("calculated POS", newPos);
//        myOpMode.telemetry.addData("leftShoulderPOS", deliveryGyroLeft.getPosition());
//        myOpMode.telemetry.addData("rightShoulderPOS", deliveryGyroRight.getPosition());
//        myOpMode.telemetry.update();
//        0.94 - shoulder; 0.495 - delivery;
//        0.28 - delivery; 0.479 - shoulder;
//        0.34 - delivery; 0.5207 - shoulder;
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

    public static RobotState robotState;

    public enum DepositPivotState {
        SCORING,
        FRONT_SPECIMEN_SCORING,
        FRONT_SPECIMEN_INTAKE,
        TRANSFER,
        READY_TRANSFER,
        MIDDLE_HOLD,
        AUTO_TOUCH_BAR
    }
    public static DepositPivotState depositPivotState;


}
