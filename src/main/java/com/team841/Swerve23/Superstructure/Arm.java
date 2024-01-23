package com.team841.Swerve23.Superstructure;

import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkMax;
// import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.SparkMaxPIDController;
import com.team841.Swerve23.Constants.ConstantsIO;
import com.team841.Swerve23.Constants.SC;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Arm extends SubsystemBase {

  private final CANSparkMax armMotor =
      new CANSparkMax(ConstantsIO.CANID.kArm, MotorType.kBrushless);

  public SparkMaxPIDController armPID = armMotor.getPIDController();

  public Arm() {
    armMotor.restoreFactoryDefaults();

    armMotor.setSmartCurrentLimit(SC.Arm.currentLimit);

    armPID.setP(SC.Arm.pidGains.kP);
    armPID.setI(SC.Arm.pidGains.kI);
    armPID.setD(SC.Arm.pidGains.kD);
    armPID.setIZone(SC.Arm.pidGains.kIz);

    armPID.setFeedbackDevice(armMotor.getEncoder());
  }

  public Command armToPosition(double angle) {
    return Commands.run(() -> armPID.setReference(angle, ControlType.kPosition))
        .withInterruptBehavior(Command.InterruptionBehavior.kCancelSelf)
        .handleInterrupt(() -> armMotor.set(0));
  }

  public void armOut() {
    driveArm(1);
  }

  public void armIn() {
    driveArm(-1);
  }

  public void armStop() {
    driveArm(0);
  }

  public void driveArm(double power) {
    armMotor.set(power);
  }

  @Override
  public void periodic() {}
}
