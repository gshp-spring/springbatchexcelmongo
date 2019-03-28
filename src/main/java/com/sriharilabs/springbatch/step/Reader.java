package com.sriharilabs.springbatch.step;
 
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Service;

import com.sriharilabs.springbatch.model.Summary;

 
 @Service
public class Reader implements ItemReader<List<Summary>>{
 
    private String[] messages = {"Hello World!", "Welcome to Spring Batch!"};
     
    private int count=0;
    XSSFWorkbook myWorkBook = null;
	XSSFSheet mySheet = null;
	Integer columnIndex;

	int nameColumnNumber = 1;
	int idColumnNumber = 0;
    Logger logger = LoggerFactory.getLogger(this.getClass());
     
    @Override
    public List<Summary> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
    	
        InputStream targetStream  = new FileInputStream("Books.xlsx");
        
    	myWorkBook = new XSSFWorkbook(targetStream);
    	mySheet=myWorkBook.getSheetAt(0);
    	List<Summary> list=getEmployeesFromYesColumn();
 
    	
        return list;
    }
    
    List<Summary> employeeList = new ArrayList<Summary>();
    public List<Summary> getEmployeesFromYesColumn() {

		
		
		System.out.println(mySheet.getLastRowNum()+".."+mySheet.getPhysicalNumberOfRows()+"....");
		
		mySheet.rowIterator().forEachRemaining(row -> {
			
			Summary summary = new Summary();

			if (row.getRowNum() != 0 && row.getRowNum()!=1)  {
				
					summary.setModelId(row.getCell(nameColumnNumber).toString());
					summary.setMvmUseCases(row.getCell(idColumnNumber).toString());
					summary.setTdmTableName(row.getCell(2).toString());
					employeeList.add(summary);

			}

		});
		return employeeList;

	}
    public boolean isThisRowNamesAndIdsExist(Row row, int cellNumber) {
		if (row.getCell(cellNumber) != null && !(row.getCell(cellNumber).toString().trim()).equals("")) {
			return true;
		}
		return false;
	}

	
}