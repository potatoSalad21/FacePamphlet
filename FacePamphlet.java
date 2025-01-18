/*
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import acm.program.*;
import acm.graphics.*;
import acm.util.*;
import java.awt.event.*;
import javax.swing.*;

public class FacePamphlet extends Program
					implements FacePamphletConstants {

	/**
	 * This method has the responsibility for initializing the
	 * interactors in the application, and taking care of any other
	 * initialization that needs to be performed.
	 */
	public void init() {
        db = new FacePamphletDatabase();
        canvas = new FacePamphletCanvas();
        add(canvas);
        addInteractors();
        addActionListeners();
    }

    /**
     * This class is responsible for detecting when the buttons are
     * clicked or interactors are used, so you will have to add code
     * to respond to these actions.
     */
    // TODO remove println calls
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        String name = nameField.getText();

        switch (cmd) {
        case "Add":
            handleProfileAdd(name);
            break;
        case "Delete":
            handleProfileDelete(name);
            break;
        case "Lookup":
            handleProfileLookup(name);
            break;
        case "Change Status":
            handleStatusChange();
            break;
        case "Change Picture":
            handlePictureChange();
            break;
        case "Add Friend":
            handleAddFriend();
            break;
        }
	}

    private void handleProfileAdd(String name) {
        if (db.containsProfile(name)) {
            println("Add: profile for " + name + " already exists: " + db.getProfile(name).toString());
        } else {
            FacePamphletProfile profile = new FacePamphletProfile(name);
            db.addProfile(profile);
            println("Add: new profile: " + profile.toString());
        }
        activeProfile = db.getProfile(name);
        println(activeProfile != null ? ":: > Current profile: " + activeProfile.toString() : ":: > No current profile");
    }

    private void handleProfileDelete(String name) {
        if (db.containsProfile(name)) {
            db.deleteProfile(name);
            println("Delete: profile of " + name + " deleted");
        } else {
            println("Delete: profile with the name " + name + " does not exist");
        }
        activeProfile = null;
        println(activeProfile != null ? ":: > Current profile: " + activeProfile.toString() : ":: > No current profile");
    }

    private void handleProfileLookup(String name) {
        if (db.containsProfile(name)) {
            FacePamphletProfile profile = db.getProfile(name);
            println("Lookup: " + profile.toString());
            activeProfile = profile;
        } else {
            println("Lookup: profile with name " + name + " does not exist");
            activeProfile = null;
        }
        println(activeProfile != null ? ":: > Current profile: " + activeProfile.toString() : ":: > No current profile");
    }

    private void handleStatusChange() {
        if (activeProfile != null) {
            println("Status updated to " + statusField.getText());
            activeProfile.setStatus(statusField.getText());
            println(activeProfile != null ? ":: > Current profile: " + activeProfile.toString() : ":: > No current profile");
        } else {
            println("No active profile; Select the profile first");
        }
    }

    private void handlePictureChange() {
        if (activeProfile != null) {
            GImage image;
            try {
                image = new GImage(pictureField.getText());
                activeProfile.setImage(image);
                println("Change Picture: profile picture changed to: " + pictureField.getText());
            } catch (ErrorException ex) {
                println("Change Picture: Invalid filename");
            }
        } else {
            println("No active profile; Select the profile first");
        }
    }

    private void handleAddFriend() {
        if (activeProfile != null) {
            String friendName = newFriendField.getText();
            if (db.containsProfile(friendName) && activeProfile.getName() != friendName) {
                if (!activeProfile.addFriend(friendName)) {
                    println("Add Friend: Name is already in the friend list");
                } else {
                    db.getProfile(friendName).addFriend(activeProfile.getName());
                    println("Add Friend: Added to friend list");
                }
            } else {
                println("Add Friend: invalid friend name");
            }
        } else {
            println("Add Friend: No active profile; Select the profile first");
        }

        println(activeProfile != null ? ":: > Current profile: " + activeProfile.toString() : ":: > No current profile");
    }

    // adds all the interactors for the GUI
    private void addInteractors() {
        addNorthInteractors();
        addWestInteractors();
    }

    private void addNorthInteractors() {
        JLabel nameLabel = new JLabel("Name");
        nameField = new JTextField("", TEXT_FIELD_SIZE);
        nameField.addActionListener(this);

        add(nameLabel, NORTH);
        add(nameField, NORTH);
        add(new JButton("Add"), NORTH);
        add(new JButton("Delete"), NORTH);
        add(new JButton("Lookup"), NORTH);
    }

    private void addWestInteractors() {
        statusField = new JTextField("", TEXT_FIELD_SIZE);
        statusField.setActionCommand("Change Status");
        statusField.addActionListener(this);
        add(new JLabel(EMPTY_LABEL_TEXT), WEST); // leave empty space

        pictureField = new JTextField("", TEXT_FIELD_SIZE);
        pictureField.setActionCommand("Change Picture");
        pictureField.addActionListener(this);
        add(new JLabel(EMPTY_LABEL_TEXT), WEST); // leave empty space

        newFriendField = new JTextField("", TEXT_FIELD_SIZE);
        newFriendField.setActionCommand("Add Friend");
        newFriendField.addActionListener(this);

        add(statusField, WEST);
        add(new JButton("Change Status"), WEST);
        add(pictureField, WEST);
        add(new JButton("Change Picture"), WEST);
        add(newFriendField, WEST);
        add(new JButton("Add Friend"), WEST);
    }

    /* INSTANCE VARIABLES */

    // GUI components
    private JTextField nameField;
    private JTextField statusField;
    private JTextField pictureField;
    private JTextField newFriendField;

    private FacePamphletCanvas canvas;

    private FacePamphletDatabase db;
    private FacePamphletProfile activeProfile;
}
