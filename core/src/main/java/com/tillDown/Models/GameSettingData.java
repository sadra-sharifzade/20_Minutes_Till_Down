package com.tillDown.Models;

import java.util.Map;

public class GameSettingData {
    public boolean isSFXEnabled = true;
    public boolean isAutoReloadEnabled = true;
    public boolean isBlackAndWhiteEnabled = false;
    public Map<String, Integer> keyBindings;
    public String heroName;
    public String weaponName;
    public int gameTime;
    public float remainingTime;
}

