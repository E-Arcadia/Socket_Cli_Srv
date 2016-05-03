package Util;

import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;

import Entidade.Pessoa;

public class Pacote implements Serializable{
	private indicativo acao;
	private Date data;
	private Object obj;
	
	
	public Pacote() {
		super();
	}
	
	public Pacote(indicativo acao) {
		super();
		this.acao = acao;
	}

	public Pacote(indicativo acao, Object obj) {
		super();
		this.acao = acao;
		this.obj = obj;
		data = new Date();
	}

	public indicativo getAcao() {
		return acao;
	}

	public String getAcaoString() {
		return acao.toString();
	}
	
	public void setAcao(indicativo acao) {
		this.acao = acao;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}





	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((acao == null) ? 0 : acao.hashCode());
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((obj == null) ? 0 : obj.hashCode());
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
		Pacote other = (Pacote) obj;
		if (acao == null) {
			if (other.acao != null)
				return false;
		} else if (!acao.equals(other.acao))
			return false;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (this.obj == null) {
			if (other.obj != null)
				return false;
		} else if (!this.obj.equals(other.obj))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Pacote [acao=" + acao + ", data=" + data + ", obj=" + obj + "]";
	}



	public enum indicativo {
		MENSAGEM, //enviando uma mensagem texto (String).
		DATA, 	// solicita a data do servidor
		HORA, 	// solicita a hora do servidor
		INSERE, // envia um objeto pessoa ao servidor
		LISTA, 	// solicita ao servidor que liste as pessoas cadastradas
		SAIR, 	// cliente desconecta do servidor
		FECHAR; // encerra o servidor e se desconecta.
	}
}


