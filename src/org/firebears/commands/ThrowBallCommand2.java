package org.firebears.commands;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;
import org.firebears.Robot;
import org.firebears.RobotMap;
import org.firebears.subsystems.Arm;

/**
 * State machine command for throwing the ball.
 */
public class ThrowBallCommand2 extends Command {
    
    private int state = 0;
    private double max_speed = 2.0;
    //private double startAngle = RobotMap.BK_PICK_UP_ANGLE-10;
    private double startAngle = RobotMap.bkAngleLo-10;
    //private double releaseAngle = RobotMap.BK_PICK_UP_ANGLE - RobotMap.THROW_SWING;
    private double releaseAngle = RobotMap.angleThrowRelease;
    //private double targetAngle = releaseAngle - 30;
    private double targetAngle = RobotMap.angleThrowEnd;
    private long timeToProceed;

    //private Preferences preferences;
    
//    final String START_ANGLE = "Start_Angle";
//    final String RELEASE_ANGLE = "Release_Angle";
//    final String TARGET_ANGLE = "Target_Angle";
    
    public ThrowBallCommand2() {
        requires(Robot.arm);
        requires(Robot.vacuum);
        
//        preferences = Preferences.getInstance();
//        if(!preferences.containsKey(START_ANGLE)) {
//            preferences.putDouble(START_ANGLE, RobotMap.BK_PICK_UP_ANGLE - 10);
//            preferences.putDouble(RELEASE_ANGLE, RobotMap.BK_PICK_UP_ANGLE - RobotMap.THROW_SWING);
//            preferences.putDouble(TARGET_ANGLE, (RobotMap.BK_PICK_UP_ANGLE - RobotMap.THROW_SWING) - 30);
//            preferences.save();
//        }
    }

    /**
     * Reset all angle variables and set to the begin state.
     */
    protected void initialize() {
//        startAngle = RobotMap.BK_PICK_UP_ANGLE - 10;
//        releaseAngle = RobotMap.BK_PICK_UP_ANGLE - RobotMap.THROW_SWING;
//        targetAngle = releaseAngle - 30;
        state = 0;

//        startAngle = preferences.getDouble(START_ANGLE, startAngle);
//        releaseAngle = preferences.getDouble(RELEASE_ANGLE, releaseAngle);
//        targetAngle = preferences.getDouble(TARGET_ANGLE, targetAngle);
    }

    protected void execute() {
        long timeNow = System.currentTimeMillis();
        switch (state) {
            
            case 0 :   /* Arm goes back to start angle. */
                Robot.arm.gotoAngle(startAngle);
                if (!Robot.vacuum.isVacuumOn()) {
                    Robot.vacuum.vacuumOn(true);
                    timeToProceed = timeNow + 500;
                    state = 5;
                } else {
                    timeToProceed = timeNow;
                    state = 10;
                }
                break;
                
            case 5 :   /* Pause for vacuum to really take hold */
                if (timeNow >= timeToProceed) { 
                    state = 10;
                }
                break;
                
            case 10 :  /* Wait until arm reaches start angle. */
                if (Robot.arm.onTarget()) { 
                    Robot.arm.max_speed = this.max_speed;
                    timeToProceed = timeNow + 600;
                    state = 15;
                }
                break;
                
            case 15 :   /* Pause before throwing ball */
                if (timeNow >= timeToProceed) { 
                    state = 20;
                }
                break;
                
            case 20 :  /* Start swinging forward at maximum speed. */
                Robot.arm.gotoAngle(targetAngle);
                state = 30;
                break;
                
            case 30 :  /* Throw ball when we reach the release angle. */
                if (Robot.arm.getAngle() <= releaseAngle) {
                    Robot.vacuum.vacuumOn(false);
                    state = 40;
                }
                break;
                
            case 40 : /* End command when arm reaches target angle. */
                if (Robot.arm.onTarget()) { 
                    state = 99;
                }
                break;
                
            default :
                System.err.println("Unknown state in ThrowBallCommand2: " + state);
                state++;
        }
    }

    protected boolean isFinished() {
         return state >= 99;
    }

    protected void end() {
        state = 0;
        Robot.arm.max_speed = Arm.MAX_SPEED;
        Robot.vacuum.vacuumOn(false);
    }

    protected void interrupted() {
        end();
    }
    
}
