package it.polito.tdp.porto.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;


import it.polito.tdp.porto.db.PortoDAO;



public class Model {

	UndirectedGraph<Author, DefaultEdge> grafo ;
	UndirectedGraph<Author, DefaultEdge> grafo2;
	PortoDAO dao = new PortoDAO();
	List<Author> autori;
	private AutoreID aID = new AutoreID();
	private PaperID pID = new PaperID();
	private CreatorID cID = new CreatorID();
	
	public UndirectedGraph<Author, DefaultEdge> createGraph(Author autore){
		grafo = new SimpleGraph<Author, DefaultEdge>(DefaultEdge.class);
		
		for(Author a : this.getAutori()){
			grafo.addVertex(a);
		}
		
		List<Creator> creatorsA = dao.getCreatorsAutore(autore, aID, pID, cID) ;
		List<Creator> creators = dao.getCreator(cID);
		
		for(Creator c  : creatorsA){
			for(Creator c1 : creators){
				if(!autore.equals(c1.getAutore())){
					if(c.getPaper().equals(c1.getPaper())){
						grafo.addEdge(autore, c1.getAutore());
					}
				}
			}
		}
		return grafo;
	}
	
	public List<Author> getAutori(){
		if(this.autori == null){
			autori = dao.getAutori(aID);
		}
		return autori;
	}
	
	public List<Author> getAllCoautori(Author autore){
		return Graphs.neighborListOf(this.getGraph2(), autore);
		
	}
	
	
	public List<Author> getNonCoautori(Author autore){
		List<Author> nonCoautori = this.getAutori();
		nonCoautori.removeAll(this.getAllCoautori(autore));
		nonCoautori.remove(autore);
		return nonCoautori;
	}
	
	public UndirectedGraph<Author, DefaultEdge> createGraph2(){
		grafo2 = new SimpleGraph<Author, DefaultEdge>(DefaultEdge.class);
		
		
		List<Creator> creators = dao.getCreator(cID);
		
		for(Author a : this.getAutori()){
			grafo2.addVertex(a);
		}
		for(Author autore : this.getAutori()){
			List<Creator> creatorsA =  dao.getCreatorsAutore(autore, aID, pID, cID);
			for(Creator c  : creatorsA ){
				for(Creator c1 : creators){
					if(!autore.equals(c1.getAutore())){
						if(c.getPaper().equals(c1.getPaper())){
							grafo2.addEdge(autore, c1.getAutore());
						}
					}
				}
		}
		
		}
		return grafo2;
	}
	
	public UndirectedGraph<Author, DefaultEdge> getGraph2(){
		if(this.grafo2 == null){
			grafo2 = this.createGraph2();
		}
		return this.grafo2;
	}
	
	public List<Paper> sequenzaArticoli(Author autoreI, Author autoreF){
		DijkstraShortestPath<Author, DefaultEdge> dijkstra = new DijkstraShortestPath<Author, DefaultEdge>(
				this.getGraph2(), autoreI, autoreF);
		List<Paper> paper = new ArrayList<Paper>();
		
		GraphPath<Author, DefaultEdge>camm = dijkstra.getPath();
		if(camm != null){
			for(DefaultEdge e : camm.getEdgeList()){
				paper.add(dao.getPaperComune(this.getGraph2().getEdgeSource(e).getId(), this.getGraph2().getEdgeTarget(e).getId(),pID));
			}
			return paper;
		}
		else{
			return null;
		}
	}
	
	
}
