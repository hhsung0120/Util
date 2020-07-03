package com.heeseong.util.util;

import java.util.List;
import java.util.Map;

public interface DataFileReader
{
	public int rowNum(); // current row number!

	public List<Map<String, Object>> readRows(int batchSize) throws Exception;

	List<Map<String, Object>> readRows() throws Exception;
}
