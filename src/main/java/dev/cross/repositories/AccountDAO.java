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
	
	public List<Account> getAllAccounts(int id) {
		List<Account> accounts = new ArrayList<>();
		String sql = "select * from bankingapp.accounts where user_id = ?";
		try (Connection conn = cu.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
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
	
	public List<Account> getAllAccountsWithParams(int id, int floor, int ceiling) {
		List<Account> accounts = new ArrayList<>();
		String sql = "select * from bankingapp.accounts where user_id = ? and balance > ? and balance < ?";
		try (Connection conn = cu.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setInt(2, floor);
			ps.setInt(3, ceiling);
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
	
	public Account getAccountById(int id) {
		String sql = "select * from bankingapp.accounts where id = ?";
		Account a = null;
		try (Connection conn = cu.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1,  id);;
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				a = new Account(rs.getInt("id"), rs.getBoolean("savings"), rs.getInt("balance"), rs.getInt("user_id"));
			} else {
				a = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return a;
	}
	
	public boolean updateAccount(Account updatedAccount) {
		String sql = "update bankingapp.accounts set (savings, balance, user_id)" + "= (?, ?, ?) where id = ?";
		try (Connection conn = cu.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setBoolean(1, updatedAccount.getSavings());
			ps.setInt(2, updatedAccount.getBalance());
			ps.setInt(3, updatedAccount.getUserID());
			ps.setInt(4, updatedAccount.getId());
			if (ps.executeUpdate() == 0) {
				return false;
			} else {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean deleteAccount (int id) {
		String sql = "delete from bankingapp.accounts where id = ?";
		try (Connection conn = cu.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			if (ps.executeUpdate() == 0) {
				return false;
			} else {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
