package topologyAPI.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Equal {

	public Equal() {
		// TODO Auto-generated constructor stub
	}
	public static boolean EqualHash(HashMap<String, String> map1, HashMap<String, String> map2) {
		// check over keys 
		if(!map1.keySet().equals(map2.keySet()))return false;
		 // Value set of map1
        HashSet<String> set1 = new HashSet<>(map1.values());
        // Value set of map2
        HashSet<String> set2 = new HashSet<>(map2.values());
        List<String> list1 = sortSet(set1);
        List<String> list2 = sortSet(set2);
        if(!list1.toString().equals(list2.toString()))return false;
        
		return true;
	}
	public static Map<String, Boolean> areEqualKeyValues(Map<String, String> first, Map<String, String> second) {
	    return first.entrySet().stream()
	      .collect(Collectors.toMap(e -> e.getKey(), 
	        e -> e.getValue().equals(second.get(e.getKey()))));
	}
	private static List<String> sortSet(HashSet<String> set) {
		List<String> list = new ArrayList<String>(set);
        Collections.sort(list);
        return list;
	}

}
