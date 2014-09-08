package org.firebears.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.firebears.Robot;

public class AutonomousCommand extends CommandGroup {

    public AutonomousCommand() {
        requires(Robot.arm);
        requires(Robot.vacuum);
        addSequential(new VacuumOn(true));
        addSequential(new ChassisDrivetoDist(155.0));
        //addSequential(new VisionLiveGoal());
//-------------------------------------------------------------------------
//        if (Robot.vision.getRight() == true) {
//            addSequential(new ChassisRotate("right"));
//        } else if (Robot.vision.getLeft() == true) {
//            addSequential(new ChassisRotate("left"));
//        } else {
//        }
//**************************************************************************         
        addSequential(new ChassisDrivetoDist(5.6));
//**************************************************************************         
//        if (Robot.vision.getRight() == true) {
//            addSequential(new ChassisRotate("left"));
//        } else if (Robot.vision.getLeft() == true) {
//            addSequential(new ChassisRotate("right"));
//        }
//-------------------------------------------------------------------------       
        addSequential(new ArmAngleCommand(110.0));
        addSequential(new MoveDirectionTime(2.4, 1, 0));
        addSequential(new VacuumOn(false));
        addSequential(new MoveDirectionTime(2.0, -1, 0));
        addSequential(new ArmAngleCommand(180.0));
        //addSequential(new ThrowBallCommand2());
    }
}
