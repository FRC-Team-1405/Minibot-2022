/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.DriveBaseController;
import frc.robot.commands.TurnToAngle;
import frc.robot.lib.SmartSupplier;
import frc.robot.subsystems.ArcadeDrive;
import frc.robot.lib.DigitalFlag;
import frc.robot.lib.MathTools;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private ArcadeDrive driveBase = new ArcadeDrive();

  private XboxController driver = new XboxController(Constants.pilot);
  
  public SmartSupplier kP = new SmartSupplier("PID/kP", Constants.PIDConstants.kP);
  public SmartSupplier kI = new SmartSupplier("PID/kI", Constants.PIDConstants.kI);
  public SmartSupplier kD = new SmartSupplier("PID/kD", Constants.PIDConstants.kD);

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    driveBase.setDefaultCommand( new DriveBaseController( this::driveSpeed, this::driveRotation, driveBase) );

    System.out.printf("Slot 0: %s\n", DigitalFlag.IsCIM() ? "CIM" : "Falcon");
    System.out.printf("Slot 1: ---\n");
    System.out.printf("Slot 2: %s\n", DigitalFlag.IsTank() ? "Tank" : "Mechanum");
    System.out.printf("Slot 3: ---\n");
    System.out.printf("Slot 4: ---\n");
    System.out.printf("Slot 5: ---\n");
    System.out.printf("Slot 6: ---\n");
    System.out.printf("Slot 7: %s\n", DigitalFlag.IsSimulation() ? "Simulation" : "Real");
  }

  double speedInputLimit = new SmartSupplier("Drive/SpeedInputLimit", 1.0).getAsDouble();
  SlewRateLimiter driveSpeedFilter = new SlewRateLimiter( new SmartSupplier("Drive/SpeedRateLimit", 0.5).getAsDouble() * speedInputLimit );
  private double driveSpeed(){
    double speed = MathTools.map(driver.getRightX(), -1, 1, -speedInputLimit, speedInputLimit); 

    if(Math.abs(speed) < Constants.deadBand)
      speed = 0.0;
    
    return driveSpeedFilter.calculate(speed);
  }

  double turnInputLimit = new SmartSupplier("Drive/TurnInputLimit", 1.0).getAsDouble();
  SlewRateLimiter driveRotationFilter = new SlewRateLimiter( new SmartSupplier("Drive/TurnRateLimit", 0.5).getAsDouble() * turnInputLimit );
  private double driveRotation(){
    double rotation = MathTools.map(driver.getLeftY(), -1, 1, -turnInputLimit, turnInputLimit);
    if(Math.abs(rotation) < Constants.deadBand)
      rotation = 0.0;

    return driveRotationFilter.calculate(rotation);
  }

  
  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    new JoystickButton(driver, XboxController.Button.kX.value)
      .whenPressed(new TurnToAngle(this, driveBase, 90));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return null;
  }
}
