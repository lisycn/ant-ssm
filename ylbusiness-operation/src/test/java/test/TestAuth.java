package test;

import org.junit.Test;

import java.util.*;



public class TestAuth {

	
	@Test
	public void testMap()throws Exception{
		Map map = new HashMap();
		map.put("a", "a");
		map.put("b", "b");
		Map map1 = new HashMap();
		map1.put("b", "b");
		map1.put("c", "c");
		map.putAll(map1);
		System.out.println(map.toString());
	}
	
	@Test
	public void testSplit()throws Exception{
//		String itemIds = "101,101102,101103,101104,101102101,101102101101,101102101101101,101102101101101101";
		String itemIds = "101,101102,101102103,101102103104,101102103104105,101102103104105106,101102103104105106107";
		String[] ary = itemIds.split(",");
		Map<String,String> keyMap = new HashMap<String,String>();
		for(String s : ary){
			keyMap.putAll(getMapByStr(s));
		}
		System.out.println(keyMap.toString());
		Set<String> set = keyMap.keySet();
		for(String s : set){
			System.out.println(s);
		}
	}
	
	public Map<String,String> getMapByStr(String str){
		Map<String,String> map = new HashMap<String,String>();
		if(str==null || "".equals(str = str.trim())){
			return map;
		}
		map.put(str, str);
		while(true){
			if(str.length()<=3){
				map.put(str, str);
				break;
			}else{
				str = str.substring(0, str.length()-3);
				map.put(str, str);
			}
		}
		return map;
	}
	@Test
	public void strSplit(){
		String s = "108102103104105109";
		System.out.println(s.substring(0, s.length()-3));
//		System.out.println(s.substring(3, s.length()));
	}
	
	@Test
	public void disableModelMain()throws Exception{
//		AuthPropUtil.getValue("123456", "chen_yq");
//		AuthPropUtil.getValue("123456", "chen_yq","li_shou","刘官正");
	}
	
	@Test
	public void testList()throws Exception{
		List<String> list1 = new ArrayList<String>();
		for(int i=0;i<5;i++){//01234
			list1.add(""+i);
		}
		List<String> list2 = new ArrayList<String>();
		for(int i=0;i<3;i++){//012
			list2.add(""+i);
		}
		list1.removeAll(list2);
		System.out.println(list1.toString());

	}	
}

