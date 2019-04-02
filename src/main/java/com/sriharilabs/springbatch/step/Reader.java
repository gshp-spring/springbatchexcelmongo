package com.sriharilabs.springbatch.step;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

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

	int nameColumnNumber = 1;
	int idColumnNumber = 0;
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	SummaryRepository summaryRepository;

	Integer first = 0;
	Integer last = 0;

	int counts = 0;
	static int size = 0;
	List<Summary> listsummary = null;

	@Override
	public Summary read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		InputStream targetStream = new FileInputStream("Books.xlsx");
		myWorkBook = new XSSFWorkbook(targetStream);
		mySheet = myWorkBook.getSheetAt(0);
		System.out.println("REader.....");

		if (listsummary == null) {
			listsummary = getEmployeesFromYesColumn();
			return listsummary.get(0);
		} else if (size < listsummary.size()-1) {
			size++;
			return listsummary.get(size);
		}

		return null;
	}

	public List<Summary> getEmployeesFromYesColumn() {
		List<Summary> employeeList = new ArrayList<Summary>();

		mySheet.rowIterator().forEachRemaining(row -> {
			System.out.println(mapHeaders);
			Summary summary = new Summary();

			if (row.getRowNum() != 0 && row.getRowNum() != 1) {

				Map<String, String> mapvalues = new HashMap<String, String>();
				IntStream.range(0, row.getLastCellNum()).forEach(num -> {
					mapvalues.put(mapHeaders.get(num), row.getCell(num).toString());
				});
				System.out.println(mapvalues);
				summary.setId(row.getRowNum());
				summary.setMap(mapvalues);
				employeeList.add(summary);

			} else if (row.getRowNum() == 0) {
				getHeader(row);
			}

		});
		return employeeList;

	}

	Map<Integer, String> mapHeaders = new HashMap<Integer, String>();

	public void getHeader(Row row) {

		IntStream.range(0, row.getLastCellNum()).forEach(num -> {
			mapHeaders.put(num, row.getCell(num).toString());

		});

	}

}