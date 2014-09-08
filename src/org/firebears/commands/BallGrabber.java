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
public class BallGrabber extends Command {
    public BallGrabber() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        setTimeout(60);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
      if (Robot.vision.getCoordsx() > 350){
          Robot.chassis.move(0.0, .4);
      }
      if (Robot.vision.getCoordsx() < 335){
          Robot.chassis.move(0.0, -.4);
      }
      if (Robot.vision.getRadius() < 198 ){
          Robot.chassis.move(0.2, 0);
      }
      
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if (Robot.vision.getCoordsx() <= 345 && Robot.vision.getCoordsx() >= 340){
            if (Robot.vision.getRadius() <= 199){
                return true;
            }
        }
        
        return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
        Robot.chassis.move(0.0, 0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
