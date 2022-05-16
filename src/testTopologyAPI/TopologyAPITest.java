package testTopologyAPI;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.io.File;
import java.io.IOException;

import org.junit.Test;

import topologyAPI.Device;
import topologyAPI.JsonIO;
import topologyAPI.Topology;
import topologyAPI.TopologyAPI;

public class TopologyAPITest {
	
	private void setupTopologies() throws IOException {
		String initPath = "src/testTopologyAPI/testJSONS/";
		String JSONFiles[] = {"topology.json","topology2.json","topology3.json",
							  "topology4.json","topology5.json"};
		for (int i = 0; i < JSONFiles.length; i++) {			
			File file = new File(initPath + JSONFiles[i]);
			String path = file.getAbsolutePath();
			TopologyAPI.readJSON(path);
		}
	}
	
	@Test
	public void readJSONTest() throws IOException {
		setupTopologies();
		//check id's
		assertEquals(testIds(), true);
		//check components
		assertEquals(testComponents(), true);

	}
	
	@Test 
	public void queryDevicesTest() throws IOException {
		setupTopologies();
		assertEquals(testDevices(), true);
	}
	
	@Test 
	public void writeJSONTest() throws IOException {
		String initPath = "src/testTopologyAPI/testJSONS/";
		String JSONFiles[] = {"CreatedTopology.json","CreatedTopology2.json",
							  "CreatedTopology3.json",
							  "CreatedTopology4.json","CreatedTopology5.json"};
		
		String ids[] = {"top1","top2","top3","top4","top5"};
		for (int i = 0; i < JSONFiles.length; i++) {			
			// write the topology to JSON file topologies
			File file = new File(initPath + JSONFiles[i]);
			String path = file.getAbsolutePath();
			TopologyAPI.writeJSON(ids[i], path);
			// create Topology out of JSON file 
			Topology CreatedTopology = JsonIO.ReadFromJson(path);
			// compare the created topology with a real topology
			Topology RealTopology = CreateRealTopologie(ids[i]);
			assertEquals(CreatedTopology.equals(RealTopology), true);
		}
	}
	
	@Test
	public void deleteTopologyTest() throws IOException {
		setupTopologies();
		String ids[] = {"top1","top2","top3","top4","top5"};
		boolean passed = true;
		for (int i = 0; i < ids.length; i++) {
			//delete the topology
			TopologyAPI.deleteTopology(ids[i]);
			//check the remaining four
			for (int j = 0; j < ids.length; j++) {
				// check if the deleted topology still exist 
				if((i==j) && (findTopology(ids[j]) != null)) {
					passed = false;
					break;
				}
				if(i == j) continue;
				// check if the remaining still exist
				if(findTopology(ids[j]) == null) {
					passed = false;
					break;
				}
			}
			if(passed == false) break;
			// reconstruct the topologies
			setupTopologies();
		}
		assertEquals(passed, true);
	}
	@Test
	public void queryDevicesWithNetlistNodeTest() {
		ArrayList<Device> TrueDevices = CreateRealDevices();
		ArrayList<Device> CreatedDevices = TopologyAPI.queryDevicesWithNetlistNode("top1","n1");
		// test getting the two devices that are common in n1
		assertEquals(TrueDevices.equals(CreatedDevices),true);
		// test getting one device with v1 node
		Device TrueDevice = CreateRealDevices().get(0);
		CreatedDevices = TopologyAPI.queryDevicesWithNetlistNode("top1","vdd");
		assertEquals((CreatedDevices.size() == 1) && 
				(TrueDevice.equals(CreatedDevices.get(0))),true);
		// test getting one device with vin node
		TrueDevice = CreateRealDevices().get(1);
		CreatedDevices = TopologyAPI.queryDevicesWithNetlistNode("top1","vin");
		assertEquals((CreatedDevices.size() == 1) && 
				(TrueDevice.equals(CreatedDevices.get(0))),true);
		// test enter not existing node
		CreatedDevices = TopologyAPI.queryDevicesWithNetlistNode("top1","emf");
		assertEquals(CreatedDevices.size(),0);
		// test enter not existing topologyId
		CreatedDevices = TopologyAPI.queryDevicesWithNetlistNode("top55","emf");
		assertEquals(CreatedDevices,null);
	}
	private boolean testIds() throws IOException {
		return compareJsonNamesToRealNames(TopologyAPI.queryTopologies());
	}
	
	private boolean testComponents() {
		String ids[] = {"top1","top2","top3","top4","top5"};
		ArrayList<Device> realComponenets = CreateRealDevices();
		boolean IsEqual = true;
		for (int i = 0; i < ids.length; i++) {
			Topology top = findTopology(ids[i]);
			if(!top.getComponents().equals(realComponenets)) {
				IsEqual = false;
				break;
			}
		}
		return IsEqual;
	}
	private boolean testDevices() throws IOException {
		String ids[] = {"top1","top2","top3","top4","top5"};
		ArrayList<Device> realDevices = CreateRealDevices();
		boolean IsEqual = true;
		for (int i = 0; i < ids.length; i++) {
			if(!TopologyAPI.queryDevices(ids[i]).equals(realDevices)) {
				IsEqual = false;
				break;
			}
		}
		return IsEqual;
	}
	
	private boolean compareJsonNamesToRealNames(ArrayList<Topology> topologis){
		String names[] = {"top1","top2","top3","top4","top5"};
		boolean isEqual = true;
		for (Iterator<Topology> iterator = topologis.iterator(); iterator.hasNext();) {
			Topology topology = (Topology) iterator.next();
			String id = topology.getID();
			boolean found = false;
			for (int i = 0; i < names.length; i++) {
				if(names[i].equals(id)) {
					found = true;
					break;
				}
			}
			if(found == false) {
				isEqual = false;
				break;
			}
		}
		return isEqual;
	}
	
	private ArrayList<Device> CreateRealDevices () {
		ArrayList<Device> devices = new ArrayList<Device>();
		String types[] = {"resistor","nmos"};
		String ids[] = {"res1","m1"};
		HashMap<String, String> characteristics1 = new HashMap<String, String>();
		characteristics1.put("default","100.0");
		characteristics1.put("min","10.0");
		characteristics1.put("max","1000.0");
		HashMap<String, String> netlist1 = new HashMap<String, String>();
		netlist1.put("t1","vdd");
		netlist1.put("t2","n1");
		HashMap<String, String> characteristics2 = new HashMap<String, String>();
		characteristics2.put("default","1.5");
		characteristics2.put("min","1.0");
		characteristics2.put("max","2.0");
		HashMap<String, String> netlist2 = new HashMap<String, String>();
		netlist2.put("drain","n1");
		netlist2.put("gate","vin");
		netlist2.put("source","vss");
		for (int i = 0; i < 2; i++) {
			if(i == 0) devices.add(new Device(types[i], ids[i], characteristics1, netlist1));
			if(i == 1) devices.add(new Device(types[i], ids[i], characteristics2, netlist2));
		}
		return devices;
	}
	
	private Topology CreateRealTopologie(String Id) {
		ArrayList<Device> components = CreateRealDevices();
		return new Topology(Id, components);
	}
	
	private Topology findTopology(String TopologyId) {
		for(var topology:TopologyAPI.queryTopologies()) {
			if(topology.getID().equals(TopologyId)) {
				return topology;
			}
		}
		return null; // return null if there is no topology found
	}
	
}
