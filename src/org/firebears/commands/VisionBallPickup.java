/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.firebears.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.firebears.Robot;

/**
 *
 * @author javad
 */
public class VisionBallPickup extends CommandGroup {
    public VisionBallPickup() {
        requires(Robot.arm);
        requires(Robot.vacuum);
        addSequential(new BallGrabber());
        addSequential(new VacuumOn(true));
    //    addSequential(new PrepBallPickup2());
        addSequential(new MoveDirectionTime(1.0, 1,1));
       // addSequential(new StowBall(180));
    }
}