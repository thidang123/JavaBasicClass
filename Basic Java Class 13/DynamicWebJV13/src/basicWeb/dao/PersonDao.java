package basicWeb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import basicWeb.dbConnection.MySQLConnection;
import basicWeb.model.Person;

public class PersonDao {
	Connection co;

	public void delete(int id) {
		String sql = "delete from persons where id = ?";
	}

	public boolean checkUserName(String userName) {
		boolean isExisted = false;

		return isExisted;
	}

	public void save(Person p)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		MySQLConnection mCon = new MySQLConnection();
		StringBuilder sql = new StringBuilder("insert into persons (lastName,")
				.append("firstName, address, city, username, password, phone)")
				.append("values(?,?,?,?,?,SHA2(?, 256),?)");
		co = mCon.getCon();
		PreparedStatement ps = co.prepareStatement(sql.toString());
		ps.setString(1, p.getLastName());
		ps.setString(2, p.getFirstName());
		ps.setString(3, p.getAddress());
		ps.setString(4, p.getCity());
		ps.setString(5, p.getUsername());
		ps.setString(6, p.getPassword());
		ps.setString(7, p.getPhone());

		ps.executeUpdate();
		mCon.closeConnection();
	}

	public void update(Person p, String username)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		MySQLConnection mCon = new MySQLConnection();
		StringBuilder sql = new StringBuilder(
				"update person set lastName = ?, firstName = ?, address = ?, city = ?, password = ?, phone = ? where personId = ?");
		co = mCon.getCon();
		PreparedStatement ps = co.prepareStatement(sql.toString());
		ps.setString(1, p.getLastName());
		ps.setString(2, p.getFirstName());
		ps.setString(3, p.getAddress());
		ps.setString(4, p.getCity());
		ps.setString(5, p.getPassword());
		ps.setString(6, p.getPhone());
		ps.setInt(7, p.getPersonId());
		ps.executeUpdate();
		mCon.closeConnection();
	}

	public List<Person> getAll()
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		MySQLConnection mCon = new MySQLConnection();
		String sql = "select * from persons";
		Statement st = mCon.getCon().createStatement();
		ResultSet rs = st.executeQuery(sql);
		List<Person> pers = new ArrayList<Person>();

		Person p = null;
		while (rs.next()) {
			p = new Person(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6),
					rs.getString(7), rs.getString(8));
			p.setPersonId(rs.getInt("personId"));

			pers.add(p);
		}

		mCon.closeConnection();
		return pers;
	}
}
