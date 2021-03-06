package org.firebears;

import org.firebears.commands.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    public JoystickButton driverTriggerShift;
    public JoystickButton armTriggerCommand;
    public JoystickButton floorPickupPrep;
    public JoystickButton prepBallCatch;
    public JoystickButton stowHi;
    public JoystickButton driverStowHi;
    public JoystickButton stowBridge;
    public JoystickButton passHiAngle;
    public JoystickButton passFloorAngle;
    public JoystickButton passLoScoreAngle;
    public JoystickButton releaseBall;
    public JoystickButton setHiPassAngle;
    public JoystickButton ballthrow;
    //public JoystickButton prepBallPickup;
    
    
    //public JoystickButton vacuumOn;
    //public JoystickButton vacuumOff;
    //public JoystickButton vacuumToggle;
    public Joystick joystickDrive; // nameing?
    public Joystick joystickArm;
    

    public OI() {

        joystickDrive = new Joystick(1);
        joystickArm = new Joystick(2);
        
        driverTriggerShift = new JoystickButton(joystickDrive, 1);
        driverTriggerShift.whileHeld(new TriggerShift());
        
        driverStowHi = new JoystickButton(joystickDrive, 3);
        driverStowHi.whenPressed(new StowBall("hi"));
        
        armTriggerCommand = new JoystickButton(joystickArm, 1);
        armTriggerCommand.whileHeld(new ArmJoystickCommand());
        
        floorPickupPrep = new JoystickButton(joystickArm, 2);
        floorPickupPrep.whenPressed(new ArmAngleFloorPickupCommandGroup());
        
        prepBallCatch = new JoystickButton(joystickArm, 3);
        prepBallCatch.whenPressed(new PrepBallCatch());
        
        stowHi = new JoystickButton(joystickArm, 4);
        stowHi.whenPressed(new StowBall("hi"));
        
        stowBridge = new JoystickButton(joystickArm, 5);
        stowBridge.whenPressed(new StowBall("lo"));
        
        passHiAngle = new JoystickButton(joystickArm, 6);
        passHiAngle.whenPressed(new ArmPassAngleCommand("hi"));
        
        passLoScoreAngle = new JoystickButton(joystickArm, 8);
        passLoScoreAngle.whenPressed(new PreferenceSetup(RobotMap.FW_ANGLE_PASS_HI)); 
        
        passFloorAngle = new JoystickButton(joystickArm, 7);
        passFloorAngle.whenPressed(new ArmAngleFloorCommandGroup());
        
        passLoScoreAngle = new JoystickButton(joystickArm, 11);
        passLoScoreAngle.whenPressed(new ArmPassAngleCommand("med"));
        
        releaseBall = new JoystickButton(joystickArm, 10);
        releaseBall.whenPressed(new VacuumOn(false));
  
        ballthrow = new JoystickButton(joystickArm, 9);
        ballthrow.whenPressed(new ThrowBallCommand2());
        
 

        // SmartDashboard Buttons
        if (RobotMap.DEBUG) {
   //         SmartDashboard.putData("Autonomous Command", new AutonomousCommand());
            SmartDashboard.putData("ShiftUpCommand", new ShiftUpCommand());
            SmartDashboard.putData("ShiftDownCommand", new ShiftDownCommand());
            SmartDashboard.putData("fwAngleLoTest", new ArmAngleFloorPickupCommandGroup());//Test
            SmartDashboard.putData("fwAngleStowLoTest", new ArmAngleCommand(RobotMap.fwAngleStowLo));//Test
            SmartDashboard.putData("AngleStowHiTest", new ArmAngleCommand(RobotMap.angleStowHi));//Test
            SmartDashboard.putData("bkAngleLoTest", new ArmAngleCommand(RobotMap.bkAngleLo));//Test
            SmartDashboard.putData("vacuum on",new VacuumOn(true));
            SmartDashboard.putData("vacuum off",new VacuumOn(false));
            SmartDashboard.putData("prepBallCatch",new PrepBallCatch());
            
            //preference setup buttons
            SmartDashboard.putData(RobotMap.FW_ANGLE_LO,new PreferenceSetup(RobotMap.FW_ANGLE_LO));
            SmartDashboard.putData(RobotMap.FW_ANGLE_CATCH,new PreferenceSetup(RobotMap.FW_ANGLE_CATCH));
            SmartDashboard.putData(RobotMap.FW_ANGLE_STOW_LO,new PreferenceSetup(RobotMap.FW_ANGLE_STOW_LO));
            SmartDashboard.putData(RobotMap.ANGLE_STOW_HI,new PreferenceSetup(RobotMap.ANGLE_STOW_HI));           
            SmartDashboard.putData(RobotMap.FW_ANGLE_LO_SCORE,new PreferenceSetup(RobotMap.FW_ANGLE_LO_SCORE));
            SmartDashboard.putData(RobotMap.FW_ANGLE_PASS_HI,new PreferenceSetup(RobotMap.FW_ANGLE_PASS_HI));          
            SmartDashboard.putData(RobotMap.ANGLE_THROW_END,new PreferenceSetup(RobotMap.ANGLE_THROW_END));
            SmartDashboard.putData(RobotMap.ANGLE_THROW_RELEASE,new PreferenceSetup(RobotMap.ANGLE_THROW_RELEASE));
            SmartDashboard.putData(RobotMap.BK_ANGLE_LO,new PreferenceSetup(RobotMap.BK_ANGLE_LO));
            SmartDashboard.putData(RobotMap.BK_ANGLE_CATCH,new PreferenceSetup(RobotMap.BK_ANGLE_CATCH));           
            SmartDashboard.putData(RobotMap.BK_ANGLE_STOW_LO,new PreferenceSetup(RobotMap.BK_ANGLE_STOW_LO));
            SmartDashboard.putData(RobotMap.BK_ANGLE_LO_SCORE,new PreferenceSetup(RobotMap.BK_ANGLE_LO_SCORE));
            SmartDashboard.putData(RobotMap.BK_ANGLE_PASS_HI,new PreferenceSetup(RobotMap.BK_ANGLE_PASS_HI));
            SmartDashboard.putData("Arm Calibrate Mode", new ChassisModeSetup(RobotMap.IS_ARM_CALIBRATE_MODE,true));
            SmartDashboard.putData("Arm Normal Mode", new ChassisModeSetup(RobotMap.IS_ARM_CALIBRATE_MODE, false));
            SmartDashboard.putData("Throw", new ThrowBallCommand2());            
        }
        SmartDashboard.putData(RobotMap.NO_BALL_VACUUM_CURENT,new PreferenceSetup(RobotMap.NO_BALL_VACUUM_CURENT));
        SmartDashboard.putData(RobotMap.ATTACHED_BALL_VACUUM_CURENT,new PreferenceSetup(RobotMap.ATTACHED_BALL_VACUUM_CURENT));
 
        SmartDashboard.putData("Reset " +RobotMap.ARM_VOLTS_90,new PreferenceSetup(RobotMap.ARM_VOLTS_90));
        SmartDashboard.putData("Reset " +RobotMap.ARM_VOLTS_270,new PreferenceSetup(RobotMap.ARM_VOLTS_270));
        SmartDashboard.putData("PID driveMode", new ChassisModeSetup(RobotMap.IS_PID_DRIVE_MODE,true));
        SmartDashboard.putData("OpenLoop driveMode", new ChassisModeSetup(RobotMap.IS_PID_DRIVE_MODE, false)); 
        
        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        if (Robot.lights != null && RobotMap.DEBUG) {
            SmartDashboard.putData("Lights:All to FireBears Anim", new SetLights(Robot.lights.LT_SET_ALL,Robot.lights.LT_ANIM_FB));
            SmartDashboard.putData("Lights:All to Moving Anim", new SetLights(Robot.lights.LT_SET_ALL,Robot.lights.LT_ANIM_MV));
            SmartDashboard.putData("Lights:All to Switching Anim", new SetLights(Robot.lights.LT_SET_ALL,Robot.lights.LT_ANIM_SW));
            SmartDashboard.putData("Lights:All to Bouncing Anim", new SetLights(Robot.lights.LT_SET_ALL,Robot.lights.LT_ANIM_BN));
            SmartDashboard.putData("Lights:All to Blinking Anim", new SetLights(Robot.lights.LT_SET_ALL,Robot.lights.LT_ANIM_BL));
            SmartDashboard.putData("Lights:All to Growing fire anim", new SetLights(Robot.lights.LT_SET_ALL,Robot.lights.LT_ANIM_GR));
        }
    }
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS

    public Joystick getJoystickDrive() {
        return joystickDrive;
    }

    public Joystick getJoystickArm() {
        return joystickArm;
    }
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS
}
