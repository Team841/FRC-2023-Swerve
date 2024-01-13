package com.team841.lib.util;

import com.team841.Swerve23.Constants.ConstantsIO;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class tunableNumber {

  public static final String tabKey = "Tunable Numbers";

  private final String key;
  private double defaultValue;
  private double oldValue = defaultValue;

  public tunableNumber(String DashboardKey) {
    this.key = tabKey + "/" + DashboardKey;
  }

  public tunableNumber(String DashboardKey, double defaultValue) {
    this(DashboardKey);
    setDefault(defaultValue);
  }

  public void setDefault(double defaultValue) {
    this.defaultValue = defaultValue;
    if (ConstantsIO.inTune) {
      SmartDashboard.putNumber(key, defaultValue);
    } else {
      SmartDashboard.clearPersistent(key);
    }
  }

  public double get() {
    return 0; // C.inTune ? SmartDashboard.getNumber(key, defaultValue) : defaultValue;
  }

  public boolean hasChanged() {
    double currentValue = get();
    if (currentValue != oldValue) {
      oldValue = currentValue;
      return true;
    }
    return false;
  }
}
