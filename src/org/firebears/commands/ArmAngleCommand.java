
package org.firebears.commands;
import edu.wpi.first.wpilibj.command.Command;
import org.firebears.Robot;
/**
 *
 */
public class ArmAngleCommand extends Command {
    double targetAngle;
    public ArmAngleCommand(double setAngle) {
        requires(Robot.arm);
        targetAngle = setAngle;
    }
    protected void initialize() {
        Robot.arm.gotoAngle(targetAngle);
        setTimeout(2);
    }
    protected void execute() {
    }
    protected boolean isFinished() {
       // return Robot.arm.onTarget();
        return (Math.abs(Robot.arm.m_angleSetpoint - Robot.arm.getAngle()) <= 3 )|| isTimedOut() ;//accounts for gravity comp
        
    }
 
    protected void end() {
    }
    protected void interrupted() {
    }
}

