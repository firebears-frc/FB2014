package org.firebears.commands;

import edu.wpi.first.wpilibj.command.PIDCommand;
import org.firebears.Robot;

/**
 * Drive until we are a certain number of inches from the wall.
 */
public class ChassisDriveToWall extends PIDCommand {
    
    final double targetDistance;
    
    public ChassisDriveToWall(double inches) {
        super(0.02, 0.0, 0.0);
        targetDistance = inches;
        getPIDController().setAbsoluteTolerance(5);
    }

    protected double returnPIDInput() {
        return Robot.chassis.getDistance();
    }

    protected void usePIDOutput(double output) {
        Robot.chassis.move(output, 0.0);
    }

    protected void initialize() {
        getPIDController().setSetpoint(targetDistance);
        setTimeout(2);
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return getPIDController().onTarget() || isTimedOut();
    }

    protected void end() {
        Robot.vacuum.vacuumOn(false);
        Robot.chassis.move(0.0, 0.0);
    }

    protected void interrupted() {
        end();
    }
    
}
