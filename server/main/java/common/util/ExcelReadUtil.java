package common.util;

import common.vo.CommonFile;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelReadUtil {
    /**
     * @author 김재영
     * @since 2021.11.15
     *
     * 일반 엑셀 Read xls, xlsx
     * 엑셀파일 읽기 poi라이브러리 사용
     */
    public static Map<String, Object> excelFileRead(CommonFile commonFile) throws Exception {
        Map<String, Object> dataBySheet = new LinkedHashMap<String, Object>();
        String fileFullPath= commonFile.getFileFullPath();
        String sheetTitle= "sheet0";
        File excelFile = new File(fileFullPath);
        Workbook wb = WorkbookFactory.create(excelFile);



        for (int sheetIdx = 0; sheetIdx < wb.getNumberOfSheets(); sheetIdx++) {
            //한개의 시트에 대한 행,열 데이터 List
            Map<String, Object> sheetData = new LinkedHashMap<String, Object>();
            //rowData 담는 리스트
            List<LinkedHashMap<String, Object>> rowData = new ArrayList<LinkedHashMap<String, Object>>();
            List<List<String>> columnData = new ArrayList<List<String>>();
            //시트 객체
            Sheet sheet = wb.getSheetAt(sheetIdx);

            //데이터가 들어있는 시트의 첫 번째 행 index
            int firstRowNum = sheet.getFirstRowNum();
            //데이터가 들어있는 시트의 최종 행 index
            int lastRowNum = sheet.getLastRowNum();

            //데이터가 들어있는 시트의 첫 번째 행의 마지막 열 index
            //int firstCellNum = sheet.getRow(firstRowNum).getFirstCellNum();
            //int lastCellNum = sheet.getRow(firstRowNum).getLastCellNum();

            //Row for문
            for(int rowIndex = firstRowNum+1; rowIndex <= lastRowNum; rowIndex++) {
                //Row 객체
                Row row = sheet.getRow(rowIndex);
                if(row == null) continue;

                //해당 index 행의 마지막 열 index
                int lastCellNum = row.getLastCellNum();

                //행에 열데이터가 1개라도 존재하는지 체크
                boolean isRowDataExist = false;

                //한개의 행에 대한 열 데이터
                LinkedHashMap<String, Object> cells = new LinkedHashMap<String, Object>();
                //Row의 Cell 데이터 담기
                for(int cellIndex = 0; cellIndex < lastCellNum; cellIndex++) {
                    Cell cell = row.getCell(cellIndex);
                    try {
                        String value = getCellValue(cell, wb);
                        if (StringUtil.isEmpty(value) == true) {
                            cells.put("cell"+cellIndex, null);
                        } else {
                            cells.put("cell"+cellIndex, value);
                            isRowDataExist = true;
                        }
                    } catch (Exception e) {
                        cells.put("cell"+cellIndex, null);
                        e.printStackTrace();
                    }
                }

                //행에 열데이터가 1개라도 존재하면 -> row데이터 추가
                if (isRowDataExist == true) {
                    //row데이터 추가
                    rowData.add(cells);
                }

                //raw데이터에 대한 명칭 입력
                //dataTable.setTitle(dataTitle);
            }//Row for문 끝
            sheetData.put("rowData",rowData);
            //시트가 2개 이상일 때, 데이터 명 == 시트명
            if (wb.getNumberOfSheets() > 1) {
                sheetTitle = "sheet"+sheetIdx;
            }

            //row데이터가 없을 때, 메세지 처리
//            if (dataTable.getRowData().size() == 0) {
//                dataTable.getCheckMessage().setIsSuccess(false);
//                dataTable.getCheckMessage().setMessage("데이터가 없습니다");
//            } else {
//                dataTable.getCheckMessage().setIsSuccess(true);
//                dataTable.getCheckMessage().setMessage("엑셀(.xls) 파일 읽기 완료");
//            }

            //Key(시트명) : Value(시트의 행,열 데이터)
            dataBySheet.put(sheetTitle, sheetData);
        }
        //스트림 닫기
        if (wb != null) {
            wb.close();
        }

        return dataBySheet;
    }

    /**
     * @author 김재영
     * @since 2021.11.15
     *
     * 일반 엑셀 Read 관련
     * Excel cell value 데이터 타입 파악 후, 원본형태의 문자열로 반환
     */
    public static String getCellValue(Cell cell, Workbook wb) throws Exception {
        if(cell != null) {
            switch (cell.getCellType()) {
                case FORMULA:
                    FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
                    if (evaluator.evaluateFormulaCell(cell) == CellType.NUMERIC) {
                        if (DateUtil.isCellDateFormatted(cell)) {
                            return "";
                        } else {
                            Double dou = new Double(cell.getNumericCellValue());
                            if ((double) dou.longValue() == dou.doubleValue()) {
                                return Long.toString(dou.longValue());
                            } else {
                                return StringUtil.toString(dou);
                            }
                        }
                    } else if (evaluator.evaluateFormulaCell(cell) == CellType.STRING) {
                        return cell.getStringCellValue();
                    } else if (evaluator.evaluateFormulaCell(cell) == CellType.BOOLEAN) {
                        return StringUtil.toString(new Boolean(cell.getBooleanCellValue()));
                    } else {
                        return cell.getCellFormula();
                    }
                case NUMERIC:
                    BigDecimal big = new BigDecimal(cell.getNumericCellValue());
                    BigDecimal fp = big.subtract(new BigDecimal(big.longValue()));
                    if(DateUtil.isCellDateFormatted(cell)) {
                        Date date = cell.getDateCellValue();
                        return new SimpleDateFormat("yyyy-MM-dd").format(date);
                    } else if(checkDateFormat(cell)) {
                        Date date = cell.getDateCellValue();
                        return new SimpleDateFormat("yyyy-MM-dd").format(date);
                    } else if(fp.doubleValue()==0.0d) {
                        return Long.toString(big.longValue());
                    } else {
                        return StringUtil.toString(new Double(cell.getNumericCellValue()));
                    }
                case STRING:
                    return cell.getStringCellValue();
                case BOOLEAN:
                    return StringUtil.toString(new Boolean(cell.getBooleanCellValue()));
                case ERROR:
                    return StringUtil.toString(new Byte(cell.getErrorCellValue()));
                case BLANK:
                    return "";
                default:
                    return "";
            }
        } else {
            return "";
        }
    }

    /**
     * @author 김재영
     * @since 2021.11.15
     *
     * 일반 엑셀 Read 관련
     * Excel cell의 포맷 형태가 데이트관련 포맷인지 체크
     */
    public static boolean checkDateFormat(Cell cell) throws Exception{
        try {
            int formatIndex = cell.getCellStyle().getDataFormat();
            switch(formatIndex) {
                case 31: //excel: yyyy년 MM월 dd일
                    return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
}
