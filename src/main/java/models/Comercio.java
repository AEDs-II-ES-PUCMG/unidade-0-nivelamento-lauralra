package models;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Comercio {

    static final int MAX_NOVOS_PRODUTOS = 10;

    static String nomeArquivoDados;

    //Scanner para leitura do teclado
    static Scanner teclado;

    // Vetor de produtos cadastrados. Sempre terá espaço para 10 novos produtos a cada execução
    static Produto[] produtosCadastrados;

    //Quantidade produtos cadastrados atualmente no vetor
    static int quantosProdutos = 0;

    // Gera um efeito de pausa na CLI. Espera por um enter para continuar
    static void pausa() {
        System.out.println("Digite enter para continuar...");
        teclado.nextLine();
    }

    // Cabeçalho principal da CLI do sistema
    static void cabecalho() {
        System.out.println("AEDS II COMÉRCIO DE COISINHAS");
        System.out.println("===========================");
    }

    /**
     * Imprime o menu principal, lê a opção do usuário e a retorna (int).
     * Perceba que poderia haver uma melhor modularização com a criação de uma
     * classe Menu.
     *
     * @return Um inteiro com a opção do usuário.
     */
    static int menu() {
        cabecalho();
        System.out.println("1 - Listar todos os produtos");
        System.out.println("2 - Procurar e listar um produto");
        System.out.println("3 - Cadastrar novo produto");
        System.out.println("0 - Sair");
        System.out.print("Digite sua opção: ");
        return Integer.parseInt(teclado.nextLine());
    }

    /**
     * Lê os dados de um arquivo texto e retorna um vetor de produtos. Arquivo
     * no formato N (quantiade de produtos) <br/>
     * tipo; descrição;preçoDeCusto;margemDeLucro;[dataDeValidade] <br/>
     * Deve haver uma linha para cada um dos produtos. Retorna um vetor vazio em
     * caso de problemas com o arquivo.
     *
     * @param nomeArquivoDados Nome do arquivo de dados a ser aberto.
     * @return Um vetor com os produtos carregados, ou vazio em caso de
     * problemas de leitura.
     */
    /*Ler a primeira linha do arquivoDados contendo a quantidade de produtos armazenados no arquivo.
Instanciar o vetorProdutos com o tamanho necessário para acomodar todos os produtos do arquivo + o
espaço reserva MAX_NOVOS_PRODUTOS. Após isso, ler uma após a outra o restante das linhas do arquivo,
convertendo, a cada leitura de linha, seus dados em objetos do tipo Produto (utilizar o método
criarDoTexto()). Cada objeto Produto instanciado será armazenado no vetorProdutos.*/
    static Produto[] lerProdutos(String nomeArquivoDados) {

        /*Ler a primeira linha do arquivoDados contendo a quantidade de produtos armazenados no arquivo.
Instanciar o vetorProdutos com o tamanho necessário para acomodar todos os produtos do arquivo + o
espaço reserva MAX_NOVOS_PRODUTOS. Após isso, ler uma após a outra o restante das linhas do arquivo,
convertendo, a cada leitura de linha, seus dados em objetos do tipo Produto (utilizar o método
criarDoTexto()). Cada objeto Produto instanciado será armazenado no vetorProdutos.*/

        Produto[] vetorProdutos;
        vetorProdutos = new Produto[MAX_NOVOS_PRODUTOS];
        int i;

        try {
            File arquivo = new File(nomeArquivoDados);
            Scanner leitor = new Scanner (arquivo);
            // transforma a string de quantidade de produtos em int
            int n = Integer.parseInt(leitor.nextLine().trim());
            vetorProdutos = new Produto[n + MAX_NOVOS_PRODUTOS];

            for (i=0; i<n; i++){
                if (leitor.hasNextLine()){
                    String linha = leitor.nextLine();
                    vetorProdutos[i] = Produto.criarDoTexto(linha);
                    quantosProdutos++;
                }
            }        
            leitor.close();
            
        }

        catch (Exception e){
            System.err.println("Erro ao ler arquivo. Inicializando um novo vetor vazio");
            return new Produto[MAX_NOVOS_PRODUTOS];
        }

        return vetorProdutos;
    }

    /**
     * Lista todos os produtos cadastrados, numerados, um por linha
     */
    static void listarTodosOsProdutos() {
        cabecalho();
        System.out.println("\nPRODUTOS CADASTRADOS:");
        for (int i = 0; i < produtosCadastrados.length; i++) {
            if (produtosCadastrados[i] != null) {
                System.out.println(String.format("%02d - %s",
                        (i + 1), produtosCadastrados[i].toString()));
            }
        }
    }

    /**
     * Localiza um produto no vetor de cadastrados, a partir do nome
     * (descrição), e imprime seus dados. A busca não é sensível ao caso. Em
     * caso de não encontrar o produto, imprime mensagem padrão
     */
    static void localizarProdutos() {
        /*Ler do teclado a descrição (nome) do produto que o usuário deseja localizar, procurar no vetor de
produtosCadastrados o produto em questão (utilizar o método equals() já implementado na classe Produto)
e imprimir na tela seus dados.*/
        int i;
        cabecalho();
        System.out.println("Insira o nome do produto que deseja buscar: ");
        String descricao = teclado.nextLine();

        Produto buscado = new Produto(descricao, 0.01, 0.01);

        boolean encontrado = false;


        for (i=0; i<quantosProdutos; i++){
            if (produtosCadastrados[i].equals(buscado)){
                System.out.println("Produto encontrado: " + produtosCadastrados[i].toString());
                encontrado = true;
            }
        }
        if (!encontrado) {
            System.out.println("Produto não encontrado");

        }
    }

    /**
     * Rotina de cadastro de um novo produto: pergunta ao usuário o tipo do
     * produto, lê os dados correspondentes, cria o objeto adequado de acordo
     * com o tipo, inclui no vetor. Este método pode ser feito com um nível
     * muito melhor de modularização. As diversas fases da lógica poderiam ser
     * encapsuladas em outros métodos. Uma sugestão de melhoria mais
     * significativa poderia ser o uso de padrão Factory Method para criação dos
     * objetos.
     */
    static void cadastrarProduto() {
        /*Implementar a sub-rotina de exibir o novo menu para cadastro de novo produto, ler os dados necessários
conforme o tipo desejado, criar o objeto correspondente, salvando-o no vetor de produtosCadastrados e
incrementando a variável de controle da quantidade de produtos.*/
        int tipo;

        System.out.println("Cadastro de novo produto");
        System.out.println("1 - Produto não perecivel");
        System.out.println("2 - Produto perecivel");
        tipo = teclado.nextInt();
        teclado.nextLine();

        System.out.print("Descrição: ");
        String descricao = teclado.nextLine();

        System.out.print("Preço de custo: ");
        double precoCusto = teclado.nextDouble();

        System.out.print("Margem de lucro: ");
        double margemLucro = teclado.nextDouble();
        teclado.nextLine();

        Produto novoProduto = null;

        try {

            if (tipo == 1) {
               novoProduto = new ProdutoNaoPerecivel(descricao, precoCusto, margemLucro);
            } 
            
            else if (tipo == 2) {

                System.out.println("Data de validade (dd/mm/yyyy): ");
                String dataValidade = teclado.nextLine();
                DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                novoProduto = new ProdutoPerecivel(descricao, precoCusto, margemLucro, LocalDate.parse(dataValidade, formato));

            } 

            else {
                System.err.println("Opção inválida");
                return;
            }
        }

        catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return;
        }

        if (quantosProdutos < produtosCadastrados.length) {
            produtosCadastrados[quantosProdutos] = novoProduto;
            quantosProdutos++;
            System.out.println("Produto cadastrado com sucesso");
        } else {
            System.out.println("Erro: Vetor de produtos está cheio");
        }

    }

    /**
     * Salva os dados dos produtos cadastrados no arquivo csv informado.
     * Sobrescreve todo o conteúdo do arquivo.
     *
     * @param nomeArquivo Nome do arquivo a ser gravado.
     */
    public static void salvarProdutos(String nomeArquivo) {
        /*Você deve implementar aqui a lógica que abrirá um arquivo para escrita com o nome informado no
parâmetro, percorrerá um por um todos os produtos existentes no vetor de produtosCadastrados, gerando
uma linha de texto com os dados de cada objeto Produto, escrevendo-a no arquivo.*/

        try (PrintWriter escritor = new PrintWriter(new FileWriter(nomeArquivo))) {
        
        escritor.println(quantosProdutos);

        for (int i = 0; i < quantosProdutos; i++) {

            Produto p = produtosCadastrados[i];

            if (p != null) {
                escritor.println(p.gerarDadosTexto());
            }
        }
        System.out.println("Produtos salvos com sucesso!");
        
        } 
        
        catch (IOException e) {
            System.err.println("Erro ao salvar: " + e.getMessage());
        }

    }

    public static void main(String[] args) throws Exception {
        teclado = new Scanner(System.in, Charset.forName("ISO-8859-2"));
        nomeArquivoDados = "dadosProdutos.csv";
        produtosCadastrados = lerProdutos(nomeArquivoDados);
        int opcao = -1;
        System.out.println("DEBUG: Sistema carregou " + quantosProdutos + " produtos.");
        System.out.println("DEBUG: Tamanho do vetor: " + produtosCadastrados.length);
        do {
            opcao = menu();
            switch (opcao) {
                case 1 ->
                    listarTodosOsProdutos();
                case 2 ->
                    localizarProdutos();
                case 3 ->
                    cadastrarProduto();
            }
            pausa();
        } while (opcao != 0);
        salvarProdutos(nomeArquivoDados);
        teclado.close();
    }
}