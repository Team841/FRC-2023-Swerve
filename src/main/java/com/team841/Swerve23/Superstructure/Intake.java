package com.team841.Swerve23.Superstructure;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.team841.Swerve23.Constants.ConstantsIO;
import com.team841.Swerve23.Constants.SC;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.DigitalInput;

public class Intake extends SubsystemBase {

  private final CANSparkMax intakeOneMotor =
      new CANSparkMax(ConstantsIO.CANID.kIntakeOne, MotorType.kBrushless);

  private final CANSparkMax intakeTwoMotor =
      new CANSparkMax(ConstantsIO.CANID.kIntakeTwo, MotorType.kBrushless);
  
  private final DigitalInput Intake_Index_Sensor = new DigitalInput(0); 

  private int outTakeClock = 0;    

  public Intake() {

    intakeOneMotor.restoreFactoryDefaults();
    intakeTwoMotor.restoreFactoryDefaults();

    intakeOneMotor.setSmartCurrentLimit(SC.Intake.currentLimit);
    intakeTwoMotor.setSmartCurrentLimit(SC.Intake.currentLimit);

    intakeTwoMotor.follow(intakeOneMotor, false);

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
    setIntakeMotor(0.3);
  }

  public void outTake() {
    setIntakeMotor(-0.3);
    outTakeClock = 20;
  }
  
  public void StopTake() {
    setIntakeMotor(0.0);
  }

public boolean getSensor() {
  return !Intake_Index_Sensor.get();
  
}

  @Override
  public void periodic() {
    if (outTakeClock == 0) {
      if (getSensor()){
        StopTake();
      }
    }
    else {
      outTakeClock--;
    }
  }
}
