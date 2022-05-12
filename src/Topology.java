import java.util.ArrayList;

public class Topology {
	
	private String id;
	private ArrayList<Device> components;
	
	public Topology(String id,ArrayList<Device> components) {
		// TODO Auto-generated constructor stub
		this.setID(id);
		this.setComponents(components);
	}
	public void setID(String id) {
		this.id = id;
	}
	public String getID() {
		return this.id;
	}
	public ArrayList<Device> getComponents() {
		return components;
	}
	public void setComponents(ArrayList<Device> components) {
		this.components = components;
	}
	
}
