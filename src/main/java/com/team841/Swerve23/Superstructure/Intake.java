package com.team841.Swerve23.Superstructure;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.team841.Swerve23.Constants.ConstantsIO;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {

  private final CANSparkMax intakeOneMotor = new CANSparkMax(ConstantsIO.CANID.kIntakeOne, MotorType.kBrushless);
  
  private final CANSparkMax intakeTwoMotor = new CANSparkMax(ConstantsIO.CANID.kIntakeTwo, MotorType.kBrushless);

  public Intake() {}


  @Override
  public void periodic() {
  }
}
