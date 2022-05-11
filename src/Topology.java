import java.util.ArrayList;

public class Topology {
	
	private String ID;
	private ArrayList<Device> devices;
	
	public Topology() {
		// TODO Auto-generated constructor stub
	}
	public void setID(String id) {
		this.ID = id;
	}
	public String getID() {
		return this.ID;
	}
	public ArrayList<Device> getDevices() {
		return devices;
	}
	public void setDevices(ArrayList<Device> devices) {
		this.devices = devices;
	}
}
