package org.firebears;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.firebears.commands.*;
import org.firebears.subsystems.*;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
    Command autonomousCommand;
    long count = 0;
    public static OI oi;
    
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public static Arm arm;
    public static Vacuum vacuum;
    public static Chassis chassis;
    public static Lights lights;
    public static Vision vision;
    public static Diagnostics diagnostics;
    public static PreferenceSetup preferenceSetup;
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        diagnostics = new Diagnostics();


        RobotMap.init();
        //preferenceSetup = new PreferenceSetup();
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        arm = new Arm();
        vacuum = new Vacuum();
        chassis = new Chassis();
        lights = new Lights();
        vision = new Vision();
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        vision.networkTable.putBoolean(Vision.AUTONOMOUS, false);
        vision.networkTable.putBoolean(Vision.TELEOPERATED, false);
        // This MUST be here. If the OI creates Commands (which it very likely
        // will), constructing it during the construction of CommandBase (from
        // which commands extend), subsystems are not guaranteed to be
        // yet. Thus, their requires() statements may grab null pointers. Bad
        // news. Don't move it.
        oi = new OI();
        // instantiate the command used for the autonomous period
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS
   //     autonomousCommand = new ChassisDriveToWall(48);
        autonomousCommand = new ChassisDriveToWallPause();
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS
        DriverStation ds = DriverStation.getInstance();
        if (ds.getAlliance().equals(DriverStation.Alliance.kBlue)) {
            System.out.println("Welcome to the Blue Alliance");
        } else {
            System.out.println("Welcome to the Red Alliance");
        }
        diagnostics.updateDisplay();
    }
    
    public void autonomousInit() {
        // schedule the autonomous command (example)
        Robot.arm.gotoAngle(RobotMap.angleStowHi);
        if (autonomousCommand != null) {
            autonomousCommand.start();
        }
       // vision.networkTable.putBoolean(Vision.AUTONOMOUS, true);
       // vision.networkTable.putBoolean(Vision.TELEOPERATED, false);
       SmartDashboard.putNumber("Chassis distance", chassis.getDistance());
    }
    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        if ((count++) % 100 == 0) { diagnostics.updateDisplay(); }
    }
    
    public void teleopInit() {
        Robot.arm.gotoAngle(RobotMap.angleStowHi);
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) {
            autonomousCommand.cancel();
        }
        vision.networkTable.putBoolean(Vision.AUTONOMOUS, false);
        vision.networkTable.putBoolean(Vision.TELEOPERATED, true);
        Robot.arm.resetMinMax();
    }
    
    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();

        if ((count++) % 100 == 0) { diagnostics.updateDisplay(); diagnostics.setInt(arm.getAngle(),RobotMap.DiagnosticsZoneFour);}//also added angle to I2C
        //SmartDashboard.putNumber("Arm angle:", arm.getAngle());
        SmartDashboard.putNumber("Arm Setpoint:", arm.getSetpoint());
        if (RobotMap.DEBUG) {
            SmartDashboard.putNumber("Chassis distance", chassis.getDistance());
            SmartDashboard.putNumber("fwAngleLo::", RobotMap.fwAngleLo);
            SmartDashboard.putNumber("fwAngleCatch::", RobotMap.fwAngleCatch);
            SmartDashboard.putNumber("fwAngleStowLo::", RobotMap.fwAngleStowLo);
            SmartDashboard.putNumber("angleStowHi::", RobotMap.angleStowHi);
            SmartDashboard.putNumber("fwAngleLoScore::", RobotMap.fwAngleLoScore);
            SmartDashboard.putNumber("fwAnglePassHi::", RobotMap.fwAnglePassHi);
            SmartDashboard.putNumber("angleThrowEnd::", RobotMap.angleThrowEnd);
            SmartDashboard.putNumber("angleThrowRelease::", RobotMap.angleThrowRelease);
            SmartDashboard.putNumber("bkAngleLo::", RobotMap.bkAngleLo);
            SmartDashboard.putNumber("bkAngleCatch::", RobotMap.bkAngleCatch);
            SmartDashboard.putNumber("bkAngleStowLo::", RobotMap.bkAngleStowLo);
            SmartDashboard.putNumber("bkAngleLoScore::", RobotMap.bkAngleLoScore);
            SmartDashboard.putNumber("bkAnglePassHi::", RobotMap.bkAnglePassHi);
            SmartDashboard.putNumber("Arm Volts 90::", RobotMap.armVolts90);
            SmartDashboard.putNumber("Arm Volts 270::", RobotMap.armVolts270);
            SmartDashboard.putBoolean("forward:", Robot.chassis.getGoingForward());
            try{
                SmartDashboard.putNumber("Vaccuum current:", RobotMap.vacuumVacuumJag.getOutputCurrent());
            }catch( Exception e) {
                
            }
        }
        SmartDashboard.putBoolean("PID Drive Status", RobotMap.isPIDDrive);
        SmartDashboard.putBoolean("Arm Calibrate mode", RobotMap.isArmCalibrateMode);
        SmartDashboard.putNumber("noBallVacuumCurrent",RobotMap.noBallVacuumCurrent);
        SmartDashboard.putNumber("attachedBallVacuumCurrent",RobotMap.attachedBallVacuumCurrent);
//RobotMap.fwAngleLo

//can jaguar amperage 
//        try {
//            if (RobotMap.DEBUG && RobotMap.armArmJagOne!=null && RobotMap.armArmJagTwo!=null) {
//                System.out.print("Arm Jag one: " + RobotMap.armArmJagOne.getOutputCurrent());
//                System.out.println("Arm Jag Two: " + RobotMap.armArmJagTwo.getOutputCurrent());
//            }
//        }//print amps from jag one and two
//        catch (CANTimeoutException ex) {
//            ex.printStackTrace();
//        }
}

    /**
     * This function called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
        if ((count++) % 100 == 0) { diagnostics.updateDisplay(); }
    }
    
    public void testInit() {
        Robot.arm.gotoAngle(RobotMap.angleStowHi);
        vision.networkTable.putBoolean(Vision.AUTONOMOUS, false);
        vision.networkTable.putBoolean(Vision.TELEOPERATED, false);
    }
    
    public void disabledInit() {
        vision.networkTable.putBoolean(Vision.AUTONOMOUS, false);
        vision.networkTable.putBoolean(Vision.TELEOPERATED, false);
    }
    
    public void disabledPeriodic() {
        if ((count++) % 100 == 0) { diagnostics.updateDisplay(); }
        
    }
    
}