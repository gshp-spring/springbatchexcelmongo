package com.sriharilabs.springbatch.step;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sriharilabs.springbatch.model.Summary;
import com.sriharilabs.springbatch.repository.SummaryRepository;

@Service
public class Reader implements ItemReader<Summary> {

	private String[] messages = { "Hello World!", "Welcome to Spring Batch!" };

	private int count = 0;
	XSSFWorkbook myWorkBook = null;
	XSSFSheet mySheet = null;
	Integer columnIndex;

	private final String mandatoryFieldsCheckPoint = "TDM_Table_Name";

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	SummaryRepository summaryRepository;
	int counts = 0;
	static int size = 0;
	Map<Integer, String> mapHeaders = new HashMap<Integer, String>();
	List<Summary> listsummary =null;
	Integer checkPointIndex = null;
	
	@Override
	public Summary read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		InputStream targetStream = new FileInputStream("Books.xlsx");
		myWorkBook = new XSSFWorkbook(targetStream);
		mySheet = myWorkBook.getSheetAt(0);
		System.out.println("REader.....");

		if (listsummary == null) {
			listsummary = new ArrayList<Summary>();
			getEmployeesFromColumn();
			return listsummary.get(0);
		} else if (size < listsummary.size() - 1) {
			size++;
			return listsummary.get(size);
		}

		return null;
	}

	public void getEmployeesFromColumn() {
		mySheet.rowIterator().forEachRemaining(row -> {
			addToList(row);
		});

	}

	public void addToList(Row row) {
		Summary summary = new Summary();
		
		if (row.getRowNum() != 0 && row.getRowNum() != 1) {
			Map<String, String> mandatoryvalues = new LinkedHashMap<String, String>();
			Map<String, String> filtervalues = new LinkedHashMap<String, String>();
			List<Map<String, String>> filtersList=new ArrayList();
			IntStream.range(0, row.getLastCellNum()).forEach(num -> {
				String cellValue = getCellValu(row.getCell(num));
				if (cellValue != null && checkPointIndex >= num) {
					mandatoryvalues.put(mapHeaders.get(num), cellValue);
					summary.setMandatory(mandatoryvalues);
				} else if (cellValue != null){
					filtervalues.put(mapHeaders.get(num), cellValue);
					
				}
			});

			filtersList.add(filtervalues);
			summary.setFilters(filtersList);
			summary.setId(row.getRowNum());

			listsummary.add(summary);

		} else if (row.getRowNum() == 0) {
			getHeaders(row);
		}

	}

	public void getHeaders(Row row) {

		IntStream.range(0, row.getLastCellNum()).forEach(num -> {
			if (getHeaderNames(row.getCell(num)) != null)
				mapHeaders.put(num, getHeaderNames(row.getCell(num)));

		});
		checkForMandoryFieldColumnNumer();
	}

	public String getHeaderNames(Cell cell) {
		if (cell != null && !cell.toString().trim().equals(""))
			return cell.toString().replaceAll("\\s+", "_");
		return null;
	}
	
	public String getCellValu(Cell cell) {
		
		if (cell != null && !cell.toString().trim().equals(""))
			return cell.toString().trim();
		return null;
	}

	public void checkForMandoryFieldColumnNumer() {
		mapHeaders.keySet().stream().filter(index -> {
			return mapHeaders.get(index).equals(mandatoryFieldsCheckPoint);
		}).forEach(index -> checkPointIndex = (Integer) index);
	}
}
