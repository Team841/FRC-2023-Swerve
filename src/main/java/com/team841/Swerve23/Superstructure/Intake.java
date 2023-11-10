package com.team841.Swerve23.Superstructure;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.team841.Swerve23.Constants.ConstantsIO;
import com.team841.Swerve23.Constants.SC;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {

  private final CANSparkMax intakeOneMotor =
      new CANSparkMax(ConstantsIO.CANID.kIntakeOne, MotorType.kBrushless);

  private final CANSparkMax intakeTwoMotor =
      new CANSparkMax(ConstantsIO.CANID.kIntakeTwo, MotorType.kBrushless);

  public Intake() {

    intakeOneMotor.restoreFactoryDefaults();
    intakeTwoMotor.restoreFactoryDefaults();

    intakeOneMotor.setSmartCurrentLimit(SC.Intake.currentLimit);
    intakeTwoMotor.setSmartCurrentLimit(SC.Intake.currentLimit);

    intakeTwoMotor.follow(intakeOneMotor, true);

    setIntakeBrakes(true);
  }

  public void setIntakeBrakes(boolean on) {
    intakeOneMotor.setIdleMode(on ? CANSparkMax.IdleMode.kBrake : CANSparkMax.IdleMode.kCoast);
    intakeTwoMotor.setIdleMode(on ? CANSparkMax.IdleMode.kBrake : CANSparkMax.IdleMode.kCoast);
  }

  public void setIntakeMotor(Double speed) {
    intakeOneMotor.set(speed);
  }

  public void intake() {
    setIntakeMotor(1.0);
  }

  public void outTake() {
    setIntakeMotor(-1.0);
  }

  @Override
  public void periodic() {}
}
