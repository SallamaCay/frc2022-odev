package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableRegistry;

/**
 * This program is configured to control a 2020 Everybot robot.
 * This version would use a pneumatic cylinder for raising and lowering the intake
 * WPILib is setup to automatically call these methods when the specific 
 * mode is enabled, don't change their names.
 */
public class Robot extends TimedRobot {
  //Controller
  XboxController controller = new XboxController(0);

  /*  Drive Train - Create 2 motor controllers, one for each side
  *   and a DifferentialDrive which lets us easily control the drive train
  */
  VictorSP leftMotor = new VictorSP(0);
  VictorSP rightMotor = new VictorSP(1);
  DifferentialDrive drive = new DifferentialDrive(leftMotor, rightMotor);

  //Intake - Create a motor controller and a pneumatic solenoid
  VictorSP intakeMotor = new VictorSP(3);
  Solenoid intakePneumatic = new Solenoid(0); 
  VictorSP climberMotor = new VictorSP(4);




    /**
   * This method is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    //These methods give names to our shuffleboard livewindow outputs
    SendableRegistry.add(drive, "drive");
    SendableRegistry.add(intakeMotor, "intakeMotor");
    SendableRegistry.add(intakePneumatic, "intakePneumatic");
    SendableRegistry.add(climberMotor, "CLmotor");


  }

  /**
   * This method is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    //arcade drive has two parmeters, one controller axis for forward and reverse
    //and one for turning the robot left and right
    drive.arcadeDrive(-1 * controller.getY(Hand.kLeft), controller.getX(Hand.kRight));

    //This is a simplified intake control methods
    //If the left bumper button on the controller is pressed 
    //the pneumatic deploys the intake, when it isn't pressed the intake is up
    if (controller.getBumper(Hand.kLeft)){
      intakePneumatic.set(true);
    } else {
      intakePneumatic.set(false);
    }

    //If the right bumper button on the controller is pressed the intake motor is spinning forward (in)
    //If the right trigger axis is pressed past 0.5 the intake motor is set to reverse (out)
    //If niether is pressed the motor stops
    if(controller.getBumper(Hand.kRight)){
      intakeMotor.set(1.0);
    } else if (controller.getTriggerAxis(Hand.kRight) > 0.5){
      intakeMotor.set(-1.0);
    } else{
      intakeMotor.stopMotor();
    }
   
    if(controller.getAButton()) {climberMotor.set(1);}

    else if (controller.getYButton()) {climberMotor.set(-1);
    
    
    } else {climberMotor.stopMotor();}
  }
}