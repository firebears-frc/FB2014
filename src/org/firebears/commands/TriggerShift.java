package org.firebears.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.firebears.Robot;

/**
 *
 */
public class TriggerShift extends Command {

    public TriggerShift() {
    }

    protected void initialize() {
        Robot.chassis.chassisShiftDown();
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        Robot.chassis.chassisShiftUp();
    }

    protected void interrupted() {
        Robot.chassis.chassisShiftUp();
    }
}