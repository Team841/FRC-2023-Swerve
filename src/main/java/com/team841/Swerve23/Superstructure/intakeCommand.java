// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team841.Swerve23.Superstructure;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.team841.Swerve23.Constants.ConstantsIO;

import edu.wpi.first.wpilibj2.command.Command;

public class intakeCommand extends Command {

  private final Intake a_Intake;
  /** Creates a new inakeCommand. */
  public intakeCommand(Intake intake) {
    // Use addRequirements() here to declare subsystem dependencies.
    a_Intake = intake;
    addRequirements(intake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    a_Intake.setIntakeMotor(0.5);

  
  }
  
  
  // Called once the ommand ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

    //stop the motor
    //StopTake();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
     // is sensor tirggered?
     //Intake_Index_Sensor
     if (a_Intake.getSensor()){
      a_Intake.StopTake();
      return true;
     }
     else {
      return false;
     }
    }
  }
  
  
