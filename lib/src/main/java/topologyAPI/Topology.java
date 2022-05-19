package topologyAPI;

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
	
	@Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        Topology topology = (Topology) obj;
        return id.equals(topology.getID()) &&
                equalsComponents(topology.getComponents());
    }

    private boolean equalsComponents (ArrayList<Device> components) {
        if (components.size() != this.components.size()) return false;
        for (int i = 0; i < components.size(); ++i) {
            if (!components.get(i).equals(this.components.get(i))) {
                return false;
            }
        }
        return true;
    }
}
