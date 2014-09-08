package org.firebears;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.can.CANNotInitializedException;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * RobotDrive class that puts PIDControllers on each side of the robot. 
 * Note about encoders:  The encoders should be set to be of type "kRate".  
 * Here is a typical setup for encoders:
 <pre>
        Encoder chassisLeftEncoder = new Encoder(1, 4, 1, 5, false, EncodingType.k4X);
        chassisLeftEncoder.setDistancePerPulse(1.0);
        chassisLeftEncoder.setPIDSourceParameter(PIDSourceParameter.kRate);
        chassisLeftEncoder.start();
        
        Encoder chassisRightEncoder = new Encoder(1, 1, 1, 2, false, EncodingType.k4X);
        chassisRightEncoder.setDistancePerPulse(1.0);
        chassisRightEncoder.setPIDSourceParameter(PIDSourceParameter.kRate);
        chassisRightEncoder.start();
 </pre>
 */
public class PidRobotDrive extends RobotDrive {

    double m_P = 1.0;
    double m_I = 0.0;
    double m_D = 0.0;
    double m_EncoderMultiplier = 1.0;
    double m_ema;
    final byte m_syncGroup = (byte) 0x80;
    private final PIDSource leftFeedback;
    private final PIDOutput leftOutput;
    private final PIDController leftPid;
    private final PIDSource rightFeedback;
    private final PIDOutput rightOutput;
    private final PIDController rightPid;
    private static final int kFrontLeft_val = 0;
    private static final int kFrontRight_val = 1;
    private static final int kRearLeft_val = 2;
    private static final int kRearRight_val = 3;

    public PidRobotDrive(SpeedController frontLeftMotor, SpeedController rearLeftMotor,
            SpeedController frontRightMotor, SpeedController rearRightMotor,
            PIDSource leftEncoder, PIDSource rightEncoder) {
        super(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);

        leftFeedback = new EncoderFeedback(leftEncoder);
        leftOutput = new MotorOutput(m_rearLeftMotor, m_frontLeftMotor);
        leftPid = new PIDController(m_P, m_I, m_D, leftFeedback, leftOutput);
        LiveWindow.addActuator("Chassis", "RobotDrive left PID", leftPid);
        leftPid.enable();

        rightFeedback = new EncoderFeedback(rightEncoder);
        rightOutput = new MotorOutput(m_rearRightMotor, m_frontRightMotor);
        rightPid = new PIDController(m_P, m_I, m_D, rightFeedback, rightOutput);
        LiveWindow.addActuator("Chassis", "RobotDrive right PID", rightPid);
        rightPid.enable();
    }

    /**
     * Set the speed of the right and left motors.  This overrides an 
     * important method in the parent class.
     *
     * @param leftValue Speed value in the range -1.0 to 1.0.
     * @param rightValue Speed value in the range -1.0 to 1.0.
     */
    //Overrides setLeftRightMotorOutputs in RobotDrive
    public void setLeftRightMotorOutputs(double leftValue, double rightValue) {
        if (leftPid == null) {
            return;
        }
        leftPid.setSetpoint(leftValue);
        rightPid.setSetpoint(-1 * rightValue);

        if (m_isCANInitialized) {
            try {
                CANJaguar.updateSyncGroup(m_syncGroup);
            } catch (CANNotInitializedException e) {
                m_isCANInitialized = false;
            } catch (CANTimeoutException e) {
            }
        }

        if (m_safetyHelper != null) {
            m_safetyHelper.feed();
        }
    }

    /**
     * Make a live change of the P, I, and D values.
     */
    public void setPidParams(double p, double i, double d) {
        m_P = p;
        m_I = i;
        m_D = d;
        leftPid.setPID(p, i, d);
        rightPid.setPID(p, i, d);
    }
    
    /**
     * Change the multiplier for the encoders.  This should be adjusted so 
     * the PID feedback values are always in the range -1.0 to 1.0.
     * You may need to change this every time the gearbox shifts.
     */
    public void setEncoderMultiplier(double m) {
        m_EncoderMultiplier = m;
    }

    
    
    
    /**
     * Wrapper class around the encoder for one side of the robot.  Additional 
     * filtering logic can be added here.
     */
    //By PID source, I assume they mean feedback?????
    protected class EncoderFeedback implements PIDSource {

        PIDSource encoder;

        public EncoderFeedback(PIDSource enc) {
            this.encoder = enc;
        }
//TODO can we change this to pidGetFeedback??
        public double pidGet() {
            double rate = this.encoder.pidGet();
            m_ema = (rate - m_ema ) * .5 + m_ema;//exponential moving average filter
            return m_EncoderMultiplier * m_ema;
        }
    }

    
    
    
    /**
     * Wrapper around the two CANJaguars on one side of the robot.  Sends 
     * the same speed output to the two motors.
     */
    protected class MotorOutput implements PIDOutput {

        SpeedController rearMotor;
        SpeedController frontMotor;

        public MotorOutput(SpeedController rearM, SpeedController frontM) {
            this.rearMotor = rearM;
            this.frontMotor = frontM;
        }

        public void pidWrite(double output) {
            double speed = limit(output) * m_maxOutput;
            if (rearMotor != null) {
                rearMotor.set(speed * m_invertedMotors[kFrontLeft_val], m_syncGroup);
            }
            rearMotor.set(speed * m_invertedMotors[kRearLeft_val], m_syncGroup);
        }
    }
    
    
    
}
