import java.io.IOException;

public class MainClass {

	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		Topology topology = JsonIO.ReadFromJson("E:\\java play ground\\topology api\\src\\test.json");
		System.out.println(topology.getComponents().get(0).getType());
		System.out.println(topology.getComponents().get(0).getId());
		System.out.println(topology.getComponents().get(0).getCharacteristics());
		System.out.println(topology.getComponents().get(0).getNetlist());
		System.out.println(topology.getComponents().get(0).getCharacteristicsName());
//		
		JsonIO.WriteToJson("tops.json",topology);
	}
	
	
}
