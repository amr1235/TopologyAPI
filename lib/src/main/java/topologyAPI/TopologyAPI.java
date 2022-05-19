package topologyAPI;

import java.io.IOException;
import java.util.ArrayList;

public class TopologyAPI {

	private TopologyAPI() {
	}
	
	// Read a topology from a given JSON file and store it in the memory 
	public static void readJSON(String FilePath) throws IOException {
		try {	
			// convert JSON to Topology Object
			Topology topology = JsonIO.ReadFromJson(FilePath);
			// check if the topology already exists 
			if(findTopology(topology.getID()) != null) {
				return;
			}
			// store the topology to the database
			DataBase.topologies.add(topology);
			
		} catch(Exception e) {
			System.out.println("Something went wrong...");
		}
	}
	
	// Read a topology from a given JSON file and store it in the memory 
	public static void writeJSON(String TopologyId,String FilePath) throws IOException {
		Topology topology = findTopology(TopologyId);
		if(topology != null) {
			JsonIO.WriteToJson(FilePath, topology);
		}else {
			System.out.println("Toplogy Not Found...");
		}
	}
	
	// Query about which topologies are currently in the memory 
	public static ArrayList<Topology> queryTopologies() {
		return DataBase.topologies;
	}
	
	// Delete a given topology from memory
	public static void deleteTopology(String TopologyId) {
		Topology topology = findTopology(TopologyId);
		if(topology != null) {
			try {
				DataBase.topologies.remove(topology);
			} catch(Exception e) {
				System.out.println("Something went wrong...");
				System.out.println(e);
			}
		}else {
			System.out.println("Topology Not Found...");
		}
	}
	
	// Query about which devices are in a given topology 
	public static ArrayList<Device> queryDevices(String TopologyId){
		Topology topology = findTopology(TopologyId);
		if(topology != null) {
			return topology.getComponents();
		}else {
			System.out.println("Topology Not Found...");
			return null;
		}
	}
	
	// Query about which devices are connected to a given netlist node in a given topology
	public static ArrayList<Device> queryDevicesWithNetListNode(String TopologyId,String NetListNodeID) {
		
		Topology topology = findTopology(TopologyId);
		if(topology != null) {
			return findDevicesWithTheSameNode(topology,NetListNodeID);
		}else {
			System.out.println("Topology Not Found...");
			return null;
		}
		
	}
	
	// check for topology matches the same ID
	private static Topology findTopology(String TopologyId) {
		for(var topology:DataBase.topologies) {
			if(topology.getID().equals(TopologyId)) {
				return topology;
			}
		}
		return null; // return null if there is no topology found
	}
	
	// check for devices matches the same node
	private static ArrayList<Device> findDevicesWithTheSameNode(Topology topology,String NetlistNodeID){
		ArrayList<Device> devices = topology.getComponents();
		ArrayList<Device> finalDevices = new ArrayList<Device>();
		
		for(var device : devices) {
			if(device.getNetlist().containsValue(NetlistNodeID)) {
				finalDevices.add(device);
			}
		}
		
		return finalDevices;
	}
	
}
