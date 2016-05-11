package Util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Rede {
	private String endereco = null, porta = null;
	private Socket clienteSocket = null;
	private ObjectOutputStream objOutPut = null;
	InterfaceCliente interfacecliente = null;
	Pacote umPacote = null;

	public boolean Conectar(InterfaceCliente interfacecliente, String endereco, String porta) {
		this.interfacecliente = interfacecliente;
		this.endereco = endereco;
		this.porta = porta;
		if (!isConectado()) {
			try {
				clienteSocket = new Socket("127.0.0.1", 12345);
				objOutPut = new ObjectOutputStream(clienteSocket.getOutputStream());
				new Thread(new Recebe()).start();
			} catch (IOException e) {
				return false;
			}
		}
		return true;

	}

	public boolean isConectado() {
		return clienteSocket == null ? false : true;
	}

	public boolean envia(Object object){
		try {
			objOutPut.writeObject(object);
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
	public boolean desconectar(){
		try {
			clienteSocket.close();
			
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	public class Recebe implements Runnable{
		
		@Override
		public void run() {
			try {
				ObjectInputStream objInput = new ObjectInputStream(clienteSocket.getInputStream());
				umPacote = (Pacote) objInput.readObject();
				interfacecliente.retorno(umPacote);
			} catch (ClassNotFoundException e1) {
				//e1.printStackTrace();
			} catch (IOException e1) {
				//e1.printStackTrace();
			}
			
		}
		
	}
	
}
