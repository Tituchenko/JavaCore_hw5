package org.example;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;


import static java.nio.file.StandardCopyOption.*;

import static java.nio.file.LinkOption.*;


/*
1. Написать функцию, создающую резервную копию всех файлов в директории(без поддиректорий) во вновь созданную папку
./backup
2. Предположить, что числа в исходном массиве из 9 элементов имеют диапазон[0, 3], и представляют собой, например,
состояния ячеек поля для игры в крестикинолики, где 0 – это пустое поле, 1 – это поле с крестиком, 2 – это поле
с ноликом, 3 – резервное значение. Такое предположение позволит хранить в одном числе типа int всё поле 3х3.
Реализовать функционал с записью в файл и обратно игрового поля. Выводить в консоль игровое поле после импорта,
заменяя числа символами X, O, •(пусто)
 */
public class Main {
    public static void main(String[] args) {
        Backup("C:\\Users\\Илья\\Documents\\Учеба\\17. Java Core\\homework4\\JavaCore_hw4\\src\\main\\java\\org\\example");
        xoWrite(new int[]{0, 1, 1, 1, 0, 1, 2, 2, 0},"xo.txt");
        int[] xo=getXO("xo.txt");
        printXO(xo);
    }

    public static void Backup (String pathName) {
        String backupName="\\./backup";
        File folder = new File(pathName);
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles.length>0) {
            new File(pathName+backupName).mkdirs();
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    String source = pathName+"\\"+file.getName();
                    String dest = pathName+backupName+"\\"+file.getName();
                    try {
                        Path bytes = Files.copy(

                                new java.io.File(source).toPath(),

                                new java.io.File(dest).toPath(),

                                REPLACE_EXISTING,

                                COPY_ATTRIBUTES,

                                NOFOLLOW_LINKS);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        }
    }
    public static void xoWrite (int [] XO,String fileName){
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            for (int i = 0; i < 3; i++) {
                byte buff = 0;
                for (int j = 0; j < 3; j++) {
                    buff += (byte) (XO[3 * i + j] << (j * 2));
                    }
                fos.write(buff);
                }
            fos.flush();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static int[] getXO (String fileName){
        int[] result = new int[9];

        FileInputStream fis = null;
        int b;
        int i = 0;
        try {
            fis = new FileInputStream(fileName);
            while ((b = fis.read()) != -1) {
                for (int v = 0; v < 3; ++v) {
                    result[i++] = b >> (v * 2) & 0x3;
                }
            }
            fis.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        System.out.println(Arrays.toString(result));
        return result;

    }
    public static void printXO(int[] arr){
        for (int i=0;i<arr.length;i++) {

            switch (arr[i]){
                case 0:
                    System.out.print('.');
                    break;
                case 1:
                    System.out.print('X');
                    break;
                case 2:
                    System.out.print('0');
                    break;
            }
            if ((i+1)%3==0) System.out.println();

        }
    }
}