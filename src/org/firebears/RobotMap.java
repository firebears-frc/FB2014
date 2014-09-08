package org.firebears;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.PIDSource.PIDSourceParameter;
import edu.wpi.first.wpilibj.can.*;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.Preferences;


/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    
    public static final boolean DEBUG = true;

    public static CANJaguar armArmJagOne;
    public static CANJaguar armArmJagTwo;
    public static AnalogChannel armAngleSensor;
    public static CANJaguar vacuumVacuumJag;
    public static Solenoid vacuumWasteGateOpen;
    public static Solenoid vacuumWasteGateClose;
    public static CANJaguar chassisFrontRight;
    public static CANJaguar chassisFrontLeft;
    public static CANJaguar chassisRearRight;
    public static CANJaguar chassisRearLeft;
    public static RobotDrive chassisRobotDrive;
    public static Gyro chassisGyro;
    public static AnalogChannel chassisRangefinder;
    public static Solenoid chassisShiftUp;
    public static Solenoid chassisShiftDown;
    public static Encoder chassisLeftEncoder;
    public static Encoder chassisRightEncoder;
    public static Compressor compressor;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    
    //Drive mode setup******************************************************************************
    public static final boolean IS_PID_DRIVE = true;
    //public static final int OPEN_LOOP_DRIVE = 2;
    //public static int driveMode = PID_DRIVE;//************Drive mode setup **********
    public static double HI_SPEED_DR_MAX = 30000;
    public static double LO_SPEED_DR_MAX = 15000;
    
    
    
    public static double fwAngleLo = 86;
    public static final String FW_ANGLE_LO = "FW_ANGLE_LO";
    public static double fwAngleCatch = 150;
    public static final String FW_ANGLE_CATCH = "FW_ANGLE_CATCH";
    public static double fwAngleStowLo = 150;
    public static final String FW_ANGLE_STOW_LO = "FW_ANGLE_STOW_LO";
    public static double angleStowHi = 180;
    public static final String ANGLE_STOW_HI = "ANGLE_STOW_HI";
    public static double fwAngleLoScore = 102;
    public static final String FW_ANGLE_LO_SCORE = "FW_ANGLE_LO_SCORE";
    public static double fwAnglePassHi = 126;
    public static final String FW_ANGLE_PASS_HI = "FW_ANGLE_PASS_HI";
    public static double angleThrowEnd = 101;
    public static final String ANGLE_THROW_END = "ANGLE_THROW_END";
    public static double angleThrowRelease = 210;
    public static final String ANGLE_THROW_RELEASE = "ANGLE_THROW_RELEASE";
    public static double bkAngleLo = 278;
    public static final String BK_ANGLE_LO = "BK_ANGLE_LO";
    public static double bkAngleCatch = 224;
    public static final String BK_ANGLE_CATCH = "BK_ANGLE_CATCH";
    public static double bkAngleStowLo = 220;
    public static final String BK_ANGLE_STOW_LO = "BK_ANGLE_STOW_LO";
    public static double bkAngleLoScore = 266;
    public static final String BK_ANGLE_LO_SCORE = "BK_ANGLE_LO_SCORE";
    public static double bkAnglePassHi = 223;
    public static final String BK_ANGLE_PASS_HI = "BK_ANGLE_PASS_HI";
    public static boolean isPIDDrive = IS_PID_DRIVE;
    public static final String IS_PID_DRIVE_MODE = "IS_PID_DRIVE_MODE";
    public static double armVolts90 = 4.237;
    public static final String ARM_VOLTS_90 = "ARM_VOLTS_90";
    public static double armVolts270 = 1.910;
    public static final String ARM_VOLTS_270 = "ARM_VOLTS_270";
    public static boolean isArmCalibrateMode = false;
    public static final String IS_ARM_CALIBRATE_MODE = "IS_ARM_CALIBRATE_MODE";
    public static double noBallVacuumCurrent = 13.0;
    public static final String NO_BALL_VACUUM_CURENT = "NO_BALL_VACUUM_CURENT";
    public static double attachedBallVacuumCurrent = 7.0;
    public static final String ATTACHED_BALL_VACUUM_CURENT = "ATTACHED_BALL_VACUUM_CURENT";
    
    

//    public static final String FW_PICK_UP_ANGLE_LO="FW_PICK_UP_ANGLE_LO";
//    public double fwPickupAngleLo;
    
//    public static double FW_PICK_UP_ANGLE = Robot.arm.min_angle;
//    public static int FW_UNDER_TRUSS_ANGLE = 135;
//   // public static int FW_PREP_THROW_ANGLE = 220;
//    public static double BK_PICK_UP_ANGLE = Robot.arm.max_angle;
//    public static int BK_UNDER_TRUSS_ANGLE = 225;
//    //public static int BK_PREP_THROW_ANGLE = 125;
//    public static final int THROW_SWING = 45;
    
    //diagnostics Zones Formats 1-4
    public static final int DiagnosticsZoneOne = 1;
    public static final int DiagnosticsZoneTwo = 2;
    public static final int DiagnosticsZoneThree = 3;
    public static final int DiagnosticsZoneFour = 4;
    
    
    private static Preferences preferences;
    
    public static void init() {

        //Grab angles stored in preferences and pur into angle "constants" variables
        preferences = Preferences.getInstance();
        
        fwAngleLo = preferences.getDouble(FW_ANGLE_LO,86);
        fwAngleCatch = preferences.getDouble(FW_ANGLE_CATCH,150);
        fwAngleStowLo = preferences.getDouble(FW_ANGLE_STOW_LO,150);
        angleStowHi =  preferences.getDouble(ANGLE_STOW_HI,180);
        fwAngleLoScore = preferences.getDouble(FW_ANGLE_LO_SCORE,102);
        fwAnglePassHi = preferences.getDouble(FW_ANGLE_PASS_HI,126);
        angleThrowEnd = preferences.getDouble(ANGLE_THROW_END,101);
        angleThrowRelease = preferences.getDouble(ANGLE_THROW_RELEASE,210);
        bkAngleLo = preferences.getDouble(BK_ANGLE_LO,278);
        bkAngleCatch = preferences.getDouble(BK_ANGLE_CATCH,224);
        bkAngleStowLo = preferences.getDouble(BK_ANGLE_STOW_LO,220);
        bkAngleLoScore = preferences.getDouble(BK_ANGLE_LO_SCORE,266);
        bkAnglePassHi = preferences.getDouble(BK_ANGLE_PASS_HI,223);
        isPIDDrive = preferences.getBoolean(IS_PID_DRIVE_MODE, false);
        armVolts90 =  preferences.getDouble(ARM_VOLTS_90, 2.5);
        armVolts270 =  preferences.getDouble(ARM_VOLTS_270, 2.5);
        noBallVacuumCurrent = preferences.getDouble(NO_BALL_VACUUM_CURENT, 13);
        attachedBallVacuumCurrent = preferences.getDouble(ATTACHED_BALL_VACUUM_CURENT,7);
        // 12, 13, 14 arm jags and vac
        try { 
            armArmJagOne = new CANJaguar(2);
            armArmJagOne.configNeutralMode(CANJaguar.NeutralMode.kBrake);
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
            Robot.diagnostics.addErrorMessage("Jag(2)arm", DiagnosticsZoneTwo);
        }


        try { 
            armArmJagTwo = new CANJaguar(4);
            armArmJagTwo.configNeutralMode(CANJaguar.NeutralMode.kBrake);
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
            Robot.diagnostics.addErrorMessage("Jag(4)arm", DiagnosticsZoneTwo);
        }
               
        armAngleSensor = new AnalogChannel(1, 3);
	LiveWindow.addSensor("Arm", "AngleSensor", armAngleSensor);
        
        try { 
            vacuumVacuumJag = new CANJaguar(5);
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
            Robot.diagnostics.addErrorMessage("Jag(5)Vac", DiagnosticsZoneTwo);
        }

        vacuumWasteGateOpen = new Solenoid(1, 4);
        LiveWindow.addActuator("Vacuum", "WasteGateOpen", vacuumWasteGateOpen);

        vacuumWasteGateClose = new Solenoid(1, 5);
        LiveWindow.addActuator("Vacuum", "WasteGateClose", vacuumWasteGateClose);
        
        try { 
            chassisFrontRight = new CANJaguar(12);
            LiveWindow.addActuator("Chassis", "chassisFrontRight", chassisFrontRight);
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
            Robot.diagnostics.addErrorMessage("Jag(14)F.R", DiagnosticsZoneTwo);
        }


        try {
            chassisFrontLeft = new CANJaguar(14);
            LiveWindow.addActuator("Chassis", "chassisFrontLeft", chassisFrontLeft);
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
            Robot.diagnostics.addErrorMessage("Jag(12)F.L", DiagnosticsZoneTwo);
        }


        try {
            chassisRearRight = new CANJaguar(11);
            LiveWindow.addActuator("Chassis", "chassisRearRight", chassisRearRight);
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
            Robot.diagnostics.addErrorMessage("Jag(13)R.R", DiagnosticsZoneTwo);
        }

	
        try { 
            chassisRearLeft = new CANJaguar(13);
            LiveWindow.addActuator("Chassis", "chassisRearLeft", chassisRearLeft);
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
            Robot.diagnostics.addErrorMessage("Jag(11)R.L", DiagnosticsZoneTwo);
        }
        LiveWindow.addActuator("Vacuum", "WasteGateClose", vacuumWasteGateClose);
        
        
        //Open loop drive setup *********************************************************************************
        if(isPIDDrive == false){
        try {
            chassisRobotDrive = new RobotDrive(chassisFrontLeft, chassisRearLeft,
                  chassisFrontRight, chassisRearRight);
            chassisRobotDrive.setSafetyEnabled(false);
            chassisRobotDrive.setExpiration(0.1);
            chassisRobotDrive.setSensitivity(0.5);
            chassisRobotDrive.setMaxOutput(1.0);
            chassisRobotDrive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
            chassisRobotDrive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
            chassisRobotDrive.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
            chassisRobotDrive.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
        } catch (Exception ex) {
            ex.printStackTrace();
            Robot.diagnostics.addErrorMessage("ChasDriv", DiagnosticsZoneTwo);
        }
        }
        //END open loop drive setup
        
        
        try {
            chassisGyro = new Gyro(1, 2);
            LiveWindow.addSensor("Chassis", "Gyro", chassisGyro);
            chassisGyro.setSensitivity(0.0070);
        } catch (Exception ex) {
            ex.printStackTrace();
            Robot.diagnostics.addErrorMessage("Gyro", DiagnosticsZoneTwo);
        }
        
        try {
            chassisRangefinder = new AnalogChannel(1, 7);
            LiveWindow.addSensor("Chassis", "Rangefinder", chassisRangefinder);
        } catch (Exception ex) {
            ex.printStackTrace();
            Robot.diagnostics.addErrorMessage("RangeFinr", DiagnosticsZoneTwo);
        }

        chassisShiftUp = new Solenoid(1, 1);
        LiveWindow.addActuator("Chassis", "ShiftUp", chassisShiftUp);

        try {
            chassisShiftDown = new Solenoid(1, 2);
            LiveWindow.addActuator("Chassis", "ShiftDown", chassisShiftDown);
        } catch (Exception ex) {
            ex.printStackTrace();
            Robot.diagnostics.addErrorMessage("Solenoid", DiagnosticsZoneTwo);
        }

        try {
            chassisLeftEncoder = new Encoder(1, 4, 1, 5, false, EncodingType.k4X);
            LiveWindow.addSensor("Chassis", "LeftEncoder", chassisLeftEncoder);
            chassisLeftEncoder.setDistancePerPulse(1.0);
            chassisLeftEncoder.setPIDSourceParameter(PIDSourceParameter.kRate);
            chassisLeftEncoder.start();
            chassisRightEncoder = new Encoder(1, 1, 1, 2, false, EncodingType.k4X);
            LiveWindow.addSensor("Chassis", "RightEncoder", chassisRightEncoder);
            chassisRightEncoder.setDistancePerPulse(1.0);
            chassisRightEncoder.setPIDSourceParameter(PIDSourceParameter.kRate);
            chassisRightEncoder.start();
        } catch (Exception ex) {
            ex.printStackTrace();
            Robot.diagnostics.addErrorMessage("Encoders", DiagnosticsZoneTwo);
        }
        
        try {
            compressor = new Compressor(11, 2);
        } catch (Exception ex) {
            ex.printStackTrace();
            Robot.diagnostics.addErrorMessage("Compressor", DiagnosticsZoneTwo);
        }

        //PID drive setup *****************************************************************************
        
        if(isPIDDrive == true){
        chassisRobotDrive = new PidRobotDrive(chassisFrontLeft, chassisRearLeft,
                chassisFrontRight, chassisRearRight, chassisLeftEncoder, chassisRightEncoder);
        chassisRobotDrive.setSafetyEnabled(false);
        chassisRobotDrive.setExpiration(0.1);
        chassisRobotDrive.setSensitivity(0.5);
        chassisRobotDrive.setMaxOutput(1.0);
        chassisRobotDrive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
        chassisRobotDrive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
        chassisRobotDrive.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
        chassisRobotDrive.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
        ((PidRobotDrive) chassisRobotDrive).setEncoderMultiplier(1.0 / RobotMap.HI_SPEED_DR_MAX);
        }
         
        // END PID drive setup

        // Turn on Compressor
        compressor.start();

        // Shift gearbox to high gear initially
        chassisShiftDown.set(false);
        chassisShiftUp.set(true);
        
        
        //Grab angles stored in preferences and pur into angle "constants" variables
//        preferences = Preferences.getInstance();
//        
//        fwAngleLo = preferences.getDouble(FW_ANGLE_LO,86);
//        fwAngleCatch = preferences.getDouble(FW_ANGLE_CATCH,150);
//        fwAngleStowLo = preferences.getDouble(FW_ANGLE_STOW_LO,150);
//        angleStowHi =  preferences.getDouble(ANGLE_STOW_HI,180);
//        fwAngleLoScore = preferences.getDouble(FW_ANGLE_LO_SCORE,102);
//        fwAnglePassHi = preferences.getDouble(FW_ANGLE_PASS_HI,126);
//        angleThrowEnd = preferences.getDouble(ANGLE_THROW_END,101);
//        angleThrowRelease = preferences.getDouble(ANGLE_THROW_RELEASE,210);
//        bkAngleLo = preferences.getDouble(BK_ANGLE_LO,278);
//        bkAngleCatch = preferences.getDouble(BK_ANGLE_CATCH,224);
//        bkAngleStowLo = preferences.getDouble(BK_ANGLE_STOW_LO,220);
//        bkAngleLoScore = preferences.getDouble(BK_ANGLE_LO_SCORE,266);
//        bkAnglePassHi = preferences.getDouble(BK_ANGLE_PASS_HI,223);
        
        
        
    }
}