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

public class FacePamphlet extends ConsoleProgram
					implements FacePamphletConstants {

	/**
	 * This method has the responsibility for initializing the
	 * interactors in the application, and taking care of any other
	 * initialization that needs to be performed.
	 */
	public void init() {
        db = new FacePamphletDatabase();
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
            if (db.containsProfile(name)) {
                println("Add: profile for " + name + " already exists: " + db.getProfile(name).toString());
            } else {
                FacePamphletProfile profile = new FacePamphletProfile(name);
                db.addProfile(profile);
                println("Add: new profile: " + db.getProfile(name).toString());
            }
            break;
        case "Delete":
            if (db.containsProfile(name)) {
                db.deleteProfile(name);
                println("Delete: profile of " + name + " deleted");
            } else {
                println("Delete: profile with the name " + name + " does not exist");
            }
            break;
        case "Lookup":
            if (db.containsProfile(name)) {
                println("Lookup: " + db.getProfile(name).toString());
            } else {
                println("Lookup: profile with name " + name + " does not exist");
            }
            break;
        case "Change Status":
            println(cmd + ": " + statusField.getText());
            break;
        case "Change Picture":
            println(cmd + ": " + pictureField.getText());
            break;
        case "Add Friend":
            println(cmd + ": " + newFriendField.getText());
            break;
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
        statusField.setActionCommand("Change Picture");
        pictureField.addActionListener(this);
        add(new JLabel(EMPTY_LABEL_TEXT), WEST); // leave empty space

        newFriendField = new JTextField("", TEXT_FIELD_SIZE);
        statusField.setActionCommand("Add Friend");
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
    JTextField nameField;
    JTextField statusField;
    JTextField pictureField;
    JTextField newFriendField;

    FacePamphletDatabase db;
    FacePamphletProfile activeProfile;
}
