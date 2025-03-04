package hardware;

import static hardware.Globals.*;

import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.localization.PoseUpdater;
import com.pedropathing.util.Constants;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;

public class Robot {
    private OpMode myOpMode = null;

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
    public Limelight3A limelight;

    //Follower
    private Follower follower;
    public PoseUpdater poseUpdater;
//    Controller controle;

//    public List<LynxModule> allHubs;
//
//    public LynxModule ControlHub;
//
//    private static Robot instance = new Robot(getInstance().myOpMode);
//    public boolean enabled;
////
//    public static Robot getInstance() {
//        if (instance == null) {
//            instance = new Robot(getInstance().myOpMode);
//        }
//        instance.enabled = true;
//        return instance;
//    }

    public Robot (OpMode opmode) {myOpMode = opmode;}

    public void init(HardwareMap hardwareMap) {


        rightElevatorDrive = hardwareMap.get(DcMotor.class, "elevadorDireito"); //EH 0
        leftElevatorDrive = hardwareMap.get(DcMotor.class, "elevadorEsquerdo"); //EH 1
        intakeSliderDrive = hardwareMap.get(DcMotor.class, "sliderDrive");      //EH 3
        intakeDrive = hardwareMap.get(DcMotor.class, "intakeDrive");            //EH 2
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");                //CH 3
        leftRear = hardwareMap.get(DcMotor.class, "leftRear");                  //CH 2
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");              //CH 1
        rightRear = hardwareMap.get(DcMotor.class, "rightRear");                //CH 0


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
        deliveryClaw = hardwareMap.get(Servo.class, "deliveryGarra");             //CH Servo 3
        deliveryGyro = hardwareMap.get(Servo.class, "deliveryGiro");              //CH Servo 2
        deliveryGyroLeft = hardwareMap.get(Servo.class, "deliveryOmbroEsquerdo"); //CH Servo 0
        deliveryGyroRight = hardwareMap.get(Servo.class, "deliveryOmbroDireito"); //CH Servo 1

        intakeLeftGyro = hardwareMap.get(Servo.class, "intakeEsquerdo");          //EH Servo 1
        intakeRightGyro = hardwareMap.get(Servo.class, "intakeDireito");          //EH Servo 0

        hangRight = hardwareMap.get(Servo.class, "hangDireito");                  //CH Servo 5
        hangLeft = hardwareMap.get(Servo.class, "hangEsquerdo");                  //CH Servo 2

        deliveryClaw.setPosition(MID_SERVO);
        deliveryGyro.setPosition(MID_SERVO);
        deliveryGyroRight.setPosition(MID_SERVO);
        deliveryGyroLeft.setPosition(MID_SERVO);
//
//        intakeRightGyro.setPosition(MID_SERVO);
//        intakeLeftGyro.setPosition(MID_SERVO);

        //Sensor de Cor
        colorSensor = hardwareMap.get(RevColorSensorV3.class, "colorSensor");
        colorSensor.enableLed(true);

//        limelight = hardwareMap.get(Limelight3A.class, "limelight");

        // Bulk reading enabled!
        // AUTO mode will bulk read by default and will redo and clear cache once the exact same read is done again
        // MANUAL mode will bulk read once per loop but needs to be manually cleared
        // Also in opModes only clear ControlHub cache as it is a hardware write
//        allHubs = hardwareMap.getAll(LynxModule.class);
//        for (LynxModule hub : allHubs) {
//            hub.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
//            if (hub.isParent() && LynxConstants.isEmbeddedSerialNumber(hub.getSerialNumber())) {
//                ControlHub = hub;
//            }
//
//        }

        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap);
        poseUpdater = new PoseUpdater(hardwareMap);

        FollowerConstants.useBrakeModeInTeleOp = true;

        follower.startTeleopDrive();


//        if (opModeType.equals(OpModeType.TELEOP)) {
//            follower.startTeleopDrive();
//            INTAKE_HOLD_SPEED = 0;
//
//
//            follower.setStartingPose(autoEndPose);
//        } else {
//            follower.setStartingPose(new Pose(0, 0, 0));
////            limelight.pipelineSwitch(1);
////            limelight.start();
//            INTAKE_HOLD_SPEED = 0.15;
//        }


    }

    public void initHasMovement() {
//        deposit.init();
//        intake.init();
//        drive.init();

        robotState = RobotState.MIDDLE_RESTING;
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
}
