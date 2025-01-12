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
        addInteractors();
        addActionListeners();
    }

    /**
     * This class is responsible for detecting when the buttons are
     * clicked or interactors are used, so you will have to add code
     * to respond to these actions.
     */
    public void actionPerformed(ActionEvent e) {
	}

    // adds all the interactors for the GUI
    private void addInteractors() {
        addNorthInteractors();
        addWestInteractors();
    }

    private void addNorthInteractors() {
        JLabel nameLabel = new JLabel("Name");
        JTextField textField = new JTextField("", TEXT_FIELD_SIZE);
        textField.addActionListener(this);

        add(nameLabel, NORTH);
        add(textField, NORTH);
        add(new JButton("Add"), NORTH);
        add(new JButton("Delete"), NORTH);
        add(new JButton("Lookup"), NORTH);
    }

    private void addWestInteractors() {
        JTextField statusField = new JTextField("", TEXT_FIELD_SIZE);
        statusField.setActionCommand("Change Status");
        statusField.addActionListener(this);
        add(new JLabel(EMPTY_LABEL_TEXT), WEST); // leave empty space

        JTextField pictureField = new JTextField("", TEXT_FIELD_SIZE);
        statusField.setActionCommand("Change Picture");
        pictureField.addActionListener(this);
        add(new JLabel(EMPTY_LABEL_TEXT), WEST); // leave empty space

        JTextField newFriendField = new JTextField("", TEXT_FIELD_SIZE);
        statusField.setActionCommand("Add Friend");
        newFriendField.addActionListener(this);

        add(statusField, WEST);
        add(new JButton("Change Status"), WEST);
        add(pictureField, WEST);
        add(new JButton("Change Picture"), WEST);
        add(newFriendField, WEST);
        add(new JButton("Add Friend"), WEST);
    }
}
