package cl.ucn.bean;

import jakarta.persistence.*;


@Entity
@Table(name="cliente")
public class Cliente {

	@Id
	@Column(nullable=false, unique=true, length=20)
	int rut;
	@Column(nullable=false, length=120)
	String nombre;
	int sueldo;

	public int getRut() {
		return rut;
	}
	public void setRut(int rut) {
		this.rut = rut;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getSueldo() {
		return sueldo;
	}
	public void setSueldo(int sueldo) {
		this.sueldo = sueldo;
	}
	
}
