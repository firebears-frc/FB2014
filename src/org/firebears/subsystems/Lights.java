package org.firebears.subsystems;
import edu.wpi.first.wpilibj.DigitalModule;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.util.Vector;
import org.firebears.Robot;
import org.firebears.commands.DefaultLights;

public class Lights extends Subsystem {
    
    //CRIO->Arduino First Bytes
    public final byte LT_SET_ALL = 0x10; //set all lights
    public final byte LT_SET_ARM = 0x1C; //set arm lights
    public final byte LT_SET_LOG = 0x1A; //set logo lights
    public final byte LT_SET_UND = 0x1B; //set under lights
    
    public final byte LT_SET_COL = 0x18; //sets color of default animation
    
    //second bytes: animations
    public final byte LT_ANIM_FB = 0x21; //fire bears animation
    public final byte LT_ANIM_MV = 0x22; //moving animation
    public final byte LT_ANIM_BL = 0x23; //blink animation
    public final byte LT_ANIM_BN = 0x24; //bouncing animation
    public final byte LT_ANIM_SW = 0x25; //switching animations
    public final byte LT_ANIM_SP = 0x26; //spinning animation
    public final byte LT_ANIM_PL = 0x27; //pulse animation
    public final byte LT_ANIM_GR = 0x28; //growing fire animation
    public final byte LT_ANIM_OC = 0x29; //one color animation
    public final byte LT_ANIM_GL = 0x2A; //glowing animation

    //colors
    public final byte LT_COL_BRIGHT_RED = 0x01;
    public final byte LT_COL_MEDIUM_RED = 0x02;
    public final byte LT_COL_DULL_RED = 0x03;
    public final byte LT_COL_BRIGHT_YELLOW = 0x04;
    public final byte LT_COL_MEDIUM_YELLOW_A = 0x05;
    public final byte LT_COL_MEDIUM_YELLOW_B = 0x06;
    public final byte LT_COL_MEDIUM_ORANGE = 0x07;
    public final byte LT_COL_DULL_ORANGE = 0x08;
    public final byte LT_COL_BRIGHT_ORANGE = 0x09;
    public final byte LT_COL_ALIEN_BLUE = 0x0A;
    public final byte LT_COL_GREEN = 0x0B;
    
    public final byte SLAVE_ADRESS = 0x04;
    
    private I2C arduino;
    
    private byte arm = 0x00; //11
    private byte log = 0x00; //1a
    private byte und = 0x00; //1b
    private byte col = 0x00; //18
    
    private boolean var_arduinoIsConnected = false;
    
    private final String LIT_SET = "LightSet:";
    
    private MessageProcessor messageProcessor;
    private Thread messageThread;
     
    public Lights() {
        System.out.println("arduino initializing...");
        DigitalModule digitalModule = DigitalModule.getInstance(1);
        arduino = digitalModule.getI2C(SLAVE_ADRESS);
        this.pause();
        System.out.println("testing connection....");
        if(arduino.addressOnly()) {
            System.out.println(" ERROR: ARDUINO IS *NOT* CONNECTED!!!!");
            var_arduinoIsConnected = false;
            Robot.diagnostics.addErrorMessage("Ard.NotCon", 3);
        }else{
            System.out.println(" Arduino IS alive( It's Alive! ) ");
            arduino.setCompatabilityMode(true);
            var_arduinoIsConnected = true;
            System.out.println("Arduino initialized!");
        }
        SmartDashboard.putString(LIT_SET, "");
        
        messageProcessor = new MessageProcessor();
        messageThread = new Thread(messageProcessor);
        messageThread.setPriority(Thread.MIN_PRIORITY);
        messageThread.start();
    }
    
    public void initDefaultCommand() { 
      //  setDefaultCommand(new DefaultLights());
    }
    
    private void pause() { 
//        try{
//            Thread.sleep(50);
//        }catch(Exception a) {
//        }  

    }

            
    private byte[] sendRecv(byte p_B1, byte p_B2) {
        byte[] buffer = new byte[6];
        if(var_arduinoIsConnected == false) {
            buffer[0] = 0x38; buffer[1] = 0x54; buffer[0] = 0x00;
            return buffer;
        }
        buffer[0] = p_B1;
        buffer[1] = p_B2;
        if(p_B1 == LT_SET_ARM) {
            if(p_B2 == arm)
                return buffer;
            else
                arm = p_B2;
        }else if(p_B1 == LT_SET_LOG) {
            if(p_B2 == log)
                return buffer;
            else
                log = p_B2;
        }else if(p_B1 == LT_SET_UND) {
            if(p_B2 == und)
                return buffer;
            else
                und = p_B2;
        }else if(p_B1 == 0x18) {
            if(p_B2 == col)
                return buffer;
            else
                col = p_B2;
        }
        System.out.println("WIll not return...");
        System.out.println("trying send recv " + p_B1 + "," + p_B2);
        
//        pause();
//        System.out.println("transaction send");
          
//        if(arduino.transaction(buffer, 2, buffer, 2)) {
//            System.out.println("ERROR:Failed Transaction");
//        }
        
        messageProcessor.addMessage(buffer[0], buffer[1]);
        
//        System.out.println("transaction send finish");
//        pause();
//        System.out.println("transaction recv");
//        arduino.transaction(buffer,0,buffer, 2);
        System.out.println("added to buffer");
        return buffer;
    }
    
    public boolean verify() { //returns true if connected
        if(var_arduinoIsConnected == false) {
            return false;
        }
        byte[] get = sendRecv((byte)0x17,(byte)0x4C);
        if((get[0] == 0x37) && (get[1] == 0x4C)) {
            return true;
        }else{
            System.out.println("Wrong Byte Sequence:"+get[0]+","+get[1]);
            System.out.println("Expected"+0x37+","+0x4C);
            return false;
        }
    }
    
    public void sendPacket(byte a, byte b) {
        byte[] get = sendRecv(a,b);
//        displayLightStatus(get);
    }
    
    protected void displayLightStatus(byte[] get) {
        String Set = "ARDUINO: INVALID NUMBER, ISN'T PACKET";
        if(get[0] == 0x30) {
            Set = "ARDUINO:has set strip 1 to ";
        }else if(get[0] == 0x31) {
            Set = "ARDUINO:has set strip 2 to ";
        }else if(get[0] == 0x32) {
            Set = "ARDUINO:has set strip 3 to ";
        }else if(get[0] == 0x33) {
            Set = "ARDUINO:has set strip 1&2 to ";
        }else if(get[0] == 0x34) {
            Set = "ARDUINO:has set strip 2&3 to ";
        }else if(get[0] == 0x35) {
            Set = "ARDUINO:has set strip 3&1 to ";
        }else if(get[0] == 0x36) {
            Set = "ARDUINO:set all strips to ";
        }else if(get[0] == 0x37) {
            Set = "ARDUINO:verified";
            return;
        }else if(get[0] == 0x38) {
            System.out.print("ARDUINO:ERROR:");
            if(get[1] == 0x50) {
                System.out.print("no such strip");
            }else if(get[1] == 0x51) {
                System.out.print("request w/o send");
            }else if(get[1] == 0x52) {
                System.out.print("wrong second byte:verify");
            }
            return;
        }else if(get[0] == 0xEE) {
            System.out.println("CANNOT CONNECT!!!");
            return;
        }
        if(get[1] == LT_ANIM_FB)
            SmartDashboard.putString(LIT_SET, Set+"Fire Bears Animation");
        else if(get[1] == LT_ANIM_MV)
            SmartDashboard.putString(LIT_SET, Set+"Moving Animation");
        else if(get[1] == LT_ANIM_BL)
            SmartDashboard.putString(LIT_SET, Set+"Blinking Animation");
        else if(get[1] == LT_ANIM_BN)
            SmartDashboard.putString(LIT_SET, Set+"Bouncing Animation");
        else if(get[1] == LT_ANIM_SW)
            SmartDashboard.putString(LIT_SET, Set+"Switching Animation");
        else if(get[1] == LT_ANIM_SP)
            SmartDashboard.putString(LIT_SET, Set+"Spinning Animation");
        else if(get[1] == LT_ANIM_PL)
            SmartDashboard.putString(LIT_SET, Set+"Pulsing Animation");
        else if(get[1] == LT_ANIM_GR)
            SmartDashboard.putString(LIT_SET, Set+"Growing Animation");
        else
            SmartDashboard.putString(LIT_SET, Set+String.valueOf(get[1]));
    }
    
    public boolean arduinoIsConnected() {
        return var_arduinoIsConnected;
    }
    
    
    
    
    
      public class MessageProcessor implements Runnable {

        protected Vector messageQueue = new Vector(10);
        public boolean running = true;
        
        /** Send a message of two bytes to the Arduino. */
        public void addMessage(byte b0, byte b1) {
            synchronized (messageQueue) {
                messageQueue.addElement(new Message(b0, b1));
                if (messageQueue.size() > 100) {
                    System.out.println("Something's wrong.  Too many queued messages");
                    running = false;
                }
            }
        }
        
        /** Process messages within a thread. */
        public void run() {
            byte[] buffer = new byte[6];
            while (running) {
                Message message = null;
                if (messageQueue.size() > 0) {
                    synchronized (messageQueue) {
                        message = (Message) messageQueue.elementAt(0);
                        messageQueue.removeElementAt(0);
                    }
                }
                if (message != null) {
                    buffer[0] = message.b0;
                    buffer[1] = message.b1;
                    System.out.println("Sending to arduino: " + buffer[0] +","+buffer[1]);
                    arduino.transaction(buffer, 2, buffer, 2);
                    displayLightStatus(buffer);
                }
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                }
            }
        }

        /** One pair of bytes to be sent to the Arduino. */
        public class Message {
            final public byte b0;
            final public byte b1;
            public Message(byte b0, byte b1) {
                this.b0 = b0;
                this.b1 = b1;
            }
        }
    }
}
