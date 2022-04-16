/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ArcadeDrive extends SubsystemBase {

  public double speedLimit; 

  public String driveModeIndicator = "Slow Mode"; 

  public int driveMode;  

 
  DifferentialDrive driveBase = new DifferentialDrive( new CANSparkMax(1 , MotorType.kBrushless),
                                                       new CANSparkMax(2 , MotorType.kBrushless) );
  public ArcadeDrive() {  
    
  }

  @Override
  public void periodic() { 
    switch (getDriveMode()) { 
      case 0: speedLimit = 0.25; driveModeIndicator = "Slow Mode"; 
      break; 
      case 1: speedLimit = 0.5;  driveModeIndicator = "Medium Mode"; 
      break; 
      case 2: speedLimit = 0.75; driveModeIndicator = "Fast Mode";  
      break; 
      default: speedLimit = 0.25;  driveModeIndicator = "Slow Mode"; 
      break; 
    } 
    //Shuffleboard.getTab("DriveBase").add("Drive Mode", driveModeIndicator).withPosition(5, 3);
    SmartDashboard.putString("Drive Mode", driveModeIndicator); 
    //System.out.println("speed limit -----" + speedLimit); 
    
  }

  public void driveRobot(double xSpeed, double zRotation, boolean squareInputs)   
  {
    driveBase.arcadeDrive(xSpeed * speedLimit, zRotation * speedLimit, squareInputs);
  } 

  public void setDriveMode(){  
    if (driveMode < 2)
    {driveMode = driveMode + 1;} 
    else {driveMode = 0;}

  } 

  public int getDriveMode(){ 
    return driveMode; 
  }
  
}
