package com.roytuts.marshal.unmarshal;

import java.io.File;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.roytuts.jaxb.Catalog;
import com.roytuts.jaxb.Catalog.Book;

public class MarshalUnmarshal {

	public static void main(String[] args) {
		unmarshal();
		marshal();
	}

	private static void unmarshal() {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Catalog.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			Catalog catalogs = (Catalog) unmarshaller.unmarshal(new File("src/main/resources/xml/book.xml"));

			for (Book book : catalogs.getBook()) {
				System.out.println(book.getId());
				System.out.println(book.getTitle());
				System.out.println(book.getAuthor());
				System.out.println(book.getDescription());
				System.out.println(book.getGenre());
				System.out.println(book.getPrice());
				System.out.println(book.getPublishDate());
			}
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	private static void marshal() {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Catalog.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			Book book1 = new Book();
			Book book2 = new Book();

			GregorianCalendar gcalendar = new GregorianCalendar();
			gcalendar.setTime(new Date());
			XMLGregorianCalendar xmlDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcalendar);

			book1.setId("1001");
			book1.setTitle("Title for Book One");
			book1.setAuthor("S Roy");
			book1.setDescription("Description of Book One");
			book1.setGenre("IT");
			book1.setPrice(450.0f);
			book1.setPublishDate(xmlDate);
			book2.setId("1002");
			book2.setTitle("Title for Book Two");
			book2.setAuthor("S Roy");
			book2.setDescription("Description of Book Two");
			book2.setGenre("CS");
			book2.setPrice(400.0f);
			book2.setPublishDate(xmlDate);

			Catalog catalog = new Catalog();
			catalog.getBook().add(book1);
			catalog.getBook().add(book2);

			// Marshal the books list in file
			jaxbMarshaller.marshal(catalog, new File("src/main/resources/xml/newbooks.xml"));
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}
	}

}
