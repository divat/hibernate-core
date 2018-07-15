package de.laliluna.blob;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.postgresql.largeobject.LargeObject;
import org.postgresql.largeobject.LargeObjectManager;

/**
 * some tests with JDBC. The tables of the Hibernate example are reused. Run the
 * other example first to get the tables created.
 * 
 * @author Sebastian Hennebrueder
 */
public class BlobJdbcTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String url = "jdbc:postgresql://localhost/learninghibernate";
		Properties props = new Properties();
		props.setProperty("user", "postgres");
		props.setProperty("password", "p");
		try {
			Class.forName("org.postgresql.Driver");
			Connection conn = DriverManager.getConnection(url, props);
			conn.setAutoCommit(false);

			createBlob(conn);
			conn.commit();
			conn = DriverManager.getConnection(url, props);
			conn.setAutoCommit(false);
			deleteBlob(conn);

			// Finally, commit the transaction.
			conn.commit();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	private static void deleteBlob(Connection conn) throws SQLException {
		LargeObjectManager lobj = ((org.postgresql.PGConnection) conn)
				.getLargeObjectAPI();
		PreparedStatement preparedStatement = conn
				.prepareStatement("select * from imageslo");
		ResultSet rs = preparedStatement.executeQuery();
		while (rs.next()) {
			int oid = rs.getInt("imgoid");
			System.out.println("Deleting " + oid);
			lobj.delete(oid);
		}
		rs.close();
		conn.prepareStatement("delete from imageslo").execute();

	}

	private static void createBlob(Connection conn) throws SQLException,
			IOException {
		// Get the Large Object Manager to perform operations with
		LargeObjectManager lobj = ((org.postgresql.PGConnection) conn)
				.getLargeObjectAPI();

		// Create a new large object
		int oid = lobj.create(LargeObjectManager.READ
				| LargeObjectManager.WRITE);

		// Open the large object for writing
		LargeObject obj = lobj.open(oid, LargeObjectManager.WRITE);

		// Now open the file
		byte byteArray[] = new byte[10000000];
		for (int i = 0; i < byteArray.length; i++) {
			byteArray[i] = '1';
		}
		ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(
				byteArray);

		// Copy the data from the file to the large object
		byte buf[] = new byte[2048];
		int s, tl = 0;
		while ((s = arrayInputStream.read(buf, 0, 2048)) > 0) {
			obj.write(buf, 0, s);
			tl += s;
		}

		// Close the large object
		obj.close();

		// Now insert the row into imageslo
		PreparedStatement ps = conn
				.prepareStatement("INSERT INTO imageslo VALUES (?, ?)");
		ps.setString(1, "test");
		ps.setInt(2, oid);
		ps.executeUpdate();
		ps.close();
		arrayInputStream.close();
	}

}
