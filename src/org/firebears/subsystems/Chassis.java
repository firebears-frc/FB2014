package org.firebears.subsystems;
import org.firebears.RobotMap;
import org.firebears.commands.*;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.firebears.PidRobotDrive;
import org.firebears.Robot;
/**
 *
 */
public class Chassis extends Subsystem {
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    CANJaguar frontRight = RobotMap.chassisFrontRight;
    CANJaguar frontLeft = RobotMap.chassisFrontLeft;
    CANJaguar rearRight = RobotMap.chassisRearRight;
    CANJaguar rearLeft = RobotMap.chassisRearLeft;
    RobotDrive robotDrive = RobotMap.chassisRobotDrive;
    Gyro gyro = RobotMap.chassisGyro;
    AnalogChannel rangefinder = RobotMap.chassisRangefinder;
    Solenoid shiftUp = RobotMap.chassisShiftUp;
    Solenoid shiftDown = RobotMap.chassisShiftDown;
    Encoder leftEncoder = RobotMap.chassisLeftEncoder;
    Encoder rightEncoder = RobotMap.chassisRightEncoder;
    Compressor compressor = RobotMap.compressor;
    private boolean goingForward = true;
    boolean isMoving = false;
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public void initDefaultCommand() {
        setDefaultCommand(new DriveCommand());
    }
   
    //TODO Fix reverse wrong diretion
    public void move(double forward, double turn) {
        if(forward > 0.1){
            goingForward = false;
            isMoving = true;
        } else if (forward < -0.1){
            goingForward = true;
            isMoving = true;
        }else{
            isMoving = false;
        }
        
        robotDrive.arcadeDrive(forward, turn);
    }
    public void chassisShiftUp() {
        RobotMap.chassisShiftUp.set(true);
        RobotMap.chassisShiftDown.set(false);
        if(RobotMap.chassisRobotDrive instanceof PidRobotDrive ){//Make sure  in PID Mode******
            ((PidRobotDrive)RobotMap.chassisRobotDrive).setEncoderMultiplier(1.0 / RobotMap.HI_SPEED_DR_MAX);
        }
    }
    public void chassisShiftDown() {
        RobotMap.chassisShiftUp.set(false);
        RobotMap.chassisShiftDown.set(true);
        if(RobotMap.chassisRobotDrive instanceof PidRobotDrive ){//Make sure  in PID Mode******
            ((PidRobotDrive)RobotMap.chassisRobotDrive).setEncoderMultiplier(1.0 / RobotMap.LO_SPEED_DR_MAX); 
        }
    }
    /**
     * @return rangefinder distance in inches.
     */
    //TODO  The range finder scale doesnn't look right  VOLT_DIST_RATIO = 0.00929687; //5.084 Volts / 512 inch range 0.009929687
    public double getDistance() {
        return RobotMap.chassisRangefinder.getAverageVoltage() * 100;
    }
    public boolean getGoingForward(){
        return goingForward;
    }
    public boolean getMoving() {
        return isMoving;
    }
}
