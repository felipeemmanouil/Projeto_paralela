# Projeto_paralela
Projeto 1 de programação paralela

Implementação de programa que le um arquivo e verifica quais números presentes são primos

Para a implementação, primeiro foi feito o metodo de verificação dos números primos, ao qual verifica se o número é 1 ou dois, caso não, faz uma tratativa quanto a raiz do número, é um calculo de otimização, no qual pegasse a raiz do número, Verificasse numeros primos menores ou iguais ao valor da raiz, caso o número seja divisivel por algum desses primos, ele não é primo.

Após isso, começa a implementação para o paralelismo do código, com a criação da classe worker, a qual posteriormente vai ser responsavél por trabalhar com as partes divididas do arquivo. Este sendo dividido pelo número de threads definido pelo utilizador do código.

Basicamente o Worker recebera o bloco de arquivo no qual vai trabalhar, ira verificar primalidade dos números, ordenar assim como estava no arquivo, e devolver para então ir para um novo arquivo de saida. Posteriormente ao trabalho do Worker, escreve-se no arquivo e verifica-se o intervalo de processamento, para assim termos uma análise do tempo de processo com diferentes números de threads.

Gráfico com analise do tempo decorrido por Threads utilizadas localizado em grafico.png lembrando de considerar que tinham outros processos em atividade na máquina ao qual o estudo foi realizado, fazendo assim o melhor resultado ser com 7 threads. Ja hávia verificado anteriormente em outra oportunidade e se mostrou como sendo melhor com 15 threads.