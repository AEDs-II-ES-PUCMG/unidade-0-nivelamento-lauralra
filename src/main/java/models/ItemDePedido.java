package models;

import java.util.Scanner;

public class ItemDePedido {

    // Atributos encapsulados
    private Produto produto;
    private int quantidade;
    private double precoVenda;

    static Scanner teclado;

    /**
     * Construtor da classe ItemDePedido.
     * O precoVenda deve ser capturado do produto no momento da criação do item,
     * garantindo que alterações futuras no preço do produto não afetem este pedido.
     */
    public ItemDePedido(Produto produto, int quantidade, double precoVenda) {

    }

    public double calcularSubtotal() {
        return 0;
    }



    public int produtoJaNoPedido(int quantItens, int quantidadeEmEstoque, boolean produtoIncluso){

        if (produtoIncluso){
            System.out.println("Produto ja inserido");
            System.out.println("Deseja alterar a quantidade do item? (1- sim 2- nao)");
            int verificador = teclado.nextInt();

            if ( verificador == 1){
                System.out.println("Quantos itens deseja adicionar?");
                quantidade = teclado.nextInt();

                quantItens = quantItens + quantidade;

                if (quantItens <= quantidadeEmEstoque){
                    return quantItens;
                }
                else {
                    return 0;
                }
            }
            else if (verificador == 2){
                return quantItens;
            }
            else{
                return 0;
            }
        }
        else {
            return 0;
        }

    }


    // --- Sobrescrita do método equals ---

    /**
     * Compara a igualdade entre dois itens de pedido.
     * A regra de negócio define que dois itens são iguais se possuírem o mesmo Produto.
     */
    @Override
    public boolean equals(Object obj) {

        ItemDePedido item = (ItemDePedido) obj;

        if (obj == item){
            return true;
        }
        else {
            return false;
        }
        
    }
}