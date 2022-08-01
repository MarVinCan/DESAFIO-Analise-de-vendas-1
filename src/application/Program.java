package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import model.entities.Sale;

public class Program {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Entre o caminho do arquivo: ");
		String path = sc.nextLine();
		
		File file = new File(path);

		try (BufferedReader br = new BufferedReader(new FileReader(file))){
			
			String line = br.readLine();
			List<Sale> saleList = new ArrayList<>();
			
			while (line != null) {
				
				String[] split = line.split(",");
				
				Integer month = Integer.parseInt(split[0]);
				Integer year = Integer.parseInt(split[1]);
				String saller = split[2];
				Integer items = Integer.parseInt(split[3]);
				Double total = Double.parseDouble(split[4]);
				
				saleList.add(new Sale(month, year, saller, items, total));
				
				line =br.readLine();
			}
			
			
			System.out.println();
			System.out.println("Cinco primeiras vendas de 2016 de maior preço médio");
			saleList.stream().filter(s -> s.getYear() == 2016).
			sorted((s1, s2) -> s1.averagePrice().compareTo(s2.averagePrice()) * - 1)
			.limit(5)
			.forEach(s -> System.out.println(s));
			
			Double sum = saleList.stream().filter(s -> s.getSaller().equals("Logan"))
					.filter(s -> s.getMonth() == 1 || s.getMonth() == 7)
					.map(s -> s.getTotal())
					.reduce(0.0, (x , y) -> x + y);	
			
			
			System.out.println();
			System.out.printf("Valor total vendido pelo vendedor Logan nos meses 1 e 7 = %.2f",sum);
			
		} catch (FileNotFoundException e) {
			System.out.println("Erro: " + path +" (O sistema não pode encontrar o arquivo especificado)");
		} catch (IOException e) {
			System.out.println("Erro: " + e.getMessage());
		} finally {
			sc.close();
		}
		
	}

}
