package com.hyznrj.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import com.hyznrj.utils.CommandExplainer;

public class Main {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		Scanner scanner = new Scanner(System.in);
		String src = scanner.nextLine();
		CommandExplainer.run(src);
		scanner.close();
	}
}
