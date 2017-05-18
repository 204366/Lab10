package it.polito.tdp.porto.model;

public class Creator {
	private Author autore;
	private Paper paper;
	
	
	public Creator( Paper paper, Author autore) {
		super();
		this.autore = autore;
		this.paper = paper;
	}


	public Author getAutore() {
		return autore;
	}


	public void setAutore(Author autore) {
		this.autore = autore;
	}


	public Paper getPaper() {
		return paper;
	}


	public void setPaper(Paper paper) {
		this.paper = paper;
	}


	@Override
	public String toString() {
		return "Creator [autore=" + autore + ", paper=" + paper + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((autore == null) ? 0 : autore.hashCode());
		result = prime * result + ((paper == null) ? 0 : paper.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Creator other = (Creator) obj;
		if (autore == null) {
			if (other.autore != null)
				return false;
		} else if (!autore.equals(other.autore))
			return false;
		if (paper == null) {
			if (other.paper != null)
				return false;
		} else if (!paper.equals(other.paper))
			return false;
		return true;
	}
	
	
	

}
