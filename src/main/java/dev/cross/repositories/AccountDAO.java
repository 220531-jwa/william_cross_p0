package dev.cross.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dev.cross.models.Account;
import dev.cross.models.Client;
import dev.cross.utils.ConnectionUtil;

public class AccountDAO {

private static ConnectionUtil cu = ConnectionUtil.getConnectionUtil();
	
	public Account createAccount(Account a) {
		String sql = "insert into bankingapp.accounts values (default, ?, ?, ?) returning *";
		try (Connection conn = cu.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setBoolean(1, a.getSavings());
			ps.setInt(2, a.getBalance());
			ps.setInt(3, a.getUserID());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				a.setID(rs.getInt("id"));
				a.setSavings(rs.getBoolean("savings"));
				a.setBalance(rs.getInt("balance"));
				a.setUserID(rs.getInt("user_id"));
			} else {
				a = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return a;
	}
	
	public List<Account> getAllAccounts() {
		List<Account> accounts = new ArrayList<>();
		String sql = "select * from bankingapp.accounts";
		try (Connection conn = cu.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				accounts.add(new Account(
						rs.getInt("id"),
						rs.getBoolean("savings"),
						rs.getInt("balance"),
						rs.getInt("user_id")));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return accounts;
	}
	
	public List<Account> getAllAccountsWithParams(int floor, int ceiling) {
		List<Account> accounts = new ArrayList<>();
		String sql = "select * from bankingapp.accounts where balance >= ? and balance <= ?";
		try (Connection conn = cu.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, floor);
			ps.setInt(2, ceiling);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				accounts.add(new Account(
						rs.getInt("id"),
						rs.getBoolean("savings"),
						rs.getInt("balance"),
						rs.getInt("user_id")));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return accounts;
	}
	
}
