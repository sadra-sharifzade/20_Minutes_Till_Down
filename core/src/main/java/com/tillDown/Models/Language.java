package com.tillDown.Models;

public enum Language {
    ENGLISH("You Upgraded To Level ","! Choose From Below Abilities:",", You Won!",", You Lost!",
            "Score: ","Kills: ","Time Alive: ","Back to Main Menu","LEVEL ","Number of Killed Enemies: ",
            "Login","Forget Password","Back To Signup","Confirm","where were you born?","Enter your username and answer the security question",
            "Username:","Password:","Resume Last Game","Settings","Profile","Scoreboard","Create A New Game",
            "Talent","Sign Out","Resume","Cheat Codes","Gained Abilities","Quit & Save","Quit & Giveup"," Black & White",
            "Back","Start Game","Pre-Game Menu","Choose Hero:","Choose Weapon: ","Choose Game Time:",
            "Change Username","Change Password","Delete Account","Change Avatar","Choose Music:","Volume","Press a key...",
            "Sign Up","Play as Guest","Hints","Heroes Info","Key Bindings","Abilities Info","Choose Language"),
    GERMAN("Du bist aufgestiegen auf Level ","! Wähle eine Fähigkeit unten aus:",", Du hast gewonnen!",", Du hast verloren!",
           "Punktzahl: ","Kills: ","Überlebenszeit: ","Zurück zum Hauptmenü","LEVEL ","Anzahl besiegter Feinde: ",
           "Anmelden","Passwort vergessen","Zurück zur Registrierung","Bestätigen","Wo wurdest du geboren?","Gib deinen Benutzernamen ein und beantworte die Sicherheitsfrage",
           "Benutzername:","Passwort:","Letztes Spiel fortsetzen","Einstellungen","Profil","Bestenliste","Neues Spiel erstellen",
           "Talent","Abmelden","Fortsetzen","Cheat-Codes","Erhaltene Fähigkeiten","Beenden & Speichern","Beenden & Aufgeben"," Schwarz-Weiß",
           "Zurück","Spiel starten","Vorbereitungsmenü","Wähle Held:","Wähle Waffe: ","Wähle Spielzeit:",
           "Benutzernamen ändern","Passwort ändern","Konto löschen","Avatar ändern","Wähle Musik:","Lautstärke","Drücke eine Taste...",
           "Registrieren","Als Gast spielen","Tipps","Heldeninfo","Tastenbelegung","Fähigkeiteninfo","Sprache wählen"
    );
    public String youUpgraded;
    public String chooseFrom;
    public String won;
    public String lost;
    public String score;
    public String kills;
    public String timeAlive;
    public String backToMainMenu;
    public String level;
    public String numberOfKilledEnemies;
    public String login;
    public String forgotPassword;
    public String backToSignUp;
    public String confirm;
    public String whereWereYouBorn;
    public String enterYourUsername;
    public String username;
    public String password;
    public String resumeLastGame;
    public String settings;
    public String profile;
    public String scoreboard;
    public String createANewGame;
    public String talent;
    public String signOut;
    public String resume;
    public String cheatCodes;
    public String gainedAbilities;
    public String quitAndSave;
    public String quitAndGiveUp;
    public String blackAndWhite;
    public String back;
    public String startGame;
    public String preGameMenu;
    public String chooseHero;
    public String chooseWeapon;
    public String chooseGameTime;
    public String changeUsername;
    public String changePassword;
    public String deleteAccount;
    public String changeAvatar;
    public String chooseMusic;
    public String volume;
    public String pressAKey;
    public String signUp;
    public String playAsGuest;
    public String hints;
    public String heroesInfo;
    public String keyBindings;
    public String abilitiesInfo;
    public String chooseLanguage;

    Language(String youUpgraded, String chooseFrom, String won, String lost, String score, String kills,
             String timeAlive, String backToMainMenu, String level, String numberOfKilledEnemies, String login,
             String forgotPassword, String backToSignUp, String confirm, String whereWereYouBorn,
             String enterYourUsername, String username, String password, String resumeLastGame, String settings,
             String profile, String scoreboard, String createANewGame, String talent, String signOut, String resume,
             String cheatCodes, String gainedAbilities, String quitAndSave, String quitAndGiveUp,
             String blackAndWhite, String back, String startGame, String preGameMenu, String chooseHero,
             String chooseWeapon, String chooseGameTime, String changeUsername, String changePassword,
             String deleteAccount, String changeAvatar, String chooseMusic, String volume, String pressAKey,
             String signUp, String playAsGuest, String hints, String heroesInfo, String keyBindings,
             String abilitiesInfo,String chooseLanguage) {
        this.youUpgraded = youUpgraded;
        this.chooseFrom = chooseFrom;
        this.won = won;
        this.lost = lost;
        this.score = score;
        this.kills = kills;
        this.timeAlive = timeAlive;
        this.backToMainMenu = backToMainMenu;
        this.level = level;
        this.numberOfKilledEnemies = numberOfKilledEnemies;
        this.login = login;
        this.forgotPassword = forgotPassword;
        this.backToSignUp = backToSignUp;
        this.confirm = confirm;
        this.whereWereYouBorn = whereWereYouBorn;
        this.enterYourUsername = enterYourUsername;
        this.username = username;
        this.password = password;
        this.resumeLastGame = resumeLastGame;
        this.settings = settings;
        this.profile = profile;
        this.scoreboard = scoreboard;
        this.createANewGame = createANewGame;
        this.talent = talent;
        this.signOut = signOut;
        this.resume = resume;
        this.cheatCodes = cheatCodes;
        this.gainedAbilities = gainedAbilities;
        this.quitAndSave = quitAndSave;
        this.quitAndGiveUp = quitAndGiveUp;
        this.blackAndWhite = blackAndWhite;
        this.back = back;
        this.startGame = startGame;
        this.preGameMenu = preGameMenu;
        this.chooseHero = chooseHero;
        this.chooseWeapon = chooseWeapon;
        this.chooseGameTime = chooseGameTime;
        this.changeUsername = changeUsername;
        this.changePassword = changePassword;
        this.deleteAccount = deleteAccount;
        this.changeAvatar = changeAvatar;
        this.chooseMusic = chooseMusic;
        this.volume = volume;
        this.pressAKey = pressAKey;
        this.signUp = signUp;
        this.playAsGuest = playAsGuest;
        this.hints = hints;
        this.heroesInfo = heroesInfo;
        this.keyBindings = keyBindings;
        this.abilitiesInfo = abilitiesInfo;
        this.chooseLanguage = chooseLanguage;
    }
}
