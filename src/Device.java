import java.util.HashMap;

public class Device {
	
	private String type;
	private String id;
	private HashMap<String, String> characteristics; 
	private HashMap<String, String> netlist;
	
	public Device(String type,String id,HashMap<String, String> characteristics) {
		this.setType(type);
		this.setId(id);
		this.setCharacteristics(characteristics);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public HashMap<String, String> getCharacteristics() {
		return characteristics;
	}

	public void setCharacteristics(HashMap<String, String> characteristics) {
		this.characteristics = characteristics;
	}

	public HashMap<String, String> getNetlist() {
		return netlist;
	}

	public void setNetlist(HashMap<String, String> netlist) {
		this.netlist = netlist;
	}

}
