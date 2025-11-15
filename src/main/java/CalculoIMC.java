/*
The MIT License (MIT)

Copyright (c) 2016 Wolfgang Almeida <wolfgang.almeida@yahoo.com>

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

import java.util.Scanner;

/**
 * Programa de cálculo do Índice de Massa Corporal (IMC).
 *
 * <p>Código original clonado de: https://github.com/Wolfterro/Projetos-em-Java/tree/master/CalculoIMC</p>
 *
 * <p>Este código foi mantido em sua forma original para atender os requisitos do TP1,
 * que solicitam testar o código exatamente como foi disponibilizado no repositório.</p>
 *
 * <p>Para fins de testabilidade, foram criadas classes refatoradas no pacote com.saude.imc.*</p>
 *
 * @author Wolfgang Almeida (código original)
 * @author André Luis Becker (adaptação para testes)
 */
class CalculoIMC {
	/**
	 * Classifica o IMC em uma das 8 categorias.
	 *
	 * <p>Método original do repositório Wolfterro/Projetos-em-Java.</p>
	 *
	 * @param imc valor do Índice de Massa Corporal
	 * @return descrição da categoria correspondente
	 */
	public static String classificarIMC(double imc) {
		if (imc < 16.0) {
			return "Magreza grave";
		}
		else if (imc == 16.0 || imc < 17.0) {
			return "Magreza moderada";
		}
		else if (imc == 17.0 || imc < 18.5) {
			return "Magreza leve";
		}
		else if (imc == 18.5 || imc < 25.0) {
			return "Saudável";
		}
		else if (imc == 25.0 || imc < 30.0) {
			return "Sobrepeso";
		}
		else if (imc == 30.0 || imc < 35.0) {
			return "Obesidade Grau I";
		}
		else if (imc == 35.0 || imc < 40.0) {
			return "Obesidade Grau II";
		}
		else {
			return "Obesidade Grau III";
		}
	}

	/**
	 * Calcula o IMC com base no peso e altura fornecidos.
	 *
	 * <p>Método original do repositório Wolfterro/Projetos-em-Java.</p>
	 *
	 * @param peso peso em quilogramas
	 * @param altura altura em metros
	 * @return valor do IMC calculado
	 */
	public static double calcularPeso(double peso, double altura) {
		return peso / (altura * altura);
	}

	/**
	 * Programa principal que interage com o usuário via linha de comando.
	 *
	 * <p>Método original do repositório Wolfterro/Projetos-em-Java.</p>
	 *
	 * @param versao versão do programa
	 */
	public static void programaIMC(String versao) {
		double peso, altura, imc;
		Scanner pScan = new Scanner(System.in);
		Scanner aScan = new Scanner(System.in);

		System.out.println("===========================");
		System.out.printf("Cálculo do IMC - Versão %s\n", versao);
		System.out.println("===========================");

		System.out.print("Insira o seu peso em quilogramas: ");
		peso = Double.parseDouble(pScan.nextLine());

		System.out.print("Insira o sua altura em metros: ");
		altura = Double.parseDouble(aScan.nextLine());

		imc = calcularPeso(peso, altura);

		System.out.printf("\nSeu índice de massa corporal é: %.2f kg/m²\n", imc);
		System.out.printf("Classificação: %s.\n", classificarIMC(imc));
	}

	/**
	 * Método main - ponto de entrada do programa.
	 *
	 * <p>Para executar: <code>java CalculoIMC</code></p>
	 *
	 * @param args argumentos de linha de comando (não utilizados)
	 */
	public static void main(String[] args) {
		String versao = "1.0";
		programaIMC(versao);
	}
}
