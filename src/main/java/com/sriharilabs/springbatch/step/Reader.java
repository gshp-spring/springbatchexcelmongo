package com.sriharilabs.springbatch.step;
 
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import com.sriharilabs.springbatch.model.Summary;

 
 
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
			
			//System.out.println(row.getRowNum()+" rowsize.."+row);
			
			
			Summary emp = new Summary();

			if (row.getRowNum() != 0 && row.getRowNum()!=1)  {
				
					emp.setModelId(row.getCell(nameColumnNumber).toString());
					emp.setMvmUseCases(row.getCell(idColumnNumber).toString());
					employeeList.add(emp);

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