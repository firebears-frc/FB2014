
package org.firebears.commands;
import edu.wpi.first.wpilibj.command.Command;
import org.firebears.Robot;
import org.firebears.RobotMap;
/**
 *
 */
public class PrepBallCatch extends Command {
    public PrepBallCatch() {
        requires(Robot.arm);
    }
    protected void initialize() {
        Robot.vacuum.vacuumOn(true);
        if(Robot.chassis.getGoingForward()){
            Robot.arm.gotoAngle(RobotMap.fwAngleCatch);
        }
        else {
            Robot.arm.gotoAngle(RobotMap.bkAngleCatch);
        }
        setTimeout(2);
    }
    protected void execute() {
    }
    protected boolean isFinished() {
        return (Math.abs(Robot.arm.m_angleSetpoint - Robot.arm.getAngle()) <= 3 )|| isTimedOut() ;//accounts for gravity comp
    }
 
    protected void end() {
        //Robot.vacuum.vacuumOn(true);
    }
    protected void interrupted() {
    }
}