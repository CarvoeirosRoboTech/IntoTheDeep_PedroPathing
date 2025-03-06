package opmode.Auto;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.PathBuilder;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Constants;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;

@Autonomous (name = "João Basket Azul", group = "Carvoeiros")
public class AutoAzulBasket extends OpMode {

    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer;

    private int pathState;
    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }

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
                    .setReversed(true).build();
            builder.addPath(
                            new BezierCurve(
                                    new Point(38.000, 78.000, Point.CARTESIAN),
                                    new Point(22.000, 78.000, Point.CARTESIAN),
                                    new Point(42.426, 121.539, Point.CARTESIAN),
                                    new Point(22.461, 123.286, Point.CARTESIAN)
                            )
                    )
                    .setTangentHeadingInterpolation().build();
            builder.addPath(
                            // Line 3
                            new BezierCurve(
                                    new Point(22.461, 123.286, Point.CARTESIAN),
                                    new Point(15.972, 123.785, Point.CARTESIAN),
                                    new Point(12.977, 128.776, Point.CARTESIAN)
                            )
                    )
                    .setTangentHeadingInterpolation().build();

            builder.addPath(
                            // Line 4
                            new BezierLine(
                                    new Point(12.977, 128.776, Point.CARTESIAN),
                                    new Point(23.958, 130.773, Point.CARTESIAN)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(123), Math.toRadians(180)).build();

            builder.addPath(
                            // Line 5
                            new BezierLine(
                                    new Point(23.958, 130.773, Point.CARTESIAN),
                                    new Point(14.974, 130.024, Point.CARTESIAN)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(135))
                    .setReversed(true).build();

            builder.addPath(
                            // Line 6
                            new BezierLine(
                                    new Point(14.974, 130.024, Point.CARTESIAN),
                                    new Point(23.709, 132.520, Point.CARTESIAN)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(135), Math.toRadians(-155)).build();

            builder.addPath(
                            // Line 7
                            new BezierLine(
                                    new Point(23.709, 132.520, Point.CARTESIAN),
                                    new Point(14.974, 128.776, Point.CARTESIAN)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(-155), Math.toRadians(135)).build();

            builder.addPath(
                            // Line 8
                            new BezierCurve(
                                    new Point(14.974, 128.776, Point.CARTESIAN),
                                    new Point(63.706, 117.899, Point.CARTESIAN),
                                    new Point(62.157, 94.894, Point.CARTESIAN)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(135), Math.toRadians(-90)).build();

        }
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
