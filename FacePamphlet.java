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
        setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
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
            canvas.displayProfile(db.getProfile(name));
            canvas.showMessage("A profile with the name " + name + " already exists");
        } else {
            FacePamphletProfile profile = new FacePamphletProfile(name);
            db.addProfile(profile);
            canvas.displayProfile(profile);
            canvas.showMessage("New profile created");
        }
        activeProfile = db.getProfile(name);
    }

    private void handleProfileDelete(String name) {
        if (db.containsProfile(name)) {
            db.deleteProfile(name);
            canvas.showMessage("Profile of " + name + " deleted");
        } else {
            canvas.showMessage("A profile with the name " + name + " does not exist");
        }
        canvas.removeAll();
        activeProfile = null;
    }

    private void handleProfileLookup(String name) {
        if (db.containsProfile(name)) {
            FacePamphletProfile profile = db.getProfile(name);
            canvas.displayProfile(profile);
            canvas.showMessage("Displaying " + name);
            activeProfile = profile;
        } else {
            canvas.showMessage("A profile with the name " + name + " does not exist");
            canvas.removeAll();
            activeProfile = null;
        }
    }

    private void handleStatusChange() {
        if (activeProfile != null) {
            activeProfile.setStatus(statusField.getText());

            canvas.showMessage("Status updated to " + activeProfile.getStatus());
            canvas.displayProfile(activeProfile);
        } else {
            canvas.showMessage("Please select a profile to change status");
        }
    }

    private void handlePictureChange() {
        if (activeProfile != null) {
            GImage image;
            String imgName = pictureField.getText();
            try {
                image = new GImage(imgName);
                activeProfile.setImage(image);

                canvas.displayProfile(activeProfile);
                canvas.showMessage("Picture updated");
            } catch (ErrorException ex) {
                canvas.showMessage("Unable to open image file: " + imgName);
            }
        } else {
            canvas.showMessage("Please select a profile to change picture");
        }
    }

    private void handleAddFriend() {
        if (activeProfile != null) {
            String friendName = newFriendField.getText();
            String name = activeProfile.getName();
            if (db.containsProfile(friendName)) {
                if (!activeProfile.addFriend(friendName)) {
                    canvas.showMessage(name + " already has " + friendName + " as a friend");
                } else {
                    db.getProfile(friendName).addFriend(activeProfile.getName());
                    canvas.displayProfile(activeProfile);
                    canvas.showMessage(friendName + " added as a friend");
                }
            } else {
                canvas.showMessage(friendName + " does not exist");
            }
        } else {
            canvas.showMessage("Please select a profile to add friend");
        }
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
