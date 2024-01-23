/*package com.team841.Swerve23.Superstructure;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.team841.Swerve23.Constants.ConstantsIO;
import com.team841.Swerve23.Constants.SC;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase{
//Creates a new shooter
    private final CANSparkMax shooterOneMotor =
        new CANSparkMax(ConstantsIO.CANID.kShooterOne, MotorType.kBrushless);

    private final CANSparkMax shooterTwoMotor =
        new CANSparkMax(ConstantsIO.CANID.kShooterTwo, MotorType.kBrushless);

    private final CANSparkMax kickerOneMotor =
        new CANSparkMax(ConstantsIO.CANID.kKickerOne, MotorType.kBrushless);

    private final CANSparkMax kickerTwoMotor =
        new CANSparkMax(ConstantsIO.CANID.kKickerTwo, MotorType.kBrushless);

    private final DigitalInput Kicker_Index_Sensor = new DigitalInput(ConstantsIO.PWMPorts.Kicker_Index_Sensor);

    private int outTakeclock = 0;

    enum ShooterState{
        OFF,
        IDLE,
        PASS_TO_SHOOTER,
        SHOOT,
      }

    /*public Shooter() {

    shooterOneMotor.restoreFactoryDefaults();
    shooterTwoMotor.restoreFactoryDefaults();
    

    shooterOneMotor.setSmartCurrentLimit(SC.Intake.currentLimit);
    shooterTwoMotor.setSmartCurrentLimit(SC.Intake.currentLimit);

    shooterOneMotor.follow(shooterOneMotor, false);

    kickerOneMotor.restoreFactoryDefaults();
    kickerTwoMotor.restoreFactoryDefaults();
    

    kickerOneMotor.setSmartCurrentLimit(SC.Intake.currentLimit);
    kickerTwoMotor.setSmartCurrentLimit(SC.Intake.currentLimit);

    kickerOneMotor.follow(shooterOneMotor, false);

    setKickerBrakes(true);
    }

    public void setKickerBrakes(boolean on) {
        kickerOneMotor.setIdleMode(on ? CANSparkMax.IdleMode.kBrake : CANSparkMax.IdleMode.kCoast);
        kickerTwoMotor.setIdleMode(on ? CANSparkMax.IdleMode.kBrake : CANSparkMax.IdleMode.kCoast);
      }
    
      public void setKickerMotor(Double speed) {
        shooterOneMotor.set(speed);
      }
    
      public void KickerIn() {
        setKickerMotor(0.8);
      }
    
      public void KickerOut() {
        setKickerMotor(-1.0);
        kickerOneMotor = 20;
      }
    
      public void StopKicker() {
        setKickerMotor(0.0);
      }
    
      /*public boolean getSensor() {
        return !Kicker_Index_Sensor.get();
      }
    
      @Override
      public void periodic() {
        if (KickerOutClock == 0) {
          if (getSensor()) {
            StopKicker();
          }
        } else {
          outTakeClock--;
        }*/
      

