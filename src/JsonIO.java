import java.util.HashMap;
import java.util.Map;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import utils.Pair;

//System.out.println(topology.getDevices().get(1).getType());
//System.out.println(topology.getDevices().get(1).getId());
//System.out.println(topology.getDevices().get(1).getCharacteristics());
//System.out.println(topology.getDevices().get(1).getNetlist());

public class JsonIO {

	public JsonIO() {
		// TODO Auto-generated constructor stub
	}

	public static Topology ReadFromJson(String FilePath) throws IOException {
		
		String JsonString = new String(Files.readAllBytes(Paths.get(FilePath)));
		Topology topology = new Gson().fromJson(JsonString, Topology.class);
		
		Map<String, Object> jsonTopology = new Gson().fromJson(JsonString, Map.class);
		ArrayList<Object> devices = (ArrayList<Object>) jsonTopology.get("components");
		ArrayList<Device> EditedDevices = new ArrayList<Device>();
		
		int deviceIndex = 0;
		for (Iterator<Object> iterator = devices.iterator(); iterator.hasNext();) {
			Object object = iterator.next();
			Device d = topology.getComponents().get(deviceIndex);
			Pair<String,HashMap<String, String>> DeviceCharacteristics = GetCharacteristicsFromJsonString((String) new Gson().toJson(object));
			d.setCharacteristics(DeviceCharacteristics.getSecond());
			d.setCharacteristicsName(DeviceCharacteristics.getFirst());
			EditedDevices.add(d);
			deviceIndex++;
		}
		
		topology.setComponents(EditedDevices);
		return topology;
	}
	
	public static void WriteToJson(String FilePath,Topology topology) throws IOException {
		String JsonString = getJsonStringFromTopology(topology);
		BufferedWriter writer = new BufferedWriter(new FileWriter(FilePath));
        writer.write(JsonString);
        writer.close();
        
//		System.out.println(JsonString);
	}
	private static String getJsonStringFromTopology(Topology topology) {
		
		ArrayList<Object> componentsMap = new ArrayList<>();
		ArrayList<Device> CurrentDevices = topology.getComponents();
		
		for (Iterator<Device> iterator = CurrentDevices.iterator(); iterator.hasNext();) {
			Device device = (Device) iterator.next();
			HashMap<String, Object> deviceMap = new HashMap<>();
			deviceMap.put("id", device.getId());
            deviceMap.put("type", device.getType());
            deviceMap.put("netlist", device.getNetlist());
            deviceMap.put(device.getCharacteristicsName(), device.getCharacteristics());
            componentsMap.add(deviceMap);
		}
		
		HashMap<String, Object> topologyMap = new HashMap<>();
        topologyMap.put("id", topology.getID());
        topologyMap.put("components",componentsMap);
        
        return new GsonBuilder().setPrettyPrinting().create().toJson(topologyMap);
	}
	private static Pair<String,HashMap<String, String>> GetCharacteristicsFromJsonString(String JsonString) {
		Map <String, Object> jsonDevice = new Gson().fromJson(JsonString, Map.class);
		HashMap<String, String> characteristics = null;
		String CharacteristicsName = "";
		for (Map.Entry<String, Object> field: jsonDevice.entrySet()) {
				switch (field.getKey()) {
	            case "type" :
	            	break;
	            case "id" :
	            	break;
	            case "netlist" :
	            	break;
	            default :
	            	String value = field.getValue().toString();
	            	CharacteristicsName = field.getKey();
	            	characteristics = new Gson().fromJson(value, HashMap.class);
	        }
        }
		return new Pair<String,HashMap<String, String>>(CharacteristicsName,characteristics);
	}

}
