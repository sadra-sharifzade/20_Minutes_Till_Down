package com.tillDown.Controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.tillDown.Main;
import com.tillDown.Models.GameAssetManager;
import com.tillDown.Models.User;
import com.tillDown.Views.ProfileMenuView;
import com.tillDown.Views.SignupMenuView;

import java.io.File;
import java.io.IOException;

public class ProfileMenuController {
    private ProfileMenuView view;

    public ProfileMenuController(ProfileMenuView view) {
        this.view = view;
    }

    public void showChangeUsernameDialog() {
        Skin skin = GameAssetManager.getGameAssetManager().getSkin();
        Dialog dialog = new Dialog("Change Username", skin) {
            @Override
            protected void result(Object object) {
            }
        };
        dialog.pad(20);

        TextField usernameField = new TextField("", skin);
        Label errorLabel = new Label("", skin);
        errorLabel.setColor(Color.RED);

        TextButton confirmButton = new TextButton("Confirm", skin);
        confirmButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String newUsername = usernameField.getText().trim();

                if (newUsername.isEmpty()) {
                    errorLabel.setText("Username can't be empty.");
                } else if (Main.getUserByUsername(newUsername) != null) {
                    errorLabel.setText("Username is already taken.");
                } else if (newUsername.equals(Main.getCurrentUser().getUsername())) {
                    errorLabel.setText("Your username is already " + newUsername);
                } else {
                    Main.getCurrentUser().setUsername(newUsername);
                    errorLabel.setText("Username changed successfully.");
                    errorLabel.setColor(Color.GREEN);
                    showOKButton(dialog, skin);
                }
            }
        });

        dialog.getContentTable().add(new Label("New Username:", skin)).left().row();
        dialog.getContentTable().add(usernameField).width(500).row();
        dialog.getContentTable().add(errorLabel).width(800).center().row();
        dialog.getButtonTable().add(confirmButton).padTop(10);
        dialog.button("Cancel", false);
        dialog.show(view.getStage());
    }

    public void showChangePasswordDialog() {
        Skin skin = GameAssetManager.getGameAssetManager().getSkin();
        Dialog dialog = new Dialog("Change Password", skin) {
            @Override
            protected void result(Object object) {
            }
        };

        dialog.pad(20);

        TextField passwordField = new TextField("", skin);

        Label messageLabel = new Label("", skin);
        messageLabel.setColor(Color.RED);

        TextButton confirmButton = new TextButton("Confirm", skin);
        confirmButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String password = passwordField.getText().trim();

                if (password.length() < 8) messageLabel.setText("Password must be at least 8 characters!");
                else if (!password.matches(".*[@#$%&*()_]+.*"))
                    messageLabel.setText("Password should contain at least one of this characters: @#$%&*()_!");
                else if (!password.matches(".*\\d+.*"))
                    messageLabel.setText("Password should contain at least one digit");
                else if (!password.matches(".*[A-Z]+.*"))
                    messageLabel.setText("Password should contain at least one uppercase letter");
                else {
                    Main.getCurrentUser().setPassword(password);
                    messageLabel.setColor(Color.GREEN);
                    messageLabel.setText("Password changed successfully!");
                    showOKButton(dialog, skin);
                }
            }
        });

        dialog.getContentTable().add(new Label("New Password:", skin)).left().row();
        dialog.getContentTable().add(passwordField).width(800).row();
        dialog.getContentTable().add(messageLabel).width(1000).center().row();
        dialog.getButtonTable().add(confirmButton).padTop(10);
        dialog.button("Cancel", false);
        dialog.show(view.getStage());

    }

    private static void showOKButton(Dialog dialog, Skin skin) {
        if (dialog.getButtonTable().findActor("okButton") == null) {
            TextButton okButton = new TextButton("OK", skin, "default");
            okButton.setName("okButton");
            okButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    dialog.hide();
                }
            });
            dialog.getButtonTable().add(okButton).padTop(10);
        }
    }

    public void showDeleteAccountDialog() {
        Skin skin = GameAssetManager.getGameAssetManager().getSkin();
        Dialog confirmDialog = new Dialog("Confirm", skin);
        confirmDialog.text("Are you sure you want to delete your account?");

        TextButton yesButton = new TextButton("Yes", skin);
        TextButton noButton = new TextButton("No", skin);

        yesButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.removeUser(Main.getCurrentUser());
                Main.setCurrentUser(null);
                Main.getMain().setScreen(new SignupMenuView());
                confirmDialog.hide();
            }
        });

        noButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                confirmDialog.hide();
            }
        });

        confirmDialog.getButtonTable().add(yesButton).pad(10);
        confirmDialog.getButtonTable().add(noButton).pad(10);
        confirmDialog.show(view.getStage());
    }

    public void showChangeAvatarDialog() {
        Skin skin = GameAssetManager.getGameAssetManager().getSkin();
        Dialog dialog = new Dialog("Change Profile Picture", skin);

        TextButton predefinedBtn = new TextButton("Predefined", skin);
        TextButton fileBtn = new TextButton("Choose File", skin);
        TextButton dragBtn = new TextButton("Drag & Drop", skin);
        TextButton cancelBtn = new TextButton("Cancel", skin);

        predefinedBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dialog.hide();
                showPredefinedAvatarsDialog();
            }
        });

        fileBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dialog.hide();
                openFileChooserForAvatar();
            }
        });

        dragBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dialog.hide();
                showDragAndDropDialog();
            }
        });

        cancelBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dialog.hide();
            }
        });

        dialog.getButtonTable().add(predefinedBtn).pad(5).row();
        dialog.getButtonTable().add(fileBtn).pad(5).row();
        dialog.getButtonTable().add(dragBtn).pad(5).row();
        dialog.getButtonTable().add(cancelBtn).pad(5).row();

        dialog.show(view.getStage());

    }

    private void showDragAndDropDialog() {
        Skin skin = GameAssetManager.getGameAssetManager().getSkin();
        Dialog dialog = new Dialog("Drag And Drop", skin);
        Label label = new Label("", skin);
        dialog.getContentTable().add(label).width(800).height(600).center().row();
        view.setDragAndDropDialog(dialog);
        dialog.show(view.getStage());
    }

    private void showPredefinedAvatarsDialog() {
        Skin skin = GameAssetManager.getGameAssetManager().getSkin();
        Dialog dialog = new Dialog("Choose Avatar", skin);

        Table grid = new Table();
        grid.defaults().pad(5);

        for (int i = 1; i <= 10; i++) {
            final String avatarFile = i + ".png";
            final Image avatar = new Image(new Texture(Gdx.files.internal("avatars/" + avatarFile)));
            avatar.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Main.getCurrentUser().setAvatarName(avatarFile);
                    dialog.hide();
                }
            });
            grid.add(avatar).size(64f,64f);
            if (i % 5 == 0) grid.row();
        }

        dialog.getContentTable().add(grid).pad(10);
        dialog.button("Cancel");
        dialog.show(view.getStage());
    }

    private void openFileChooserForAvatar() {
        java.awt.FileDialog dialog = new java.awt.FileDialog((java.awt.Frame) null, "Select Avatar");
        dialog.setMode(java.awt.FileDialog.LOAD);
        dialog.setVisible(true);

        String dir = dialog.getDirectory();
        String file = dialog.getFile();
        if (file != null) {
            File avatarFile = new File(dir, file);
            try {
                // You can copy this to internal folder if needed
                FileHandle source = Gdx.files.absolute(avatarFile.getAbsolutePath());
                FileHandle target = Gdx.files.local("assets/avatars/" + file);
                source.copyTo(target);
                Main.getCurrentUser().setAvatarName(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void handleDroppedFiles(String[] files) {
        Gdx.app.postRunnable(() -> {
            for (String filePath : files) {
                try {
                    User currentUser = Main.getCurrentUser();
                    if (filePath.toLowerCase().endsWith(".png") ||
                        filePath.toLowerCase().endsWith(".jpg")) {
                        FileHandle source = Gdx.files.absolute(filePath);
                        int length = filePath.length();
                        FileHandle target = Gdx.files.local("assets/avatars/" + (100+currentUser.getId()) + filePath.toLowerCase().substring(length-4));
                        source.copyTo(target);
                        currentUser.setAvatarName((100+currentUser.getId()) + ".png");
                        view.getDragAndDropDialog().hide();
                    }
                } catch (Exception e) {
                    Gdx.app.error("FileDrop", "Error processing file: " + filePath, e);
                }
            }
        });
    }
}
