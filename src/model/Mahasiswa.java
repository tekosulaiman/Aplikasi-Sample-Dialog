package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the mahasiswa database table.
 * 
 */
@Entity
@NamedQuery(name="Mahasiswa.findAll", query="SELECT m FROM Mahasiswa m")
public class Mahasiswa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String npm;

	private String nama;

	public Mahasiswa() {
	}

	public String getNpm() {
		return this.npm;
	}

	public void setNpm(String npm) {
		this.npm = npm;
	}

	public String getNama() {
		return this.nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

}