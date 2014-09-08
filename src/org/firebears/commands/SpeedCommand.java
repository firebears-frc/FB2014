package org.firebears.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.firebears.Robot;

/**
 *  set speed of arm
 */
public class SpeedCommand extends Command{
    double speed;
    
    public SpeedCommand(double p_speed) {
        requires(Robot.arm);
        speed = p_speed;
    }
    protected void initialize() {
        Robot.arm.max_speed = speed;
    }
    protected void execute() {
    }
    protected boolean isFinished() {
        return true;
    }
    protected void end() { }
    protected void interrupted() { }
}
