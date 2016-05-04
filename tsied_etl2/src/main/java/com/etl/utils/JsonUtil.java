package com.etl.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

/**
 * <pre>
 * Author     Version       Date        Changes
 * rock      1.0           2016年3月1日     Created
 * </pre>
 */

public class JsonUtil {

	/**
	 * parse json
	 * 
	 * @param data
	 * @return
	 */
	public static List<String> parseJson(Map<String, String> paramMap) {
		List<String> storeList = new ArrayList<>();
		if (paramMap.containsKey("aggs")) {
			String aggs = paramMap.get("aggs");
			Map<String, String> secMap = JSON.parseObject(aggs,
					new TypeReference<Map<String, String>>() {
					});
			Collection<String> callBackCollection = secMap.values();
			String tempKey = "";
			for (String key : secMap.keySet()) {
				tempKey = key;
			}
			for (String key : callBackCollection) {
				secMap = JSON.parseObject(key,
						new TypeReference<Map<String, String>>() {
						});// Map
				Set<String> set = secMap.keySet();
				for (String setKey : set) {
					String secKey = secMap.get(setKey);
					if ("aggs".equals(setKey)) {
						Map<String, String> parseMap = new HashMap<>();
						parseMap.put(setKey, secKey);
						List<String> sigleParseList = parseJson(parseMap);
						storeList.addAll(sigleParseList);
					} else {
						Map<String, Map<String, Object>> map = new HashMap<>();
						Map<String, Object> childMap = new HashMap<>();
						childMap.put(setKey, JSON.parseObject(secKey));
						map.put(tempKey, childMap);
						storeList.add(JSON.toJSONString(map));

					}
				}
			}
		}
		return storeList;
	}
}
