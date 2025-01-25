/*
 * File: FacePamphletCanvas.java
 * -----------------------------
 * This class represents the canvas on which the profiles in the social
 * network are displayed.  NOTE: This class does NOT need to update the
 * display when the window is resized.
 */


import acm.graphics.*;
import java.awt.*;
import java.util.*;

public class FacePamphletCanvas extends GCanvas
					implements FacePamphletConstants {

	/**
	 * Constructor
	 * This method takes care of any initialization needed for
	 * the display
	 */
	public FacePamphletCanvas() {
	}


	/**
	 * This method displays a message string near the bottom of the
	 * canvas.  Every time this method is called, the previously
	 * displayed message (if any) is replaced by the new message text
	 * passed in.
	 */
	public void showMessage(String msg) {
        if (msgLabel != null) {
            remove(msgLabel); // remove old label if it exists
        }
        msgLabel = new GLabel(msg);
        msgLabel.setFont(MESSAGE_FONT);
        add(msgLabel, (getWidth() - msgLabel.getWidth()) / 2.0, getHeight() - BOTTOM_MESSAGE_MARGIN);
	}


	/**
	 * This method displays the given profile on the canvas.  The
	 * canvas is first cleared of all existing items (including
	 * messages displayed near the bottom of the screen) and then the
	 * given profile is displayed.  The profile display includes the
	 * name of the user from the profile, the corresponding image
	 * (or an indication that an image does not exist), the status of
	 * the user, and a list of the user's friends in the social network.
	 */
	public void displayProfile(FacePamphletProfile profile) {
        drawProfileName(profile.getName());
        drawProfilePicture(profile.getImage());
        drawProfileStatus(profile.getName(), profile.getStatus());
        drawFriends(profile.getFriends());
	}

    // draw the friend labels
    private void drawFriends(Iterator<String> friends) {
        GLabel headerLabel = new GLabel("Friends:");
        headerLabel.setFont(PROFILE_FRIEND_LABEL_FONT);
        add(headerLabel, getWidth() / 2.0, TOP_MARGIN);

        int friendNum = 1;
        double headerHeight = headerLabel.getHeight();
        while (friends.hasNext()) {
            String friend = friends.next();
            GLabel friendLabel = new GLabel(friend);
            friendLabel.setFont(PROFILE_FRIEND_FONT);
            add(friendLabel, getWidth() / 2.0, headerHeight + friendNum * headerHeight);

            friendNum++;
        }
    }

    // draw profile status if it's not empty
    private void drawProfileStatus(String name, String status) {
        GLabel statusLabel = new GLabel("No current status");
        if (!status.isEmpty()) {
            statusLabel.setLabel(name + " is " + status);
        }
        statusLabel.setFont(PROFILE_STATUS_FONT);
        add(statusLabel, LEFT_MARGIN, TOP_MARGIN + IMAGE_HEIGHT + STATUS_MARGIN);
    }

    // draw the profile name
    private void drawProfileName(String name) {
        GLabel nameLabel = new GLabel(name);
        nameLabel.setColor(Color.BLUE);
        nameLabel.setFont(PROFILE_NAME_FONT);

        add(nameLabel, LEFT_MARGIN, TOP_MARGIN);
    }

    // draw the profile picture (or a placeholder)
    private void drawProfilePicture(GImage img) {
        if (img != null) {
            img.scale(IMAGE_WIDTH / img.getWidth(), IMAGE_HEIGHT / img.getHeight());
            add(img, LEFT_MARGIN, TOP_MARGIN + IMAGE_MARGIN);
            return;
        }

        GRect frame = new GRect(IMAGE_WIDTH, IMAGE_HEIGHT);
        GLabel imgText = new GLabel("No Image");
        imgText.setFont(PROFILE_IMAGE_FONT);

        double textX = LEFT_MARGIN + (IMAGE_WIDTH - imgText.getWidth()) / 2.0;
        double textY = TOP_MARGIN + (IMAGE_HEIGHT - imgText.getAscent()) / 2.0;
        add(imgText, textX, textY);
        add(frame, LEFT_MARGIN, TOP_MARGIN);
    }

    /* INSTANCE VARIABLES */
    GLabel msgLabel;
}
