/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.firebears.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.firebears.Robot;
import org.firebears.subsystems.Vision;

/**
 *
 * @author javad
 */
public class VisionLiveGoal extends Command {
    
    public VisionLiveGoal() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        Robot.vision.networkTable.putBoolean(Vision.AUTONOMOUS, true);
        setTimeout(3);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if (Robot.vision.getLivegoal() == true){
            return true;
        }
        return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
