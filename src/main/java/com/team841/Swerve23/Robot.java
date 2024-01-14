package com.team841.Swerve23;

import com.team841.Swerve23.Constants.ConstantsIO;
import com.team841.Swerve23.Constants.ConstantsIO.robotStates;
import com.team841.lib.util.tunableNumber;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private RobotContainer m_robotContainer;

  private Spark LED = new Spark(7);

  private tunableNumber ledValue = new tunableNumber("LED value:" );

  @Override
  public void robotInit() {
    m_robotContainer = new RobotContainer();

    ConstantsIO.robotCurrentState = robotStates.disabled;

    ledValue.setDefault(0);
    LED.set(-0.95);
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();

    if (ledValue.hasChanged())
      LED.set(ledValue.get());

    SmartDashboard.putNumber("led channel", LED.getChannel());
  }

  @Override
  public void disabledInit() {
    ConstantsIO.robotCurrentState = robotStates.disabled;
  }

  @Override
  public void disabledPeriodic() {}

  @Override
  public void disabledExit() {}

  @Override
  public void autonomousInit() {

    ConstantsIO.robotCurrentState = robotStates.autonomous;

    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void autonomousExit() {}

  @Override
  public void teleopInit() {

    ConstantsIO.robotCurrentState = robotStates.teleop;

    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {}

  @Override
  public void teleopExit() {}

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {}

  @Override
  public void testExit() {}
}
