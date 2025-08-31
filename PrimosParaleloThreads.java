import java.io.*;
import java.util.*;

public class PrimosParaleloThreads {

    // Função para verificar se um número é primo
    public static boolean ehPrimo(int numero) {
        if (numero < 2) return false;
        if (numero == 2) return true;
        if (numero % 2 == 0) return false;

        int limite = (int) Math.sqrt(numero);
        for (int i = 3; i <= limite; i += 2) {
            if (numero % i == 0) return false;
        }
        return true;
    }

    // Classe Worker: cada thread processa um pedaço do vetor
    static class Worker extends Thread {
        private final List<Integer> numeros;
        private final boolean[] resultados;
        private final int inicio;
        private final int fim;

        // Lista de trabalhadores
        public Worker(List<Integer> numeros, boolean[] resultados, int inicio, int fim) {
            this.numeros = numeros;
            this.resultados = resultados;
            this.inicio = inicio;
            this.fim = fim;
        }

        //Paralelismo, no qual chama o metodo ehPrimo para a lista de cada worker
        @Override
        public void run() {
            for (int i = inicio; i < fim; i++) {
                resultados[i] = ehPrimo(numeros.get(i));
            }
        }
    }

    public static void main(String[] args) {
        String arquivoEntrada = "entrada.txt";
        String arquivoSaida = "saida.txt";
        int numThreads = 1; // 🔹 aqui você define quantas threads quer usar

        try {
            // Lê os números do arquivo
            List<Integer> numeros = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(arquivoEntrada))) {
                String linha;
                while ((linha = br.readLine()) != null) {
                    linha = linha.trim();
                    if (!linha.isEmpty()) {
                        numeros.add(Integer.parseInt(linha));
                    }
                }
            }

            int n = numeros.size();
            boolean[] resultados = new boolean[n];

            // Divide os índices entre as threads
            Worker[] threads = new Worker[numThreads];
            int tamanhoBloco = (int) Math.ceil(n / (double) numThreads);

            for (int t = 0; t < numThreads; t++) {
                int inicio = t * tamanhoBloco;
                int fim = Math.min(inicio + tamanhoBloco, n);
                if (inicio < fim) {
                    threads[t] = new Worker(numeros, resultados, inicio, fim);
                    threads[t].start();
                }
            }

            // "Joinando" todas as threads 
            for (Worker thread : threads) {
                if (thread != null) {
                    thread.join();
                }
            }

            // Grava os primos no arquivo de saída, mantendo a ordem original
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivoSaida))) {
                for (int i = 0; i < n; i++) {
                    if (resultados[i]) {
                        bw.write(String.valueOf(numeros.get(i)));
                        bw.newLine();
                    }
                }
            }

            System.out.println("Processamento concluído! Veja o arquivo: " + arquivoSaida);

        } catch (IOException e) {
            System.err.println("Erro ao ler ou escrever arquivo: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Erro: O arquivo contém valores que não são inteiros.");
        } catch (InterruptedException e) {
            System.err.println("Erro: Thread interrompida.");
        }
    }
}
