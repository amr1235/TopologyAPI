package topologyAPI;

import java.util.HashMap;


import topologyAPI.utils.Equal;

public class Device {
	
	private String type;
	private String id;
	private String CharacteristicsName = "";
	private HashMap<String, String> characteristics;
	private HashMap<String, String> netlist;
	
	public Device(String type,String id,
			HashMap<String, String> characteristics,
			HashMap<String, String> netlist) {
		
		this.setType(type);
		this.setId(id);
		this.setCharacteristics(characteristics);
		this.setNetlist(netlist);
		
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

	public HashMap<String, String> getNetlist() {
		return netlist;
	}

	public void setNetlist(HashMap<String, String> netlist) {
		this.netlist = netlist;
	}

	public HashMap<String, String> getCharacteristics() {
		return characteristics;
	}

	public void setCharacteristics(HashMap<String, String> characteristics) {
		this.characteristics = characteristics;
	}

	public String getCharacteristicsName() {
		return CharacteristicsName;
	}

	public void setCharacteristicsName(String characteristicsName) {
		CharacteristicsName = characteristicsName;
	}
	
	@Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        Device device = (Device) obj;
        return type.equals(device.getType()) &&
                id.equals(device.getId()) &&
                Equal.EqualHash(characteristics,device.getCharacteristics()) &&
                Equal.EqualHash(netlist,device.getNetlist());
    }

}
