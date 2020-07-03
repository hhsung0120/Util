package com.heeseong.util.util;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *  수정 안함.. 귀찮..
 *  20200703 얼른 다른거 해야함.. 소스 확보..
 */
public class XExcelFileReader implements DataFileReader, Closeable
{
	private int rowNum = 0;

	private OPCPackage opcPkg;
	private ReadOnlySharedStringsTable stringsTable;
	private XMLStreamReader xmlReader;

  	/**
  	 * 생성자(초기 엑셀 파일을 XML 형식으로 읽어들임)
  	 * @param excelPath
  	 * @param
  	 * @return
	 * @throws Exception
  	 */
	public XExcelFileReader(String excelPath) throws Exception
	{
		// 파일 읽을 수 있게 컨테이너 생성
		opcPkg = OPCPackage.open(excelPath, PackageAccess.READ);
		this.stringsTable = new ReadOnlySharedStringsTable(opcPkg);

		// opc컨테이너 XSSF형식으로 읽기
		XSSFReader xssfReader = new XSSFReader(opcPkg);
		XMLInputFactory factory = XMLInputFactory.newInstance();
		InputStream inputStream = xssfReader.getSheetsData().next();

		xmlReader = factory.createXMLStreamReader(inputStream);

		while (xmlReader.hasNext())
		{
			xmlReader.next();
			if (xmlReader.isStartElement())
			{
				if (xmlReader.getLocalName().equals("sheetData"))
					break;
			}
		}
	}

	/**
	 * 생성자(특정 sheet 읽어오기)
	 * @param excelPath
	 * @param sheetName
	 * @throws Exception
	 */
	@SuppressWarnings("resource")
	public XExcelFileReader(String excelPath, String sheetName) throws Exception
	{
		// 파일 읽을 수 있게 컨테이너 생성
		opcPkg = OPCPackage.open(excelPath, PackageAccess.READ);
		this.stringsTable = new ReadOnlySharedStringsTable(opcPkg);

		// 특정 Sheet 키값 가져오기
		XSSFWorkbook wb = new XSSFWorkbook(excelPath);
		XSSFSheet sh = wb.getSheet(sheetName);
		String rId = wb.getRelationId(sh);

		// opc컨테이너 XSSF형식으로 읽기
		XSSFReader xssfReader = new XSSFReader(opcPkg);
		XMLInputFactory factory = XMLInputFactory.newInstance();
		InputStream inputStream = xssfReader.getSheet(rId);

		xmlReader = factory.createXMLStreamReader(inputStream);

		while (xmlReader.hasNext())
		{
			xmlReader.next();
			if (xmlReader.isStartElement())
			{
				if (xmlReader.getLocalName().equals("sheetData"))
					break;
			}
		}
	}

	public XExcelFileReader(InputStream in) throws Exception
	{
		// 파일 읽을 수 있게 컨테이너 생성
		opcPkg = OPCPackage.open(in);
		this.stringsTable = new ReadOnlySharedStringsTable(opcPkg);

		// opc컨테이너 XSSF형식으로 읽기
		XSSFReader xssfReader = new XSSFReader(opcPkg);
		XMLInputFactory factory = XMLInputFactory.newInstance();
		InputStream inputStream = xssfReader.getSheetsData().next();
		xmlReader = factory.createXMLStreamReader(inputStream);

		while (xmlReader.hasNext())
		{
			xmlReader.next();
			if (xmlReader.isStartElement())
			{
				if (xmlReader.getLocalName().equals("sheetData"))
					break;
			}
		}
	}

  	/**
  	 * Row 개수 설정 값
  	 * @param
  	 * @param
  	 * @return
	 * @throws
  	 */
	@Override
	public int rowNum()
	{
		return rowNum;
	}

  	/**
  	 * Row 단위를 요청한 Row수만큼 List에 담기
  	 * @param
  	 * @param
  	 * @return
	 * @throws Exception
  	 */
	@Override
	public List<Map<String, Object>> readRows(int batchSize) throws XMLStreamException
	{
		String elementName = "row";
		List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
		if (batchSize > 0)
		{
			while (xmlReader.hasNext())
			{
				xmlReader.next();

				if (xmlReader.isStartElement())
				{
					if (xmlReader.getLocalName().equals(elementName))
					{
						rowNum++;

						// Row 단위로 List에 add
						dataRows.add(getDataRow());

						// 요청한 파일 Row 개수와 같으면 Loop 빠져 나가기
						if (dataRows.size() == batchSize)
							break;
					}
				}
			}
		}
		return dataRows;
	}

  	/**
  	 * 전체 Row List에 담기
  	 * @param
  	 * @param
  	 * @return
	 * @throws Exception
  	 */
	@Override
	public List<Map<String, Object>> readRows() throws Exception
	{
		String elementName = "row";
		List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();

		while (xmlReader.hasNext())
		{
			xmlReader.next();
			if (xmlReader.isStartElement())
			{
				if (xmlReader.getLocalName().equals(elementName))
				{
					rowNum++;
					// Row 단위로 List에 add
					dataRows.add(getDataRow());
				}
			}
		}
		return dataRows;
	}

  	/**
  	 * 셀을 Map에 담기
  	 * @param
  	 * @param
  	 * @return
	 * @throws Exception
  	 */
	private Map<String, Object> getDataRow() throws XMLStreamException
	{
		Map<String, Object> cellMap = new HashMap<String, Object>();

		int i = 0;
		while (xmlReader.hasNext())
		{
			xmlReader.next();

			if (xmlReader.isStartElement())
			{
				if (xmlReader.getLocalName().equals("c"))
				{
					CellReference cellReference = new CellReference(xmlReader.getAttributeValue(null, "r"));

					// 공백 셀 채우기
					int check = 0;
					while(cellMap.size() < cellReference.getCol()){
						if(check++ > 100){
							return null;
						}else{
							cellMap.put(i+"","");
							i++;
						}
					}
					String cellType = xmlReader.getAttributeValue(null, "t");
					cellMap.put(i+"", getCellValue(cellType));
					i++;
				}
			}
			else if (xmlReader.isEndElement() && xmlReader.getLocalName().equals("row"))
			{
				break;
			}
		}
		return cellMap;
	}

  	/**
  	 * cell을 읽어서 String에 담기
  	 * @param
  	 * @param
  	 * @return
	 * @throws Exception
  	 */
	private String getCellValue(String cellType) throws XMLStreamException
	{
		// 디폴드 값
		String value = "";

		// 셀 개수만큼 Loop
		while (xmlReader.hasNext())
		{
			xmlReader.next();

			if (xmlReader.isStartElement())
			{
				if (xmlReader.getLocalName().equals("v"))
				{
					if (cellType != null && cellType.equals("s"))
					{
						int idx = Integer.parseInt(xmlReader.getElementText());
						return new XSSFRichTextString(stringsTable.getEntryAt(idx)).toString();
					}
					else
					{
						return xmlReader.getElementText();
					}
				}
			}
			else if ("inlineStr".equals(cellType))
			{
				return new XSSFRichTextString(xmlReader.getText()).toString();
			}
			else if (xmlReader.isEndElement() && xmlReader.getLocalName().equals("c"))
			{
				break;
			}
		}
		return value;
	}

	@Override
	public void finalize() throws Throwable
	{
		if (opcPkg != null)
			opcPkg.close();
		super.finalize();
	}

	public void close() throws IOException
	{
		try {
			if (opcPkg != null) opcPkg.close();
			super.finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}