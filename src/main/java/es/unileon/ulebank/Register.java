package es.unileon.ulebank;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class Register {

	String login;
	String pass;
	String passCod;
	String sacarLogin;
	String sacarPass;
	Codificador codificar;
	String ruta = System.getProperty("user.dir");
	String separado = System.getProperty("file.separator");

	public Register() {

	}

	public Register(String login, String pass) {
		this.login = login;
		this.pass = pass;
	}

	public void guardarDatos(String login, String pass) {
		File archivo = new File(ruta + separado + "archivo.txt");
		codificar = new Codificador();
		try {
			FileWriter escribirArchivo = new FileWriter(archivo, true);
			BufferedWriter buffer = new BufferedWriter(escribirArchivo);
			buffer.write(login);
			buffer.newLine();
			buffer.write(codificar.encriptarPass(pass));
			buffer.newLine();
			buffer.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// TODO Aqui es donde tienes que recojer los datos de la interfaz para
	// guardarlos en el texto plano
	public void guardarDatos() {
		File archivo = new File(ruta + separado + "archivo.txt");
		codificar = new Codificador();
		try {
			FileWriter escribirArchivo = new FileWriter(archivo, true);
			BufferedWriter buffer = new BufferedWriter(escribirArchivo);
			buffer.write(this.login);
			buffer.newLine();
			buffer.write(codificar.encriptarPass(this.pass));
			buffer.newLine();
			buffer.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
