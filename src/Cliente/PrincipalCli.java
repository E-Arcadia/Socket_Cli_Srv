package Cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import Entidade.Pessoa;
import Util.Pacote;
import Util.Pacote.indicativo;
import View.ClienteSwing;

public class PrincipalCli {
	private static String mensagem = null;
	private static boolean Encerra = true;
	private static Scanner recebidoSRV = null;
	private static Socket cliente;
	private static Scanner teclado;
	private static ObjectOutputStream objOutPut;
	private static ObjectInputStream objInput;
	private static Pacote novoPacote;

	public static void main(String[] args) {
		new ClienteSwing();
//		teclado = new Scanner(System.in);
//		Boolean Continua = true;
//		do {
//			Continua = true;
//			switch (menuUM()) {
//			case "01":
//			case "1": // Conectar
//				try {
//					cliente = new Socket("127.0.0.1", 12345);
//					objOutPut = new ObjectOutputStream(cliente.getOutputStream());
//					System.out.println("Conectado...");
//				} catch (IOException e) {
//					System.out.println("Erro de conexão.");
//				}
//				comunica();
//				break;
//			case "09":
//			case "9": // Sair
//				Continua = false;
//				break;
//			}
//			System.out.println("ENCERRADO");
//		} while (Continua);

	}

	private static void comunica() {
		Boolean Continua = true;
		do {
			switch (menu()) {
			case "01":
			case "1":// MENSAGEM
				System.out.print("Digite sua mensagem: ");
				mensagem = teclado.nextLine();
				novoPacote = new Pacote(indicativo.MENSAGEM, new String(mensagem));
				EnviaRecebePacote(novoPacote);
				break;
			case "02":
			case "2": // DATA
				EnviaRecebePacote(new Pacote(indicativo.DATA));
				break;

			case "03":
			case "3": // HORA
				EnviaRecebePacote(new Pacote(indicativo.HORA));
				break;
			case "04":
			case "4": // INSERE
				Pessoa umaPessoa = new Pessoa();
				System.out.println("Digite o ID: ");
				umaPessoa.setId(teclado.nextLine());

				System.out.println("Digite o Nome: ");
				umaPessoa.setNome(teclado.nextLine());

				System.out.println("Digite o E-Mail: ");
				umaPessoa.setEmail(teclado.nextLine());
				EnviaRecebePacote(new Pacote(indicativo.INSERE, umaPessoa));
				break;
			case "05":
			case "5": // LISTAR
				EnviaRecebePacote(new Pacote(indicativo.LISTA));
				break;
			case "06":
			case "6": //LISTAR_LOCAL
				EnviaRecebePacote(new Pacote(indicativo.LISTA_LOCAL));
				break;
			case "07":
			case "7": // ENCERRA SERVIDOR
//				EnviaRecebePacote(new Pacote(indicativo.FECHAR));
				Continua = false;
				break;
			case "08":
			case "8": // DESCONECTA
//				EnviaRecebePacote(new Pacote(indicativo.SAIR));
				Continua = false;
				break;

			}
			
		} while (Continua);
		System.out.println("\n\nCliente encerrou comunicação!!!");
		try {
			cliente.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static Pacote EnviaRecebePacote(Pacote novoPacote) {

//		try {
//			objOutPut.writeObject(novoPacote);
//			System.out.println("Pacote enviado.\n");
//		} catch (IOException e) {
//			System.out.println("Erro ao enviar pacote...");
//		}

		Pacote umPacote = null;
//		try {
//			objInput = new ObjectInputStream(cliente.getInputStream());
//			umPacote = (Pacote) objInput.readObject();
//		} catch (ClassNotFoundException e1) {
//			e1.printStackTrace();
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}

//		switch (umPacote.getAcaoString()) {
//		case "MENSAGEM":
//			System.out.println("MENSAGEM: " + (String) umPacote.getObj());
//			break;
//		case "DATA":
//			System.out.println("DATA: " + (String) umPacote.getObj());
//			break;
//		case "HORA":
//			System.out.println("HORA: " + (String) umPacote.getObj());
//			break;
//		case "INSERE":
//			System.out.println("RESULTADO: " + (String) umPacote.getObj());
//			break;
//		case "LISTA":
//			System.out.println("RESULTADO: " + (String) umPacote.getObj());
//			break;
//		case "LISTA_LOCAL":
//			ArrayList<Pessoa> listaPessoas = new ArrayList<>();
//			listaPessoas = (ArrayList<Pessoa>) umPacote.getObj();
//			for(Pessoa umaPessoa : listaPessoas){
//				System.out.println(umaPessoa.toString());	
//			}
//			break;
//		case "FECHAR":
//			System.out.println("RESULTADO: " + (String) umPacote.getObj());
//			break;
//		case "SAIR":
//			System.out.println("RESULTADO: " + (String) umPacote.getObj());
//			break;
//		}

		return null;
	}

	private static String menuUM() {
		System.out.println("\n\n  -- MENU --");
		System.out.println("01 - Conectar");
		System.out.println("09 - Sair");
		System.out.println("\n Digite sua opção: ");
		return new Scanner(System.in).nextLine();
	}

	private static String menu() {
		System.out.println("\n\n  -- MENU --");
		System.out.println("01 - MENSAGEM");
		System.out.println("02 - DATA");
		System.out.println("03 - HORA");
		System.out.println("04 - INSERE");
		System.out.println("05 - LISTA");
		System.out.println("06 - LISTAR LOCAL");
		System.out.println("07 - Encerra Servidor");
		System.out.println("08 - Desconecta");
		System.out.println("\n Digite sua opção: ");
		return new Scanner(System.in).nextLine();
	}

}
