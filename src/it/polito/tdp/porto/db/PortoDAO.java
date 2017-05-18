package it.polito.tdp.porto.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.porto.model.Author;
import it.polito.tdp.porto.model.AutoreID;
import it.polito.tdp.porto.model.Creator;
import it.polito.tdp.porto.model.CreatorID;
import it.polito.tdp.porto.model.Paper;
import it.polito.tdp.porto.model.PaperID;

public class PortoDAO {

	/*
	 * Dato l'id ottengo l'autore.
	 */
	public Author getAutore(int id, AutoreID aID) {

		final String sql = "SELECT * FROM author where id=?";
		Author old = aID.getAutoriMap().get(id);

		if(old != null){
			return old;
		}
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, id);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {

				Author autore = new Author(rs.getInt("id"), rs.getString("lastname"), rs.getString("firstname"));
				aID.put(autore);
				return autore;
			}
			conn.close();
			return null;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

	/*
	 * Dato l'id ottengo l'articolo.
	 */
	public Paper getArticolo(int eprintid, PaperID pID) {

		final String sql = "SELECT * FROM paper where eprintid=?";
		
		Paper old = pID.getPaperMap().get(eprintid);
		if(old != null){
			return old;
		}

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, eprintid);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				Paper paper = new Paper(rs.getInt("eprintid"), rs.getString("title"), rs.getString("issn"),
						rs.getString("publication"), rs.getString("type"), rs.getString("types"));
				
			
				pID.put(paper);
				conn.close();
				return paper;
			}
			conn.close();
			return null;

		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}
	
	public Paper getPaperComune(int a1, int a2, PaperID pID ){
		final String sql = "SELECT p.eprintid as pId  FROM paper as p, creator as c, creator as c2 "
				+ "WHERE c.authorid =? AND c2.authorid=? AND p.eprintid = c.eprintid AND p.eprintid = c2.eprintid";
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, a1);
			st.setInt(2, a2);
			

			ResultSet rs = st.executeQuery();

			if(rs.next()) {
				Paper p = this.getArticolo(rs.getInt("pId"),pID);
				conn.close();
				
				return p;
			}

			

		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
		
		return null;
		
	}
	
	
	public List<Paper> getPaperComune2(int a1, int a2, PaperID pID ){
		final String sql = "SELECT p.eprintid as pId  FROM paper as p, creator as c, creator as c2 "
				+ "WHERE c.authorid =? AND c2.authorid=? AND p.eprintid = c.eprintid AND p.eprintid = c2.eprintid";
		List<Paper> papers= new ArrayList<Paper>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, a1);
			st.setInt(2, a2);
			

			ResultSet rs = st.executeQuery();

			while(rs.next()) {
				Paper p = this.getArticolo(rs.getInt("pId"),pID);
				papers.add(p);
			}

			

		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
		
		return papers;
		
	}
	
	public List<Creator> getCreatorsAutore(Author a, AutoreID aID, PaperID pID, CreatorID cID ) {

		final String sql = "SELECT * FROM creator where authorid=?";
		List<Creator> creator = new ArrayList<Creator>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, a.getId());

			ResultSet rs = st.executeQuery();

			while(rs.next()) {
				Creator c = new Creator(this.getArticolo(rs.getInt("eprintid"),pID),this.getAutore(rs.getInt("authorid"),aID));
				String s = ""+c.getAutore().getId()+""+c.getPaper().getEprintid();
				Creator old = cID.getCreatorMap().get(s);
				
				if(old == null){
					creator.add(c);
					cID.put(c);
				}
				else{
					creator.add(old);
				}
			}

			conn.close();

		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
		
		return creator;
	}
	
	
	public List<Creator> getCreator(CreatorID cID){
		
		final String sql = "SELECT a.id, a.lastname, a.firstname, c.eprintid, "
				+ "p.eprintid, p.title, p.issn, p.type, p.types "
				+ "FROM author as a, creator as c, paper as p "
				+ "WHERE a.id = c.authorid  AND p.eprintid = c.eprintid ";
		List<Creator> creator = new ArrayList<Creator>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Creator c = new Creator(new Paper(rs.getInt("eprintid"),rs.getString("title"), rs.getString("issn"),null,rs.getString("type"),rs.getString("types")),new Author(rs.getInt("id"),rs.getString("lastname"),rs.getString("firstname")));
				String s = ""+c.getAutore().getId()+""+c.getPaper().getEprintid();
				Creator old = cID.getCreatorMap().get(s);
				
				if(old == null){
					creator.add(c);
					cID.put(c);
				}
				else{
					creator.add(old);
				}
				
			}

			

		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
		
		return creator;
	}
	
	public List<Paper> getPapersAutore(Author a, PaperID pID){
		
		final String sql = "SELECT * FROM creator where authorid=?";
		List<Paper> papers = new ArrayList<Paper>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Paper p = this.getArticolo(rs.getInt("eprintid"), pID);
				papers.add(p);
			}

			

		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
		
		return papers;
	}
	
	public List<Author> getAutori( AutoreID aID) {

		final String sql = "SELECT * FROM author ";
		List<Author> autori = new ArrayList<Author>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Author a = new Author(rs.getInt("id"), rs.getString("lastname"), rs.getString("firstname"));
				
				Author old = aID.getAutoriMap().get(rs.getInt("id"));
				
				if(old == null){	
					autori.add(a);
					aID.put(a);
				}
				else{
					autori.add(old);
				}

			}

		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
		return autori;
	}
	
	
	
}