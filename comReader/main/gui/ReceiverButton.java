package gui;

import gui.enumeration.ReceiverButtonState;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import utilities.Utilities;
import components.Receiver;

/**
 * This class is used to add or remove <code>Receiver</code> to/from <code>MapPreviewPanel</code>.
 * 
 * @author Danilo
 * @see Receiver
 */
public class ReceiverButton extends JButton {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant PLUS_IMAGE. */
	private static final String PLUS_IMAGE = "images/plus.png";

	/** The Constant MINUS_IMAGE. */
	private static final String MINUS_IMAGE = "images/minus.png";

	/** The add receiver to map image. */
	private static Image addReceiverToMapImage;

	/** The remove receiver from map image. */
	private static Image removeReceiverFromMapImage;

	/** The receiver. */
	private Receiver receiver;

	/** The state. */
	private ReceiverButtonState state;

	/** <code>Logger</code> object. */
	@SuppressWarnings("unused")
	private Logger logger;

	/** The add map dialog. */
	private AddMapDialog addMapDialog;

	/**
	 * Instantiates a new <code>ReceiverButton</code>.
	 * 
	 * @param receiver
	 *            <code>Receiver</code> model
	 * @param parent
	 *            <code>AddMapDialog</code> instance
	 */
	public ReceiverButton(Receiver receiver, AddMapDialog parent) {

		this.receiver = receiver;
		this.addMapDialog = parent;
		initialize();
	}

	/**
	 * Initializes the <code>ReceiverButton</code>.
	 */
	private void initialize() {
		logger = Utilities.initializeLogger(this.getClass().getName());
		ReceiverButtonState buttonState = (receiver.isOnMap()) ? ReceiverButtonState.REMOVE : ReceiverButtonState.ADD;
		addActionListener(new ReceiverButtonListener());
		addReceiverToMapImage = Utilities.loadImage(PLUS_IMAGE);
		removeReceiverFromMapImage = Utilities.loadImage(MINUS_IMAGE);
		setState(buttonState);
	}

	/**
	 * Gets the state.
	 * 
	 * @return the state
	 */
	public ReceiverButtonState getState() {
		return state;
	}

	/**
	 * Sets the state.
	 * 
	 * @param state
	 *            the new state
	 */
	public void setState(ReceiverButtonState state) {
		this.state = state;
		refreshIcon();
	}

	/**
	 * Refresh icon.
	 */
	private void refreshIcon() {

		if (state == ReceiverButtonState.ADD) {
			this.setText(receiver.getID() + "");
			this.setIcon(new ImageIcon(addReceiverToMapImage));
		} else {
			this.setText(receiver.getID() + "");
			this.setIcon(new ImageIcon(removeReceiverFromMapImage));
		}
	}

	/**
	 * Toggles between the two states. It can be in state <code>ADD</code> or state <code>REMOVE</code>. <br>
	 * <br>
	 * <ul>
	 * <li>In state <code>ADD</code>, receiver is not on the map and button has '+' sign on it.</li>
	 * <li>In state <code>REMOVE</code>, receiver is on the map and button has '-' sign on it.</li>
	 * </ul>
	 * 
	 * @see ReceiverButtonState
	 */
	private void toggle() {

		if (this.state == ReceiverButtonState.ADD) {

			if (this.addMapDialog.getMapPreviewPanel().getBackgroundImage() == null) {
				return;
			}
			addMapDialog.addReceiverToMap(receiver);

		} else { // it is ReceiverButtonState.REMOVE

			addMapDialog.removeReceiverFromMap(receiver);
		}

		ReceiverButtonState newState = (this.state == ReceiverButtonState.ADD) ? ReceiverButtonState.REMOVE
				: ReceiverButtonState.ADD;
		this.setState(newState);
		refreshIcon();

	}

	/**
	 * The listener interface for receiving receiverButton events. The class that is interested in processing a
	 * receiverButton event implements this interface, and the object created with that class is registered with a
	 * component using the component's <code>addReceiverButtonListener</code> method. When the receiverButton event
	 * occurs, that object's appropriate method is invoked.
	 * 
	 * @see ReceiverButtonEvent
	 */
	private class ReceiverButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (!(e.getSource() instanceof ReceiverButton)) { // not a button
				return;
			}
			ReceiverButton button = (ReceiverButton) e.getSource();
			button.toggle();
		}
	}

}
