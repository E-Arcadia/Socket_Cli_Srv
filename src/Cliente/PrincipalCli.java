package Cliente;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.SingleSelectionModel;

public class PrincipalCli {
	private static String mensagem = null;
	private static boolean Encerra = true;

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
		Socket cliente = new Socket("127.0.0.1", 12345);
		System.out.println("O cliente se conectou ao servidor!");

		Scanner teclado = new Scanner(System.in);
		PrintStream saida = new PrintStream(cliente.getOutputStream());
		do {
			System.out.print("Digite sua mensagem: ");
			mensagem = teclado.nextLine();
			if ("/SAIR".equals(mensagem.toUpperCase())) {
				break;
			} else {
				saida.println(mensagem);
				if("/FECHAR".equals(mensagem.toUpperCase())){
					System.out.println("Servidor encerrado...");
					break;
				}
			}
		} while (teclado.hasNextLine());
		System.out.println("\n\nCliente encerrou comunicação!!!");
		//teclado.close();
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
