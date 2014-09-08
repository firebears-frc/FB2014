/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.firebears.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *wait 1/2 second then drives to wall
 */
public class ChassisDriveToWallPause extends CommandGroup {
    
    public ChassisDriveToWallPause() {
        addSequential(new VacuumOn(true));
        addSequential(new WaitCommand(1.0));
        addSequential(new ChassisDriveToWall(6));
        addSequential(new VacuumOn(false));
    }
}
