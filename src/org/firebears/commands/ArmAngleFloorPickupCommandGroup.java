package org.firebears.commands;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;
import org.firebears.Robot;
import org.firebears.RobotMap;
import org.firebears.subsystems.Arm;

/**
 * State machine command for moving arm to the floor.
 */
public class ArmAngleFloorPickupCommandGroup extends Command {

    private int state = 0;
    private long timeToProceed;
    private long timeOut;
    private double angle1;
    private double angle2;
    private double targetAngle; 

    public ArmAngleFloorPickupCommandGroup() {
        requires(Robot.arm);
        requires(Robot.vacuum);

    }

    /**
     * Reset all angle variables and set to the begin state.
     */
    protected void initialize() {
        state = 0;
        Robot.vacuum.vacuumOn(true);
        Robot.arm.max_speed = Arm.MAX_SPEED;
        if (Robot.chassis.getGoingForward()) {
            angle1 = RobotMap.fwAngleLoScore;
            angle2 = RobotMap.fwAngleLo;
        } else {
            angle1 = RobotMap.bkAngleLoScore;
            angle2 = RobotMap.bkAngleLo;
        }
    }

    protected void execute() {
        long timeNow = System.currentTimeMillis();
        switch (state) {

            case 0:

                Robot.arm.gotoAngle(angle1);
                targetAngle = angle1;
                timeOut = timeNow + 2000;
                state = 10;
                break;

            case 10:  /* Wait until arm reaches target angle. */

                if ((Math.abs(targetAngle - Robot.arm.getAngle()) <= 3) || timeNow > timeOut) {
                    timeToProceed = timeNow + 100;
                    state = 15;
                }
                break;

            case 15:   /* Pause  */

                if (timeNow >= timeToProceed) {
                    Robot.arm.gotoAngle(angle2);
                    targetAngle = angle2;
                    timeOut = timeNow + 2000;
                    Robot.arm.max_speed = 0.10;
                    state = 20;
                }
                break;

            case 20: /* Wait until arm reaches target angle. */

                if ((Math.abs(targetAngle - Robot.arm.getAngle()) <= 3) || timeNow > timeOut) {
                    state = 99;
                }
                break;

            default:
                System.err.println("Unknown state in ArmAngleFloorPickupCommandGroup: " + state);
                state++;
        }
    }

    protected boolean isFinished() {
        return state >= 99;
    }

    protected void end() {
        state = 0;
        Robot.arm.max_speed = Arm.MAX_SPEED;
    }

    protected void interrupted() {
        end();
    }

}
