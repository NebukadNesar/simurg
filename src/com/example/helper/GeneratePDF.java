package com.example.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import android.os.Environment;
import android.util.Log;

import com.example.model.Nabs;
import com.itextpdf.text.Anchor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfWriter;

public class GeneratePDF {

	private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
			Font.BOLD);
	private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
			Font.BOLD);
	private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
			Font.BOLD);

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	public String generate(ArrayList<Nabs> arr) {
		try {
			Document document = new Document();
			File directory = new File(Environment
					.getExternalStoragePublicDirectory("").toString(),
					"Simurg");
			if (!directory.exists()) {
				boolean a = directory.mkdirs();
				Log.d("mkdir ", directory.getAbsolutePath() + " make " + a);
			}

			File file = new File("");
			if (directory != null && !directory.getAbsolutePath().equals(""))
				file = new File(directory, "outputpdfdoc.pdf");
			else
				file = new File("outputpdfdoc.pdf");

			System.out.println("\t\n\n ********* : " + file.getAbsolutePath());
			if (!file.exists()) {
				try {
					if (file.createNewFile()) {
						System.out.println("Created...");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			System.out.println("\t\n\n ********* : " + file);

			PdfWriter.getInstance(document, new FileOutputStream(file));

			document.open();
			addMetaData(document);
			addTitlePage(document);
			addContent(document, arr);
			document.close();
			return file.getPath();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	private static void addMetaData(Document document) {
		document.addTitle("Simurg");
		document.addSubject("Generated PDF documant");
		document.addAuthor(android.os.Build.MANUFACTURER
				+ android.os.Build.PRODUCT);
		document.addCreator(android.os.Build.MANUFACTURER
				+ android.os.Build.PRODUCT);
	}

	private static void addTitlePage(Document document)
			throws DocumentException {
		Paragraph preface = new Paragraph();
		// We add one empty line
		addEmptyLine(preface, 1);
		// Lets write a big header
		preface.add(new Paragraph("Title of the document", catFont));

		addEmptyLine(preface, 1);
		// Will create: Report generated by: _name, _date
		preface.add(new Paragraph("Report generated by: "
				+ android.os.Build.MANUFACTURER + android.os.Build.PRODUCT
				+ ", " + new Date(), smallBold));
		addEmptyLine(preface, 3);
		preface.add(new Paragraph(
				"This documant contains the saved data in the device "
						+ android.os.Build.MANUFACTURER
						+ android.os.Build.PRODUCT + " of application Simurg.",
				smallBold));

		addEmptyLine(preface, 8);

		document.add(preface);
		// Start a new page
		document.newPage();
	}

	private static void addContent(Document document, ArrayList<Nabs> nabs)
			throws DocumentException {
		Anchor anchor = new Anchor("Unos", catFont);
		anchor.setName("Unos Chapter");

		// Second parameter is the number of the chapter
		Chapter catPart = new Chapter(new Paragraph(anchor), 1);

		Paragraph subPara = new Paragraph("List", subFont);
		Section subCatPart = catPart.addSection(subPara);
		List list = new List(true, false, 10);
		for (int i = 0; i < nabs.size(); i++) {
			list.add(new ListItem("\t " + nabs.get(i).getAlies() + ", "
					+ nabs.get(i).getUsername() + ", "
					+ nabs.get(i).getPassswd()));
		}
		subCatPart.add(list);

		// now add all this to the document
		document.add(catPart);

		// Next section
		anchor = new Anchor("Second Chapter", catFont);
		anchor.setName("Second Chapter");

		// Second parameter is the number of the chapter
		catPart = new Chapter(new Paragraph(anchor), 1);

		// now add all this to the document
		document.add(catPart);

	}

	private static void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}

}