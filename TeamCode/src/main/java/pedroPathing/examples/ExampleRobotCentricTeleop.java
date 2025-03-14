package pedroPathing.examples;

import static hardware.Globals.FRONT_HIGH_SPECIMEN_HEIGHT;
import static hardware.Globals.HIGH_BUCKET_HEIGHT;
import static hardware.Globals.LOW_BUCKET_HEIGHT;
import static hardware.Globals.MID_SERVO;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.util.Constants;
import  com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import hardware.Robot;
import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;

/**
 * This is an example teleop that showcases movement and robot-centric driving.
 *
 * @author Baron Henderson - 20077 The Indubitables
 * @version 2.0, 12/30/2024
 */

@TeleOp(name = "Example Robot-Centric Teleop", group = "Examples")
public class ExampleRobotCentricTeleop extends OpMode {

    public Servo Garra = null;
    public DcMotor leftElevatorDrive = null;
    public DcMotor rightElevatorDrive = null;
    public DcMotor intakeSliderDrive = null;

    public enum SCORING {
        HIGH_BUCKET,
        LOW_BUCKET,
        HIGH_SPECIMEN
    }

    SCORING SCORING_STATUS = SCORING.HIGH_BUCKET;

    private Follower follower;
    private final Pose startPose = new Pose(0,0,0);

    /** This method is call once when init is played, it initializes the follower **/
    @Override
    public void init() {
        Constants.setConstants(FConstants.class,LConstants.class);
        follower = new Follower(hardwareMap);
        follower.setStartingPose(startPose);

        rightElevatorDrive = hardwareMap.get(DcMotor.class, "elevadorDireito"); //EH 0
        leftElevatorDrive = hardwareMap.get(DcMotor.class, "elevadorEsquerdo"); //EH 1
        intakeSliderDrive = hardwareMap.get(DcMotor.class, "sliderDrive");      //EH 3



        rightElevatorDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        leftElevatorDrive.setDirection(DcMotorSimple.Direction.REVERSE);

        intakeSliderDrive.setDirection(DcMotorSimple.Direction.REVERSE);

        leftElevatorDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightElevatorDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        intakeSliderDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        leftElevatorDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightElevatorDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        intakeSliderDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftElevatorDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightElevatorDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        intakeSliderDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        Garra = hardwareMap.get(Servo.class, "deliveryGarra");

        Garra.setPosition(MID_SERVO);
    }

    /** This method is called continuously after Init while waiting to be started. **/
    @Override
    public void init_loop() {
    }

    /** This method is called once at the start of the OpMode. **/
    @Override
    public void start() {
        follower.startTeleopDrive();
    }

    double VEL = 0.8;

    /** This is the main loop of the opmode and runs continuously after play **/
    @Override
    public void loop() {


        /* Update Pedro to move the robot based on:
        - Forward/Backward Movement: -gamepad1.left_stick_y
        - Left/Right Movement: -gamepad1.left_stick_x
        - Turn Left/Right Movement: -gamepad1.right_stick_x
        - Robot-Centric Mode: true
        */

        if (gamepad1.triangle) {
            switch (SCORING_STATUS) {
                case HIGH_BUCKET: {
                    setDeliveryElevatorTarget((int) HIGH_BUCKET_HEIGHT);
                    break;
                }
                case LOW_BUCKET: {
                    setDeliveryElevatorTarget((int) LOW_BUCKET_HEIGHT);
                    break;
                }
                case HIGH_SPECIMEN: {
                    setDeliveryElevatorTarget((int) FRONT_HIGH_SPECIMEN_HEIGHT);
                    break;
                }
            }
        }

        if (gamepad1.cross) {
            Garra.setPosition(1);
        }

        if (gamepad1.circle) {
            Garra.setPosition(0);
        }

        if (gamepad1.square) {
//            Garra.setPosition(MID_SERVO);
            SCORING_STATUS = SCORING.HIGH_BUCKET;
        }

        if (gamepad1.dpad_up) {
//            intakeSliderDrive.setTargetPosition((int) MAX_EXTENDO_EXTENSION);
//            intakeSliderDrive.setPower(VEL);
//            intakeSliderDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            SCORING_STATUS = SCORING.LOW_BUCKET;
        }

        if (gamepad1.dpad_down) {
//            intakeSliderDrive.setTargetPosition((int) MIN_EXTENDO_EXTENSION);
//            intakeSliderDrive.setPower(VEL);
//            intakeSliderDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            SCORING_STATUS = SCORING.HIGH_SPECIMEN;
        }

        if (gamepad1.right_bumper) {
//            rightElevatorDrive.setTargetPosition(rightElevatorDrive.getCurrentPosition()+100);
//            leftElevatorDrive.setTargetPosition(leftElevatorDrive.getCurrentPosition()+100);
//            rightElevatorDrive.setTargetPosition(-600);
//            leftElevatorDrive.setTargetPosition(-600);

            rightElevatorDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftElevatorDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            rightElevatorDrive.setPower(0.3);
            leftElevatorDrive.setPower(0.3);
        }

        if (gamepad1.left_bumper) {
            rightElevatorDrive.setTargetPosition(rightElevatorDrive.getCurrentPosition()-100);
            leftElevatorDrive.setTargetPosition(leftElevatorDrive.getCurrentPosition()-100);

            rightElevatorDrive.setTargetPosition(-1200);
            leftElevatorDrive.setTargetPosition(-1200);

            rightElevatorDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftElevatorDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            rightElevatorDrive.setPower(0.8);
            leftElevatorDrive.setPower(0.8);
        }

        if (gamepad1.dpad_up) {
            intakeSliderDrive.setTargetPosition(intakeSliderDrive.getCurrentPosition()-10);
        }
        follower.setTeleOpMovementVectors(-gamepad1.left_stick_y, -gamepad1.left_stick_x, -gamepad1.right_stick_x, true);
        follower.update();

        /* Telemetry Outputs of our Follower */
        telemetry.addData("X", follower.getPose().getX());
        telemetry.addData("Y", follower.getPose().getY());
        telemetry.addData("Heading in Degrees", Math.toDegrees(follower.getPose().getHeading()));

        telemetry.addData("EL. E. POS:", leftElevatorDrive.getCurrentPosition());
        telemetry.addData("EL. D. POS:", rightElevatorDrive.getCurrentPosition());
        telemetry.addData("Intake Slider POS:", intakeSliderDrive.getCurrentPosition());

        /* Update Telemetry to the Driver Hub */
        telemetry.update();

    }

    /** We do not use this because everything automatically should disable **/
    @Override
    public void stop() {
    }

// 0.68 shoulder
    public void setDeliveryElevatorTarget(int target) {
        rightElevatorDrive.setTargetPosition(target);
        leftElevatorDrive.setTargetPosition(target);

        rightElevatorDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftElevatorDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        rightElevatorDrive.setPower(0.6);
        leftElevatorDrive.setPower(0.6);
    }
}