package org.firebears.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.firebears.Robot;

/**
 *
 */
//TODO  Do we use toggle mode??? if not, eliminate it    Shouldn't we combine waste gate operation here??
public class VacuumOn extends Command {
    boolean on = true;
    //boolean toggleMode = false;

   public VacuumOn() {
        on = true;
        //toggleMode = true;
    }
    
    public VacuumOn(boolean setVacuum) {
        on = setVacuum;    
        //toggleMode = false;
    }

    protected void initialize() {
        Robot.vacuum.vacuumOn(on);
    }

    protected void execute() {
        Robot.vacuum.vacuumOn(on);
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
       // if (toggleMode) { Robot.vacuum.vacuumOn(false); }
    }

    protected void interrupted() {
        end();
    }
}