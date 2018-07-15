package de.laliluna.blob;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.dialect.PostgreSQLDialect;

import de.laliluna.hibernate.InitSessionFactory;

public class BlobTest {

	/**
     * @param args
     */
	public static void main(String[] args) {
		createCharacterBlob();
		createBinaryBlob();

	}

	private static void createBinaryBlob() {
		byte byteArray[] = new byte[10000000];
		for (int i = 0; i < byteArray.length; i++) {
			byteArray[i] = '1';
		}
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		Image image = new Image();
		
		image.setImageAsBlob(byteArray);
		image.setImageAsBlob2(session.getLobHelper().createBlob(byteArray));
		image.setImageAsBytea(byteArray);
		session.save(image);
		session.getTransaction().commit();

		/* get one image from db and save it as file */
		session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		Image imageReloaded = (Image) session.createQuery("from Image").setMaxResults(1).uniqueResult();
		
		
		try {
			FileOutputStream outputStream = new FileOutputStream(new File("image_file_blob_array"+new Random().nextInt()));
			outputStream.write(imageReloaded.getImageAsBlob());
			outputStream.close();
			outputStream = new FileOutputStream(new File("image_file_blob_blob"+new Random().nextInt()));
			outputStream.write(imageReloaded.getImageAsBlob2().getBytes(1, (int)imageReloaded.getImageAsBlob2().length()));
			outputStream.close();
			outputStream = new FileOutputStream(new File("image_file_bytea"+new Random().nextInt()));
			outputStream.write(imageReloaded.getImageAsBytea());
			outputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		session.getTransaction().commit();
		
		session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		session.delete(image);
		session.getTransaction().commit();
	}

	private static void createCharacterBlob() {
		/* create a 10 MB String */
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < 1000000; i++)
			buffer.append("0123456789");

		Document document = new Document();
		document.setText(buffer.toString());
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		session.save(document);
		session.getTransaction().commit();
		

	}

}
