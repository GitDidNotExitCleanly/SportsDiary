package database;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import models.ExerciseTableModel;
import models.DiaryModel;
import models.ExerciseModel;
import models.NutritionModel;
import models.NutritionTableModel;
import models.UserInfoModel;
import util.CalendarHelper;

public class DatabaseHelper {

	// singleton pattern
	private static DatabaseHelper databaseHelper;

	private DatabaseHelper() {}

	public static DatabaseHelper getInstance() {
		if (databaseHelper == null) {
			databaseHelper = new DatabaseHelper();
		}
		return databaseHelper;
	}

	// TODO save files outside JAR
	private static final String USER_INFO_PREFIX = "src/database/USER_INFO";
	private static final String DIARY_PREFIX = "src/database/DIARY_";
	private static final String FILE_TYPE = ".xml";
	
	public UserInfoModel readUserInfo() {
		//  model
		UserInfoModel userInfoModel = UserInfoModel.getInstance();
		// check file exists
		File file = new File(USER_INFO_PREFIX + FILE_TYPE);
		if (file.exists()) {
			try {
				// get document
				Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
				Element root = document.getDocumentElement();

				// read data into empty model
				int height = Integer.valueOf(root.getAttribute("height"));
				int weight = Integer.valueOf(root.getAttribute("weight"));
				userInfoModel.setHeight(height);
				userInfoModel.setWeight(weight);

			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			}
			// set isModified value
			userInfoModel.setIsModified(false);
		} else {
			// set isModified value
			userInfoModel.setIsModified(true);
		}
		return userInfoModel;
	}

	public DiaryModel readDiary() {
		// empty model
		DiaryModel diaryModel = DiaryModel.getInstance();
		ExerciseModel exerciseModel = diaryModel.getExerciseModel();
		NutritionModel nutritionModel = diaryModel.getNutritionModel();
		exerciseModel.removeAllTableModel();
		nutritionModel.removeAllTableModel();

		// check file exists
		int year = CalendarHelper.getInstance().getCalendar().get(Calendar.YEAR);
		int month = CalendarHelper.getInstance().getCalendar().get(Calendar.MONTH) + 1;
		File file = new File(DIARY_PREFIX + year + month + FILE_TYPE);
		if (file.exists()) {
			// get document
			Document document = null;
			try {
				document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);	
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			}

			// read data into empty model
			Element root = document.getDocumentElement();
			NodeList dateElementList = root.getElementsByTagName("date");
			for (int i = 0; i < dateElementList.getLength(); i++) {
				Element dateElement = (Element) dateElementList.item(i);

				Element exercisePanelElement = (Element)dateElement.getElementsByTagName("exercisepanel").item(0);
				NodeList itemList = exercisePanelElement.getElementsByTagName("item");
				ExerciseTableModel exerciseTableModel = new ExerciseTableModel();
				for (int j=0;j<itemList.getLength();j++) {
					Element item = (Element)itemList.item(j);
					String[] itemValue = item.getFirstChild().getNodeValue().split(",");
					exerciseTableModel.increaseTotalTime(Integer.valueOf(itemValue[3]));
					exerciseTableModel.addRow(itemValue);
				}
				exerciseModel.increaseTotalTime(exerciseTableModel.getTotalTime());
				exerciseModel.addTableModel(exerciseTableModel);

				Element nutritionPanelElement = (Element)dateElement.getElementsByTagName("nutritionpanel").item(0);
				itemList = nutritionPanelElement.getElementsByTagName("item");
				NutritionTableModel nutritionTableModel = new NutritionTableModel();
				for (int j=0;j<itemList.getLength();j++) {
					Element item = (Element)itemList.item(j);
					String[] itemValue = item.getFirstChild().getNodeValue().split(",");
					nutritionTableModel.increaseTotalCalories(Integer.valueOf(itemValue[4]));
					nutritionTableModel.addRow(itemValue);
				}
				nutritionModel.increaseTotalCalories(nutritionTableModel.getTotalCalories());
				nutritionModel.addTableModel(nutritionTableModel);
			}
		}
		else {
			int days = CalendarHelper.getInstance().getCalendar().getActualMaximum(Calendar.DAY_OF_MONTH);
			for (int i=0;i<days;i++) {
				exerciseModel.addTableModel(new ExerciseTableModel());
				nutritionModel.addTableModel(new NutritionTableModel());
			}
		}
		int date = CalendarHelper.getInstance().getCalendar().get(Calendar.DATE);
		exerciseModel.setCurrentTableModel(date - 1);
		nutritionModel.setCurrentTableModel(date - 1);
		return diaryModel;
	}

	public void writeUserInfo(UserInfoModel userInfoModel) {
		// check file exists
		File file = new File(USER_INFO_PREFIX + FILE_TYPE);
		Document document = null;
		if (!file.exists()) {
			// create empty file
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}

			// create document and element
			// insert value
			try {
				document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			}
			Element root = document.createElement("user");
			root.setAttribute("height", String.valueOf(userInfoModel.getHeight()));
			root.setAttribute("weight", String.valueOf(userInfoModel.getWeight()));
			document.appendChild(root);
		}
		else {
			// get document and element
			// insert value
			try {
				document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			}
			Element root = document.getDocumentElement();
			root.setAttribute("height", String.valueOf(userInfoModel.getHeight()));
			root.setAttribute("weight", String.valueOf(userInfoModel.getWeight()));
		}
		// write to file
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(file);
			transformer.transform(source, result);
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		// set isModified value
		userInfoModel.setIsModified(false);
	}

	public void writeDiary(DiaryModel diaryModel) {
		// check file exists
		int year = CalendarHelper.getInstance().getCalendar().get(Calendar.YEAR);
		int month = CalendarHelper.getInstance().getCalendar().get(Calendar.MONTH) + 1;
		File file = new File(DIARY_PREFIX + year + month + FILE_TYPE);
		Document document = null;
		if (!file.exists()) {
			// create empty file
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}

			// create document and element
			// insert value
			try {
				document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			}

			Element root = document.createElement("diary");
			int numOfDays = CalendarHelper.getInstance().getCalendar().getActualMaximum(Calendar.DAY_OF_MONTH);
			for (int i=0;i<numOfDays;i++) {
				Element dateElement = document.createElement("date");
				dateElement.setAttribute("id", String.valueOf(i+1));

				Element exercisepanel = document.createElement("exercisepanel");
				ExerciseTableModel exerciseTableModel = diaryModel.getExerciseModel().getTableModelAt(i);
				int exerciseRowCount = exerciseTableModel.getRowCount();
				for (int j=0;j<exerciseRowCount;j++) {
					Element item = document.createElement("item");
					Vector rowData = (Vector)(exerciseTableModel.getDataVector().elementAt(j));
					String rowDataText = (String)rowData.elementAt(0)+","
							+(String)rowData.elementAt(1)+","
							+(String)rowData.elementAt(2)+","
							+(String)rowData.elementAt(3)+","
							+(String)rowData.elementAt(4);
					Text itemText = document.createTextNode(rowDataText);
					item.appendChild(itemText);
					exercisepanel.appendChild(item);
				}
				exerciseTableModel.setIsModified(false);

				Element nutritionpanel = document.createElement("nutritionpanel");
				NutritionTableModel nutritionTableModel = diaryModel.getNutritionModel().getTableModelAt(i);
				int nutritionRowCount = nutritionTableModel.getRowCount();
				for (int j=0;j<nutritionRowCount;j++) {
					Element item = document.createElement("item");
					Vector rowData = (Vector)(nutritionTableModel.getDataVector().elementAt(j));
					String rowDataText = (String)rowData.elementAt(0)+","
							+(String)rowData.elementAt(1)+","
							+(String)rowData.elementAt(2)+","
							+(String)rowData.elementAt(3)+","
							+(String)rowData.elementAt(4)+","
							+(String)rowData.elementAt(5)+","
							+(String)rowData.elementAt(6)+","
							+(String)rowData.elementAt(7);
					Text itemText = document.createTextNode(rowDataText);
					item.appendChild(itemText);
					nutritionpanel.appendChild(item);
				}
				nutritionTableModel.setIsModified(false);

				dateElement.appendChild(exercisepanel);
				dateElement.appendChild(nutritionpanel);
				root.appendChild(dateElement);
			}
			document.appendChild(root);
		}
		else {
			// get document and element
			// insert value
			try {
				document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			}

			Element root = document.getDocumentElement();
			NodeList dateElementList = root.getElementsByTagName("date");
			for (int i=0;i<dateElementList.getLength();i++) {
				ExerciseTableModel exerciseTableModel = diaryModel.getExerciseModel().getTableModelAt(i);
				NutritionTableModel nutritionTableModel = diaryModel.getNutritionModel().getTableModelAt(i);

				Element dateElement = (Element) dateElementList.item(i);
				if (exerciseTableModel.isModified()) {
					Element exercisepanel = (Element)dateElement.getElementsByTagName("exercisepanel").item(0);
					// remove all child nodes
					while (exercisepanel.hasChildNodes()) {
						exercisepanel.removeChild(exercisepanel.getFirstChild());
					}
					int exerciseRowCount = exerciseTableModel.getRowCount();
					for (int j=0;j<exerciseRowCount;j++) {
						Element item = document.createElement("item");
						Vector rowData = (Vector)(exerciseTableModel.getDataVector().elementAt(j));
						String rowDataText = (String)rowData.elementAt(0)+","
								+(String)rowData.elementAt(1)+","
								+(String)rowData.elementAt(2)+","
								+(String)rowData.elementAt(3)+","
								+(String)rowData.elementAt(4);
						Text itemText = document.createTextNode(rowDataText);
						item.appendChild(itemText);
						exercisepanel.appendChild(item);
					}
					exerciseTableModel.setIsModified(false);
				}

				if (nutritionTableModel.isModified()) {
					Element nutritionpanel = (Element)dateElement.getElementsByTagName("nutritionpanel").item(0);
					// remove all child nodes
					while (nutritionpanel.hasChildNodes()) {
						nutritionpanel.removeChild(nutritionpanel.getFirstChild());
					}
					int nutritionRowCount = nutritionTableModel.getRowCount();
					for (int j=0;j<nutritionRowCount;j++) {
						Element item = document.createElement("item");
						Vector rowData = (Vector)(nutritionTableModel.getDataVector().elementAt(j));
						String rowDataText = (String)rowData.elementAt(0)+","
								+(String)rowData.elementAt(1)+","
								+(String)rowData.elementAt(2)+","
								+(String)rowData.elementAt(3)+","
								+(String)rowData.elementAt(4)+","
								+(String)rowData.elementAt(5)+","
								+(String)rowData.elementAt(6)+","
								+(String)rowData.elementAt(7);
						Text itemText = document.createTextNode(rowDataText);
						item.appendChild(itemText);
						nutritionpanel.appendChild(item);
					}
					nutritionTableModel.setIsModified(false);
				}
			}
		}
		// write to file
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(file);
			transformer.transform(source, result);
		} catch (TransformerException e) {
			e.printStackTrace();
		}

		// set isModified value
		diaryModel.getExerciseModel().setIsModified(false);
		diaryModel.getNutritionModel().setIsModified(false);
	}
}
