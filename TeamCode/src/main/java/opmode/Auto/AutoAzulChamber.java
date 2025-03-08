package opmode.Auto;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.PathBuilder;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Constants;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import hardware.Globals;
import hardware.Robot;
import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;

@Autonomous (name = "Luan Chamber", group = "Carvoeiros")
public class AutoAzulChamber extends OpMode {

    hardware.Robot Robot = new Robot(this);
    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer;

    private int pathState;

    public class GeneratedPath {

        public GeneratedPath() {
            PathBuilder builder = new PathBuilder();

            builder
                    .addPath(
                            // Line 1
                            new BezierLine(
                                    new Point(6.125, 78.000, Point.CARTESIAN),
                                    new Point(40.000, 78.000, Point.CARTESIAN)
                            )
                    )
                    .setTangentHeadingInterpolation()
                    .addPath(
                            // Line 2
                            new BezierLine(
                                    new Point(40.000, 78.000, Point.CARTESIAN),
                                    new Point(4.400, 30.000, Point.CARTESIAN)
                            )
                    )
                    .setTangentHeadingInterpolation()
                    .setReversed(true)
                    .addPath(
                            // Line 3
                            new BezierLine(
                                    new Point(4.400, 30.000, Point.CARTESIAN),
                                    new Point(40.000, 58.000, Point.CARTESIAN)
                            )
                    )
                    .setTangentHeadingInterpolation()
                    .addPath(
                            // Line 4
                            new BezierLine(
                                    new Point(40.000, 58.000, Point.CARTESIAN),
                                    new Point(4.400, 30.000, Point.CARTESIAN)
                            )
                    )
                    .setTangentHeadingInterpolation()
                    .setReversed(true)
                    .addPath(
                            // Line 5
                            new BezierLine(
                                    new Point(4.400, 30.000, Point.CARTESIAN),
                                    new Point(40.000, 58.000, Point.CARTESIAN)
                            )
                    )
                    .setTangentHeadingInterpolation()
                    .addPath(
                            // Line 6
                            new BezierLine(
                                    new Point(40.000, 58.000, Point.CARTESIAN),
                                    new Point(4.400, 30.000, Point.CARTESIAN)
                            )
                    )
                    .setTangentHeadingInterpolation()
                    .setReversed(true)
                    .addPath(
                            // Line 7
                            new BezierLine(
                                    new Point(4.400, 30.000, Point.CARTESIAN),
                                    new Point(40.000, 58.000, Point.CARTESIAN)
                            )
                    )
                    .setTangentHeadingInterpolation()
                    .addPath(
                            // Line 8
                            new BezierLine(
                                    new Point(40.000, 58.000, Point.CARTESIAN),
                                    new Point(44.000, 24.000, Point.CARTESIAN)
                            )
                    )
                    .setTangentHeadingInterpolation()
                    .addPath(
                            // Line 9
                            new BezierLine(
                                    new Point(44.000, 24.000, Point.CARTESIAN),
                                    new Point(60.000, 48.000, Point.CARTESIAN)
                            )
                    )
                    .setTangentHeadingInterpolation();
        }
    }

    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }

    @Override
    public void loop() {

        // These loop the movements of the robot
        follower.update();

        // Feedback to Driver Hub
        telemetry.addData("path state", pathState);
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.update();
    }

    /** This method is called once at the init of the OpMode. **/
    @Override
    public void init() {

        Robot.opMODE = Globals.OpModeType.AUTO;
        pathTimer = new Timer();
        opmodeTimer = new Timer();
        opmodeTimer.resetTimer();

        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap);
    }

    /** This method is called continuously after Init while waiting for "play". **/
    @Override
    public void init_loop() {}

    /** This method is called once at the start of the OpMode.
     * It runs all the setup actions, including building paths and starting the path system **/
    @Override
    public void start() {
        opmodeTimer.resetTimer();
        setPathState(0);
    }

    /** We do not use this because everything should automatically disable **/
    @Override
    public void stop() {

    }
}
