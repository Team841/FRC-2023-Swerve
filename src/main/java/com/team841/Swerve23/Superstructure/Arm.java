package com.team841.Swerve23.Superstructure;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.team841.Swerve23.Constants.ConstantsIO;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Arm extends SubsystemBase {

  private final CANSparkMax armMotor = new CANSparkMax(ConstantsIO.CANID.kArm, MotorType.kBrushless);

  public SparkMaxPIDController armPID = armMotor.getPIDController();

  public Arm() {}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
