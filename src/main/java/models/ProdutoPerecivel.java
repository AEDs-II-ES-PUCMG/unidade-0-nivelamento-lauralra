package models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ProdutoPerecivel extends Produto {

    private double desconto = 0.25;

    private int prazoDesconto = 7;

    private LocalDate dataDeValidade;

    public ProdutoPerecivel(String descricao, double precoCusto, double margemLucro, LocalDate dataDeValidade, int quantidadeEmEstoque) {
        super(descricao, precoCusto, margemLucro, quantidadeEmEstoque);
        this.dataDeValidade = dataDeValidade;
    }

        public double valorVenda() {
        if (dataDeValidade.isBefore(LocalDate.now().plusDays(prazoDesconto))){
            return super.valorDeVenda();
        }
        else {
            return super.valorDeVenda() - (super.valorDeVenda() * desconto);
        }
    }

    @Override
    public String toString() {
        
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        String dataFormatada = this.dataDeValidade.format(formato);

        return super.toString() + "\nData de Validade: " + dataFormatada;   
    }

    public String gerarDadosTexto(){
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return "2;" + this.descricao + ";" + this.precoCusto + ";" + this.margemLucro + ";" + this.dataDeValidade.format(formato) + ";" + this.quantidadeEmEstoque;
    }
}