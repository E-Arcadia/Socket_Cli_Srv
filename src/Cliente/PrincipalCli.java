package Cliente;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Scanner;

import javax.swing.SingleSelectionModel;

public class PrincipalCli {
	private static String mensagem = null;
	private static boolean Encerra = true;
	private static boolean ContinuaCNX = true;
	private static Scanner recebidoSRV = null;

	public static void main(String[] args) throws UnknownHostException, IOException {
		do {
			switch (menu()) {
			case "01":
			case "1":
				comunica();
				break;
			case "09":
			case "9":
				Encerra = false;
				break;
			}

		} while (Encerra);
		System.out.println("ENCERRADO");
	}

	private static void comunica() throws UnknownHostException, IOException {
		ContinuaCNX = true;
		Socket cliente = new Socket("127.0.0.1", 12345);
		recebidoSRV = new Scanner(cliente.getInputStream());

		System.out.println("O cliente se conectou ao servidor!");

		Scanner teclado = new Scanner(System.in);
		PrintStream saida = new PrintStream(cliente.getOutputStream());
		do {
			System.out.print("Digite sua mensagem: ");
			mensagem = teclado.nextLine();
			saida.println(mensagem);

			switch (mensagem.toUpperCase()) {
			case "/DATA":
				System.out.println("Recebido do Servidor - Tecle ENTER:" 
						+ recebidoSRV.nextLine());
				break;

			case "/SAIR":
				System.out.println("Desconectando... Tecle ENTER.");
				ContinuaCNX = false;
				break;

			case "/FECHAR":
				System.out.println("Servidor encerrado...");
				break;
			}

		} while (teclado.hasNextLine() && ContinuaCNX);

		System.out.println("\n\nCliente encerrou comunicação!!!");
		cliente.close();
	}

	private static String menu() {
		System.out.println("\n\n  -- MENU --");
		System.out.println("01 - Conectar");
		System.out.println("09 - Sair");
		System.out.println("\n Digite sua opção: ");
		return new Scanner(System.in).nextLine();
	}
}
