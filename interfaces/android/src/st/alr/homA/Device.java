package st.alr.homA;

import java.util.HashMap;

import android.content.Context;
import android.util.Log;

public class Device {
	private String id;
	private String name;
	private Room room;
	private HashMap<String, Control> controls;

	public Room getRoom() {
		return room;
	}

	private Context context;

	public Device(String id, Context context) {
		this(id, null, context);
	}

	public Device(String id, String name, Context context) {
		this.id = id;
		this.name = name;
		controls = new HashMap<String, Control>();
		this.context = context;
	}

	void removeFromCurrentRoom() {

		if (room != null) {
			room.removeDevice(this);

			if (room.getDevices().size() == 0) {
				Log.v(toString(), "Room " + room.getId() + " is empty, removing it");
				App.removeRoom(room);
			}
		}

	}

	void moveToRoom(String roomname) {
		String cleanedName = (roomname != null) && !roomname.equals("") ? roomname : context.getString(R.string.defaultRoomName);

		Room newRoom = App.getRoom(cleanedName);

		if (newRoom == null) {
			newRoom = new Room(context, cleanedName);
			App.addRoom(newRoom);
		}

		removeFromCurrentRoom();
		newRoom.addDevice(this);
		room = newRoom;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Control getControlWithId(String id) {
		return controls.get(id);
	}

	public void addControl(Control control) {
		controls.put(control.getId(), control);
		// TODO: Update view to reflect newly added device
	}

	public HashMap<String, Control> getControls() {
		return controls;
	}

	@Override
	public String toString() {
		return (name != null) && !name.equals("") ? name : id;
	}

}